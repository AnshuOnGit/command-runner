package org.lifeiseasy.simple.executors;

import org.lifeiseasy.simple.core.InvokeProcess;
import org.lifeiseasy.simple.reader.Record;
import org.lifeiseasy.simple.reader.State;
import org.slf4j.Logger;

public class App {
	
	private static final Logger  LOGGER = org.slf4j.LoggerFactory.getLogger(App.class);

	public static void main(String[] args) {
		State state = new State();
		state.loadRecords();

		for (Record record : state.getRecords()) {
			LOGGER.info("service = " + record.getService());
			LOGGER.info("command = " + record.getCommand());
			//System.out.println("folder = " + record.getFolderPath());
			InvokeProcess invokeProcess = new InvokeProcess(record.getCommand(),record.getFolderPath(), record.getService() );
			invokeProcess.execute(record.getCommand(),
					record.getFolderPath(), record.getService());
			LOGGER.info("started service "+ record.getService());

		}
	}

}
