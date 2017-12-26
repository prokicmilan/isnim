package etf.nim.pm160695d;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Created by Proka on 12/22/2017.
 */
public class Game {
    public Game(int[] board) {
        this.board = board;
        lastMove = 1000;
    }

    public Game(int[] board, int lastMove) {
        this.board = board;
        Arrays.sort(this.board);
        this.lastMove = lastMove;
    }

    public int[] getBoard() {
        return board;
    }

    public int getLastMove() {
        return lastMove;
    }

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
