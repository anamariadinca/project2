package Nurses;

import Pacients.IPatient;
import UrgencyEstimator.UrgencyEstimator;
import entities.enums.IllnessType;
import entities.enums.Urgency;

public interface INurse {

    boolean isNurseAvailable();

    Thread givePatientTreatment(IPatient patient);

    static void setPatientUrgency(IPatient patient)
    {
        UrgencyEstimator estimator = UrgencyEstimator.getInstance();
        IllnessType illness = patient.getIllnessType();
        Urgency urgencyLevel = estimator.estimateUrgency(illness,patient.getPatientSeverity());
        patient.setPatientUrgency(urgencyLevel);
    }
}
