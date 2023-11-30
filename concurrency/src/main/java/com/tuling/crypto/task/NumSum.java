package com.tuling.crypto.task;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * @Author:zhgw
 * @Date:18:00,2023/11/21
 */
@Slf4j
public class NumSum extends RecursiveTask<Integer> {

    private static final Integer DIVIDE_COUNT = 100000;

    private Integer[] arr;
    private int low;
    private int high;

    public NumSum(Integer[] arr, int low, int high) {
        this.arr = arr;
        this.low = low;
        this.high = high;
    }

    @SneakyThrows
    @Override
    protected Integer compute() {
        if((high - low) <= DIVIDE_COUNT) {
            Integer result = 0;
            for (int i = low; i < high; i++) {
                result += arr[i];
            }
            return result;
        }else {
            int mid = (high+low)/2;
            ForkJoinTask<Integer> leftTask = new NumSum(arr, low, mid);
            ForkJoinTask<Integer> rightTask = new NumSum(arr, mid, high);
            leftTask.fork();
            rightTask.fork();
            Integer left = leftTask.get();
            Integer right = rightTask.get();
            return left + right;
        }

    }
}
