public class TrackNode {
    int position, teleportValue;
    String effect;
    TrackNode prev, next;

    TrackNode(int position) {
        this.position = position;
        this.effect = "normal";
        this.teleportValue = 0;
    }
}
