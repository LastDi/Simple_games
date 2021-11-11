package com.javarush.task.task21.task2113;

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
        Horse skakun = new Horse("Skakun", 3d, 0d);
        Horse prigun = new Horse("Prigun", 3d, 0d);
        Horse tormoz = new Horse("Tormoz", 3d, 0d);
        gameHorses.add(skakun);
        gameHorses.add(prigun);
        gameHorses.add(tormoz);
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
