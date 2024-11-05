package Elevator;

@SuppressWarnings("FieldMayBeFinal")

public class Elevator {
    private int minFloor;
    private int maxFloor;
    private int currentFloor;

    public Elevator(int minFloor, int maxFloor) {
        this.currentFloor = 1;
        this.minFloor = minFloor;
        this.maxFloor = maxFloor;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    private void moveDown() {
        currentFloor = Math.max(currentFloor - 1, minFloor);
    }

    private void moveUp() {
        currentFloor = Math.min(currentFloor + 1, maxFloor);
    }

    public void move(int floor) {
        if (floor >= minFloor && floor <= maxFloor) {
            while (currentFloor != floor) {
                if (currentFloor > floor) {
                    moveDown();
                    System.out.println("Лифт опустился на этаж " + currentFloor);
                } else {
                    moveUp();
                    System.out.println("Лифт поднялся на этаж " + currentFloor);
                }
            }
        } else {
            System.out.println("Завдан неверный этаж");
        }
    }
}