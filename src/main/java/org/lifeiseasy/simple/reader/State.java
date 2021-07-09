package org.lifeiseasy.simple.reader;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class State {
	
	private List<Record> records;
	
	
    public void loadRecords() {
    	String configFilePath = System.getProperty("configFile");
    	File file = new File(configFilePath);
    	
    	
    	ObjectMapper mapper = new ObjectMapper();
    	
    	try {
			records = mapper.readValue(file, new TypeReference<List<Record>>(){});
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

	public List<Record> getRecords() {
		return records;
	}

	public void setRecords(List<Record> records) {
		this.records = records;
	}
}
