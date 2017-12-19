package org.summer.project.args;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.summer.project.args.ArgsException.ErrorCode;

/**
 * @author summer.xia
 */
public class StringArgumentMarshaler implements ArgumentMarshalar {
	public String stringValue = "";

	@Override
	public void set(Iterator<String> currentArgument) throws ArgsException {
		try {
			stringValue = currentArgument.next();
		} catch (NoSuchElementException e) {
			throw new ArgsException(ErrorCode.MISSING_STRING);
		}
	}

	public static String getValue(ArgumentMarshalar am) {
		if (am != null && am instanceof StringArgumentMarshaler) {
			return ((StringArgumentMarshaler) am).stringValue;
		} else {
			return "";
		}
	}
}
