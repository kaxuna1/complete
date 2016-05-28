package main.models.Enum;

/**
 * Created by vakhtanggelashvili on 11/3/15.
 */
public enum JsonReturnCodes {
    Ok(0),
    USEREXISTS(1),
    ERROR(3),
    BARRCODEEXISTS(4),
    NOTLOGGEDIN(5),
    SESSIONEXPIRED(6),
    DONTHAVEPERMISSION(7),
    REGIONDOESNOTEXIST(8),
    USER_TYPE_ERROR_NOT_COURIER(9);

    private int CODE;

    JsonReturnCodes(int i) {
        this.CODE = i;
    }

    public int getCODE() {
        return CODE;
    }

}
