package controller;

import com.medicalsystem.main.model.Pacient;
import com.medicalsystem.main.model.PacientInfo;
import com.medicalsystem.main.model.Reader;
import com.medicalsystem.main.model.Writer;
import com.medicalsystem.main.service.Database;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class BaseController {

    private List<String> listOfCurrentUsers = new ArrayList<>();
    private List<PacientInfo> listOfPacientsInfo = new ArrayList<>();
    private final File folder = new File("D:/Facultate/Master/ProgrParalela/MedicalInformationSystem/src/main/resources/static/db");

    private final SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");

    @RequestMapping(value = "/")
    public String redirectToLogin() throws IOException, ParseException {
        JSONParser parser = new JSONParser();

        for (final File fileEntry : folder.listFiles()) {
            Object obj = parser.parse(new FileReader(fileEntry));

            JSONObject jsonObject = (JSONObject) obj;
            PacientInfo pacientInfo = new PacientInfo();
            pacientInfo.setId((String) jsonObject.get("id"));
            pacientInfo.setLastName((String) jsonObject.get("last_name"));
            pacientInfo.setFirstName((String) jsonObject.get("first_name"));

            if (!listOfPacientsInfo.contains(pacientInfo)) {
                listOfPacientsInfo.add(pacientInfo);
            }
        }

        return "login";
    }

    @ResponseBody
    @RequestMapping(value = "/pacientsInfo", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PacientInfo> getPacientsInfo() {
        return listOfPacientsInfo;
    }

    @ResponseBody
    @RequestMapping(value = "/pacientInfo/{username}/{pacient}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Pacient getPacient(@PathVariable("username") String username, @PathVariable("pacient") String pacientId) {
        JSONParser parser = new JSONParser();
        Pacient pacient = new Pacient();
        try {
            Object obj = parser.parse(new FileReader(folder.getAbsolutePath()+"\\"+pacientId+".txt"));
            JSONObject jsonObject = (JSONObject) obj;
            pacient.setId((String) jsonObject.get("id"));
            pacient.setLastName((String) jsonObject.get("last_name"));
            pacient.setFirstName((String) jsonObject.get("first_name"));
            pacient.setBirthDate(formatter.parse((String) jsonObject.get("birth_date")));

            List<String> pacientDiseases = new ArrayList<>();
            JSONArray diseasesList = (JSONArray) jsonObject.get("diseases");
            for(Object o: diseasesList){
                if ( o instanceof JSONObject ) {
                    JSONObject disease = (JSONObject) o;
                    pacientDiseases.add((String) disease.get("id"));
                }
            }
            pacient.setDisease(pacientDiseases);
        } catch (IOException | java.text.ParseException | ParseException e) {
            e.printStackTrace();
        }
        return pacient;
    }

    @RequestMapping(value = "/index/{username}")
    public String redirectToIndex() {
        return "index";
    }

    @RequestMapping(value = "/{username}/{pacientId}")
    public String redirectToPacientInfo() {
        return "pacientInfo";
    }

    @ResponseBody
    @RequestMapping(value = "/start", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public void startProcess(@RequestParam("readers") int readers, @RequestParam("writers") int writers) {
        final int READERS = readers;
        final int WRITERS = writers;
//        File file1 = new File("src/resources/static/db/pacient1.txt") ;
        File file1 = new File("D:/Facultate/Master/ProgrParalela/new/MedicalInformationSystem/src/main/resources/static/db/pacient1.txt") ;
//        File file2 = new File("src/resources/static/db/pacient2.txt") ;
        File file2 = new File("D:/Facultate/Master/ProgrParalela/new/MedicalInformationSystem/src/main/resources/static/db/pacient1.txt") ;
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

    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public void login(@RequestParam("username") String username) {
        if (!listOfCurrentUsers.contains(username)) {
            listOfCurrentUsers.add(username);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/logout", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public void logout(@RequestParam("username") String username) {
        listOfCurrentUsers.remove(username);
    }
}
