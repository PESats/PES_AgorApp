package pes.agorapp.helpers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pes.agorapp.JSONObjects.Announcement;
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
                "Mariano Rajoy");
        return anAnnouncement;
    }

    public static List<Announcement> getFakeAnnouncementList() {
        List<Announcement> announcements = new ArrayList<>();

        announcements.add(new Announcement("Pintar habitacio",
                "Necessito ajuda per pintar la meva habitacio, es la meva primera vegada!!!",
                new Location(41.390368, 2.114508),
                50,
                new Date(),
                "Carles Puigdemont"));

        announcements.add(new Announcement("Regar les plantes",
                "No se regar!!!",
                new Location(41.390368, 2.114508),
                100,
                new Date(),
                "Mariano Rajoy"));

        announcements.add(new Announcement("Rentar els plats",
                "Sempre me\'ls ha rentat la dona!!!",
                new Location(41.390368, 2.114508),
                150,
                new Date(),
                "Xavier Garcia Albiol"));
        return announcements;
    }
}
