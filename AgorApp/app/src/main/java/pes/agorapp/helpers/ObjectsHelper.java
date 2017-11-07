package pes.agorapp.helpers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pes.agorapp.JSONObjects.Announcement;
import pes.agorapp.JSONObjects.Comment;
import pes.agorapp.JSONObjects.Location;

/**
 * Created by Alex on 01-Nov-17.
 */

public class ObjectsHelper {

    public static Announcement getFakeAnnouncement() {
        Announcement anAnnouncement = new Announcement("Pintar habitacio",
                "Necessito ajuda per pintar la meva habitacio, es la meva primera vegada!!!",
                new Location(41.390368, 2.114508),
                50,
                new Date(),
                "Mariano Rajoy", getFakeComments());
        return anAnnouncement;
    }

    public static List<Announcement> getFakeAnnouncementList() {
        List<Announcement> announcements = new ArrayList<>();

        announcements.add(new Announcement("Pintar habitacio",
                "Necessito ajuda per pintar la meva habitacio, es la meva primera vegada!!!",
                new Location(41.390368, 2.114508),
                50,
                new Date(),
                "Carles Puigdemont", getFakeComments()));

        announcements.add(new Announcement("Regar les plantes",
                "No se regar!!!",
                new Location(41.390368, 2.114508),
                100,
                new Date(),
                "Mariano Rajoy", getFakeComments()));

        announcements.add(new Announcement("Rentar els plats",
                "Sempre me\'ls ha rentat la dona!!!",
                new Location(41.390368, 2.114508),
                150,
                new Date(),
                "Xavier Garcia Albiol", getFakeComments())
                );
        return announcements;
    }

    private static List<Comment> getFakeComments() {
        List<Comment> comments = new ArrayList<>();
        comments.add(new Comment("Zapatero", "Prometere!", new Date()));
        comments.add(new Comment("M. Rajoy", "Y la europea?", new Date()));
        comments.add(new Comment("Iceta", "Pedro, libranos de él!", new Date()));
        return comments;
    }
}
