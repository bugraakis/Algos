import java.util.Random;

public class TrackList {
    Track head;
    int size;

    void insert(Track track) {
        track.next = head; head = track; size++;
    }

    void remove(Track target) {
        if (head == null) return;
        if (head == target) { head = head.next; size--; return; }
        Track current = head;
        while (current.next != null && current.next != target) current = current.next;
        if (current.next != null) { current.next = current.next.next; size--; }
    }

    Track getById(int id) {
        Track current = head;
        while (current != null) { if (current.id == id) return current; current = current.next; }
        return null;
    }

    Track getByIndex(int index) {
        Track current = head;
        for (int i = 0; i < index && current != null; i++) current = current.next;
        return current;
    }

    Track getRandom(Random random) {
        if (head == null) return null;
        return getByIndex(random.nextInt(size));
    }

    void print() {
        Track current = head;
        while (current != null) {
            System.out.println("  [" + current.id + "] " + current.name + " | Type: " + current.type + " | Boost: " + current.boost);
            current = current.next;
        }
    }
}
