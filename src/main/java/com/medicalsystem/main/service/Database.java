package com.medicalsystem.main.service;

import java.io.File;
import java.io.IOException;

/**
 This class represents a database.  There are many
 competing threads wishing to read and write.  It is
 acceptable to have multiple processes reading at the
 same time, but if one thread is writing then no other
 process may either read or write.
 */
public class Database
{
    private int readers;
    private int writers;
    private File file1;
    private String fileNumber;

    public String getFileNumber() {
        return fileNumber;
    }

    /**
     Initializes this database.
     */
    public Database(File file1, String fileNumber)
    {
        this.writers = 0;
        this.readers = 0;
        this.file1 = file1;
        this.fileNumber = fileNumber;
    }

    /**
     Read from this database.

     @param number Number of the reader.
     */
    public void startReading(int number) throws IOException {
        synchronized (this) {
            while (this.writers != 0)
            {
                try
                {
                    this.wait();
                }
                catch (InterruptedException e) {}
            }
            this.readers++;
            System.out.println("com.medicalsystem.main.model.Reader " + number + " starts reading on file " + fileNumber + " : ");
        }
    }

    public void stopReading(int number) throws IOException {

        synchronized(this)
        {
            System.out.println("com.medicalsystem.main.model.Reader " + number + " stops reading on file " + fileNumber + ".");
            this.readers--;
            if (this.readers == 0)
            {
                this.notifyAll();
            }
        }
    }

    /**
     Writes to this database.

     @param number Number of the writer.
     */
    public synchronized void startWriting(int number) throws IOException {
        while (this.readers != 0)
        {
            try
            {
                this.wait();
            }
            catch (InterruptedException e) {}
        }

        this.writers++;
        System.out.println("com.medicalsystem.main.model.Writer " + number + " starts writing on file " + fileNumber + ".");
    }

    public synchronized void stopWriting(int number) throws IOException {
        this.writers--;
        this.notifyAll();
        System.out.println("com.medicalsystem.main.model.Writer " + number + " stops writing on file " + fileNumber + ".");
    }
}