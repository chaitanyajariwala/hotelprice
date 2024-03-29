package hotelprice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class WordFrequency {
    // first integer is for documentIndex, and map represents word to count mapping.
    // count is document-wise for the given word.
    Map<Integer, Map<String, Integer>> frequencyMapOfEachHotel = new HashMap<>();
    List<Hotel> hotelList;

    public WordFrequency(List<Hotel> hotelList) {
        this.hotelList = hotelList;
    }

    public void setWordFrequencies() {
        for (Hotel hotel : hotelList) {
            String[] words = hotel.words;
            int hotelIndex = hotel.index;

            // store word frequencies for current document
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

            // put map _wordFrequencyMap with current document
            frequencyMapOfEachHotel.put(hotelIndex, _wordFrequencyMap);
        }
        // System.out.println(wordFrequency.toString());
    }

    public Map<Integer, Integer> calculateScores(String[] keywords, Set<Integer> documentSet) {
        // TODO: instead of map we should have used priority queue here.
        Map<Integer, Integer> documentScoreMap = new HashMap<>();
        for (int documentIndex : documentSet) {
            Map<String, Integer> wordFrequencyMap = frequencyMapOfEachHotel.get(documentIndex);
            int score = 0;
            // System.out.println(wordFrequencyMap.toString());
            for (String keyword : keywords) {
                if (wordFrequencyMap.containsKey(keyword)) {
                    score += wordFrequencyMap.get(keyword);
                    // score+=count;
                }
            }
            documentScoreMap.put(documentIndex, score);
        }
        return documentScoreMap;
    }

    public void printIndex() {
        this.frequencyMapOfEachHotel.forEach((key, value) -> System.out.println(key + ":" + value));
    }
}
