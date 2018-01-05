    package etf.nim.pm160695d;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Created by Proka on 12/22/2017.
 */
public class Game {
    /**
     * 
     * @param board niz u kom se čuva trenutno stanje na tabli - na stubu i
     * se nalazi board[i] žetona
     */
    public Game(int[] board) {
        this.board = board;
        lastMove = 1000;
    }
    /**
     * 
     * @param board niz u kom se čuva trenutno stanje na tabli - na stubu i
     * se nalazi board[i] žetona
     * @param lastMove broj žetona skinutih sa nekog stuba u prethodnom potezu
     */
    public Game(int[] board, int lastMove) {
        this.board = board;
        Arrays.sort(this.board);
        this.lastMove = lastMove;
    }
    
    /**
     * 
     * @return niz koji predstavlja trenutno stanje na tabli
     */
    public int[] getBoard() {
        return board;
    }

    /**
     * 
     * @return broj žetona skinutih u prethodnom potezu
     */
    public int getLastMove() {
        return lastMove;
    }

    /**
     * Proverava da li je igra došla do kraja. Igri je kraj kada 
     * ni na jednom stubu nema preostalih žetona
     * @return <code>true</code> ako je kraj igre, <code>false</code> ako nije
     */
    public boolean isOver() {
        boolean over = true;

        for (int discs : board) {
            if (discs != 0) {
                over = false;
                break;
            }
        }
        return over;
    }
    
    /**
     * Proverava da li je zadati potez dozvoljen. Potez je dozvoljen ako broj
     * žetona koji se skida u datom potezu nije veći od dvostrukog broja žetona
     * skinutih u prethodnom potezu, i ako skidanjem zadatog broja žetona ne
     * dolazi stanja na tabli u kom na dva stuba postoji isti broj žetona
     * @param m potez čija se validnost proverava
     * @return <code>true</code> ako je potez validan <code>false</code> ako nije
     * @see Move
     */
    public boolean isValid(Move m) {
        boolean valid = true;
        Set<Integer> states = new HashSet<>();

        if (m.getNumDiscs() > 2 * lastMove) {
            valid = false;
        }
        if (board[m.getPos()] < m.getNumDiscs()) {
            valid = false;
        }
        else {
            board[m.getPos()] -= m.getNumDiscs();
            for (int discs : board) {
                if (discs != 0 && states.contains(discs)) {
                    valid = false;
                    break;
                }
                states.add(discs);
            }
            board[m.getPos()] += m.getNumDiscs();
        }
        return valid;
    }

    /**
     * Proverava da li je zadati potez validan i ako jeste menja stanje na tabli
     * shodno informaciji u potezu - skida odgovarajući broj žetona sa odgovarajućeg
     * diska i postavlja promenljivu <code>lastMove</code> na broj diskova skinutih
     * u datom potezu. Ukoliko potez nije validan, program se završava sa kodom
     * 10. Do takve situacije nikada ne bi trebalo da dođe.
     * @param m potez koji treba odigrati
     */
    public void makeMove(Move m) {
        if (isValid(m)) {
            board[m.getPos()] -= m.getNumDiscs();
            lastMove = m.getNumDiscs();
        }
        else {
            System.out.println("Potez nije validan. ");
            System.out.println(m);
            System.exit(10);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Stanje igre: \n");
        for (int discs : board) {
            sb.append(Integer.toString(discs));
            sb.append(" ");
        }
        sb.append("\n");

        return sb.toString();
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return lastMove == game.lastMove &&
                Arrays.equals(board, game.board);
    }

    @Override
    public int hashCode() {

        int result = Objects.hash(lastMove);
        result = 31 * result + Arrays.hashCode(board);
        return result;
    }

    protected int [] board;
    protected int lastMove;
}
