package com.lock.biz;

import org.springframework.stereotype.Service;

@Service
public class LockBiz {

    /*

     */

    /**
     * 同步示例方法，锁的对象是当前示例（this）
     * 作用范围：整个方法。
     */
    public synchronized void syncMethod() {
        System.out.println("=== 同步方法 ===");
    }

    /**
     * 同步静态方法，锁的对象是当前类的Class对象
     * 作用范围：整个类
     */
    public synchronized static void syncStaticMethod() {
        System.out.println("=== 同步静态方法 ===");
    }

    /**
     * 同步方法，锁的对象是当前示例（this）
     * 锁的范围：方法内部
     */
    public void syncMethod2() {
        synchronized (this) {
            System.out.println("=== 同步方法2 ===");
        }
    }

    /**
     * 同步方法，锁的对象是自定义的锁对象
     * 锁的范围：方法内部
     */
    private final Object lock = new Object();
    public void methodUsingLock() {
        synchronized (lock) {
            System.out.println("=== 使用锁对象 ===");
        }
    }


    /**
     * 同步静态方法，锁的对象是自定义的锁对象
     * 锁的范围：方法内部
     */
    public static void methodUsingLock2() {
        synchronized (LockBiz.class) {
            System.out.println("=== 使用锁对象2 ===");
        }
    }
}
