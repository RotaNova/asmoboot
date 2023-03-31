package com.rotanava.framework.config;


import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.annotation.Resource;


@Component
public class TransactionService {
    private ThreadLocal<TransactionStatus> transactionThread = new ThreadLocal<>();


    public DataSourceTransactionManager getTransactionManager() {
        return transactionManager;
    }

    /**
     * 获取数据源事物管理器
     */
    @Resource(name = "transactionManager")
    private DataSourceTransactionManager transactionManager;

    /**
     * 开启事物
     */
    public TransactionStatus begin() {
        TransactionStatus transaction = transactionManager.getTransaction(new DefaultTransactionDefinition());
        transactionThread.set(transaction);
        return transaction;
    }

    /**
     * 开启事物
     */
    public TransactionStatus begin(TransactionDefinition transactionDefinition) {
        TransactionStatus transaction = transactionManager.getTransaction(transactionDefinition);
        transactionThread.set(transaction);
        return transaction;
    }

    /**
     * 提交事物
     */
    public void commit(TransactionStatus transaction) {
        transactionManager.commit(transaction);
        remove();
    }

    /**
     * 提交事物
     */
    public void commit() {
        TransactionStatus transactionStatus = get();
        if (transactionStatus!=null) {
            transactionManager.commit(transactionStatus);
        }
        remove();
    }

    /**
     * 回滚事物
     */
    public void rollback(TransactionStatus transaction) {
        transactionManager.rollback(transaction);
        remove();
    }

    /**
     * 回滚事物
     */
    public void rollback() {
        TransactionStatus transactionStatus = get();
        if (transactionStatus!=null) {
            transactionManager.rollback(transactionStatus);
        }
        remove();
    }

    private TransactionStatus get() {
        try {
            TransactionStatus transactionStatus = transactionThread.get();
            if (transactionStatus!=null){
                transactionThread.remove();
            }
            return transactionStatus;
        } catch (Exception e){
            transactionThread.remove();
        }
        return null;
    }

    public void remove() {
        transactionThread.remove();
    }

}