package com.tuling.crypto;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

/**
 * @Author:zhgw
 * @Date:16:52,2023/11/3
 */
@Slf4j
public class LongAdderDemo {

  public static void main(String[] args) {
    compareLongAtomicAndAdder(10, 10000);
    compareLongAtomicAndAdder(20,10000);
    compareLongAtomicAndAdder(200,100000);
  }
  private static void compareLongAtomicAndAdder(int threadNum, int add) {
    try {
      longAdderAdd(threadNum, add);
      atomicLongAdd(threadNum, add);
    }catch (InterruptedException e) {
      log.error(e.getMessage());
    }

  }

  private static void longAdderAdd(int threadNum, int add) throws InterruptedException {
    CountDownLatch countDownLatch = new CountDownLatch(threadNum);
    AtomicLong atomicLong = new AtomicLong();
    Long time = System.currentTimeMillis();
    for (int i = 0; i < threadNum; i++) {
      new Thread(() -> {
        for (int j = 0; j < add; j++) {
          atomicLong.incrementAndGet();
        }
        countDownLatch.countDown();
      }).start();
    }
    countDownLatch.await();
    log.info("LongAdder add value cost {} milli second!, the fianl value: {}",(System.currentTimeMillis()-time), atomicLong.get());
  }

  private static void atomicLongAdd(int threadNum, int add) throws InterruptedException {
    CountDownLatch countDownLatch = new CountDownLatch(threadNum);
    LongAdder longAdder = new LongAdder();
    Long time = System.currentTimeMillis();
    for (int i = 0; i < threadNum; i++) {
      new Thread(() -> {
        for (int j = 0; j < add; j++) {
          longAdder.increment();
        }
        countDownLatch.countDown();
      }).start();
    }
    countDownLatch.await();
    log.info("AtomicLong add value cost {} milli second!, the fianl value: {}",(System.currentTimeMillis()-time), longAdder);
  }

}
