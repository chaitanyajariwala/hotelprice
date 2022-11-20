package hotelprice;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class PageRank {
    
    PriorityQueue<Page> priorityQueue = new PriorityQueue<Page>(10, new PageComparator());;
    Map<Integer, Integer> documentScoreMap;

    public PageRank(Map<Integer, Integer> documentScoreMap) {
        this.documentScoreMap = documentScoreMap;
    }

    public void rankPages() {
        for (int documentIndex: documentScoreMap.keySet()) {
            int score = documentScoreMap.get(documentIndex);
            Page page = new Page(documentIndex, score);
            priorityQueue.add(page);
        }
    }

    public List<Integer> getTopKDocuments(int k) {
        List<Integer> list = new LinkedList<>();
        for (int i=0;i<k;i++){
            if (priorityQueue.isEmpty()) {
                break;
            }
            list.add(priorityQueue.remove().documentIndex);
        }
        return list;
    }
}

class Page {
    public int score;
    public int documentIndex;
    public Page(int documentIndex, int score) {
        this.score = score;
        this.documentIndex = documentIndex;
    }
}

class PageComparator implements Comparator<Page>{
    public int compare(Page page1, Page page2) {
        if (page1.score < page2.score)
            return 1;
        else if (page1.score > page2.score)
            return -1;
        return 0;
    }
}