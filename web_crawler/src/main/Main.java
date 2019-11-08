package main;

import crawler.CrawlerPayloads;

public class Main {

	public static void main(String[] args) {
		try {
			String url = "http://web.mit.edu/";
			String keyWord = "Research";
			CrawlerPayloads cwrPload = new CrawlerPayloads();
			cwrPload.processUrl(url, keyWord);
		} catch (Throwable e) {
			System.out.println("" + e.getStackTrace());
		}
	}
}
