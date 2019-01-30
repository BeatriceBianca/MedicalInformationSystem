package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.medicalsystem.main.model.Pacient;
import com.medicalsystem.main.model.PacientInfo;
import com.medicalsystem.main.model.Reader;
import com.medicalsystem.main.model.Writer;
import com.medicalsystem.main.service.Database;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

@Controller
public class BaseController {

    private List<Database> listOfDatabase = new ArrayList<>();
    private List<String> listOfCurrentUsers = new ArrayList<>();
    private List<PacientInfo> listOfPacientsInfo = new ArrayList<>();
    private final File folder = new File("D:/Facultate/Master/ProgrParalela/MedicalInformationSystem/src/main/resources/static/db");

    private Map<String, Reader> listOfReaders = new LinkedHashMap<>();
    private Map<String, Writer> listOfWriters = new LinkedHashMap<>();

    @RequestMapping(value = "/")
    public String redirectToLogin() throws IOException, ParseException {
        JSONParser parser = new JSONParser();

        for (final File fileEntry : folder.listFiles()) {
            Object obj = parser.parse(new FileReader(fileEntry));

            JSONObject jsonObject = (JSONObject) obj;
            PacientInfo pacientInfo = new PacientInfo();
            pacientInfo.setId((String) jsonObject.get("id"));
            pacientInfo.setLastName((String) jsonObject.get("lastName"));
            pacientInfo.setFirstName((String) jsonObject.get("firstName"));

            if (!listOfPacientsInfo.contains(pacientInfo)) {
                listOfPacientsInfo.add(pacientInfo);
                Database database = new Database(fileEntry, fileEntry.toString().split("db")[1].split(".txt")[0].substring(1));
                listOfDatabase.add(database);
            }
        }

        return "login";
    }

    @ResponseBody
    @RequestMapping(value = "/pacientsInfo", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PacientInfo> getPacientsInfo() {
        return listOfPacientsInfo;
    }

    @RequestMapping(value = "/index/{username}")
    public String redirectToIndex() {
        return "index";
    }

    @RequestMapping(value = "/{username}/{pacientId}/{processId}")
    public String redirectToPacientInfo() {
        return "pacientInfo";
    }

    @ResponseBody
    @RequestMapping(value = "/pacientInfo/{username}/{pacient}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Pacient getPacient(@PathVariable("username") String username, @PathVariable("pacient") String pacientId) {
        Pacient pacient = new Pacient();
        try {
            ObjectMapper mapper = new ObjectMapper();
            pacient = mapper.readValue(new File(folder.getAbsolutePath()+"\\"+pacientId+".txt"), Pacient.class);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return pacient;
    }

    @ResponseBody
    @RequestMapping(value = "/startReading/{pacientId}", method = RequestMethod.GET)
    public String startReading(@PathVariable("pacientId") String pacientId) {
        String id = null;
        final boolean[] ok = {false};
        while (!ok[0]) {
            ok[0] = true;
            listOfWriters.forEach((idWriter, writer) -> {
                if (writer.getDatabase().getFileNumber().equals(pacientId)) {
                    ok[0] = false;
                }
            });
        }
        for(Database database: listOfDatabase) {
            if (database.getFileNumber().equals(pacientId)) {
                Reader reader = new Reader(database);
                id = UUID.randomUUID().toString();
                listOfReaders.put(id, reader);
                reader.start();
            }
        }
        return id;
    }

    @ResponseBody
    @RequestMapping(value = "/stopReading", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public void stopReading(@RequestParam("processId") String processId) {
        listOfReaders.forEach((idReader, reader) -> {
            if (idReader.equals(processId)) {
                reader.terminate();
            }
        });

        listOfReaders.keySet().removeIf(key -> key.equals(processId));
    }

    @ResponseBody
    @RequestMapping(value = "/startWriting/{pacientId}", method = RequestMethod.GET)
    public String startWriting(@PathVariable("pacientId") String pacientId) {
        String id = null;
        final boolean[] ok = {false};
        while (!ok[0]) {
            ok[0] = true;
            listOfReaders.forEach((idReader, reader) -> {
                if (reader.getDatabase().getFileNumber().equals(pacientId)) {
                    ok[0] = false;
                }
            });
            listOfWriters.forEach((idWriter, writer) -> {
                if (writer.getDatabase().getFileNumber().equals(pacientId)) {
                    ok[0] = false;
                }
            });
        }
        for(Database database: listOfDatabase) {
            if (database.getFileNumber().equals(pacientId)) {
                Writer writer = new Writer(database);
                id = UUID.randomUUID().toString();
                listOfWriters.put(id, writer);
                writer.start();
            }
        }
        return id;
    }

    @ResponseBody
    @RequestMapping(value = "/stopWriting", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public void stopWriting(@RequestParam("processId") String processId,
                            @RequestParam("disease") String newDisease,
                            @RequestParam("pacientId") String pacientId) {

        ObjectMapper mapper = new ObjectMapper();
        Pacient pacient = null;
        try {
            pacient = mapper.readValue(new File(folder.getAbsolutePath()+"\\"+pacientId+".txt"), Pacient.class);
            if (!newDisease.equals(""))
                pacient.addDisease(newDisease);
            mapper.writeValue(new File(folder.getAbsolutePath()+"\\"+pacientId+".txt"), pacient);
        } catch (IOException e) {
            e.printStackTrace();
        }

        listOfWriters.forEach((idWriter, writer) -> {
            if (idWriter.equals(processId)) {
                writer.terminate();
            }
        });

        listOfWriters.keySet().removeIf(key -> key.equals(processId));
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
