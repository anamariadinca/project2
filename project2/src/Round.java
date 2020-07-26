import DoctorAssignator.DoctorAssignator;
import Doctors.IDoctor;
import ERTechnicians.ERTechnician;
import Nurses.INurse;
import Nurses.Nurse;
import Nurses.NurseManager;
import Pacients.IPatient;
import Pacients.PacientsComparator;
import Pacients.Patient;
import entities.enums.InvestigationResult;
import entities.enums.State;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Round {
    private int noOfNurses = 0;
    private int noOfERTechnicians;
    private int roundNumber = 0;
    ERTechnician erTechnician = new ERTechnician();
    private Comparator patientsComparator = new PacientsComparator();
    private List<IDoctor> doctorsList;
    private List<Patient> patientsList;
    private List<INurse> nursesList = new ArrayList<>();
    private Queue<IPatient> triageQueue;
    private ConcurrentLinkedQueue<IPatient> examinationQueue;
    private Queue<IPatient> investigationQueue;
    private List<IPatient> unsortedTriageQueue = new ArrayList<>();
    private NurseManager nurseManager;


    Round(int noOfNurses, int noOfERTechnicians, List<IDoctor> doctorsList, List<Patient> pacientsList) {
        this.noOfERTechnicians = noOfERTechnicians;
        this.noOfNurses = noOfNurses;
        this.doctorsList = doctorsList;
        this.patientsList = pacientsList;
        triageQueue = new PriorityQueue<>();
        examinationQueue = new ConcurrentLinkedQueue<>();
        investigationQueue = new PriorityQueue<>();
    }

    private List<INurse> createNursesList(int noOfNurses) {
        for (int i = 0; i < noOfNurses; i++) {
            INurse nurse = new Nurse(i);
            nursesList.add(nurse);
        }
        nurseManager = new NurseManager(nursesList);
        return nursesList;
    }

    private void getRoundNumber(int index) {
        this.roundNumber = index;
    }

    private void findAvailableDoctor(IPatient patient) {
        DoctorAssignator doctorAssignator = new DoctorAssignator(doctorsList);
        IDoctor assignableIDoctor = DoctorAssignator.findAvailableDoctor(patient);
        patient.setAssignedIDoctor(assignableIDoctor);
        assignableIDoctor.addPatientOnList(patient);
    }

    private Queue<IPatient> createTriageQueue(int index) {
        for (IPatient currentPatient : patientsList) {
            if (currentPatient.getRountHeEnters() == index) {
                INurse.setPatientUrgency(currentPatient);
                findAvailableDoctor(currentPatient);
                currentPatient.setPacientState(State.TRIAGEQUEUE);
                if (currentPatient.getAssignedIDoctor() != null) {
                    currentPatient.getAssignedIDoctor().consultPatient(currentPatient);
                }
                if (currentPatient.getPacientState() == State.INVESTIGATIONSQUEUE) {
                    unsortedTriageQueue.add(currentPatient);
                }
            }
        }
        unsortedTriageQueue.sort(patientsComparator);
        triageQueue.addAll(unsortedTriageQueue);
        return triageQueue;
    }

    private void sendPacientsToDoctors() {
        int i = 0;
        while (i < noOfNurses) {
            for (int j = 0; j < triageQueue.size(); j++) {
                IPatient examinatedPatient = triageQueue.poll();
                unsortedTriageQueue.remove(examinatedPatient);
                //System.out.println(examinatedPatient.getName() + " is examinated");
                examinationQueue.add(examinatedPatient);
                examinatedPatient.setPacientState(State.EXAMINATIONSQUEUE);
            }
            i++;
        }
    }

    private void verificationbyERTechnicians() {
        int i = 0;
        Iterator<IPatient> patient = examinationQueue.iterator();
        while (i < noOfERTechnicians) {
            while (patient.hasNext()) {
                IPatient currentPatient = patient.next();
                if (currentPatient != null && (currentPatient.getPacientState() == State.INVESTIGATIONSQUEUE)) {
                    erTechnician.investigatePatient(currentPatient);
                    examinationQueue.poll();
                }
            }
            i++;
        }
    }

    private void doctorsConsultPacients() {
        for (IDoctor IDoctor : doctorsList) {
            IDoctor.consultPatients();
        }
    }

    private void checkFinishedTreatment() {
        System.out.println("~~~~ Doctors check their hospitalized patients and give verdicts ~~~~");
        for (IDoctor IDoctor : doctorsList) {
            IDoctor.verifyHospitalizedPatients();
        }
    }

    private void nursesTreatPatients() {
        System.out.println("~~~~~Nurses treat patients~~~~~~~~");
        Queue<IPatient> treatablePatients = new PriorityQueue<>(patientsComparator);
        for (IDoctor IDoctor : doctorsList) {
            List<IPatient> patientsToTreat = IDoctor.getPatientsList();
            for (int i = 0; i < patientsToTreat.size(); i++) {
                if ((patientsToTreat.get(i).getPatientInvestigationResult() == InvestigationResult.HOSPITALIZE) && !(patientsToTreat.get(i).getPacientState() == State.EXAMINATIONSQUEUE)) {
                    treatablePatients.add(patientsToTreat.get(i));
                }
            }
        }

        ArrayList<Thread> nurseThreads = new ArrayList<>();

        for (IPatient currentPatient : treatablePatients) {
            Thread nurseThread = nurseManager.getNurse().givePatientTreatment(currentPatient);
            nurseThreads.add(nurseThread);
            nurseThread.start();
        }

        for (Thread nurseThread: nurseThreads){

            try {
                nurseThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void printPatientsStatus(int index) {
        for (IPatient currentPatient : patientsList) {
            if (currentPatient.getRountHeEnters() <= index) {
                System.out.println(currentPatient.getName() + " is " + currentPatient.getPacientState().getValue());
            }
        }
    }

    public void run(int index) {
        getRoundNumber(index);
        System.out.println("\n~~~~Patients in round " + index + "~~~~~");
        createNursesList(noOfNurses);
        triageQueue = createTriageQueue(index);
        sendPacientsToDoctors();
        doctorsConsultPacients();
        verificationbyERTechnicians();
        printPatientsStatus(index);
        nursesTreatPatients();
        checkFinishedTreatment();
    }
}
