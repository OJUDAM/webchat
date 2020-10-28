package com.example.webchat.repository;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.opencsv.CSVReader;

@Repository
public class WebChatRepository {

	public Map<String, String> getNickName() {
		//파일 경로
		String csvFileName = "data/wiki.csv";
		InputStreamReader is = null;
		Map<String, String> map = new HashMap<>();
		String firstName = "";
		String secondName = "";
		
		int randomNum = (int)(Math.random()*24671)+1;
		try {
			is = new InputStreamReader(new FileInputStream(csvFileName),"EUC-KR");
			CSVReader reader = new CSVReader(is, ',','"',24671);
			firstName=reader.readNext()[0];
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		
	
		
		map.put("firstName",firstName);
		map.put("secondName", secondName);
		
		return map;
	}

}
