package org.summer.project.args;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.summer.project.args.ArgsException.ErrorCode;

/**
 * @author summer.xia
 */
public class IntegerArgumentMarshaler implements ArgumentMarshalar {
	public int intValue = 0;

	@Override
	public void set(Iterator<String> currentArgument) throws ArgsException {
		String parameter = null;
		try {
			parameter = currentArgument.next();
			intValue = Integer.parseInt(parameter);
		} catch (NoSuchElementException e) {
			throw new ArgsException(ErrorCode.MISSING_INTEGER);
		} catch (NumberFormatException e) {
			throw new ArgsException(ErrorCode.INVALID_INTEGER, parameter);
		}
	}

	public static int getValue(ArgumentMarshalar am) {
		if (am != null && am instanceof IntegerArgumentMarshaler) {
			return ((IntegerArgumentMarshaler) am).intValue;
		} else {
			return 0;
		}
	}
}
