package etf.nim.pm160695d;

/**
 * Created by Proka on 12/22/2017.
 */
public abstract class Igrac {
    /**
     * 
     * @param maxDepth maksimalna dubina pretrage stabla
     */
    public Igrac (int maxDepth) {
        this.maxDepth = maxDepth;
    }
    
    /**
     * Vraća vrednost maksimalne dubine pretrage stabla
     * @return 
     */
    public int getMaxDepth() {
        return maxDepth;
    }
    
    /**
     * 
     * @param g trenutno stanje na tabli
     * @return optimalan potez za dato stanje na tabli
     */
    public abstract Move makeMove(Game g);
    
    protected int maxDepth;
}