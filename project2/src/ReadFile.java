import Doctors.IDoctor;
import Doctors.DoctorFactory;
import Pacients.Patient;
import Pacients.PatientFactory;
import entities.enums.IllnessType;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ReadFile {

    private String path;
    private int noOfRounds = 0;
    private int noOfNurses = 0;
    private int noOfERTechnicians = 0;
    private JSONObject obj;
    private List<IDoctor> doctorsList = new ArrayList<>();
    private List<Patient> pacientsList = new ArrayList<>();

    public ReadFile(String path) throws JSONException {
        this.path = path;
        obj = new JSONObject(readAllBytesJava7(path));
    }

    private static String readAllBytesJava7(String filePath) {
        String content = "";
        try {
            content = new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    private int readNoOfRounds() throws JSONException {
        return obj.getInt("simulationLength");
    }

    private int readNoOfNurses() throws JSONException {
        return obj.getInt("nurses");
    }

    private int readNoOfERTechnicians() throws JSONException {
        return obj.getInt("investigators");
    }

    private List<IDoctor> readDoctorsList() throws JSONException {
        JSONArray doctors = obj.getJSONArray("doctors");
        int noOfDoctors = obj.getJSONArray("doctors").length();
        for (int i = 0; i < noOfDoctors; i++) {
            String type = doctors.getJSONObject(i).getString("type");
            boolean isSurgeon = doctors.getJSONObject(i).getBoolean("isSurgeon");
            int maxForTreatment = doctors.getJSONObject(i).getInt("maxForTreatment");
            doctorsList.add(DoctorFactory.doctorFactory(type,isSurgeon,maxForTreatment));
        }
        return doctorsList;
    }

    private IllnessType setPatientIllnessType(String illness){
        switch (illness){
            case "ABDOMINAL_PAIN": return IllnessType.ABDOMINAL_PAIN;
            case "ALLERGIC_REACTION": return IllnessType.ALLERGIC_REACTION;
            case "BROKEN_BONES": return IllnessType.BROKEN_BONES;
            case "BURNS": return IllnessType.BURNS;
            case "CAR_ACCIDENT": return IllnessType.CAR_ACCIDENT;
            case "CUTS": return IllnessType.CUTS;
            case "FOOD_POISONING": return IllnessType.FOOD_POISONING;
            case "HEART_ATTACK": return IllnessType.HEART_ATTACK;
            case "HEART_DISEASE": return IllnessType.HEART_DISEASE;
            case "HIGH_FEVER": return IllnessType.HIGH_FEVER;
            case "PNEUMONIA": return IllnessType.PNEUMONIA;
            case "SPORT_INJURIES": return IllnessType.SPORT_INJURIES;
            case "STROKE": return IllnessType.STROKE;
            default:
                System.out.println("The illness has not been identified");
        }
        return null;
    }

    private List<Patient> readPacientsList() throws JSONException {
        JSONArray pacients = obj.getJSONArray("incomingPatients");
        int noOfPacients = obj.getJSONArray("incomingPatients").length();
        for (int i = 0; i < noOfPacients; i++) {
            int id = pacients.getJSONObject(i).getInt("id");
            String name = pacients.getJSONObject(i).getString("name");
            int age = pacients.getJSONObject(i).getInt("age");
            int roundHeEnters = pacients.getJSONObject(i).getInt("time");
            JSONObject state = pacients.getJSONObject(i).getJSONObject("state");
            String illness = state.getString("illnessName");
            IllnessType illnessType = setPatientIllnessType(illness);
            int severity = state.getInt("severity");
            pacientsList.add(PatientFactory.createPatient(id, name, age, roundHeEnters, illnessType, severity));
        }
        return pacientsList;
    }


    public int getNoOfRounds() throws JSONException {
        return readNoOfRounds();
    }

    public int getNoOfNurses() throws JSONException {
        return readNoOfNurses();
    }

    public int getNoOfERTechnicians() throws JSONException {
        return readNoOfERTechnicians();
    }

    public List<IDoctor> getDoctorsList() throws JSONException {
        return readDoctorsList();
    }

    public List<Patient> getPacientsList() throws JSONException {
        return readPacientsList();
    }
}