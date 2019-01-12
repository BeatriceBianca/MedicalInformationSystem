
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
            File file1 = new File("src/main/java/pacients.txt") ;
            File file2 = new File("src/main/java/pacients2.txt") ;
            Database database1 = new Database(file1, "1");
            Database database2 = new Database(file2, "2");
            for (int i = 0; i < READERS; i++)
            {
                if (i%2 == 0)
                    new Reader(database1).start();
                else new Reader(database2).start();
            }
            for (int i = 0; i < WRITERS; i++)
            {
                if (i%2 == 0)
                    new Writer(database2).start();
                else new Writer(database1).start();
            }
        }

//        JSONParser parser = new JSONParser();
////
////        List<Pacient> pacients1 = new ArrayList<Pacient>();
////        List<Pacient> pacients2 = new ArrayList<Pacient>();
////        try {
////
////            Object obj1 = parser.parse(new FileReader(
////                    "D:/Facultate/Master/project/src/main/java/pacients.txt"));
////
////            Object obj2 = parser.parse(new FileReader(
////                    "D:/Facultate/Master/project/src/main/java/pacients.txt"));
////
////            JSONObject jsonObject1 = (JSONObject) obj1;
////            JSONArray objects1 = (JSONArray) jsonObject1.get("pacients");
////
////            for(int i = 0; i < objects1.size(); i++) {
////                JSONObject object = (JSONObject) objects1.get(i);
////                Pacient pacient = new Pacient();
////                pacient.setLastName((String) object.get("lastName"));
////                pacient.setFirstName((String) object.get("firstName"));
////                pacient.setAge((Long) object.get("age"));
////                JSONArray diseaseJson = (JSONArray) object.get("disease");
////
////                List<String> disease = new ArrayList<String>();
////                for(int j = 0; j < diseaseJson.size(); j++) {
////                    disease.add((String) diseaseJson.get(j));
////                }
////                pacient.setDisease(disease);
////                pacients1.add(pacient);
////            }
////
////            JSONObject jsonObject2 = (JSONObject) obj2;
////            JSONArray objects2 = (JSONArray) jsonObject2.get("pacients");
////
////            for(int i = 0; i < objects2.size(); i++) {
////                JSONObject object = (JSONObject) objects2.get(i);
////                Pacient pacient = new Pacient();
////                pacient.setLastName((String) object.get("lastName"));
////                pacient.setFirstName((String) object.get("firstName"));
////                pacient.setAge((Long) object.get("age"));
////                JSONArray diseaseJson = (JSONArray) object.get("disease");
////
////                List<String> disease = new ArrayList<String>();
////                for(int j = 0; j < diseaseJson.size(); j++) {
////                    disease.add((String) diseaseJson.get(j));
////                }
////                pacient.setDisease(disease);
////                pacients2.add(pacient);
////            }
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
////        System.out.print(pacients1);
////        System.out.print(pacients2);

    }
}
