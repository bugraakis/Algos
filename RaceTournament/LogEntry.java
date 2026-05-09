public class LogEntry {
    String raceName, trackName, car1Name, car2Name, winnerName;
    int score1, score2;
    LogEntry next;

    LogEntry(String raceName, String trackName,
             String car1Name, int score1,
             String car2Name, int score2,
             String winnerName) {
        this.raceName = raceName; this.trackName = trackName;
        this.car1Name = car1Name; this.score1 = score1;
        this.car2Name = car2Name; this.score2 = score2;
        this.winnerName = winnerName;
    }
}
