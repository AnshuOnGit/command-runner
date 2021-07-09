package org.lifeiseasy.simple.executors;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class StopApp {
	
	public static void main(String[] args) throws IOException {
		Files.lines(Paths.get("C:\\Users\\ankumar\\dev\\wfosaas\\logs\\master.log"))
		.forEach(line -> {
			Pattern p= Pattern.compile(".*Started process id (?<processId>\\d+).*");
			Matcher ma = p.matcher(line);
			if(ma.matches()) {
				String processId = ma.group("processId");
				System.out.println(processId);
				try {
					Runtime.getRuntime().exec("taskkill /F /PID "+ processId);
					System.out.println("Successfully killed process with Id= "+ processId);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		
	}

}
