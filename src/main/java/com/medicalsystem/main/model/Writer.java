package com.medicalsystem.main.model;

import com.medicalsystem.main.service.Database;

import java.io.IOException;

/**
 This class represents a writer.
 */
public class Writer extends Thread
{
    private volatile boolean running = true;
    private volatile boolean stopped = false;
    private static int writers = 0; // number of writers

    private int number;
    private Database database;

    public void terminate() {
        running = false;
    }

    public Database getDatabase() {
        return database;
    }

    /**
     Creates a com.medicalsystem.main.model.Writer for the specified database.

     @param database database to which to write.
     */
    public Writer(Database database)
    {
        this.database = database;
        this.number = Writer.writers++;
    }

    /**
     Writes.
     */
    public void run()
    {
        if (running)
        {
            try {
                this.database.startWriting(this.number);
            } catch (IOException e) {
                running = false;
                e.printStackTrace();
            }
        }
        while (!stopped) {
            if (!running) {
                try {
                    this.database.stopWriting(this.number);
                    stopped = true;
                } catch (IOException e) {
                    running = true;
                    e.printStackTrace();
                }
            }
        }
    }
}