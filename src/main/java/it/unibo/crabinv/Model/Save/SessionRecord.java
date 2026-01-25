package it.unibo.crabinv.Model.Save;

public interface SessionRecord {

    /**
     * Returns the timeStamp of the save
     * @return
     */
    long getTimeStamp();

    int getLastLevel();

    int getLastCurrency();
}
