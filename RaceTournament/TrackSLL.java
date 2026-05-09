import java.util.Random;

public class TrackSLL {
    Track head;
    int size;

    void insert(Track track) {
        track.next = head; head = track; size++;
    }

    void remove(Track target) {
        if (head == null) return;
        if (head == target) { head = head.next; size--; return; }
        Track curr = head;
        while (curr.next != null && curr.next != target) curr = curr.next;
        if (curr.next != null) { curr.next = curr.next.next; size--; }
    }

    Track getById(int id) {
        Track curr = head;
        while (curr != null) { if (curr.id == id) return curr; curr = curr.next; }
        return null;
    }

    Track getByIndex(int idx) {
        Track curr = head;
        for (int i = 0; i < idx && curr != null; i++) curr = curr.next;
        return curr;
    }

    Track getRandom(Random rand) {
        if (head == null) return null;
        return getByIndex(rand.nextInt(size));
    }

    void print() {
        Track curr = head;
        while (curr != null) {
            System.out.println("  [" + curr.id + "] " + curr.name + " | Type: " + curr.type + " | Boost: " + curr.boost);
            curr = curr.next;
        }
    }
}
