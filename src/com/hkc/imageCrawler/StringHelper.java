package com.hkc.imageCrawler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class StringHelper {
	public static String replaceBlank(String str) {

		String dest = "";

		if (str != null) {
			

			Pattern p = Pattern.compile("\\s*|\t|\r|\n");

			Matcher m = p.matcher(str);

			dest = m.replaceAll("");
			if (dest.indexOf("/")!=-1) {				
				dest=dest.replace("/", "");
			}
			if (dest.indexOf("*")!=-1) {				
				dest=dest.replace("*", "");
			}
			if (dest.indexOf("?")!=-1) {				
				dest=dest.replace("?", "");
			}
			if (dest.indexOf("|")!=-1) {				
				dest=dest.replace("|", "");
			}

		}
		return dest;

	}
	
	

}
