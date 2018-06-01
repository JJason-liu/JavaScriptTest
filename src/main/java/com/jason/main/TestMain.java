package com.jason.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.hazelcast.util.function.Consumer;
import com.jason.exitnotify.ServerStopNotify;
import com.jason.utils.StringUtil;
import com.jason.vertx.optionsReader;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

/**
 * 
 * @author 有泪的北极星 qq: 76598166
 * @date 2018年5月31日 上午10:28:02
 */
public class TestMain {

	private static final Logger log = LogManager.getLogger(TestMain.class);

	public static void main(String[] args) throws Exception {
		TestMain test = new TestMain();
		test.initCluster();
		test.listenExit();
	}

	public void listenExit() {

		// 启动钩子函数
		Runtime.getRuntime().addShutdownHook(new Thread(new ServerStopNotify()));

		// 启动监听
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
					try {
						String readLine = bufferedReader.readLine().trim();
						if (!StringUtil.isEmpty(readLine)) {
							if ("exit".equals(readLine)) {
								System.exit(0);
							}
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

			}
		}).start();
	}

	/**
	 * 初始化vertx集群
	 * 
	 * @throws Exception
	 */
	public void initCluster() throws Exception {
		final VertxOptions options = new VertxOptions();
		// 设置参数，启用集群
		options.setClustered(true);

		DeploymentOptions readOpts = optionsReader.readOpts();

		Consumer<Vertx> runner = vertx -> {
			vertx.deployVerticle("com.jason.vertx.RootVerticle", readOpts);
			vertx.deployVerticle("com.jason.vertx.PollVerticle", readOpts);
			vertx.deployVerticle("com.jason.vertx.TestCase", readOpts);
		};

		if (options.isClustered()) {
			Vertx.clusteredVertx(options, result -> {

				if (result.succeeded()) {
					Vertx vertx = result.result();
					runner.accept(vertx);
					log.error("**********************************************");
					log.error("**************** ^_^ 服务器启动成功  ^_^ **********");
					log.error("**********************************************");
				} else {
					log.error("cluster running with error: " + result.cause().getMessage());
				}

			});
		} else {
			Vertx vertx = Vertx.vertx(options);
			runner.accept(vertx);
		}
	}
}
