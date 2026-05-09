public class RaceLog {
    LogEntry head, tail;

    void add(String raceName, String trackName,
             String car1Name, int score1,
             String car2Name, int score2,
             String winnerName) {
        LogEntry entry = new LogEntry(raceName, trackName, car1Name, score1,
                                      car2Name, score2, winnerName);
        if (head == null) head = tail = entry;
        else { tail.next = entry; tail = entry; }
    }

    void print() {
        System.out.println("\n=== RACE LOG ===");
        LogEntry current = head;
        int number = 1;
        while (current != null) {
            System.out.println(number + ")[" + current.raceName + " | " + current.trackName + " | " +
                current.car1Name + ":" + current.score1 + " vs " + current.car2Name + ":" + current.score2 +
                " | WINNER: " + current.winnerName + "]");
            if (current.next != null) System.out.println("\u2193");
            current = current.next;
            number++;
        }
    }
}
