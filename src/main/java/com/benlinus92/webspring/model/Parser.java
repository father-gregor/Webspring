package com.benlinus92.webspring.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Parser {
	
	public Parser() {
		
	}
	//public void retrieveJson
	
	public String performRequest(String urlStr) throws IOException {
		URL url = new URL(urlStr);
		StringBuilder sb = new StringBuilder();
		HttpURLConnection http = (HttpURLConnection) url.openConnection();
		try {
			 BufferedReader br = new BufferedReader(new InputStreamReader(http.getInputStream()));
	            char[] buf = new char[1000000];
	            int r = 0;
	            do {
	                if ((r = br.read(buf)) > 0)
	                    sb.append(new String(buf, 0, r));
	            } while (r > 0);
		} finally {
			http.disconnect();
		}
		return sb.toString();
	}
}
