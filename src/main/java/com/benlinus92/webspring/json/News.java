package com.benlinus92.webspring.json;

public class News {
	String header;
	String link;
	String source;
	String date;
	public News() { }
	public News(String header, String link, String source, String date) {
		this.header = header;
		this.link = link;
		this.source = source;
		this.date = date;
	}
	public String getHeader() {
		return this.header;
	}
	public void setHeader(String header) {
		this.header = header;
	}
	public String getLink() {
		return this.link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getSource() {
		return this.source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getDate() {
		return this.date;
	}
	public void setDate(String date) {
		this.date = date;
	}
}
