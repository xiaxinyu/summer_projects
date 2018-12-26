package com.web.server.core.pool;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class ConnectionFactory {
	private final static int corePoolSize = 10;
	private final static int maximumPoolSize = 100;
	private final static int keepAliveSeconds = 60;

	public static ConnectionPool getConnectionPool() {
		ConnectionPool pool = new ConnectionPool(corePoolSize, maximumPoolSize, keepAliveSeconds, TimeUnit.SECONDS,
				new LinkedBlockingQueue<Runnable>());
		return pool;
	}
}
