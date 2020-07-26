package ERTechnicians;

import Pacients.IPatient;
import entities.enums.InvestigationResult;
import entities.enums.State;

public class ERTechnician {
    private int c1 = 75;
    private int c2 = 40;

    public void setPatientInvestigationResult(IPatient patient){
        int severity = patient.getPatientSeverity();
        if(severity > this.c1){
            patient.setPatientInvestigationResult(InvestigationResult.OPERATE);
        }
        if((severity > this.c2) && (severity <= this.c1)){
            patient.setPatientInvestigationResult(InvestigationResult.HOSPITALIZE);
        }
        if(severity <= this.c2){
            patient.setPatientInvestigationResult(InvestigationResult.TREATMENT);
        }
    }

    public void investigatePatient(IPatient patient){
        if(patient.getPatientInvestigationResult()== InvestigationResult.NOT_DIAGNOSED){
            patient.setPacientState(State.EXAMINATIONSQUEUE);
            setPatientInvestigationResult(patient);
        }
    }
}
