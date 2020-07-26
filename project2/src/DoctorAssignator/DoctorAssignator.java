package DoctorAssignator;

import Doctors.IDoctor;
import Pacients.IPatient;
import entities.enums.InvestigationResult;
import entities.enums.State;

import java.util.ArrayList;
import java.util.List;

public class DoctorAssignator {

    static List<IDoctor> IDoctorList = new ArrayList<>();

    public DoctorAssignator(List<IDoctor> IDoctorList){
        this.IDoctorList = IDoctorList;
    }

    public static IDoctor findAvailableDoctor(IPatient patient){
        for(IDoctor IDoctor : IDoctorList){
            if(IDoctor.treatsIllness(patient.getIllnessType()) && (IDoctor.isBusy() == false)){
                if(patient.getPatientInvestigationResult() == InvestigationResult.OPERATE){
                    if(IDoctor.isSurgeon()){
                        return IDoctor;
                    }
                }
                return IDoctor;
            }
        }
        if(patient.getAssignedIDoctor() == null && patient.getPatientInvestigationResult() == InvestigationResult.OPERATE){
            patient.setPacientState(State.OTHERHOSPITAL);
        }
        return null;
    }
}
