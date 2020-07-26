package Pacients;

import Doctors.IDoctor;
import entities.enums.IllnessType;
import entities.enums.InvestigationResult;
import entities.enums.State;
import entities.enums.Urgency;

public interface IPatient {
    void setAssignedIDoctor(IDoctor assignedIDoctor);
    int getPatientSeverity();
    void setPatientSeverity(int severity);
    int getNoOfRoundsOfHospitalisation();
    void setNoOfRoundsOfHospitalisation(int noOfRoundsOfHospitalisation);
    IDoctor getAssignedIDoctor();
    void setPatientUrgency(Urgency urgency);
    Urgency getUrgency();
    State getPacientState();
    void setPacientState(State state);
    InvestigationResult getPatientInvestigationResult();
    void setPatientInvestigationResult(InvestigationResult investigationResult);
    IllnessType getIllnessType();
    int getRountHeEnters();
    String getName();
    boolean isTreated();
    void setTreated(boolean treated);

}
