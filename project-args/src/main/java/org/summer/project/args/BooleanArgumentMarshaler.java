package org.summer.project.args;

import java.util.Iterator;

/**
 * @author summer.xia
 */
public class BooleanArgumentMarshaler implements ArgumentMarshalar {
	private boolean booleanValue = false;

	@Override
	public void set(Iterator<String> currentArgument) throws ArgsException {
		booleanValue = true;
	}

	public static boolean getValue(ArgumentMarshalar am) {
		if (am != null && am instanceof BooleanArgumentMarshaler) {
			return ((BooleanArgumentMarshaler) am).booleanValue;
		} else {
			return false;
		}
	}
}
