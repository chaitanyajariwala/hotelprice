package hotelprice;

public class Hotel {
    String price;
    String location;
    String score;
    String url;
    String name;
    String pageData;
    String[] words;
    int index;

    public Hotel(int index, String price, String location, String score, String name, String url, String[] words) {
        this.index = index;
        this.price = price;
        this.location = location;
        this.score = score;
        this.name = name;
        this.url = url;
        // this.pageData = pageData;
        this.words = words;
    }
}
