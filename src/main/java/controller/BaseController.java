package controller;

import com.medicalsystem.main.model.Reader;
import com.medicalsystem.main.model.Writer;
import com.medicalsystem.main.service.Database;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;

@Controller
public class BaseController {

    @RequestMapping(value = "/")
    public String redirectToIndex() {
        return "index";
    }

    @ResponseBody
    @RequestMapping(value = "/start", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public void startProcess(@RequestParam("readers") int readers, @RequestParam("writers") int writers) {
        final int READERS = readers;
        final int WRITERS = writers;
//        File file1 = new File("src/resources/static/db/pacients.txt") ;
        File file1 = new File("D:/Facultate/Master/ProgrParalela/new/MedicalInformationSystem/src/main/resources/static/db/pacients.txt") ;
//        File file2 = new File("src/resources/static/db/pacients2.txt") ;
        File file2 = new File("D:/Facultate/Master/ProgrParalela/new/MedicalInformationSystem/src/main/resources/static/db/pacients.txt") ;
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

    @RequestMapping(value = "/stop", method = RequestMethod.POST)
    public void stopProcess() {

    }
}
