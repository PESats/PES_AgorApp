package pes.agorapp.globals;

/**
 * Created by marc on 15/10/17.
 */

public class Constants {

    /*màquina on hi tenim el backend*/
    private static String DNS = "10.4.41.145:3000";

    private static String ip = DNS;

    public static String DOMAIN = "http://" + ip;
    public static String BASE_URL = DOMAIN + "/";

    //Languages (per més endavant)
    public static String LANG_CA = "ca";
    public static String LANG_EN = "en";
    public static String LANG_ES = "es";

}
