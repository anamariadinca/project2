package Pacients;

import Doctors.IDoctor;
import entities.enums.*;

public class Patient implements IPatient, Comparable<Patient> {
    private int id;
    private String name;
    private int age;
    private int rountHeEnters;
    private int severity;
    private boolean isTreated;
    private IDoctor assignedIDoctor;
    private int noOfRoundsOfHospitalisation;
    private Urgency urgency;
    private State state;
    private InvestigationResult investigationResult;
    private IllnessType illnessType;

    public Patient(int id, String name, int age, int rountHeEnters, IllnessType illnessType, int severity) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.investigationResult = InvestigationResult.NOT_DIAGNOSED;
        this.rountHeEnters = rountHeEnters;
        this.illnessType = illnessType;
        this.severity = severity;
        this.isTreated = false;
    }

    public void setAssignedIDoctor(IDoctor assignedIDoctor){
        this.assignedIDoctor = assignedIDoctor;
    }


    public int getPatientSeverity() {
        return severity;
    }

    public void setPatientSeverity(int severity) {
        this.severity = severity;
    }


    public int getNoOfRoundsOfHospitalisation() {
        return noOfRoundsOfHospitalisation;
    }

    public void setNoOfRoundsOfHospitalisation(int noOfRoundsOfHospitalisation) {
        this.noOfRoundsOfHospitalisation = noOfRoundsOfHospitalisation;
    }

    public IDoctor getAssignedIDoctor() {
        return assignedIDoctor;
    }

    public void setPatientUrgency(Urgency urgency) {
        this.urgency = urgency;
    }


    public Urgency getUrgency() {
        return urgency;
    }


    public State getPacientState() {
        return state;
    }

    public void setPacientState(State state) {
        this.state = state;
    }

    public InvestigationResult getPatientInvestigationResult() {
        return investigationResult;
    }

    public void setPatientInvestigationResult(InvestigationResult investigationResult) {
        this.investigationResult = investigationResult;
    }

    public IllnessType getIllnessType() {
        return illnessType;
    }

    public int getRountHeEnters() {
        return rountHeEnters;
    }

    public void setRountHeEnters(int rountHeEnters) {
        this.rountHeEnters = rountHeEnters;
    }


    public String getName() {
        return name;
    }

    @Override
    public int compareTo(Patient o) {
        int firstPacientUrgency = Urgency.getUrgencyValue(this.getUrgency());
        int secondPacientUrgency = Urgency.getUrgencyValue(o.getUrgency());
        if(firstPacientUrgency > secondPacientUrgency){
            return -1;
        }
        else if(firstPacientUrgency <secondPacientUrgency){
            return 1;
        }else if(firstPacientUrgency == secondPacientUrgency){
            return 0;
        }
        return 0;
    }

    public boolean isTreated() {
        return isTreated;
    }

    public void setTreated(boolean treated) {
        isTreated = treated;
    }
}
