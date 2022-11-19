package hotelprice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


public class WordFrequency {
	Map<Integer, Map<String, Integer>> wordFrequency = new HashMap<>();
    List<Hotel> hotelList;
    
    public WordFrequency(List<Hotel> hotelList) {
        this.hotelList = hotelList;
    }
    public void setWordFrequencies() {
        for (Hotel hotel: hotelList) {
            String[] words = hotel.words;
            int hotelIndex = hotel.index;
            
            //store word frequencies for current document
            Map<String, Integer> _wordFrequencyMap = new HashMap<>();
            for (String w : words) {
                String word = w.toLowerCase();
                if (_wordFrequencyMap.containsKey(word)) {
                    // System.out.println("inside contains");
                    _wordFrequencyMap.put(word, _wordFrequencyMap.get(word) + 1);
                } else {
                    _wordFrequencyMap.put(word, 1);
                }
            }
            
            //put map _wordFrequencyMap with current document
            wordFrequency.put(hotelIndex, _wordFrequencyMap);
        }
		// System.out.println(wordFrequency.toString());
	}
}
