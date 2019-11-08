package crawler;

import java.io.IOException;

import org.jsoup.*;
import org.jsoup.nodes.Document;

public class JsoupUtil {

	public static Document getJsoupConnect(String url) throws IOException {
		return Jsoup.connect(url).get();
	}

}
