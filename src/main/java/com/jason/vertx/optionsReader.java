package com.jason.vertx;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.VertxOptions;
import io.vertx.core.json.JsonObject;

/**
 * 获取集群和部署配置类属性
 * 
 * @author 有泪的北极星 qq: 76598166
 * @date 2018年5月30日 上午11:44:19
 */
public class optionsReader {
	private static final String VX_PREFIX = "vertx.";
	private static ResourceLoader loader = ResourceLoader.getInstance();

	public static VertxOptions readOpts(final String name) throws Exception {
		final VertxOptions options = new VertxOptions();
		options.setEventLoopPoolSize((int) loader.getValue(VX_PREFIX + name + ".pool.size.event.loop", Integer.TYPE));
		options.setWorkerPoolSize((int) loader.getValue(VX_PREFIX + name + ".pool.size.worker", Integer.class));
		options.setInternalBlockingPoolSize(
				(int) loader.getValue(VX_PREFIX + name + ".pool.size.internal.blocking", Integer.class));

		options.setClustered((boolean) loader.getValue(VX_PREFIX + name + ".cluster.enabled", Boolean.class));
		options.setClusterHost((String) loader.getValue(VX_PREFIX + name + ".cluster.host", String.class));
		options.setClusterPort((int) loader.getValue(VX_PREFIX + name + ".cluster.port", Integer.class));

		options.setClusterPingInterval(
				(long) loader.getValue(VX_PREFIX + name + ".cluster.ping.interval", Boolean.class));
		options.setClusterPingReplyInterval(
				(long) loader.getValue(VX_PREFIX + name + ".cluster.ping.interval.reply", Boolean.class));

		options.setBlockedThreadCheckInterval(
				(long) loader.getValue(VX_PREFIX + name + ".blocked.thread.check.interval", Boolean.class));
		options.setMaxEventLoopExecuteTime(
				(long) loader.getValue(VX_PREFIX + name + ".execute.time.max.event.loop", Boolean.class));
		options.setMaxWorkerExecuteTime(
				(long) loader.getValue(VX_PREFIX + name + ".execute.time.max.worker", Boolean.class));

		options.setHAEnabled((boolean) loader.getValue(VX_PREFIX + name + ".ha.enabled", Boolean.class));
		options.setHAGroup((String) loader.getValue(VX_PREFIX + name + ".ha.group", String.class));
		options.setQuorumSize((int) loader.getValue(VX_PREFIX + name + ".quorum.size", Integer.class));
		options.setWarningExceptionTime(
				(long) loader.getValue(VX_PREFIX + name + ".warning.exception.time", Boolean.class));

		return options;
	}

	public static DeploymentOptions readOpts() throws Exception {
		final DeploymentOptions options = new DeploymentOptions();
		options.setConfig(new JsonObject().put("http.port", (int) loader.getValue("http.port", Integer.class))
				.put("db_name", (String) loader.getValue("db_name", String.class))
				.put("connection_string", (String) loader.getValue("connection_string", String.class)));
		return options;
	}

}
