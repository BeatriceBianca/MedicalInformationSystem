package com.company;

public class Main {

    public static void main(String[] args) {
        if (args.length < 2)
        {
            System.out.println("Usage: java Simulator <number of readers> <number of writers>");
        }
        else
        {
            final int READERS = Integer.parseInt(args[0]);
            final int WRITERS = Integer.parseInt(args[1]);
            Database database = new Database();
            for (int i = 0; i < READERS; i++)
            {
                new Reader(database).start();
            }
            for (int i = 0; i < WRITERS; i++)
            {
                new Writer(database).start();
            }
        }
    }
}
