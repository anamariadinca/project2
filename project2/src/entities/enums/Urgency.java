package entities.enums;

/**
 * IMMEDIATE > URGENT > LESS_URGENT > NON_URGENT
 * NON_URGENT means it will not enter the emergency flows
 *
 * [Part of the homework's skeleton]
 */
public enum Urgency {
    IMMEDIATE("IMMEDIATE"),
    URGENT("URGENT"),
    LESS_URGENT("LESS_URGENT"),
    NON_URGENT("NON_URGENT"),
    NOT_DETERMINED("NOT_DETERMINED"),
    ;
    private String value;

    Urgency(String value) {
        this.value = value;
    }

    public String getUrgencyValue(){
        return value;
    }

    public static int getUrgencyValue(Urgency urgency){
        switch (urgency.getUrgencyValue()){
            case "IMMEDIATE": return 4;
            case "URGENT": return 3;
            case "LESS_URGENT": return 2;
            case "NON_URGENT": return 1;
            case "NOT_DETERMINED": return 0;
            default:
                System.out.println("The urgency could not be determined!"); return 0;
        }
    }
}
