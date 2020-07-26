package Nurses;

import Pacients.IPatient;

public class Nurse implements INurse {
    private boolean isAvailable = true;
    private int id;
    private boolean alreadyUsedThisRound = false;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Nurse(int id){
        this.id = id;
    }

    public void alreadyUsedThisRound(boolean isIt){
        this.alreadyUsedThisRound = isIt;
    }

    public boolean isAlreadyUsedThisRound(){
        return this.alreadyUsedThisRound;
    }

    private void setBusy(){
        this.isAvailable = false;
    }

    private void availableAgain(){
        this.isAvailable = true;
    }

    @Override
    public boolean isNurseAvailable() {
        return isAvailable;
    }

    public Thread givePatientTreatment(IPatient patient) {

        return new Thread(){
            private void setNurseBusy(){
                Nurse.this.setBusy();
            }
            private void giveTreatment(){
                int decrementedNoOfRounds = patient.getNoOfRoundsOfHospitalisation() - 1;
                int decrementedSeverityWithTreatment = patient.getPatientSeverity() - patient.getAssignedIDoctor().getT();
                patient.setNoOfRoundsOfHospitalisation(decrementedNoOfRounds);
                patient.setPatientSeverity(decrementedSeverityWithTreatment);
            }

            @Override
            public void run() {
                setNurseBusy();
                giveTreatment();
                System.out.println( "Nurse " + Nurse.this.getId() + " treated " + patient.getName() + " and he has to stay " +  patient.getNoOfRoundsOfHospitalisation() + " more rounds in the hospital");
                availableAgain();
                alreadyUsedThisRound(true);
            }
        };
    }

}
