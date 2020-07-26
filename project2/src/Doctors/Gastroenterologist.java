package Doctors;

import Pacients.IPatient;
import entities.enums.IllnessType;
import entities.enums.InvestigationResult;
import entities.enums.State;

public class Gastroenterologist extends AbstractDoctor {


    public Gastroenterologist(String type, boolean isSurgeon, int maxForTreatment) {
        super(type, isSurgeon, maxForTreatment);
        treatedIllnesses = new IllnessType[]{IllnessType.ABDOMINAL_PAIN, IllnessType.ALLERGIC_REACTION, IllnessType.FOOD_POISONING};
    }

    public void consultPatient(IPatient patient) {
        InvestigationResult res = patient.getPatientInvestigationResult();
        switch (res) {
            case HOSPITALIZE: {
                if (!patient.isTreated()) {
                    int noOfRounds = computeNoOfRoundsForPatient(patient);
                    patient.setNoOfRoundsOfHospitalisation(noOfRounds);
                    patient.setTreated(true);
                    patient.setPacientState(State.HOSPITALIZED_CARDIO);
                    patient.setPatientInvestigationResult(InvestigationResult.HOSPITALIZE);
                }
            }
            break;
            case OPERATE: {
                operatePatient(patient);
            }
            break;
            case TREATMENT: {
                sendPatientHome(patient);
            }
            break;
            case NOT_DIAGNOSED: {
                if (patient.getPatientSeverity() <= maxForTreatment) {
                    sendPatientHome(patient);
                } else {
                    patient.setPacientState(State.INVESTIGATIONSQUEUE);
                }
            }
            break;
        }
    }

    @Override
    public void operatePatient(IPatient patient) {
        patient.setPacientState(State.OTHERHOSPITAL);
    }

    @Override
    public void sendPatientHome(IPatient patient) {
        patient.setPacientState(State.HOME_GASTRO);
    }

    @Override
    void setC1() {
        c1 = 0.5f;
    }

    @Override
    void setC2() {
        c2 = 0;
    }
}
