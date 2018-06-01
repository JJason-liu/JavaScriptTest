package com.jason.vertx;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.jason.Interface.IScript;
import com.jason.data.MyData;
import com.jason.manager.ScriptManager;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;

public class TestCase extends AbstractVerticle {

	@Override
	public void start(Future<Void> startFuture) throws Exception {
		ScheduledExecutorService newScheduledThreadPool = Executors.newScheduledThreadPool(1);
		newScheduledThreadPool.scheduleAtFixedRate(new Runnable() {

			@Override
			public void run() {
				List<IScript> pool = ScriptManager.getInstance().getPool();
				for (IScript iScript : pool) {
					if (iScript instanceof MyData) {
						MyData data = (MyData) iScript;
						System.out.println("this is a " + data.getName() + data.getId() + data.getAge());
					}
				}

			}
		}, 5, 20, TimeUnit.SECONDS);
	}
}
