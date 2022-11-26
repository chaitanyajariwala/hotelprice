package hotelprice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class WordCompletion {
    List<String> words;
    public WordCompletion (Map<String, Integer> words) {
        this.words = new ArrayList<>(words.keySet());
    }
    //TODO: use Trie in this function
    // List<String> suggestWordCompletion (String word) {
    //     if (!words.contains(word)) {
    //         Trie<> trie = new Trie(words);
    //         return trie.completeWord(word);
    //     }
    //     return new ArrayList<>();
    // }
}
