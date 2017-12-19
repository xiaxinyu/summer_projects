package org.summer.project.args;

import java.util.Iterator;

/**
 * @author summer.xia
 */
public interface ArgumentMarshalar {
	/**
	 * set value
	 * @param currentArgument
	 * @throws ArgsException
	 */
	void set(Iterator<String> currentArgument) throws ArgsException;
}
