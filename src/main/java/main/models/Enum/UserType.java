package main.models.Enum;

/**
 * Created by KGelashvili on 10/22/2015.
 */
public enum UserType {
    sa(1),
    admin(2),
    organisation(3),
    organisationUser(4),
    acounter(5),
    regionManager(6),
    zoneManager(7),
    courier(8),
    operator(9);
    private int CODE;

    UserType(int i) {
        this.CODE=i;
    }

    public int getCODE() {
        return CODE;
    }
}
