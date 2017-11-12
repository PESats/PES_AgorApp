package pes.agorapp.helpers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pes.agorapp.JSONObjects.Announcement;
import pes.agorapp.JSONObjects.Comment;

/**
 * Created by Alex on 01-Nov-17.
 */

public class ObjectsHelper {

    public static Announcement getFakeAnnouncement() {
        //(String title, String text, float latitude, float longitude, int reward, int user_id, Date created_at)
        Announcement anAnnouncement = new Announcement("Pintar habitacio",
                "Necessito ajuda per pintar la meva habitacio, es la meva primera vegada!!!",
                (float) 41.190368, (float) 2.814508, 50, 1, new Date());
        return anAnnouncement;
    }

    public static List<Announcement> getFakeAnnouncementList() {
        List<Announcement> announcements = new ArrayList<>();

        announcements.add(new Announcement("Pintar habitacio",
                "No se pintar una habitació, soc lerdo",
                (float) 41.490368, (float) 2.314508, 50, 1, new Date()));

        announcements.add(new Announcement("Regar plantes",
                "La meva mare m'ha deixat unes plantes i s'estan martxitan :'(",
                (float) 41.590368, (float) 2.614508, 50, 1, new Date()));

        announcements.add(new Announcement("Rentar plats",
                "Se m'ha trencat el rentaplats",
                (float) 41.890368, (float) 2.114508, 50, 1, new Date()));
        return announcements;
    }

    public static List<Comment> getFakeOwnComments() {
        List<Comment> comments = new ArrayList<>();
        comments.add(new Comment(1, "Zapatero", "Prometere!", new Date()));
        return comments;
    }

    public static List<Comment> getFakeComments() {
        List<Comment> comments = new ArrayList<>();
        comments.add(new Comment(1, "Zapatero", "Prometere!", new Date()));
        comments.add(new Comment(2, "M. Rajoy", "Y la europea?", new Date()));
        comments.add(new Comment(3, "Iceta", "Pedro, libranos de él!", new Date()));
        comments.add(new Comment(4, "Iceta2", "Pedro, libranos de él!", new Date()));
        return comments;
    }
}
