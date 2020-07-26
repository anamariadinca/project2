package Doctors;

import Pacients.IPatient;
import entities.enums.IllnessType;
import entities.enums.InvestigationResult;
import entities.enums.State;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDoctor implements IDoctor {
    protected String type;
    protected boolean isSurgeon;
    protected int maxForTreatment;
    protected float c1;
    protected float c2;
    protected static final int t = 22;
    protected boolean isBusy = false;
    protected IllnessType[] treatedIllnesses;

    protected List<IPatient> patientsList = new ArrayList<>();

    public AbstractDoctor(String type, boolean isSurgeon, int maxForTreatment) {
        this.type = type;
        this.isSurgeon = isSurgeon;
        this.maxForTreatment = maxForTreatment;
        this.setC1();
        this.setC2();
    }

    public int getT(){
        return t;
    }

    public List<IPatient> getPatientsList() {
        return patientsList;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isSurgeon() {
        return isSurgeon;
    }

    public void setSurgeon(boolean surgeon) {
        isSurgeon = surgeon;
    }

    public int getMaxForTreatment() {
        return maxForTreatment;
    }

    public void setMaxForTreatmentl(int maxForTreatment) {
        this.maxForTreatment = maxForTreatment;
    }

    AbstractDoctor(){
    }

    abstract void setC1();
    abstract void setC2();

    public boolean isBusy(){
        return isBusy;
    }

    public void setDoctorBusy(){
        this.isBusy = true;
    }

    @Override
    public String toString() {
        return "\n" + this.type + " " + this.isSurgeon + " " + this.maxForTreatment;
    }


    private int max(float severity, int constantValue){
        constantValue = 3;
        if(severity > constantValue){
            return (int)severity;
        }
        else
        {
            return constantValue;
        }
    }

    @Override
    public int computeNoOfRoundsForPatient(IPatient patient) {
        int noOfRounds = 0;
        if(patient.getPatientInvestigationResult() == InvestigationResult.HOSPITALIZE){
            int patientSeverity = patient.getPatientSeverity();
            noOfRounds = max(patientSeverity*this.c1 , 3);
        }
        return noOfRounds;
    }

    public IllnessType[] getTreatedIllnesses() {
        return treatedIllnesses;
    }

    @Override
    public boolean treatsIllness(IllnessType illness) {
        for(IllnessType illnessTreated : treatedIllnesses){
            if(illnessTreated == illness) return true;
        }
        return false;
    }

    @Override
    public void addPatientOnList(IPatient patient) {
        patientsList.add(patient);
    }

    public abstract void sendPatientHome(IPatient patient);

    @Override
    public void consultPatients() {
        for(int i = 0; i< patientsList.size(); i++){
            IPatient currentPatient = patientsList.get(i);
            consultPatient(currentPatient);
        }
    }

    @Override
    public void verifyHospitalizedPatients() {
        for(int i = 0; i< patientsList.size(); i++){
            IPatient currentPatient = patientsList.get(i);
            if(currentPatient.getPatientInvestigationResult() == InvestigationResult.HOSPITALIZE && currentPatient.isTreated()){
                if(currentPatient.getNoOfRoundsOfHospitalisation() == 0 || currentPatient.getPatientSeverity() == 0){
                    currentPatient.setPacientState(State.HOME_DONE_TREATMENT);
                    patientsList.remove(currentPatient);
                }
                else{
                    System.out.println(this.type + " says that " + currentPatient.getName() + " should remain in the hospital" );
                }
            }
        }
    }
}
