package org.lifeiseasy.simple.executors;

import org.lifeiseasy.simple.reader.Record;
import org.lifeiseasy.simple.reader.State;

public class App {

	public static void main(String[] args) {
		State state = new State();
		state.loadRecords();

		for (Record record : state.getRecords()) {
			System.out.println("command = " + record.getCommand());
			System.out.println("folder = " + record.getFolderPath());
		}
	}

}
