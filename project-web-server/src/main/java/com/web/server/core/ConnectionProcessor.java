package com.web.server.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.web.server.core.enumeration.HttpStatus;

public class ConnectionProcessor implements Runnable {
	private final static Logger logger = LoggerFactory.getLogger(ConnectionProcessor.class);

	private Socket client;
	private Map<String, Servlet> servletMapping;

	public ConnectionProcessor(Socket client, Map<String, Servlet> servletMapping) {
		this.client = client;
		this.servletMapping = servletMapping;
	}

	@Override
	public void run() {
		logger.info("Start-New connection：" + client.getInetAddress() + ":" + client.getPort());
		process(client);
	}

	private void process(Socket client) {
		InputStream is = null;
		OutputStream os = null;

		try {
			is = client.getInputStream();
			os = client.getOutputStream();

			Request request = new Request(is);
			logger.info(request.toString());

			Response response = new Response(os);
			Servlet servlet = servletMapping.get(request.getUrl());
			if (null != servlet) {
				servlet.service(request, response);
			} else {
				logger.warn("Can't found {}", request.getUrl());
				response.setStatus(HttpStatus.S_404);
			}

			response.flush();
			os.flush();
		} catch (Exception e) {
			logger.error("Process client request has error.", e);
		} finally {
			try {
				if (is != null) {
					is.close();
				}
				if (os != null) {
					os.close();
				}
				if (client != null) {
					client.close();
				}
			} catch (IOException e) {
				logger.error("Process client request has error.", e);
			}
		}
	}
}