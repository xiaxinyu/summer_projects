package com.learning.net.message.parser;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.learning.net.message.parser.bean.EthernetBean;
import com.learning.net.message.parser.bean.IPBean;
import com.learning.net.message.parser.core.EthernetParser;
import com.learning.net.message.parser.core.IPParser;
import com.learning.net.message.parser.exception.MessageFormatException;
import com.project.utils.FileHelper;

public class MessageParser {
	private static final Logger logger = LoggerFactory.getLogger(MessageParser.class);
	private static final boolean SHOW_LINE_NUMBER_DEFAULT = false;

	public void parse(String path) {
		String messages = readMessageFile(path);
		String[] messagesArray = convertMessages(messages);
		if (SHOW_LINE_NUMBER_DEFAULT) {
			MessageShower.showLineNumber(messagesArray);
		}
		String messageStr = null;
		try {
			messageStr = cleanMessages(messagesArray);
		} catch (MessageFormatException e) {
			logger.warn("Cleaning message has error, message={}", e.getMessage());
			return;
		}
		EthernetBean ethernetBean = EthernetParser.parse(messageStr);
		logger.info(ethernetBean.toString());
		
		IPBean ipBean = IPParser.parse(ethernetBean.getIPMessage());
		logger.info(ipBean.toString());
	}

	public String readMessageFile(String path) {
		String messages = StringUtils.EMPTY;
		try {
			logger.info("Start to read message file.");
			messages = FileHelper.readAsPlainText(path);
			logger.info("End to read message file.");
		} catch (IOException e) {
			logger.error("Reading message file has error.", e);
		}
		return messages;
	}

	public String[] convertMessages(String messages) {
		logger.info("Start to convert message as Array");
		if (StringUtils.isBlank(messages)) {
			return null;
		}
		String[] messagesArray = messages.split(FileHelper.LINE_BREAK);
		logger.info("End to convert message as Array");
		return messagesArray;
	}

	public String cleanMessages(String[] messagesArray) throws MessageFormatException {
		logger.info("Start to clean message");
		if (null == messagesArray || messagesArray.length <= 0) {
			return StringUtils.EMPTY;
		}

		StringBuffer messageBuilder = new StringBuffer();
		for (String message : messagesArray) {
			if (StringUtils.isBlank(message)) {
				continue;
			}
			if (!MessageParser.validateMessage(message)) {
				logger.error("Line message is illegal, message={}", message);
				throw new MessageFormatException(message);
			}
			messageBuilder.append(getLineMessage(message));
		}
		String message = messageBuilder.toString();
		logger.info("Clean message: {}", message);
		logger.info("End to clean message");
		return message;
	}

	public static boolean validateMessage(String message) {
		return !(message.length() < 4);
	}

	public static int getLineNumber(String message) {
		return Integer.valueOf(message.substring(0, 4), 16);
	}

	public static String getLineMessage(String message) {
		return message.substring(4).replaceAll(" ", "").replaceAll(FileHelper.LINE_BREAK, "");
	}
}