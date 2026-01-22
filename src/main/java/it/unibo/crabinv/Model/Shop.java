package it.unibo.crabinv.Model;

public interface Shop {

    /**
     * @param profile ciò che tiene conto se è stato effetuato l'acquisto del singolo power up
     * @param item il singolo item che è presente nello shop
     */
    public void purchase(UserProfile profile, PowerUp item);
}
