import java.util.*;
import java.io.*;

public class Main {
    static CarSLL carList = new CarSLL();
    static TrackSLL trackList = new TrackSLL();
    static LogSLL raceLog = new LogSLL();
    static Random rand = new Random();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        System.setOut(new PrintStream(System.out, true, "UTF-8"));
        loadCars();
        loadTracks();

        System.out.println("=== CARS (sorted by performance ascending) ===");
        carList.print();
        System.out.println("\n=== TRACKS ===");
        trackList.print();

        System.out.println("\n--- Select a track ---");
        trackList.print();
        System.out.print("Enter track ID: ");
        Track playerTrack = null;
        while (playerTrack == null) {
            int id = sc.nextInt();
            playerTrack = trackList.getById(id);
            if (playerTrack == null) System.out.print("Invalid ID. Try again: ");
        }
        trackList.remove(playerTrack);
        System.out.println("Selected track: " + playerTrack.name);
        System.out.println("Remaining tracks:");
        trackList.print();

        System.out.println("\n--- Select a car ---");
        carList.print();
        System.out.print("Enter car ID: ");
        Car playerCar = null;
        while (playerCar == null) {
            int id = sc.nextInt();
            playerCar = carList.getById(id);
            if (playerCar == null) System.out.print("Invalid ID. Try again: ");
        }
        carList.remove(playerCar);
        System.out.println("You selected: " + playerCar.name);

        Car compCar = computerChoose(playerCar, playerTrack);
        carList.remove(compCar);
        System.out.println("Computer chose: " + compCar.name);

        System.out.println("\n=== RACE 1a: " + playerCar.name + " vs " + compCar.name + " on " + playerTrack.name + " ===");
        RaceResult r1a = doRace(playerCar, compCar, playerTrack);
        int p1aScore1 = r1a.winner == playerCar ? r1a.winnerScore : r1a.loserScore;
        int p1aIter1  = r1a.winner == playerCar ? r1a.winnerIter  : r1a.loserIter;
        int p1aScore2 = r1a.winner == compCar   ? r1a.winnerScore : r1a.loserScore;
        int p1aIter2  = r1a.winner == compCar   ? r1a.winnerIter  : r1a.loserIter;
        raceLog.add("Race 1a", playerTrack.name, playerCar.name, p1aScore1, p1aIter1,
                    compCar.name, p1aScore2, p1aIter2, r1a.winner.name);
        System.out.println("WINNER of Race 1a: " + r1a.winner.name);

        int remaining = carList.size();
        Car comp2 = carList.getByIndex(rand.nextInt(remaining));
        carList.remove(comp2);
        Car comp3 = carList.getByIndex(rand.nextInt(remaining - 1));
        carList.remove(comp3);
        Track track1b = trackList.getRandom(rand);
        trackList.remove(track1b);

        System.out.println("\n=== RACE 1b: " + comp2.name + " vs " + comp3.name + " on " + track1b.name + " ===");
        System.out.println("Remaining tracks:");
        trackList.print();
        RaceResult r1b = doRace(comp2, comp3, track1b);
        int p1bScore1 = r1b.winner == comp2 ? r1b.winnerScore : r1b.loserScore;
        int p1bIter1  = r1b.winner == comp2 ? r1b.winnerIter  : r1b.loserIter;
        int p1bScore2 = r1b.winner == comp3 ? r1b.winnerScore : r1b.loserScore;
        int p1bIter2  = r1b.winner == comp3 ? r1b.winnerIter  : r1b.loserIter;
        raceLog.add("Race 1b", track1b.name, comp2.name, p1bScore1, p1bIter1,
                    comp3.name, p1bScore2, p1bIter2, r1b.winner.name);
        System.out.println("WINNER of Race 1b: " + r1b.winner.name);

