package hotelprice;

import java.util.Scanner;
import java.util.Set;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Arrays;
import java.util.Collections;

/**
 * Hello world!
 */
public final class App {
    /**
     * Says hello to the world.
     * @param args The arguments of the program.
     */
    WordFrequency wf;
    InvertedIndex invertedIndex;
    public String[] keywords;
    private void menu () {
        int swValue = 0;

        // Display menu graphics
        Scanner sc = new Scanner(System.in);
        WordFrequency wf = null;
        InvertedIndex invertedIndex = null;
        while(swValue != 4) {
            try {
                // Runtime.getRuntime().exec("cls");
                // System.out.print("\033[H\033[2J");  
                System.out.flush(); 
                System.out.println("=================================================");
                System.out.println("|                    MAIN MENU                   |");
                System.out.println("=================================================");
                System.out.println("| Options:                                       |");
                System.out.println("|        1. Crawl the site                       |");
                System.out.println("|        2. search pages                         |");
                System.out.println("|        3. Create word freq and inverted index  |");
                System.out.println("|        4. Exit                                 |");
                System.out.println("=================================================");
                // swValue = Keyin.inInt(" Select option: ");
                System.out.println("Select Option: ");
                String next = sc.nextLine();
                swValue = Integer.parseInt(next);
                // Switch construct
                switch (swValue) {
                    case 1:
                        // System.out.println("Option 1 selected");
                        crawl(sc);
                        Thread.sleep(1000);
                        break;
                    case 2:
                        search(sc);
                        // System.out.println("Option 2 selected");
                        Thread.sleep(1000);
                        break;
                    case 3:
                        createIndex(sc);
                        Thread.sleep(1000);
                        break;
                    case 4:
                        System.out.println("Exiting...");
                        Thread.sleep(1000);
                        break;
                    default:
                        throw new Exception();
                }
            } catch (Exception e) {
                System.out.println("Invalid selection. Try again (Press Enter key to continue)" + e.getMessage());
                sc.nextLine();
            }
        }
    }

    void search(Scanner sc) {
        keywords = getSearchKeywords(sc);
        Set<Integer> documentSet = invertedIndex.search(keywords);
		Map<Integer, Integer> scoreMap = wf.calculateScores(keywords, documentSet);
        System.out.println(scoreMap.toString());
		PageRank pagerank = new PageRank(scoreMap);
		pagerank.rankPages();
		List<Integer> documentIndexList = pagerank.getTopKDocuments(10);
        System.out.println(documentIndexList.toString());
    }

    void printIndex () {
        System.out.println("Word Frequency: ");
        wf.printIndex();
        System.out.println("Inverted Index: ");
        invertedIndex.printIndex();
    }

    void crawl (Scanner sc) {
        WebCrawler webCrawler = new WebCrawler(new Date(), new Date(), 2);
        webCrawler.runCrawler();
        System.out.println("Hotel List: ");
        HotelList.list.forEach(System.out::println);
    }
    
    void createIndex (Scanner sc) {
        System.out.println("Creating word Frequency map...");
        wf = new WordFrequency(HotelList.list);
		wf.setWordFrequencies();
        // wf.printIndex();
		System.out.println("Creating Inverted index...");
		invertedIndex = new InvertedIndex(HotelList.list);
		invertedIndex.createIndex();
        // invertedIndex.printIndex();
    }

    String[] getSearchKeywords (Scanner sc) {
        System.out.print("Enter the Search query: ");
        String query = sc.nextLine();
        String[] words = query.toLowerCase().split( "\\s+");  
        System.out.println(Arrays.toString(words));
        return words;
    }

    public static void main(String[] args) {
        App app = new App();
        app.menu();
    }
}
