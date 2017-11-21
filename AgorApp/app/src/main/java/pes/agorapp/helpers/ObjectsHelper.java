package pes.agorapp.helpers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pes.agorapp.JSONObjects.Announcement;
import pes.agorapp.JSONObjects.Comment;
import pes.agorapp.JSONObjects.Trophy;

/**
 * Created by Alex on 01-Nov-17.
 */

public class ObjectsHelper {

    public static Announcement getFakeAnnouncement() {
        //(String title, String text, float latitude, float longitude, int reward, int user_id, Date created_at)
        Announcement anAnnouncement = new Announcement("Pintar habitacio",
                "Necessito ajuda per pintar la meva habitacio, es la meva primera vegada!!!",
                (float) 41.190368, (float) 2.814508, 50, 1, new Date(),null);
        return anAnnouncement;
    }

    public static List<Announcement> getFakeAnnouncementList() {
        List<Announcement> announcements = new ArrayList<>();

        announcements.add(new Announcement("Pintar habitacio",
                "No se pintar una habitació, soc lerdo",
                (float) 41.490368, (float) 2.314508, 50, 1, new Date(), null));

        announcements.add(new Announcement("Regar plantes",
                "La meva mare m'ha deixat unes plantes i s'estan martxitan :'(",
                (float) 41.590368, (float) 2.614508, 50, 1, new Date(), null));

        announcements.add(new Announcement("Rentar plats",
                "Se m'ha trencat el rentaplats",
                (float) 41.890368, (float) 2.114508, 50, 1, new Date(), null));
        return announcements;
    }

    public static List<Comment> getFakeOwnComments() {
        List<Comment> comments = new ArrayList<>();
        comments.add(new Comment(1, "Zapatero", "Prometere!", new Date(), 200));
        return comments;
    }

    public static List<Comment> getFakeComments() {
        List<Comment> comments = new ArrayList<>();
        comments.add(new Comment(1, "Zapatero", "Prometere!", new Date(), 1000));
        comments.add(new Comment(2, "M. Rajoy", "Y la europea?", new Date(), 750));
        comments.add(new Comment(3, "Iceta", "Pedro, libranos de él!", new Date(), 250));
        comments.add(new Comment(4, "Iceta2", "Pedro, libranos de él!", new Date(), 3000));
        comments.add(new Comment(5, "Zapatero", "Prometere!", new Date(), 1000));
        comments.add(new Comment(6, "M. Rajoy", "Y la europea?", new Date(), 750));
        comments.add(new Comment(7, "Iceta", "Pedro, libranos de él!", new Date(), 250));
        comments.add(new Comment(8, "Iceta2", "Pedro, libranos de él!", new Date(), 3000));
        return comments;
    }

    public static List<Trophy> getFakeTrophies() {
        List<Trophy> trophies = new ArrayList<>();
        trophies.add(new Trophy(1, "Ajudant", "Ajuda per primer cop"));
        trophies.add(new Trophy(2, "Sol·licitant", "Publica el teu primer anunci"));
        trophies.add(new Trophy(3, "Sol·licitant expert", "Publica 10 anuncis"));
        trophies.add(new Trophy(4, "Ajudant expert", "Ajuda 10 persones diferents"));
        trophies.add(new Trophy(5, "Altruista", "Ofereix-te a ajudar per menys AgoraCoins que la recompensa d'un anunci"));
        trophies.add(new Trophy(6, "AgoraExpert", "Bescanvia AgoraCoins per algun val de descompte"));
        trophies.add(new Trophy(7, "Modernet de merda", "Comparteix un anunci a alguna xarxa social"));
        return trophies;
    }
}
