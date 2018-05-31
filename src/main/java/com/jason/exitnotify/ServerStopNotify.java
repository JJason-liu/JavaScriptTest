package com.jason.exitnotify;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerStopNotify implements Runnable {

	private static final Logger log = LogManager.getLogger(ServerStopNotify.class);

	@Override
	public void run() {
		log.error("**********************************************");
		log.error("**************** -_- 服务器关闭成功  -_- **********");
		log.error("**********************************************");
	}
}
