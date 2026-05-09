import java.util.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class Main {
    static CarList carList = new CarList();
    static TrackList trackList = new TrackList();
    static RaceLog raceLog = new RaceLog();
    static Random random = new Random();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
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
            int id = readInt();
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
            int id = readInt();
            playerCar = carList.getById(id);
            if (playerCar == null) System.out.print("Invalid ID. Try again: ");
        }
        carList.remove(playerCar);
        System.out.println("You selected: " + playerCar.name);

        Car computerCar = computerChoose(playerCar, playerTrack);
        carList.remove(computerCar);
        System.out.println("Computer chose: " + computerCar.name);

        System.out.println("\n=== RACE 1a: " + playerCar.name + " vs " + computerCar.name + " on " + playerTrack.name + " ===");
        RaceResult result1a = doRace(playerCar, computerCar, playerTrack);
        int playerScore  = result1a.winner == playerCar   ? result1a.winnerScore : result1a.loserScore;
        int playerIter   = result1a.winner == playerCar   ? result1a.winnerIter  : result1a.loserIter;
        int compScore    = result1a.winner == computerCar ? result1a.winnerScore : result1a.loserScore;
        int compIter     = result1a.winner == computerCar ? result1a.winnerIter  : result1a.loserIter;
        raceLog.add("Race 1a", playerTrack.name, playerCar.name, playerScore, playerIter,
                    computerCar.name, compScore, compIter, result1a.winner.name);
        System.out.println("WINNER of Race 1a: " + result1a.winner.name);

        int remainingCount = carList.size;
        Car computer2 = carList.getByIndex(random.nextInt(remainingCount));
        carList.remove(computer2);
        Car computer3 = carList.getByIndex(random.nextInt(remainingCount - 1));
        carList.remove(computer3);
        Track race1bTrack = trackList.getRandom(random);
        trackList.remove(race1bTrack);

        System.out.println("\n=== RACE 1b: " + computer2.name + " vs " + computer3.name + " on " + race1bTrack.name + " ===");
        System.out.println("Remaining tracks:");
        trackList.print();
        RaceResult result1b = doRace(computer2, computer3, race1bTrack);
        int comp2Score = result1b.winner == computer2 ? result1b.winnerScore : result1b.loserScore;
        int comp2Iter  = result1b.winner == computer2 ? result1b.winnerIter  : result1b.loserIter;
        int comp3Score = result1b.winner == computer3 ? result1b.winnerScore : result1b.loserScore;
        int comp3Iter  = result1b.winner == computer3 ? result1b.winnerIter  : result1b.loserIter;
        raceLog.add("Race 1b", race1bTrack.name, computer2.name, comp2Score, comp2Iter,
                    computer3.name, comp3Score, comp3Iter, result1b.winner.name);
        System.out.println("WINNER of Race 1b: " + result1b.winner.name);

        Car winner1a = result1a.winner;
        Car winner1b = result1b.winner;
        Track finalTrack;
        if (winner1a == playerCar) {
            System.out.println("\nYou won Race 1a! Choose the final track:");
            trackList.print();
            System.out.print("Enter track ID: ");
            finalTrack = null;
            while (finalTrack == null) {
                int id = readInt();
                finalTrack = trackList.getById(id);
                if (finalTrack == null) System.out.print("Invalid ID. Try again: ");
            }
        } else {
            finalTrack = trackList.getRandom(random);
            System.out.println("\nComputer selects final track: " + finalTrack.name);
        }
        trackList.remove(finalTrack);
        System.out.println("Remaining tracks:");
        trackList.print();

        System.out.println("\n=== FINAL: " + winner1a.name + " vs " + winner1b.name + " on " + finalTrack.name + " ===");
        RaceResult finalResult = doRace(winner1a, winner1b, finalTrack);
        int winner1aScore = finalResult.winner == winner1a ? finalResult.winnerScore : finalResult.loserScore;
        int winner1aIter  = finalResult.winner == winner1a ? finalResult.winnerIter  : finalResult.loserIter;
        int winner1bScore = finalResult.winner == winner1b ? finalResult.winnerScore : finalResult.loserScore;
        int winner1bIter  = finalResult.winner == winner1b ? finalResult.winnerIter  : finalResult.loserIter;
        raceLog.add("Final", finalTrack.name, winner1a.name, winner1aScore, winner1aIter,
                    winner1b.name, winner1bScore, winner1bIter, finalResult.winner.name);
        System.out.println("TOURNAMENT WINNER: " + finalResult.winner.name);

        raceLog.print();
    }

    static RaceResult doRace(Car car1, Car car2, Track track) {
        int score1 = car1.performance + getTrackBonus(car1, track) + getMatchupBonus(car1.type, car2.type);
        int score2 = car2.performance + getTrackBonus(car2, track) + getMatchupBonus(car2.type, car1.type);
        System.out.println("Initial scores -> " + car1.name + ": " + score1 + " | " + car2.name + ": " + score2);

        RaceTrack raceTrack = new RaceTrack(random);
        int pos1 = 0, pos2 = 0, iter1 = 0, iter2 = 0;

        while (true) {
            int steps1 = random.nextInt(3) + 1;
            score1 -= steps1 * 5;
            pos1 += steps1;
            iter1++;
            System.out.println("Iter " + iter1 + " | " + car1.name + " -> pos " + pos1 + " (score: " + score1 + ")");
            if (pos1 < 50 && score1 > 0) {
                TrackNode node = raceTrack.getNodeAt(pos1);
                if (node != null && node.effect.equals("teleport")) {
                    int prevPosition = pos1;
                    pos1 += node.teleportValue;
                    score1 -= 5;
                    if (pos1 < 1) pos1 = 1;
                    System.out.println("  Teleport! " + car1.name + ": " + prevPosition + " -> " + pos1 + " (score: " + score1 + ")");
                } else if (node != null && node.effect.equals("reset")) {
                    pos1 = 0;
                    score1 -= 5;
                    System.out.println("  Reset! " + car1.name + " back to start (score: " + score1 + ")");
                }
            }

            int steps2 = random.nextInt(3) + 1;
            score2 -= steps2 * 5;
            pos2 += steps2;
            iter2++;
            System.out.println("Iter " + iter2 + " | " + car2.name + " -> pos " + pos2 + " (score: " + score2 + ")");
            if (pos2 < 50 && score2 > 0) {
                TrackNode node = raceTrack.getNodeAt(pos2);
                if (node != null && node.effect.equals("teleport")) {
                    int prevPosition = pos2;
                    pos2 += node.teleportValue;
                    score2 -= 5;
                    if (pos2 < 1) pos2 = 1;
                    System.out.println("  Teleport! " + car2.name + ": " + prevPosition + " -> " + pos2 + " (score: " + score2 + ")");
                } else if (node != null && node.effect.equals("reset")) {
                    pos2 = 0;
                    score2 -= 5;
                    System.out.println("  Reset! " + car2.name + " back to start (score: " + score2 + ")");
                }
            }

            if (pos1 >= 50 || score1 <= 0 || pos2 >= 50 || score2 <= 0) break;
        }

        Car winner;
        int winnerIter, loserIter;

        if (pos1 >= 50 && pos2 < 50) {
            winner = car1; winnerIter = iter1; loserIter = iter2;
        } else if (pos2 >= 50 && pos1 < 50) {
            winner = car2; winnerIter = iter2; loserIter = iter1;
        } else if (pos1 >= 50) {
            // both reached unit 50 in the same iteration (iter1==iter2 always) — car1 wins
            winner = car1; winnerIter = iter1; loserIter = iter2;
        } else if (score1 >= score2) {
            // higher remaining score wins; equal score also gives car1 (iter1==iter2 always)
            winner = car1; winnerIter = iter1; loserIter = iter2;
        } else {
            winner = car2; winnerIter = iter2; loserIter = iter1;
        }

        score1 = Math.max(score1, 0);
        score2 = Math.max(score2, 0);
        int winnerScore = (winner == car1) ? score1 : score2;
        int loserScore  = (winner == car1) ? score2 : score1;
        System.out.println("Race ended -> " + car1.name + ": " + score1 + " pts, " + iter1 + " iters | "
                         + car2.name + ": " + score2 + " pts, " + iter2 + " iters");
        return new RaceResult(winner, winnerScore, loserScore, winnerIter, loserIter);
    }

    static int readInt() {
        while (true) {
            try { return scanner.nextInt(); }
            catch (InputMismatchException e) { scanner.nextLine(); System.out.print("Please enter a valid number: "); }
        }
    }

    static int getTrackBonus(Car car, Track track) {
        return car.type.equals(track.type) ? track.boost : 0;
    }

    static int getMatchupBonus(String carType, String opponentType) {
        if (carType.equals("Electric") && opponentType.equals("Water"))    return 15;
        if (carType.equals("Water")    && opponentType.equals("Fire"))     return 15;
        if (carType.equals("Fire")     && opponentType.equals("Earth"))    return 15;
        if (carType.equals("Earth")    && opponentType.equals("Electric")) return 15;
        if (carType.equals("Air")      && opponentType.equals("Earth"))    return 10;
        if (carType.equals("Heavy")    && opponentType.equals("Air"))      return 10;
        return 0;
    }

    static String counterType(String carType) {
        if (carType.equals("Electric")) return "Earth";
        if (carType.equals("Water"))    return "Electric";
        if (carType.equals("Fire"))     return "Water";
        if (carType.equals("Earth"))    return "Fire";
        if (carType.equals("Air"))      return "Heavy";
        return "";
    }

    static Car computerChoose(Car playerCar, Track track) {
        String targetType = counterType(playerCar.type);
        Car bestCar = null, current;

        if (!targetType.isEmpty()) {
            current = carList.head;
            while (current != null) {
                if (current.type.equals(targetType) && current.type.equals(track.type)) { bestCar = current; break; }
                current = current.next;
            }
            if (bestCar == null) {
                current = carList.head;
                while (current != null) { if (current.type.equals(targetType)) { bestCar = current; break; } current = current.next; }
            }
        }

        if (bestCar == null) {
            current = carList.head;
            while (current != null) {
                if (bestCar == null || (current.performance + getTrackBonus(current, track)) > (bestCar.performance + getTrackBonus(bestCar, track)))
                    bestCar = current;
                current = current.next;
            }
        }
        return bestCar;
    }

    static void loadCars() throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader("cars.txt"));
        String line = reader.readLine();
        while ((line = reader.readLine()) != null) {
            if (line.trim().isEmpty()) continue;
            String[] parts = line.split(",");
            carList.insert(new Car(Integer.parseInt(parts[0].trim()), parts[1].trim(),
                Integer.parseInt(parts[2].trim()), Integer.parseInt(parts[3].trim()),
                Integer.parseInt(parts[4].trim()), parts[5].trim()));
        }
        reader.close();
    }

    static void loadTracks() throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader("tracks.txt"));
        String line = reader.readLine();
        while ((line = reader.readLine()) != null) {
            if (line.trim().isEmpty()) continue;
            String[] parts = line.split(",");
            trackList.insert(new Track(Integer.parseInt(parts[0].trim()), parts[1].trim(),
                parts[2].trim(), Integer.parseInt(parts[4].trim())));
        }
        reader.close();
    }
}
