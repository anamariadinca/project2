package Doctors;

import Pacients.IPatient;
import entities.enums.*;

public class ER_Physician extends AbstractDoctor implements ISurgeon {


    public ER_Physician(String type, boolean isSurgeon, int maxForTreatment) {
        super(type, isSurgeon, maxForTreatment);
        treatedIllnesses = new IllnessType[]{IllnessType.ALLERGIC_REACTION, IllnessType.BROKEN_BONES, IllnessType.BURNS, IllnessType.CAR_ACCIDENT, IllnessType.CUTS, IllnessType.HIGH_FEVER, IllnessType.SPORT_INJURIES};
    }

    public void consultPatient(IPatient patient) {
        InvestigationResult res = patient.getPatientInvestigationResult();
        switch (res) {
            case HOSPITALIZE :
            {
                if(!patient.isTreated()){
                    int noOfRounds = computeNoOfRoundsForPatient(patient);
                    patient.setNoOfRoundsOfHospitalisation(noOfRounds);
                    patient.setTreated(true);
                    patient.setPacientState(State.HOSPITALIZED_CARDIO);
                }
            }
                break;
            case OPERATE:{
                if(isSurgeon()){
                    this.isBusy = true;
                    operatePatient(patient);
                    patient.setTreated(true);
                    patient.setPacientState(State.OPERATED_CARDIO);
                    patient.setPatientInvestigationResult(InvestigationResult.HOSPITALIZE);

                    int noOfHospitalisation = computeNoOfRoundsForPatient(patient);
                    patient.setNoOfRoundsOfHospitalisation(noOfHospitalisation);
                }

                else{
                    patient.setPacientState(State.OTHERHOSPITAL);
                }
            }
                break;
            case TREATMENT:
                sendPatientHome(patient);
                break;
            case NOT_DIAGNOSED: {
                if (patient.getPatientSeverity() <= maxForTreatment) {
                    sendPatientHome(patient);
                }else{
                    patient.setPacientState(State.INVESTIGATIONSQUEUE);
                }
            }
            break;
        }
    }

    @Override
    public void sendPatientHome(IPatient patient) {
        patient.setPacientState(State.HOME_ERPHYSICIAN);
    }

    @Override
    public void operatePatient(IPatient patient) {
            int patientSeverity = patient.getPatientSeverity();
            int reducedSeverity = (int) (patientSeverity - patientSeverity * c2);
            patient.setPatientSeverity(reducedSeverity);
            isBusy = false;
    }

    @Override
    void setC1() {
        c1 = 0.1f;
    }

    @Override
    void setC2() {
        c2 = 0.3f;
    }
}
