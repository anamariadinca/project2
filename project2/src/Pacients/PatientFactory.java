package Pacients;

import entities.enums.IllnessType;

public class PatientFactory {
    public static Patient createPatient(int id, String name, int age, int rountHeEnters, IllnessType illnessType, int severity){
        return new Patient(id, name, age, rountHeEnters, illnessType, severity);
    }
}
