import java.util.Random;

public class RaceTrack {
    TrackNode head, tail;

    RaceTrack(Random random) {
        for (int i = 1; i <= 50; i++) {
            TrackNode node = new TrackNode(i);
            if (head == null) { head = tail = node; }
            else { tail.next = node; node.prev = tail; tail = node; }
        }

        int teleportCount = 0;
        while (teleportCount < 10) {
            TrackNode node = getNodeAt(random.nextInt(50) + 1);
            if (node != null && node.effect.equals("normal")) {
                node.effect = "teleport";
                int teleportDistance = random.nextInt(5) + 1;
                node.teleportValue = random.nextBoolean() ? teleportDistance : -teleportDistance;
                teleportCount++;
            }
        }

        boolean resetPlaced = false;
        while (!resetPlaced) {
            TrackNode node = getNodeAt(random.nextInt(50) + 1);
            if (node != null && node.effect.equals("normal")) {
                node.effect = "reset";
                resetPlaced = true;
            }
        }
    }

    TrackNode getNodeAt(int position) {
        TrackNode current = head;
        while (current != null) { if (current.position == position) return current; current = current.next; }
        return null;
    }
}
