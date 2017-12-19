package org.summer.project.args;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.summer.project.args.ArgsException.ErrorCode;

/**
 * @author summer.xia
 */
public class DoubleArgumentMarshaler implements ArgumentMarshalar {
	public double doubleValue = 0;

	@Override
	public void set(Iterator<String> currentArgument) throws ArgsException {
		String parameter = null;
		try {
			parameter = currentArgument.next();
			doubleValue = Double.parseDouble(parameter);
		} catch (NoSuchElementException e) {
			throw new ArgsException(ErrorCode.MISSING_DOUBLE);
		} catch (NumberFormatException e) {
			throw new ArgsException(ErrorCode.INVALID_DOUBLE, parameter);
		}
	}

	public static double getValue(ArgumentMarshalar am) {
		if (am != null && am instanceof DoubleArgumentMarshaler) {
			return ((DoubleArgumentMarshaler) am).doubleValue;
		} else {
			return 0;
		}
	}
}
