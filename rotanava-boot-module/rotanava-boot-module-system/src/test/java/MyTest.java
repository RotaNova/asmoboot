import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.lang.tree.parser.NodeParser;
import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import com.google.common.util.concurrent.RateLimiter;
import com.rotanava.boot.system.api.module.system.bo.SysDepartment;
import org.junit.Test;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * @description:
 * @author: jintengzhou
 * @date: 2021-03-05 17:28
 */
public class MyTest {
    
    @Test
    public void loginTest() throws InterruptedException, Exception {
        String imgUrl = "https://img2.baidu.com/it/u=859882613,1564073189&fm=26&fmt=auto";
        
        URL url = new URL(imgUrl);
        final BufferedImage bufferedImage = ImageIO.read(url);
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "jpg", byteArrayOutputStream);
        byteArrayOutputStream.toByteArray();
    }
    
    @Test
    public void testTree() {
        
        TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
        treeNodeConfig.setParentIdKey("parentDeptId");
        treeNodeConfig.setWeightKey("deptOrder");
        
        
        final SysDepartment sysDepartment = new SysDepartment();
        sysDepartment.setId(1);
        sysDepartment.setParentDeptId(0);
        sysDepartment.setDeptOrder(3);
        
        final SysDepartment sysDepartment1 = new SysDepartment();
        sysDepartment1.setId(2);
        sysDepartment1.setParentDeptId(1);
        sysDepartment1.setDeptOrder(1);
        
        
        final SysDepartment sysDepartment2 = new SysDepartment();
        sysDepartment2.setId(3);
        sysDepartment2.setParentDeptId(0);
        sysDepartment2.setDeptOrder(2);
        
        final List<SysDepartment> sysDepartments = Lists.newArrayList(sysDepartment, sysDepartment1, sysDepartment2);
        
        final List<Tree<Integer>> build = TreeUtil.build(sysDepartments, null, new NodeParser<SysDepartment, Integer>() {
            @Override
            public void parse(SysDepartment sysDepartment, Tree<Integer> treeNode) {
                treeNode.setId(sysDepartment.getId());
                treeNode.setParentId(sysDepartment.getParentDeptId());
                treeNode.setName(sysDepartment.getDeptName());
                treeNode.setWeight(sysDepartment.getDeptOrder());
            }
        });
        
        System.out.println("build = " + build);
    }
    
    @Test
    public void test3() throws Exception {
        Config config = new Config();
        config.useSingleServer().setTimeout(1000000).setAddress("redis://127.0.0.1:6379");
        final RedissonClient redissonClient = Redisson.create();
        
        final RLock lock = redissonClient.getLock("");
        
        lock.unlockAsync();
    }
    
    @Test
    public void test4() {
        final RateLimiter rateLimiter = RateLimiter.create(2);
//        rateLimiter.tryAcquire();
        Queues.newLinkedBlockingDeque();
        LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(2));
        System.out.println("true = " + true);
    }
    
    public static void main(String[] args) throws InterruptedException {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://192.168.0.226:6380").setPassword("DoDRXCvBXu").setDatabase(5).setConnectionMinimumIdleSize(10);
        //创建Redisson客户端
        final RedissonClient redissonClient = Redisson.create(config);
        
        redissonClient.getLock("dasd2");
        
        final RRateLimiter rateLimiter = redissonClient.getRateLimiter("testRateLimiter1");
        final boolean trySetRate = rateLimiter.trySetRate(RateType.OVERALL, 10, 1, RateIntervalUnit.SECONDS);
        System.out.println("trySetRate = " + trySetRate);
        
        
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 1000; i++) {
            executorService.submit(() -> {
                try {
                    rateLimiter.acquire();
                    System.out.println("线程" + Thread.currentThread().getId() + "进入数据区：" + System.currentTimeMillis());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        
        executorService.awaitTermination(9999, TimeUnit.SECONDS);
    }
    
}