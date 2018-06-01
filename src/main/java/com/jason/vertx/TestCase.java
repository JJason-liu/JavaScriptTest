package com.jason.vertx;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;

public class TestCase extends AbstractVerticle {

	@Override
	public void start(Future<Void> startFuture) throws Exception {
		ScheduledExecutorService newScheduledThreadPool = Executors.newScheduledThreadPool(1);
		newScheduledThreadPool.scheduleAtFixedRate(new Runnable() {

			@Override
			public void run() {
				System.out.println("this is a " + 9527);

			}
		}, 5, 10, TimeUnit.SECONDS);
	}
}
