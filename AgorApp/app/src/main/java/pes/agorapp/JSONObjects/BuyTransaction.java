package pes.agorapp.JSONObjects;

import pes.agorapp.globals.PreferencesAgorApp;

/**
 * Created by Alex on 25-Nov-17.
 */

public class BuyTransaction {
    private UserAgorApp seller;
    private UserAgorApp buyer;
    private int freezeReward;

    public BuyTransaction(UserAgorApp seller, UserAgorApp buyer, int freezeReward) {
        this.seller = seller;
        this.buyer = buyer;
        this.freezeReward = freezeReward;
    }

    public BuyTransaction() {}

    public UserAgorApp getSeller() {
        return seller;
    }

    public void setSeller(UserAgorApp seller) {
        this.seller = seller;
    }

    public UserAgorApp getBuyer() {
        return buyer;
    }

    public void setBuyer(UserAgorApp buyer) {
        this.buyer = buyer;
    }

    public int getFreezeReward() {
        return freezeReward;
    }

    public void setFreezeReward(int freezeReward) {
        this.freezeReward = freezeReward;
    }

    public UserAgorApp getOtherUser(String myId) {
        if (seller.getId().equals(myId)) {
            return seller;
        }
        return buyer;
    }
}
