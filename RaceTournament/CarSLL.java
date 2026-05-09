public class CarSLL {
    Car head;

    void insert(Car car) {
        if (head == null || car.performance < head.performance) {
            car.next = head; head = car; return;
        }
        Car curr = head;
        while (curr.next != null && curr.next.performance <= car.performance)
            curr = curr.next;
        car.next = curr.next; curr.next = car;
    }

    void remove(Car target) {
        if (head == null) return;
        if (head == target) { head = head.next; return; }
        Car curr = head;
        while (curr.next != null && curr.next != target) curr = curr.next;
        if (curr.next != null) curr.next = curr.next.next;
    }

    Car getById(int id) {
        Car curr = head;
        while (curr != null) { if (curr.id == id) return curr; curr = curr.next; }
        return null;
    }

    Car getByIndex(int idx) {
        Car curr = head;
        for (int i = 0; i < idx && curr != null; i++) curr = curr.next;
        return curr;
    }

    int size() {
        int n = 0; Car curr = head;
        while (curr != null) { n++; curr = curr.next; }
        return n;
    }

    void print() {
        Car curr = head;
        while (curr != null) {
            System.out.println("  [" + curr.id + "] " + curr.name + " | Performance: " + curr.performance + " | Type: " + curr.type);
            curr = curr.next;
        }
    }
}
