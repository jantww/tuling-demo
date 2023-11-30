package com.tuling.crypto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @Author:zhgw
 * @Date:15:30,2023/11/3
 */
@Slf4j
public class AtomicReferenceDemo {

    @Data
    @AllArgsConstructor
    static class User {
        private String anme;
        private int age;
    }

    public static void main(String[] args) {
        User zs = new User("zhangsan", 18);
        User ls = new User("lisi", 20);
        AtomicReference<User> atomicReference = new AtomicReference<>(zs);
        log.info("Originally, the atmoic user is :{}", atomicReference.get());
        if(atomicReference.compareAndSet(zs,ls)) {
            log.info("cas change user value successfully, now the user is :{}", atomicReference.get());
        }
    }
}
