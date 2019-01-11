import java.io.*;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Random;

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
    private File file1;
    /**
     Initializes this database.
     */
    public Database(File file)
    {
        this.readers = 0;
        this.file1 = file;
    }

    /**
     Read from this database.

     @param number Number of the reader.
     */
    public void read(int number) throws IOException {
        synchronized(this)
        {
            this.readers++;
            FileReader fr =
                    new FileReader(file1);

            int i;
            while ((i=fr.read()) != -1)
                System.out.print((char) i);
            System.out.println();
        }

        final int DELAY = 5000;
        try
        {
            Thread.sleep((int) (Math.random() * DELAY));
        }
        catch (InterruptedException e) {}

        synchronized(this)
        {
            System.out.println("Reader " + number + " stops reading.");
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
    public synchronized void write(int number) throws IOException {
        while (this.readers != 0)
        {
            try
            {
                this.wait();
            }
            catch (InterruptedException e) {}
        }

        FileWriter writer = new FileWriter(file1);
        byte[] array = new byte[7]; // length is bounded by 7
        new Random().nextBytes(array);
        String generatedString = new String(array, Charset.forName("UTF-8"));
        writer.write(generatedString);
        writer.close();

        final int DELAY = 5000;
        try
        {
            Thread.sleep((int) (Math.random() * DELAY));
        }
        catch (InterruptedException e) {}

        System.out.println("Writer " + number + " stops writing.");
        this.notifyAll();
    }
}