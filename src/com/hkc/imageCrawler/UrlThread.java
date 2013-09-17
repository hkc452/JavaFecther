package com.hkc.imageCrawler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class UrlThread {
	
	ArrayList<String> urls=new ArrayList<>();
    
	HashMap<String, String> urlfile=new HashMap<>();
	String urlsbuffer;
	
	public UrlThread(String url){
		this.urlsbuffer=url;
	}
	public void run() {
		
		  StringBuffer websitebBuffer=new StringBuffer(urlsbuffer);
		  int locationstart=websitebBuffer.lastIndexOf("&s=");
		  int locationend=websitebBuffer.lastIndexOf("#J_relative");
		  Document document = null;
		  int i=0;
		  while(i<100){
			  try {
					document=Jsoup.connect(websitebBuffer.toString()).get();
				  } catch (IOException e) {
					
					e.printStackTrace();
				  }
				  Elements elements=document.select("div.item-box div.pic p.pic-box a.s210 img");
				  Elements goods=document.select("div.item-box h3.summary a");
				  Elements sellers=document.select("div.item-box div.row div[class=col seller feature-dsi-tgr] a");
				  Elements prices=document.select("div.item-box div[class=row row-focus] div[class=col price]");
				  Elements buys=document.select("div.item-box div.row div[class=col dealing]");
				  if (elements!=null&&goods!=null&&sellers!=null&&prices!=null&&buys!=null) {
					for (Element element : elements) {
						  System.out.println("×¥È¡"+element.attr("src"));
						  urls.add(element.attr("src"));
						  
					}
					for (int j = 0; j < elements.size(); j++) {
						urlfile.put(elements.get(j).attr("src"), sellers.get(j).text()+"_"+goods.get(j).attr("title")+"_"+prices.get(j).text()+"_"+buys.get(j).text());
					}
				  }else {
					  System.out.println("¿Õ");
				  }  
				  i++;
				  locationend=websitebBuffer.lastIndexOf("#J_relative");
				  websitebBuffer.replace(locationstart+3,locationend, Integer.toString(i*40));
				  
		  }
		
	}
	
	public ArrayList<String> ReturnUrls(){
		return this.urls;
	}
	public HashMap<String, String> ReturnFile(){
		return this.urlfile;
	}

}
