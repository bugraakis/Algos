import java.util.Random;

public class RaceDLL {
    RaceUnit head, tail;

    RaceDLL(Random rand) {
        for (int i = 1; i <= 50; i++) {
            RaceUnit u = new RaceUnit(i);
            if (head == null) { head = tail = u; }
            else { tail.next = u; u.prev = tail; tail = u; }
        }
        int count = 0;
        while (count < 10) {
            RaceUnit u = getAt(rand.nextInt(50) + 1);
            if (u.effect.equals("normal")) {
                u.effect = "teleport";
                int val = rand.nextInt(5) + 1;
                u.teleportValue = rand.nextBoolean() ? val : -val;
                count++;
            }
        }
        while (true) {
            RaceUnit u = getAt(rand.nextInt(50) + 1);
            if (u.effect.equals("normal")) { u.effect = "reset"; break; }
        }
    }

    RaceUnit getAt(int pos) {
        RaceUnit curr = head;
        while (curr != null) { if (curr.position == pos) return curr; curr = curr.next; }
        return null;
    }
}
