package pes.agorapp.helpers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import pes.agorapp.JSONObjects.Announcement;
import pes.agorapp.JSONObjects.Bid;
import pes.agorapp.JSONObjects.BuyTransaction;
import pes.agorapp.JSONObjects.Comment;
import pes.agorapp.JSONObjects.Coupon;
import pes.agorapp.JSONObjects.Trophy;
import pes.agorapp.JSONObjects.UserAgorApp;

/**
 * Created by Alex on 01-Nov-17.
 */

public class ObjectsHelper {

    private static Random random = new Random();

    public static Announcement getFakeAnnouncement() {
        //(String title, String text, float latitude, float longitude, int reward, int user_id, Date created_at)
        Announcement anAnnouncement = new Announcement("Pintar habitacio",
                "Necessito ajuda per pintar la meva habitacio, es la meva primera vegada!!!",
                (float) 41.190368, (float) 2.814508, 50, 1, new Date(), null, "open");
        return anAnnouncement;
    }

    public static List<Announcement> getFakeAnnouncementList() {
        List<Announcement> announcements = new ArrayList<>();

        announcements.add(new Announcement("Pintar habitacio",
                "No se pintar una habitació, soc lerdo",
                (float) 41.490368, (float) 2.314508, 50, 1, new Date(), null, "open"));

        announcements.add(new Announcement("Regar plantes",
                "La meva mare m'ha deixat unes plantes i s'estan martxitan :'(",
                (float) 41.590368, (float) 2.614508, 50, 1, new Date(), null, "closed"));

        announcements.add(new Announcement("Rentar plats",
                "Se m'ha trencat el rentaplats",
                (float) 41.890368, (float) 2.114508, 50, 1, new Date(), null, "complete"));
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

    public static List<Coupon> getFakeCoupons() {
        List<Coupon> coupons = new ArrayList<>();
        coupons.add(new Coupon(1, "1", 1, 300, 75, null));
        coupons.add(new Coupon(2, "12", 1, 150, 10, null));
        coupons.add(new Coupon(3, "6", 1, 400, 25, null));
        coupons.add(new Coupon(4, "5", 1, 700, 45, null));
        return coupons;
    }

    public static UserAgorApp getFakeUser() {
        UserAgorApp user = new UserAgorApp();
        user.setName("jonathan");
        user.setEmail("fake@mail.com");
        user.setCoins(random.nextInt(1000));
        user.setId(String.valueOf(random.nextInt(10)));

        return user;
    }

    public static UserAgorApp getFakeUser(String name) {
        UserAgorApp user = new UserAgorApp();
        user.setName(name);
        user.setEmail("fake@email.com");
        user.setCoins(random.nextInt(1000));
        user.setId(String.valueOf(random.nextInt(10)));

        return user;
    }

    public static List<BuyTransaction> getFakeTransactions() {
        List<BuyTransaction> list = new ArrayList<>();
        for (int i=0; i < 5; ++i) {
            list.add(new BuyTransaction(getFakeUser(), getFakeUser(), random.nextInt(1000)));
        }
        return list;
    }

    public static String getFakeMessage() {
        return "Fakeee message";
    }

    public static Date getFakeDate() {
        return new Date(2017,3,1,0,0);
    }

    public static List<Bid> getFakeBids() {
        List<Bid> bids = new ArrayList<>();
        bids.add(
                new Bid(250, true, getFakeUser("Gandalf"), new Announcement(), 1)
        );

        bids.add(
                new Bid(300, true, getFakeUser("Boromir"), new Announcement(), 2)
        );

        bids.add(
                new Bid(350, true, getFakeUser("Aragorn"), new Announcement(), 3)
        );
        return bids;
    }

    public static List<Bid> getFakeBidsAsAnnouncer() {
        //esta to mal
        List<Bid> bids = new ArrayList<>();
        UserAgorApp marc = new UserAgorApp();
        marc.setId("13");
        marc.setName("Marc");

        Announcement announcement = new Announcement();
        announcement.setUser(marc);
        announcement.setId(4000);
        bids.add(
                new Bid(400, true, new UserAgorApp(), announcement, 4)
        );
        return bids;
    }

}
