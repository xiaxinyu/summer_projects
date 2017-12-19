package org.summer.project.args;

public class Application {
	public static void main(String[] args) {
		try {
			Args arg = new Args("l,p#,d*", args);
			boolean logging = arg.getBoolean('1');
			int port = arg.getInt('p');
			String directory = arg.getString('d');
			executeApplication(logging, port, directory);
		} catch (ArgsException e) {
			System.out.printf("Argumenterror:%s\n", e.getMessage());
		}
	}

	public static void executeApplication(boolean logging, int port, String directory) {
		System.out.println(logging + " " + port + " " + directory);
	}
}
