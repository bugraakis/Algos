public class LogSLL {
    LogEntry head, tail;

    void add(String raceName, String trackName,
             String car1Name, int score1, int iter1,
             String car2Name, int score2, int iter2,
             String winnerName) {
        LogEntry e = new LogEntry(raceName, trackName, car1Name, score1, iter1,
                                   car2Name, score2, iter2, winnerName);
        if (head == null) head = tail = e;
        else { tail.next = e; tail = e; }
    }

    void print() {
        System.out.println("\n=== RACE LOG ===");
        LogEntry curr = head;
        int num = 1;
        while (curr != null) {
            System.out.println(num + ")[" + curr.raceName + " | " + curr.trackName + " | " +
                curr.car1Name + ":" + curr.score1 + " vs " + curr.car2Name + ":" + curr.score2 +
                " | WINNER: " + curr.winnerName + "]");
            if (curr.next != null) System.out.println("\u2193");
            curr = curr.next;
            num++;
        }
    }
}
