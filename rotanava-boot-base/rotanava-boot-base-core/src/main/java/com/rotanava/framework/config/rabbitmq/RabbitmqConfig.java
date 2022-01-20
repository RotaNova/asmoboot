package com.rotanava.framework.config.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.RetryInterceptorBuilder;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryListener;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @description: rabbitmq 配置
 * @author: jintengzhou
 * @date: 2021-03-23 9:45
 */
@Slf4j
@Configuration
public class RabbitmqConfig {

    /**
     * 路由键
     */
    public static final String ROUTING_KEY = "ROUTING_KEY";

    /**
     * 队列
     */
    public static final String EXCHANGE = "EXCHANGE";

    /**
     * 队列
     */
    public static final String MSG_BODY = "msg_body";

    /**
     * 延迟队列，把消息放在这里会自动过期
     */
    public static final String DELAY_QUEUE = "rotanava-boot.delay.queue";

    /**
     * 延迟队列交换机
     */
    public static final String DELAY_EXCHANGE = "rotanava-boot.delay.exchange";

    /**
     * 延迟队列路由
     */
    public static final String DELAY_ROUTINGKEY = "rotanava-boot.delay.routingKey";


    /**
     * 死信队列
     */
    public static final String DEAD_LETTER_QUEUE = "rotanava-boot.dlx.queue";

    /**
     * 死信交换机
     */
    private static final String DEAD_LETTER_EXCHANGE = "rotanava-boot.dlx.exchange";

    /**
     * 死信队列路由
     */
    private static final String DEAD_LETTER_ROUTINGKEY = "rotanava-boot.dlx.routingKey";


    @Bean(name = "rabbitListenerContainerFactory")
    @ConditionalOnProperty(prefix = "spring.rabbitmq.listener", name = "type", havingValue = "simple", matchIfMissing = true)
    public SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory(SimpleRabbitListenerContainerFactoryConfigurer configurer,
                                                                                     ConnectionFactory connectionFactory, RabbitTemplate rabbitTemplate) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        //消息接收之前加拦截处理，每次接收消息都会调用
        factory.setAfterReceivePostProcessors(message -> {
            log.info("接收到rabbitmq消息={}", message.toString());
            return message;
        });


        configurer.configure(factory, connectionFactory);

        // 并发消费者数量
        final int availableProcessors = Runtime.getRuntime().availableProcessors();
        factory.setConcurrentConsumers(Math.max(availableProcessors, 1));
        factory.setMaxConcurrentConsumers(Math.max(availableProcessors * 2, 2));

        factory.setAdviceChain(RetryInterceptorBuilder
                .stateless()
                //异常重试次数消耗完后的恢复器 发送到指定队列
                .recoverer(new ExceptionMessageRecoverer(rabbitTemplate, new RabbitAdmin(connectionFactory), "bootBase-exceptionDeadQueue"))
                .retryOperations(rabbitRetryTemplate())
                .build());
        return factory;
    }

    @Bean
    public RetryTemplate rabbitRetryTemplate() {
        RetryTemplate retryTemplate = new RetryTemplate();

        retryTemplate.registerListener(new RetryListener() {
            @Override
            public <T, E extends Throwable> boolean open(RetryContext retryContext, RetryCallback<T, E> retryCallback) {
                // 执行之前调用 （返回false时会终止执行）
                return true;
            }

            @Override
            public <T, E extends Throwable> void close(RetryContext retryContext, RetryCallback<T, E> retryCallback, Throwable throwable) {
                // 重试结束的时候调用 （最后一次重试 ）
//                log.error("-----重试结束调用{}", retryContext.getRetryCount());
            }

            @Override
            public <T, E extends Throwable> void onError(RetryContext retryContext, RetryCallback<T, E> retryCallback, Throwable throwable) {
                //  异常 都会调用
                log.error("mq函数处理错误", throwable);
            }
        });

        retryTemplate.setBackOffPolicy(backOffPolicyByProperties());
        retryTemplate.setRetryPolicy(new SimpleRetryPolicy(3));
        return retryTemplate;
    }

    /**
     * 功能: rabbitmq 重试机制配置
     * 作者: zjt
     * 日期: 2020/12/17 11:03
     * 版本: 1.0
     */
    @Bean
    public ExponentialBackOffPolicy backOffPolicyByProperties() {
        ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
        // 重试间隔
        backOffPolicy.setInitialInterval(3 * 1000);
        // 重试最大间隔
        backOffPolicy.setMaxInterval(20 * 1000);
        // 重试间隔乘法策略
        backOffPolicy.setMultiplier(2);
        return backOffPolicy;
    }


    @Bean
    public RabbitTemplate rabbitTemplate(CachingConnectionFactory connectionFactory) {
        connectionFactory.setPublisherConfirmType(CachingConnectionFactory.ConfirmType.CORRELATED);
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        // 当交换机无法正确投递消息的时候，RabbitMQ会调用Basic.Return命令将消息返回给生产者
        rabbitTemplate.setMandatory(true);
        return rabbitTemplate;
    }


    /**
     * @return FanoutExchange
     */
    @Bean
    public FanoutExchange delayExchange() {
        return new FanoutExchange(DELAY_EXCHANGE);
    }


    /**
     * 延迟队列配置
     * <p>
     * 1、params.put("x-message-ttl", 5 * 1000);
     * 第一种方式是直接设置 Queue 延迟时间 但如果直接给队列设置过期时间,这种做法不是很灵活,（当然二者是兼容的,默认是时间小的优先）
     * 2、rabbitTemplate.convertAndSend(book, message -> {
     * message.getMessageProperties().setExpiration(2 * 1000 + "");
     * return message;
     * });
     * 第二种就是每次发送消息动态设置延迟时间,这样我们可以灵活控制
     **/
    @Bean
    public Queue delayQueue() {
        Map<String, Object> args = new HashMap<>();
        // x-dead-letter-exchange 声明了队列里的死信转发到的DLX名称，
        args.put("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE);
        // x-dead-letter-routing-key 声明了这些死信在转发时携带的 routing-key 名称。
        args.put("x-dead-letter-routing-key", DEAD_LETTER_ROUTINGKEY);
        return new Queue(DELAY_QUEUE, true, false, false, args);
    }

    @Bean
    public Binding delayBinding() {
        return BindingBuilder.bind(delayQueue()).to(delayExchange());
    }


    @Bean
    public Queue dlxQueue() {
        return new Queue(DEAD_LETTER_QUEUE, true);
    }

    /**
     *
     **/
    @Bean
    public DirectExchange dlxExchange() {
        return new DirectExchange(DEAD_LETTER_EXCHANGE);
    }

    @Bean
    public Binding dlxBinding() {
        return BindingBuilder.bind(dlxQueue()).to(dlxExchange()).with(DEAD_LETTER_ROUTINGKEY);
    }


}