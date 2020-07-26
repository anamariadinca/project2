package Doctors;

public class DoctorFactory {

    public static IDoctor doctorFactory(String type, boolean isSurgeon, int maxForTreatment){
        switch (type){
            case "CARDIOLOGIST": return new Cardiologist(type, isSurgeon, maxForTreatment);
            case "ER_PHYSICIAN": return new ER_Physician(type, isSurgeon, maxForTreatment);
            case "GASTROENTEROLOGIST": return new Gastroenterologist(type, isSurgeon, maxForTreatment);
            case "GENERAL_SURGEON": return new GeneralSurgeon(type, isSurgeon, maxForTreatment);
            case "INTERNIST": return new Internist(type, isSurgeon, maxForTreatment);
            case "NEUROLOGIST": return new Neurologist(type, isSurgeon, maxForTreatment);
        }
        return null;
    }
}
