package Pacients;

import entities.enums.Urgency;

import java.util.Comparator;

public class PacientsComparator implements Comparator<IPatient> {
    @Override
    public int compare(IPatient p1, IPatient p2) {
        int firstPacientUrgency = Urgency.getUrgencyValue(p1.getUrgency());
        int secondPacientUrgency = Urgency.getUrgencyValue(p2.getUrgency());
        if(firstPacientUrgency > secondPacientUrgency){
            return -1;
        }

        if(firstPacientUrgency < secondPacientUrgency){
            return 1;
        }
        return 0;
    }
}
