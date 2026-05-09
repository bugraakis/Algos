public class RaceResult {
    Car winner;
    int winnerScore, loserScore, winnerIter, loserIter;

    RaceResult(Car winner, int winnerScore, int loserScore, int winnerIter, int loserIter) {
        this.winner = winner;
        this.winnerScore = winnerScore; this.loserScore = loserScore;
        this.winnerIter = winnerIter;   this.loserIter = loserIter;
    }
}
