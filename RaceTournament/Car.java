public class Car {
    int id, power, control, speed, performance;
    String name, type;
    Car next;

    Car(int id, String name, int power, int control, int speed, String type) {
        this.id = id; this.name = name;
        this.power = power; this.control = control; this.speed = speed;
        this.performance = power + control + speed;
        this.type = type;
    }
}
