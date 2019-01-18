package com.medicalsystem.main.model;

import com.medicalsystem.main.service.Database;

import java.io.IOException;

/**
 This class represents a writer.
 */
public class Writer extends Thread
{
    private static int writers = 0; // number of writers

    private int number;
    private Database database;

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
        while (true)
        {
            final int DELAY = 5000;
            try
            {
                Thread.sleep((int) (Math.random() * DELAY));
            }
            catch (InterruptedException e) {}
            try {
                this.database.write(this.number);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}