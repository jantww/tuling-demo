package com.tuling.crypto;

import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.tuling.crypto.entity.OrderEvent;
import lombok.extern.slf4j.Slf4j;

import javax.xml.stream.XMLEventFactory;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * @Author:zhgw
 * @Date:11:37,2023/12/1
 */
@Slf4j
public class Disccruptor {
  public static void main(String[] args) {
    Disruptor disruptor = new Disruptor(new OrderEventFactory(),
            8,
            Executors.defaultThreadFactory(),
            ProducerType.SINGLE,
            new YieldingWaitStrategy());
    disruptor.handleEventsWith(new OrderEventHandler());
//    disruptor.handleEventsWithWorkerPool(new OrderEventHandler(), new OrderEventHandler());
//    disruptor.handleEventsWith(new OrderEventHandler(), new OrderEventHandler());
    disruptor.start();
    RingBuffer<OrderEvent> ringBuffer = disruptor.getRingBuffer();
    OrderEventProducer producer = new OrderEventProducer(ringBuffer);
    for (int i = 0; i < 20; i++) {
      producer.produce(Long.valueOf(i), String.format("test%s", i));
    }
//    new Thread(() -> {
//      OrderEventProducer producer = new OrderEventProducer(ringBuffer);
//
//      for (int i = 0; i < 100; i++) {
//        producer.produce(Long.valueOf(i), Thread.currentThread().getName() + " test" + i);
//      }
//    },"p1").start();
//
//    new Thread(() -> {
//      OrderEventProducer producer = new OrderEventProducer(ringBuffer);
//
//      for (int i = 0; i < 100; i++) {
//        producer.produce(Long.valueOf(i), Thread.currentThread().getName() + " test" + i);
//      }
//    },"p2").start();
    disruptor.shutdown();
  }
  static class OrderEventFactory implements EventFactory<OrderEvent> {
    @Override
    public OrderEvent newInstance() {
      return new OrderEvent();
    }
  }

  static class OrderEventProducer {
    private RingBuffer<OrderEvent> ringBuffer;

    public OrderEventProducer(RingBuffer<OrderEvent> ringBuffer) {
      this.ringBuffer = ringBuffer;
    }

    public void produce(Long id, String name) {
      long sequence = ringBuffer.next();
      OrderEvent event = ringBuffer.get(sequence);
      event.setId(id);
      event.setName(name);
      ringBuffer.publish(sequence);
    }
  }

  static class OrderEventHandler implements EventHandler<OrderEvent> {

    @Override
    public void onEvent(OrderEvent orderEvent, long l, boolean b) throws Exception {
      TimeUnit.MILLISECONDS.sleep(500);
      System.out.println(String.format("eventHandler catch the order event id : %s; name : %s.", orderEvent.getId(), orderEvent.getName()));
    }

  }



}
