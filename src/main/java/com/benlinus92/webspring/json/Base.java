package com.benlinus92.webspring.json;

import java.util.Map;

public class Base {
	boolean success;
	long timestamp;
	Map<String, String> quotes;
	public boolean getSuccess() {
		return this.success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public long getTimestamp() {
		return this.timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	public Map<String, String> getQuotes() {
		return this.quotes;
	}
	public void setQuotes(Map<String, String> map) {
		this.quotes = map;
	}
}
