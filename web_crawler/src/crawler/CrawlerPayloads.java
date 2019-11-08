package crawler;

import java.net.URI;
import java.sql.ResultSet;

import database.CrawlerRepositiory;
import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CrawlerPayloads {

	public void processUrl(String url, String keyword) throws Throwable {

		try {
			CrawlerRepositiory crawlDB = new CrawlerRepositiory();
			ResultSet rs = crawlDB.getUrlResultSet(url);

			if (rs.next()) {

			} else {
				crawlDB.insertUrlIntoRepositiory(url);
				Document doc = JsoupUtil.getJsoupConnect(url);
				if (doc.text().contains(keyword)) {
					System.out.println(url);
				}
				Elements questions = doc.select(Util.CSS_QUERY);

				for (Element link : questions) {
					if (link.attr(Util.LINK_ATTRIBUTE).contains(getURI(url))) {
						processUrl(url, keyword);
					}
				}
			}
		} catch (Exception e) {
			System.out.println("exception- " + e.getStackTrace());
		}
	}

	private String getURI(String url) {
		final URI uri = URI.create(url);
		return uri.getPath() != null ? uri.getPath() : null;

	}
}
