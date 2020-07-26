import Doctors.IDoctor;
import Pacients.Patient;
import org.json.JSONException;

import java.io.FileNotFoundException;
import java.util.List;

public class Simulation {

    public static void main(String[] args) throws FileNotFoundException, JSONException {
        int noOfRounds = 0;
        int noOfNurses = 0;
        int noOfERTechnicians = 0;
        List<IDoctor> doctorsList;
        List<Patient> pacientsList;

        String path = "C:\\Users\\AMDinca\\Desktop\\test002.json";
        ReadFile fileReader = new ReadFile(path);

        noOfRounds = fileReader.getNoOfRounds();
        noOfNurses = fileReader.getNoOfNurses();
        noOfERTechnicians = fileReader.getNoOfERTechnicians();
        doctorsList = fileReader.getDoctorsList();
        pacientsList = fileReader.getPacientsList();

        Round simulationRound = new Round(noOfNurses, noOfERTechnicians, doctorsList, pacientsList);

        for(int i = 0; i < noOfRounds ; i++){
            simulationRound.run(i);
        }

    }
}
