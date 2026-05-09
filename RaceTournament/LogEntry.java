public class LogEntry {
    String raceName, trackName, car1Name, car2Name, winnerName;
    int score1, score2, iter1, iter2;
    LogEntry next;

    LogEntry(String raceName, String trackName,
             String car1Name, int score1, int iter1,
             String car2Name, int score2, int iter2,
             String winnerName) {
        this.raceName = raceName; this.trackName = trackName;
        this.car1Name = car1Name; this.score1 = score1; this.iter1 = iter1;
        this.car2Name = car2Name; this.score2 = score2; this.iter2 = iter2;
        this.winnerName = winnerName;
    }
}
