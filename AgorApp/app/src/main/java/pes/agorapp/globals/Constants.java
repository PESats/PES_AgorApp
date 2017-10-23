package pes.agorapp.globals;

/**
 * Created by marc on 15/10/17.
 */

public class Constants {

    /*inventades, s'han de posar les bones */
    private static String IP_pro = "192.168.1.1";
    private static String IP_dev = "192.168.1.1";

    /*inventat, s'han de posar les adreces del servidor de la FIB*/
    private static String DNS_PRO = "10.4.41.145:3000";
    private static String DNS_DEV = "10.4.41.145:3000";

    //indiquem si apuntem a PRO (definitiu) o a DEV (per fer proves)
    private static String ip = DNS_DEV;

    public static String DOMAIN = "http://" + ip;
    public static String BASE_URL = DOMAIN + "/";

    //Languages
    public static String LANG_ES = "es";
    public static String LANG_CA = "ca";

    //Intent tags
    public class EXTRA_INTENT_TAG {
        public static final String EMAIL = "email";
        public static final String USERNAME = "username";
        public static final String IMAGE_URL = "image_url";
        public static final String PLATFORM = "platform";
    }
}
