package Doctors;

import Pacients.IPatient;
import entities.enums.IllnessType;

import java.util.List;

public interface IDoctor {
    void operatePatient(IPatient patient);
    boolean isSurgeon();
    int computeNoOfRoundsForPatient(IPatient patient);
    int getT();
    boolean isBusy();
    boolean treatsIllness(IllnessType illness);
    void addPatientOnList(IPatient patient);
    void consultPatients();
    void consultPatient(IPatient patient);
    void sendPatientHome(IPatient patient);
    void verifyHospitalizedPatients();
    List<IPatient > getPatientsList();
}
