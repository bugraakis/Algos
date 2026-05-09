public class Track {
    int id, boost;
    String name, type;
    Track next;

    Track(int id, String name, String type, int boost) {
        this.id = id; this.name = name; this.type = type; this.boost = boost;
    }
}
