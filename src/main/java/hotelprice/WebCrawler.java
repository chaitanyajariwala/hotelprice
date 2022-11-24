package hotelprice;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.text.SimpleDateFormat;
import java.io.FileWriter;
// import java.util.regex.Matcher;
// import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class WebCrawler {
	// List<Hotel> hotelList = new ArrayList<Hotel>();
	Map<Integer, Map<String, Integer>> wordFrequency = new HashMap<>();
	// Map<String, Map<String, Integer>> wordToDocMap = new HashMap<>();
	InvertedIndex invertedIndex = new InvertedIndex(HotelList.list);
	
	private Date startDate;
    private Date endDate;
    private int numberOfAdults;
	private WebDriver driver;
    

	public WebCrawler(Date startDate, Date endDate, int numberOfAdults) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.numberOfAdults = numberOfAdults;
		System.setProperty("webdriver.chrome.driver", Config.CHROME_DRIVER_PATH);
		this.driver = new ChromeDriver();
    }

	private String buildURL(Date startDate, Date endDate, int numberOfAdults) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
        String url = Config.BASE_URL.replace("<SD>", dateFormat.format(startDate));
        url = url.replace("<ED>", dateFormat.format(endDate));
        url = url.replace("<NOA>", Integer.toString(numberOfAdults));
        return url;
    }

	// public String fetchHtml(String url, String name){
	// 	driver.get(url);
	// 	try {
	// 		Thread.sleep(15000);
	// 	}catch (Exception e) {
	// 	}
	// 	String s = driver.getPageSource();
		
	// 	try {
	// 		FileWriter myWriter = new FileWriter("webpages\\" + name + ".html");
	// 		myWriter.write(s);
	// 		myWriter.close();
	// 	}catch (Exception e) {
	// 	}
	// 	return s;
	// }

	// public void getWordFrequency(String html, int hotelIndex) {
	// 	Document doc = Jsoup.parse(html);
	// 	String text = doc.body().text();
	// 	String[] words = text.split(" ");

	// 	//store word frequencies for current document
	// 	Map<String, Integer> _wordFrequencyMap = new HashMap<>();
	// 	for (String word : words) {
	// 		if (wordFrequency.containsKey(word)) {
	// 			_wordFrequencyMap.put(word, _wordFrequencyMap.get(word) + 1);
	// 		} else {
	// 			_wordFrequencyMap.put(word, 1);
	// 		}
	// 	}

	// 	//put map _wordFrequencyMap with current document
	// 	wordFrequency.put(hotelIndex, _wordFrequencyMap);
	// 	// createInvertedIndex(words, hotelIndex);
	// 	// System.out.println(wordFrequency.toString());
	// 	// System.out.println(invertedIndex.indexList.toString());
	// }

	// public void createInvertedIndex(String[] words, int hotelIndex) {
	// 	invertedIndex.createIndex(words, hotelIndex);
	// }

	// public void parseHtml(String html){
	// 	Document doc = Jsoup.parse(html);
	// 	Elements elements = doc.getElementsByClass("kzGk");
	// 	for (Element element : elements) {
	// 		String price = element.getElementsByClass("zV27-price").first().text();
	// 		String location = element.getElementsByClass("FLpo-location-name").first().text();
	// 		String score = element.getElementsByClass("FLpo-score").first().text();
	// 		String name = element.getElementsByClass("FLpo-big-name").first().text();
	// 		String url = DOMAIN_NAME + element.getElementsByClass("FLpo-big-name").first().attr("href");
			
	// 		hotelList.add(new Hotel(price, location, score, name, url, ""));
	// 	}
	// }

	public void runCrawler () {
		// WebCrawler webCrawler = new WebCrawler(new Date(), new Date(), 2);
        
		String url = this.buildURL(new Date(), new Date(), 2);
		String name = "start";
		
		String html = HTMLUtils.fetchHtml(this.driver, url, name);
		// class="kzGk"

		// webCrawler.parseHtml(html);
		Document doc = HTMLUtils.parse(html);
		HotelList hotelListObj = new HotelList();
		hotelListObj.addDocumentToList(doc, this.driver);
	}

	public static void main(String[] args) {
		WebCrawler webCrawler = new WebCrawler(new Date(), new Date(), 2);
        
		String url = webCrawler.buildURL(new Date(), new Date(), 2);
		String name = "start";
		
		String html = HTMLUtils.fetchHtml(webCrawler.driver, url, name);
		// class="kzGk"

		// webCrawler.parseHtml(html);
		Document doc = HTMLUtils.parse(html);
		HotelList hotelListObj = new HotelList();
		hotelListObj.addDocumentToList(doc, webCrawler.driver);
		// webCrawler.abc();
		
		WordFrequency wf = new WordFrequency(HotelList.list);
		wf.setWordFrequencies();
		System.out.println("Creating Inverted index...");
		InvertedIndex invertedIndex = new InvertedIndex(HotelList.list);
		invertedIndex.createIndex();

		String[] keywords = {"which", "making"};
		Set<Integer> documentSet = invertedIndex.search(keywords);
		Map<Integer, Integer> scoreMap = wf.calculateScores(keywords, documentSet);

		PageRank pagerank = new PageRank(scoreMap);
		pagerank.rankPages();
		List<Integer> documentIndexList = pagerank.getTopKDocuments(10);


		// Scanner sc = new Scanner(System.in);
		
	}
	
	// public void abc() {
	// 	for (Hotel hotel : HotelList.list) {
	// 		System.out.println(hotel.index + " " + Arrays.toString(hotel.words));
	// 		// System.out.println(hotel.name);
	// 		String html = this.fetchHtml(hotel.url, hotel.name);
	// 		this.getWordFrequency(html, hotel.index);
	// 	}
	// }
}