        Car finalist1 = r1a.winner;
        Car finalist2 = r1b.winner;
        Track finalTrack;
        if (finalist1 == playerCar) {
            System.out.println("\nYou won Race 1a! Choose the final track:");
            trackList.print();
            System.out.print("Enter track ID: ");
            finalTrack = null;
            while (finalTrack == null) {
                int id = sc.nextInt();
                finalTrack = trackList.getById(id);
                if (finalTrack == null) System.out.print("Invalid ID. Try again: ");
            }
        } else {
            finalTrack = trackList.getRandom(rand);
            System.out.println("\nComputer selects final track: " + finalTrack.name);
        }
        trackList.remove(finalTrack);
        System.out.println("Remaining tracks:");
        trackList.print();

        System.out.println("\n=== FINAL: " + finalist1.name + " vs " + finalist2.name + " on " + finalTrack.name + " ===");
        RaceResult finalRes = doRace(finalist1, finalist2, finalTrack);
        int pfScore1 = finalRes.winner == finalist1 ? finalRes.winnerScore : finalRes.loserScore;
        int pfIter1  = finalRes.winner == finalist1 ? finalRes.winnerIter  : finalRes.loserIter;
        int pfScore2 = finalRes.winner == finalist2 ? finalRes.winnerScore : finalRes.loserScore;
        int pfIter2  = finalRes.winner == finalist2 ? finalRes.winnerIter  : finalRes.loserIter;
        raceLog.add("Final", finalTrack.name, finalist1.name, pfScore1, pfIter1,
                    finalist2.name, pfScore2, pfIter2, finalRes.winner.name);
        System.out.println("TOURNAMENT WINNER: " + finalRes.winner.name);

