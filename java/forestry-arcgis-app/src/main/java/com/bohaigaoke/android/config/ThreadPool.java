package com.bohaigaoke.android.config;

import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPool {

	/**
	 * 任务队列
	 */
	static BlockingQueue<Runnable> queue = new ArrayBlockingQueue<Runnable>(5);
	/**
	 * 线程池 CorePoolSize=3;MaxPoolSize=3;KeepAliveTime=10s
	 */
	static AbstractExecutorService pool = new ThreadPoolExecutor(3, 3, 10L,
			TimeUnit.SECONDS, queue,
			new ThreadPoolExecutor.DiscardOldestPolicy());

	private static ThreadPool instance = null;

	private ThreadPool() {
	}

	public static ThreadPool getInstance() {
		if (instance == null) {
			instance = new ThreadPool();
		}
		return instance;
	}

	public void execute(Runnable runnable) {
		pool.execute(runnable);
	}

	/**
	 * 关闭，并等待任务执行完成，不接受新任务
	 */
	public static void shutdown() {
		if (pool != null) {
			pool.shutdown();
		}
	}

	/**
	 * 立即关闭，并挂起所有正在执行的线程，不接受新任务
	 */
	public static void shutdownRightnow() {
		if (pool != null) {

			// List<Runnable> tasks =pool.shutdownNow();
			// for(Runnable task:tasks){
			// task.
			// }

			pool.shutdownNow();
			try {
				pool.awaitTermination(1, TimeUnit.MICROSECONDS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static void removeTaskFromQueue(Runnable runnable) {
		queue.remove(runnable);
	}
}
