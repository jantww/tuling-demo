package com.tuling.crypto;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Author:zhgw
 * @Date:10:18,2023/11/13
 */
@Slf4j
public class ReenTrantReadAndWriteDemo {
    private static ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
    private static ReentrantReadWriteLock.ReadLock rl = rwl.readLock();
    private static ReentrantReadWriteLock.WriteLock wl = rwl.writeLock();
    private static Map<String, Object> map = new HashMap<>();

    private Object get(String key) {
        try {
            rl.lock();
            return map.get(key);
        }finally {
            rl.unlock();
        }

    }

    private void put(String key, Object value) {
        try {
            wl.lock();
            map.put(key, value);
        }finally {
            wl.unlock();
        }
    }

    private void clear() {
        wl.lock();
        try {
            map.clear();
        }finally {
            wl.unlock();
        }
    }

    private static Object lockDownForReadAndWrite(String key, Object value) {
        boolean isRead = value == null;
        rl.lock();
        try {
            if(isRead) {
                Object o = map.get(key);
                return o;
            }
        }finally {
            rl.unlock();
        }
        if(!isRead) {
            wl.lock();
            try {
                map.put(key, value);
                return value;
            }catch (Exception ex) {
                throw  ex;
            }finally {
                wl.unlock();
                rl.lock();
            }
        }
        return null;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            map.put(i+"", new Object());
        }

//        for (int i = 0; i < 1; i++) {
//            int finalI = i;
//            new Thread(()-> {
//                System.out.println(lockDownForReadAndWrite(finalI +"", null));
//            }).start();
//        }
        for (int i = 0; i < 1; i++) {
            int finalI = i;
            new Thread(()-> {
                lockDownForReadAndWrite(finalI +"", new Object());
            }).start();
        }

    }
}
