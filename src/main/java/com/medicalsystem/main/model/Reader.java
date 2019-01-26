package com.medicalsystem.main.model;

import com.medicalsystem.main.service.Database;

import java.io.IOException;

public class Reader extends Thread
{
    private volatile boolean running = true;
    private volatile boolean stopped = false;
    private static int readers = 0; // number of readers

    private int number;
    private Database database;

    public void terminate() {
        running = false;
    }

    public Database getDatabase() {
        return database;
    }

    /**
     Creates a com.medicalsystem.main.model.Reader for the specified database.

     @param database database from which to be read.
     */
    public Reader(Database database)
    {
        this.database = database;
        this.number = Reader.readers++;
    }

    /**
     Reads.
     */
    public void run()
    {
        if (running)
        {
            try {
                this.database.startReading(this.number);
            } catch (IOException e) {
                running = false;
                e.printStackTrace();
            }
        }
        while (!stopped) {
            if (!running) {
                try {
                    this.database.stopReading(this.number);
                    stopped = true;
                } catch (IOException e) {
                    running = true;
                    e.printStackTrace();
                }
            }
        }
    }
}
