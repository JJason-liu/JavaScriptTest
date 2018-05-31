package com.jason.vertx;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;

public class PollVerticle extends AbstractVerticle {

	public static final Logger log = LogManager.getLogger(PollVerticle.class);
	public static int count = 1;

	@Override
	public void start() throws Exception {
		// final MongoClient mongo = MongoClient.createShared(vertx, new
		// JsonObject().put("db_name", "lenovodb").put("connection_string",
		// "mongodb://192.168.56.201:27017"));

		EventBus eb = vertx.eventBus();
		// 监听 pushservice-poll 消息
		eb.consumer("pushservice-poll", message -> {
			String msg = (String) message.body();
			log.info("received message: " + msg);
			JsonObject user = new JsonObject().put("id", count).put("name", "vertx" + (count++)).put("age", "333")
					.put("mobile", "no");
			log.info("插入日志成功！！！" + "内容：" + user);
			// // 插入用户信息
			// final MongoClient mongo = MongoClient.createShared(vertx,
			// config());
			// mongo.insert("users", user, lookup -> {
			// if (lookup.failed()) {
			// log.error("insert user error: " + lookup.cause());
			// return;
			// }
			// });
		});
	}

}
