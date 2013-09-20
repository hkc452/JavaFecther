/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hkc.imageCrawler;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

/**
 * @author Yasser Ganjisaffar <lastname at gmail dot com>
 */

/*
 * IMPORTANT: Make sure that you update crawler4j.properties file and set
 * crawler.include_images to true
 */

public class ImageCrawlController {

	public static void main(String[] args) throws Exception {
		
		InputStream fileInputStream=ImageCrawlController.class.getClassLoader().getResourceAsStream("DATA.properties");
		Properties pp=new Properties();
		pp.load(fileInputStream);
		String rootFolder = pp.getProperty("rootFolder");
		String storageFolder=pp.getProperty("storageFolder");
		int numberOfCrawlers=7;
		CrawlConfig config = new CrawlConfig();

		config.setCrawlStorageFolder(rootFolder);

		/*
		 * Since images are binary content, we need to set this parameter to
		 * true to make sure they are included in the crawl.
		 */
		config.setIncludeBinaryContentInCrawling(true);


		
		UrlThread urlThread=new UrlThread(pp.getProperty("crawlerurl"));
		urlThread.run();
		
		ArrayList<String> crawlDomains=urlThread.ReturnUrls();
		System.out.println(crawlDomains.size());
		HashMap<String, String> filename=urlThread.ReturnFile();
		
		String[] crawlDomain=new String[crawlDomains.size()];
		int count=0;

		PageFetcher pageFetcher = new PageFetcher(config);
		RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
		RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
		CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);
		for (String domain : crawlDomains) {
			controller.addSeed(domain);
			crawlDomain[count++]=domain;
		}

		ImageCrawler.configure(crawlDomain, storageFolder,filename);

		controller.start(ImageCrawler.class, numberOfCrawlers);
	}

}
