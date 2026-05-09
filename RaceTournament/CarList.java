public class CarList {
    Car head;

    void insert(Car car) {
        if (head == null || car.performance < head.performance) {
            car.next = head; head = car; return;
        }
        Car current = head;
        while (current.next != null && current.next.performance <= car.performance)
            current = current.next;
        car.next = current.next; current.next = car;
    }

    void remove(Car target) {
        if (head == null) return;
        if (head == target) { head = head.next; return; }
        Car current = head;
        while (current.next != null && current.next != target) current = current.next;
        if (current.next != null) current.next = current.next.next;
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

    int size() {
        int count = 0; Car current = head;
        while (current != null) { count++; current = current.next; }
        return count;
    }

    void print() {
        Car current = head;
        while (current != null) {
            System.out.println("  [" + current.id + "] " + current.name + " | Performance: " + current.performance + " | Type: " + current.type);
            current = current.next;
        }
    }
}
