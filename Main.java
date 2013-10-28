package ru.ya.lockfreetest;

import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
	private final AtomicInteger seed;

    public Main() {
        seed = new AtomicInteger(0);
    }

    public BigInteger next() {
        for (;;) {
            int n = seed.get();
            int nextn = n + 1;
            if (seed.compareAndSet(n, nextn))
                return calculateNext(n);
        }
	}
    
    private BigInteger calculateNext(int n) {
    	if(n == 0)
    		return BigInteger.ZERO;
    	if(n == 1 || n == 2)
    		return BigInteger.ONE;
    	int i = 2;
    	BigInteger fib1 = BigInteger.ONE, fib2 = BigInteger.ONE, fibSumm = fib1.add(fib2);
    	while(i++ < n) {
    		fibSumm = fib1.add(fib2);
    		fib1 = fib2;
    		fib2 = fibSumm;
    	}
    	return fibSumm;
    }
    

    public static void main(String[] args) {
    	int  i = 0;
    	final Main m = new Main();
    	while(i++ < 10) {
    		Thread t = new Thread(new Runnable() {
				@Override
				public void run() {
					System.out.println(Thread.currentThread().getName() + " - " + m.next());
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
					}
					System.out.println(Thread.currentThread().getName() + " - " + m.next());
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
					}
					System.out.println(Thread.currentThread().getName() + " - " + m.next());
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
					}
				}
			}, "thread_" + i);
    		t.start();
    	}
	}
}
