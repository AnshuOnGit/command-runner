package org.lifeiseasy.simple.core;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.concurrent.Callable;

//Todo make it singleton
public class InvokeProcess implements Callable<Optional<Process>>{
	
	private String command;
	
	private Path folderPath;

	public Optional<Process> execute(String command, Path folderPath) {
		if(!Files.exists(folderPath)) {
			throw new RuntimeException("Folder does not exist");
		}
		Path outputFilePath = folderPath.resolve("output");
		try {
			Files.deleteIfExists(outputFilePath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Path errorFilePath = folderPath.resolve("error");
		try {
			Files.deleteIfExists(errorFilePath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ProcessBuilder processBuilder = new ProcessBuilder(command);
		processBuilder.redirectOutput(outputFilePath.toFile());
		processBuilder.redirectError(errorFilePath.toFile());
		
		Process process;
		try {
			process = processBuilder.start();
			System.out.println("Started process id for command " + command +" = " + process.pid());
			return Optional.of(process);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Optional.empty();
		
	}

	public Optional<Process> call() throws Exception {
		return execute(command, folderPath);
	}

	public InvokeProcess(String command, Path folderPath) {
		this.command = command;
		this.folderPath = folderPath;
	}

}
