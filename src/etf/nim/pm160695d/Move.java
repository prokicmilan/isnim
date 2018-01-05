package etf.nim.pm160695d;

/**
 * Created by Proka on 12/22/2017.
 */
public class Move {
    /**
     * 
     * @param pos pozicija sa koje se skidaju žetoni
     * @param numDiscs broj žetona koji treba skinuti
     */
    public Move(int pos, int numDiscs) {
        this.pos = pos;
        this.numDiscs = numDiscs;
        this.value = -2000;
    }

    /**
     * 
     * @param pos pozicija sa koje se skidaju žetoni
     * @param numDiscs broj žetona koji treba skinuti
     * @param isMax <code> true </code> ako je potez igrao maksimizer, <code> false </code> inace
     */
    public Move(int pos, int numDiscs, boolean isMax) {
        this(pos, numDiscs);
        this.isMax = isMax;
    }
    
    /**
     * 
     * @return pozicija sa koje treba skinuti žetone
     */
    public int getPos() {
        return pos;
    }

    /**
     * 
     * @return broj žetona koji treba skinuti
     */
    public int getNumDiscs() {
        return numDiscs;
    }

    /**
     * 
     * @return procenjena vrednost poteza
     */
    public int getValue() {
        return value;
    }

    /**
     * 
     * @return <code>true</code> ako je potez igrao maksimizer, <code>false</code> inače
     */
    public boolean getIsMax() {
        return isMax;
    }

    /**
     * 
     * @param pos pozicija sa koje treba skinuti žetone
     */
    public void setPos(int pos) {
        this.pos = pos;
    }

    /**
     * 
     * @param value procenjena vrednost poteza 
     */
    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return Integer.toString(numDiscs) + " diskova sa stuba " + Integer.toString(pos) + " (vrednost " + Integer.toString(value) + ")";
    }

    private int pos;
    private int numDiscs;
    private int value;
    private boolean isMax;
}
