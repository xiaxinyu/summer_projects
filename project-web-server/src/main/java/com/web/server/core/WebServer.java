package com.web.server.core;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.web.server.core.pool.ConnectionFactory;
import com.web.server.core.pool.ConnectionPool;

/**
 * A simple http server using bio to read a socket stream<br>
 * and response the uri in http request line to client, <br>
 * that is uri after method blank and before the blank of protocol/version<br>
 * if the uri is "/stop", server close<br>
 * just test a method to read a complete arrival socket stream with a very small
 * buffer<br>
 * so the buffer should be used repeatedly to join the hold arrival message<br>
 * the key point is BufferedRead.ready() function which tell the next read() is
 * guaranteed not to block for input<br>
 * but the ready() return false do not guarantee the next read() is 100%
 * block<br>
 * 
 * Created by summer.xia on 2018.12.19
 *
 */
public class WebServer {
	private final static Logger logger = LoggerFactory.getLogger(WebServer.class);
	public static final int PORT = 8080;
	public static final int BACK_LOG = 50;
	public static ConnectionPool pool = ConnectionFactory.getConnectionPool();

	public void start(Map<String, Servlet> configuration) {
		ServerSocket server = null;
		try {
			server = new ServerSocket();
			server.bind(new InetSocketAddress(PORT), BACK_LOG);
			logger.info("Start server, listen port {}", PORT);
			while (true) {
				pool.execute(new ConnectionProcessor(server.accept(), configuration));
			}
		} catch (IOException e) {
			logger.error("Startring server has error", e);
			System.exit(1);
		} finally {
			if (server != null) {
				try {
					server.close();
				} catch (IOException e) {
					logger.error("Startring server has error", e);
				}
			}
		}
	}
}