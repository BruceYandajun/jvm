package com.letv.shop.jvm;

/**
 * 线程死锁等待演示
 */
public class DeadLockTest implements Runnable {
	int a, b;

	public DeadLockTest(int a, int b) {
		this.a = a;
		this.b = b;
	}

	@Override
	public void run() {
		synchronized (Integer.valueOf(a)) {
			synchronized (Integer.valueOf(b)) {
				System.out.println(a + b);
			}
		}
	}

	public static void main(String[] args) {
		for (int i = 0; i < 100; i++) {
			new Thread(new DeadLockTest(1, 2)).start();
			new Thread(new DeadLockTest(2, 1)).start();
		}
	}
}
