package com.benlinus92.webspring.configuration;

public final class AppConstants {
	public static final String CURR_API_HISTORY = "http://apilayer.net/api/historical?access_key=194a276698ccbe38f3d622a013f4dc64&date=";
	public static final String CURR_API_SET = "&currencies="; 
	public static final String CURR_API_RATESLIST = "ARS,AUD,BRL,BTC,BYR,CAD,CNY,EUR,GEL,GBP,JPY,MXN,RUB,SEK,UAH,USD,XAG,XAU";
	public static final String PROPERTIES_PATH = "/var/lib/openshift/5645f0262d5271789e00000c/"
			+ "app-root/runtime/repo/src/main/resources/update.properties"; //path of actual file, not classpath
}