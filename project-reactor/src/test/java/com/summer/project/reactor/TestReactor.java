package com.summer.project.reactor;

import java.io.IOException;

public class TestReactor {
	public static void main(String[] args) {
		try {
			Thread reactor = new Thread(new Reactor(9999));
			reactor.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
