package Nurses;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class NurseManager {
    List<INurse> nursesList = new ArrayList<>();

    public NurseManager(List<INurse> nursesList) {
        this.nursesList = nursesList;
    }

    public INurse getNurse() {

        for (Iterator<INurse> it = nursesList.iterator(); it.hasNext(); ) {
            INurse nurse = it.next();

            if (nurse.isNurseAvailable()) {
                nursesList.remove(nurse);
                nursesList.add(nurse);

                return nurse;
            }
        }
        
        return null;
    }

}
