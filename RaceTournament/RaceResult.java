public class RaceResult {
    Car winner, loser;
    int winnerScore, loserScore, winnerIter, loserIter;

    RaceResult(Car winner, Car loser, int winnerScore, int loserScore, int winnerIter, int loserIter) {
        this.winner = winner; this.loser = loser;
        this.winnerScore = winnerScore; this.loserScore = loserScore;
        this.winnerIter = winnerIter; this.loserIter = loserIter;
    }
}
