package com.jason.vertx;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jason.manager.ScriptManager;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;

/**
 * 设置监听，请求路由，接收请求然后发送消息到PollVertical
 * 
 * @author 有泪的北极星 qq: 76598166
 * @date 2018年5月30日 下午2:07:28
 */
public class RootVerticle extends AbstractVerticle {

	public static final Logger log = LogManager.getLogger(RootVerticle.class);

	@Override
	public void start(Future<Void> future) throws Exception {
		final HttpServer server = vertx.createHttpServer();

		Router router = Router.router(vertx);

		// 监听并返回
		router.route("/").handler(routingContext -> {
			HttpServerResponse response = routingContext.response();
			response.putHeader("content-type", "text/html").end("<h2>Hello from vertx cluster.</h2>");
		});

		// 正则式拦截网址，
		// eg： http://localhost:8900/pushservice/aa////appfeedback?lpsst=12
		router.routeWithRegex("/pushservice/*.*/poll*").handler(routingContext -> {
			HttpServerResponse response = routingContext.response();
			boolean hastype = routingContext.request().params().contains("type");
			if (hastype) {
				// 使用eventbus发送消息
				EventBus eb = vertx.eventBus();
				// public to everyone
				// eb.publish("pushservice-poll", "datareport");
				// send to onlyone
				eb.send("pushservice-poll", "datareport");
			} else {
				//
			}
			response.putHeader("content-type", "application/json")
					.end(new JsonObject().put("code", 200).put("status", "ok").toString());
		});

		// 拦截GM ***********************8
		router.routeWithRegex("/refreshScript").handler(routingContext -> {
			HttpServerResponse response = routingContext.response();
			try {
				ScriptManager.getInstance().loadJavaFile("D:\\MyEclipsTestProgame\\JavaScriptTest");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			response.putHeader("content-type", "text/html").end("<h2>receive GM request!!!</h2>");
		});
		router.route("/exit").handler(routingContext -> {
			HttpServerResponse response = routingContext.response();
			response.putHeader("content-type", "text/html").end("<h2>exit successs!!!</h2>");
			System.exit(0);
		});

		// ::代表这里是引用router的accept方法
		server.requestHandler(router::accept);
		server.listen(config().getInteger("http.port", 8900), result -> {
			if (result.succeeded()) {
				future.complete();
			} else {
				future.fail(result.cause());
			}
		});
		log.info("httpserver listenning on port : " + config().getInteger("http.port", 8900));
	}

}
