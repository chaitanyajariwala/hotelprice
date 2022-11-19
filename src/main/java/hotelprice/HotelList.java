package hotelprice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;

public class HotelList {
    static List<Hotel> list = new ArrayList<Hotel>();
    public void addDocumentToList(Document doc, WebDriver driver) {
        Elements elements = doc.getElementsByClass("kzGk");
		int count = 0;
		for (Element element : elements) {
			if (count ==2) break;
			count++;
			int index = list.size();
			String price = element.getElementsByClass("zV27-price").first().text();
			String location = element.getElementsByClass("FLpo-location-name").first().text();
			String score = element.getElementsByClass("FLpo-score").first().text();
			String name = element.getElementsByClass("FLpo-big-name").first().text();
			String url = Config.DOMAIN_NAME + element.getElementsByClass("FLpo-big-name").first().attr("href");
            
			String text = fetchTextFromUrl(driver, url, name);
			
			String[] words = text.split(" ");
			words = Arrays.asList(words).stream().map(String::toLowerCase).toArray(String[]::new);

			System.out.println("Crawling: " + name);
			list.add(new Hotel(index, price, location, score, name, url, words));    
		}
    }
	public String fetchTextFromUrl(WebDriver driver, String url, String name) {
		String html = HTMLUtils.fetchHtml(driver, url, name);
		Document doc = HTMLUtils.parse(html);
		return doc.body().text();
	}
}
