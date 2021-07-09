package org.lifeiseasy.simple.core;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Callable;

import org.lifeiseasy.simple.executors.App;
import org.slf4j.Logger;

//Todo make it singleton
public class InvokeProcess implements Callable<Optional<Process>> {
	
	private static final Logger  LOGGER = org.slf4j.LoggerFactory.getLogger(InvokeProcess.class);

	private String command;

	private String folderPath;
	
	private String service;

	public Optional<Process> execute(String command, String folderPath, String service) {
		File logFolder = new File(folderPath + File.separator + service);
		
		logFolder.mkdirs();

		File outputlogFile = new File(folderPath + File.separator + service + File.separator +"output.log");
		
		if(outputlogFile.exists()) {
			Path pathFromFile = outputlogFile.toPath();
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss");
			String string  = dateFormat.format(new Date())+ "-output";
			if(!Paths.get(pathFromFile.getParent().toString(), "old").toFile().exists()) {
				Paths.get(pathFromFile.getParent().toString(), "old").toFile().mkdir();
			}
			Path newPath = Paths.get(pathFromFile.getParent().toString(), "old",string);
			try {
				Files.move(pathFromFile, newPath, StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//outputlogFile.delete();
		}

		try {
			outputlogFile.createNewFile();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		File errlogFile = new File(folderPath + File.separator + service + File.separator+ "error.log");

		if(errlogFile.exists()) {
			errlogFile.delete();
		}

		try {
			errlogFile.createNewFile();	
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		
		
		ProcessBuilder processBuilder = new ProcessBuilder("C:\\Users\\ankumar\\java\\zulu8.33.0.2-sa-jdk8.0.192-win_x64\\bin\\java", "-jar" , command, "-Xmx128m" , "--management.port=-1");
		//processBuilder.directory(new File("C:\\Program Files\\Java\\jdk1.8.0_231\\bin"));
		Map<String, String> env = processBuilder.environment();
		env.put("management.port", "-1");
		env.put("management.server.port", "-1");
		env.put("NICE_STACK_NAME", "dev");
		env.put("SERVICE_NAME", service);
		env.put("PATH", "C:\\Users\\ankumar\\java\\zulu8.33.0.2-sa-jdk8.0.192-win_x64\\bin");
		processBuilder.redirectOutput(outputlogFile);
		processBuilder.redirectError(errlogFile);

		Process process;
		try {
			process = processBuilder.start();
			LOGGER.info(String.format("Started process id %d for command ", process.pid()) + " "+command);
			return Optional.of(process);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Optional.empty();

	}

	public Optional<Process> call() throws Exception {
		return execute(command, folderPath, service);
	}

	public InvokeProcess(String command, String folderPath, String service) {
		super();
		this.command = command;
		this.folderPath = folderPath;
		this.service = service;
	}

}
