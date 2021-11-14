import java.util.ArrayList;
import java.util.List;

public class Hippodrome {
    private List<Horse> horses;
    public static Hippodrome game;
    private String winner;

    public Hippodrome(List<Horse> horses) {
        this.horses = horses;
    }

    public static void main(String[] args) {
        List<Horse> gameHorses = new ArrayList<>();
        Horse pony = new Horse("Pony", 3d, 0d);
        Horse rocket = new Horse("Rocket", 3d, 0d);
        Horse bullet = new Horse("Bullet", 3d, 0d);
        gameHorses.add(pony);
        gameHorses.add(rocket);
        gameHorses.add(bullet);
        game = new Hippodrome(gameHorses);
        game.run();
        game.printWinner();
    }

    public List<Horse> getHorses() {
        return horses;
    }

    public Horse getWinner() {
        double max = 0;
        int index = 0;
        for (int i = 0; i < getHorses().size(); i++) {
            if (getHorses().get(i).distance > max) {
                max = getHorses().get(i).distance;
                index = i;
            }
        }
        return getHorses().get(index);
    }

    public void printWinner() {
        System.out.printf("Winner is %s!\n", getWinner().getName());
    }

    public void run() {
        for (int i = 0; i < 100; i++) {
            move();
            print();
            try {
                Thread.sleep(200);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void move() {
        for (Horse horse : getHorses()) {
            horse.move();
        }
    }

    public void print() {
        for (Horse horse : getHorses()) {
            horse.print();
        }
        for (int i = 0; i < 10; i++) {
            System.out.println();
        }
    }
}