        raceLog.print();
    }

    static RaceResult doRace(Car car1, Car car2, Track track) {
        int score1 = car1.performance + getTrackBonus(car1, track) + getMatchupBonus(car1.type, car2.type);
        int score2 = car2.performance + getTrackBonus(car2, track) + getMatchupBonus(car2.type, car1.type);
        System.out.println("Initial scores -> " + car1.name + ": " + score1 + " | " + car2.name + ": " + score2);

        RaceDLL dll = new RaceDLL(rand);
        int pos1 = 0, pos2 = 0, iter1 = 0, iter2 = 0;

        while (true) {
            int m1 = rand.nextInt(3) + 1;
            score1 -= m1 * 5;
            pos1 += m1;
            iter1++;
            System.out.println("Iter " + iter1 + " | " + car1.name + " -> pos " + pos1 + " (score: " + score1 + ")");
            if (pos1 < 50 && score1 > 0) {
                RaceUnit u = dll.getAt(pos1);
                if (u != null && u.effect.equals("teleport")) {
                    int old = pos1;
                    pos1 += u.teleportValue;
                    score1 -= 5;
                    if (pos1 < 1) pos1 = 1;
                    System.out.println("  Teleport! " + car1.name + ": " + old + " -> " + pos1 + " (score: " + score1 + ")");
                } else if (u != null && u.effect.equals("reset")) {
                    pos1 = 0;
                    score1 -= 5;
                    System.out.println("  Reset! " + car1.name + " back to start (score: " + score1 + ")");
                }
            }

            int m2 = rand.nextInt(3) + 1;
            score2 -= m2 * 5;
            pos2 += m2;
            iter2++;
            System.out.println("Iter " + iter2 + " | " + car2.name + " -> pos " + pos2 + " (score: " + score2 + ")");
            if (pos2 < 50 && score2 > 0) {
                RaceUnit u = dll.getAt(pos2);
                if (u != null && u.effect.equals("teleport")) {
                    int old = pos2;
                    pos2 += u.teleportValue;
                    score2 -= 5;
                    if (pos2 < 1) pos2 = 1;
                    System.out.println("  Teleport! " + car2.name + ": " + old + " -> " + pos2 + " (score: " + score2 + ")");
                } else if (u != null && u.effect.equals("reset")) {
                    pos2 = 0;
                    score2 -= 5;
                    System.out.println("  Reset! " + car2.name + " back to start (score: " + score2 + ")");
                }
            }

            if (pos1 >= 50 || score1 <= 0 || pos2 >= 50 || score2 <= 0) break;
        }

        System.out.println("Race ended -> " + car1.name + ": " + score1 + " pts, " + iter1 + " iters | "
                         + car2.name + ": " + score2 + " pts, " + iter2 + " iters");

        Car winner, loser;
        int ws, ls, wi, li;

        if (pos1 >= 50 && pos2 < 50) {
            winner = car1; loser = car2; ws = score1; ls = score2; wi = iter1; li = iter2;
        } else if (pos2 >= 50 && pos1 < 50) {
            winner = car2; loser = car1; ws = score2; ls = score1; wi = iter2; li = iter1;
        } else if (pos1 >= 50) {
            if (iter1 <= iter2) { winner = car1; loser = car2; ws = score1; ls = score2; wi = iter1; li = iter2; }
            else                { winner = car2; loser = car1; ws = score2; ls = score1; wi = iter2; li = iter1; }
        } else if (score1 > score2) {
            winner = car1; loser = car2; ws = score1; ls = score2; wi = iter1; li = iter2;
        } else if (score2 > score1) {
            winner = car2; loser = car1; ws = score2; ls = score1; wi = iter2; li = iter1;
        } else {
            if (iter1 <= iter2) { winner = car1; loser = car2; ws = score1; ls = score2; wi = iter1; li = iter2; }
            else                { winner = car2; loser = car1; ws = score2; ls = score1; wi = iter2; li = iter1; }
        }
        return new RaceResult(winner, loser, ws, ls, wi, li);
    }

    static int getTrackBonus(Car car, Track track) {
        return car.type.equals(track.type) ? track.boost : 0;
    }

    static int getMatchupBonus(String a, String b) {
        if (a.equals("Electric") && b.equals("Water"))  return 15;
        if (a.equals("Water")    && b.equals("Fire"))   return 15;
        if (a.equals("Fire")     && b.equals("Earth"))  return 15;
        if (a.equals("Earth")    && b.equals("Electric")) return 15;
        if (a.equals("Air")      && b.equals("Earth"))  return 10;
        if (a.equals("Heavy")    && b.equals("Air"))    return 10;
        return 0;
    }

    static String counterType(String t) {
        if (t.equals("Electric")) return "Earth";
        if (t.equals("Water"))    return "Electric";
        if (t.equals("Fire"))     return "Water";
        if (t.equals("Earth"))    return "Fire";
        if (t.equals("Air"))      return "Heavy";
        return "";
    }

    static Car computerChoose(Car playerCar, Track track) {
        String counter = counterType(playerCar.type);
        Car best = null;
        Car curr = carList.head;
        while (curr != null) {
            if (curr.type.equals(counter) && curr.type.equals(track.type)) { best = curr; break; }
            curr = curr.next;
        }
        if (best == null) {
            curr = carList.head;
            while (curr != null) { if (curr.type.equals(counter)) { best = curr; break; } curr = curr.next; }
        }
        if (best == null) {
            curr = carList.head;
            while (curr != null) {
                if (best == null || curr.performance > best.performance) best = curr;
                curr = curr.next;
            }
        }
        return best;
    }

    static void loadCars() throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("cars.txt"));
        String line = br.readLine();
        while ((line = br.readLine()) != null) {
            if (line.trim().isEmpty()) continue;
            String[] p = line.split(",");
            carList.insert(new Car(Integer.parseInt(p[0].trim()), p[1].trim(),
                Integer.parseInt(p[2].trim()), Integer.parseInt(p[3].trim()),
                Integer.parseInt(p[4].trim()), p[5].trim()));
        }
        br.close();
    }

    static void loadTracks() throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("tracks.txt"));
        String line = br.readLine();
        while ((line = br.readLine()) != null) {
            if (line.trim().isEmpty()) continue;
            String[] p = line.split(",");
            trackList.insert(new Track(Integer.parseInt(p[0].trim()), p[1].trim(),
                p[2].trim(), Integer.parseInt(p[3].trim()), Integer.parseInt(p[4].trim())));
        }
        br.close();
    }
}
