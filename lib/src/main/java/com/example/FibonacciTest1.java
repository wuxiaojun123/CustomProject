package com.example;

/**
 * 斐波纳契数列(又称黄金分割数列)
 * f(0) = 0;f(1) = 1;f(n) = f(n-1)+f(n-2)
 */
public class FibonacciTest1 {

    public static void main(String[] args) {
        System.out.println("打印出来的数据是：" + fibonacci(5));
    }

    public static int fibonacci(int number) {
        if (number <= 1) {
            return number;
        }
        int a = fibonacci(number - 1) + fibonacci(number - 2);
        return a;
    }

}
