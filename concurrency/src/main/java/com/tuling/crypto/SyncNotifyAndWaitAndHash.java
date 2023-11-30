package com.tuling.crypto;

import lombok.extern.slf4j.Slf4j;
import org.openjdk.jol.info.ClassLayout;

/**
 * @Author:zhgw
 * @Date:17:46,2023/11/7
 */
@Slf4j
public class SyncNotifyAndWaitAndHash {
  public static void main(String[] args) {
    Object lock = new Object();
    lock.hashCode();
//    lock.notify();
    log.info("synchronized information when has code or notify:");
    System.out.println(ClassLayout.parseInstance(lock).toPrintable());
    synchronized (lock) {
      try {
        lock.wait(1);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    log.info("wait lock information: {}", ClassLayout.parseInstance(lock).toPrintable());

  }
}
