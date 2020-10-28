package com.example.webchat.repository;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.opencsv.CSVReader;


public class WebChatRepository {

	public Map<String, String> getNickName() {
		//파일 경로
		String csvFileName = "/var/data/wiki.csv";
		InputStreamReader secondIs = null;
		InputStreamReader firstIs = null;
		Map<String, String> map = new HashMap<>();
		String firstName = "";
		String secondName = "";
		
		CSVReader firstReader =null;
		CSVReader secondReader = null;
		try {
			System.out.println("----------------debug");
			firstIs = new InputStreamReader(new FileInputStream(csvFileName),"UTF-8");
			firstReader = new CSVReader(firstIs, ',','"',(int)(Math.random()*24672)+1);
			firstName=firstReader.readNext()[1];
			
			secondIs = new InputStreamReader(new FileInputStream(csvFileName),"UTF-8");
			secondReader = new CSVReader(secondIs, ',','"',(int)(Math.random()*24672)+1);
			secondName=secondReader.readNext()[1];
		} catch (IOException e) {
			System.out.println("파일경로오류");
			e.printStackTrace();
		}finally {
			try {
				firstReader.close();
				secondReader.close();
				firstIs.close();
				secondIs.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
	
		
		map.put("firstName",firstName);
		map.put("secondName", secondName);
		
		return map;
	}

}
