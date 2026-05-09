public class Track {
    int id, difficulty, boost;
    String name, type;
    Track next;

    Track(int id, String name, String type, int difficulty, int boost) {
        this.id = id; this.name = name; this.type = type;
        this.difficulty = difficulty; this.boost = boost;
    }
}
