package com.learning.net.message.parser;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageShower {
	private static final Logger logger = LoggerFactory.getLogger(MessageParser.class);

	public static void showLineNumber(String[] messagesArray) {
		if (null == messagesArray || messagesArray.length <= 0) {
			return;
		}

		for (int i = 0; i < messagesArray.length; i++) {
			String message = messagesArray[i];
			if (StringUtils.isBlank(message)) {
				continue;
			}
			if (!MessageParser.validateMessage(message)) {
				logger.warn("Bad  Message : {}", message);
				continue;
			}
			logger.info("{} - {}", MessageParser.getLineNumber(message), MessageParser.getLineMessage(message));
		}
	}
}