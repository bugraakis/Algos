public class RaceUnit {
    int position, teleportValue;
    String effect;
    RaceUnit prev, next;

    RaceUnit(int position) {
        this.position = position;
        this.effect = "normal";
        this.teleportValue = 0;
    }
}
