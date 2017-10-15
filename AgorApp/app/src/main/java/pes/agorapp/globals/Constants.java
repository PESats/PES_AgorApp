package pes.agorapp.globals;

/**
 * Created by marc on 15/10/17.
 */

public class Constants {

    /*inventades, s'han de posar les bones */
    private static String IP_pro = "192.168.1.1";
    private static String IP_dev = "192.168.1.1";

    /*inventat, s'han de posar les adreces del servidor de la FIB*/
    private static String DNS_PRO = "pes.agorapp.org";
    private static String DNS_DEV = "dev.pes.agorapp.com";

    //indiquem si apuntem a PRO (definitiu) o a DEV (per fer proves)
    private static String ip = DNS_DEV;

    public static String DOMAIN = "https://" + ip;
    public static String BASE_URL = DOMAIN + "/api/v1/";
}
