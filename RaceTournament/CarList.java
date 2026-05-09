public class CarList {
    Car head;
    int size;

    void insert(Car car) {
        if (head == null || car.performance < head.performance) {
            car.next = head; head = car; size++; return;
        }
        Car current = head;
        while (current.next != null && current.next.performance <= car.performance)
            current = current.next;
        car.next = current.next; current.next = car; size++;
    }

    void remove(Car target) {
        if (head == null) return;
        if (head == target) { head = head.next; size--; return; }
        Car current = head;
        while (current.next != null && current.next != target) current = current.next;
        if (current.next != null) { current.next = current.next.next; size--; }
    }

    Car getById(int id) {
        Car current = head;
        while (current != null) { if (current.id == id) return current; current = current.next; }
        return null;
    }

    Car getByIndex(int index) {
        Car current = head;
        for (int i = 0; i < index && current != null; i++) current = current.next;
        return current;
    }

    void print() {
        Car current = head;
        while (current != null) {
            System.out.println("  [" + current.id + "] " + current.name + " | Performance: " + current.performance + " | Type: " + current.type);
            current = current.next;
        }
    }
}
