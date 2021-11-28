package carsharing;

import carsharing.view.Carsharing;

public class Main {

    public static void main(String[] args) {
        Carsharing carsharing = new Carsharing();
        carsharing.startMenu(args.length > 1 ? args[1] : "carsharing");
    }
}