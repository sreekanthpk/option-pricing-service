package com.sree.forkjoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ForkJoinRecursiveSum extends RecursiveTask<Integer> {
    public static final int SEQUENTIAL_THRESHOLD = 10;

    private int lo, hi;
    private int[] arr;

    public ForkJoinRecursiveSum(int[] arr, int lo, int hi) {
        this.lo = lo;
        this.hi = hi;
        this.arr = arr;
    }

    @Override
    public Integer compute() {
        if (hi - lo <= SEQUENTIAL_THRESHOLD) {
            int ans = 0;
            for (int i = lo; i < hi; i++) {
                ans += arr[i];
            }
            return ans;
        } else {
            int mid = (lo + hi) / 2;
            ForkJoinRecursiveSum left = new ForkJoinRecursiveSum(arr, lo, mid);
            ForkJoinRecursiveSum right = new ForkJoinRecursiveSum(arr, mid, hi);
            left.fork();
            int rightAns = right.compute();
            int leftAns = left.join();
            return leftAns + rightAns;
        }
    }


    private static final ForkJoinPool fjPool = new ForkJoinPool();


    public static int sum(int[] arr) throws InterruptedException {
        return fjPool.invoke(new ForkJoinRecursiveSum(arr, 0, arr.length));
    }

    public static void main(String[] args) throws InterruptedException {
        int[] arr = new int[100];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = i;
        }
        int sum = sum(arr);
        System.out.println("Sum: " + sum);
    }

}