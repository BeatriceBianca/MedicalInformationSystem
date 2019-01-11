import java.io.IOException;

public class Reader extends Thread
{
    private static int readers = 0; // number of readers

    private int number;
    private Database database;

    /**
     Creates a Reader for the specified database.

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
        while (true)
        {
            final int DELAY = 5000;
            try
            {
                Thread.sleep((int) (Math.random() * DELAY));
            }
            catch (InterruptedException e) {}
            try {
                this.database.read(this.number);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
