package etf.nim.pm160695d;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Proka on 12/22/2017.
 */
public class AlfaBetaIgrac implements Igrac {
    public AlfaBetaIgrac(int maxDepth) {
        this.maxDepth = maxDepth;
    }

    @Override
    public int getMaxDepth() {
        return maxDepth;
    }

    @Override
    public Move makeMove(Game g) {
        int lastMove = g.getLastMove();
        int alpha = -2000;
        int beta = 2000;
        Move bestMove = new Move(0, 1);
        Move firstValid = null;

        for(int i = 0; i < g.board.length; i++) {
            if (g.board[i] != 0) {
                for (int j = 1; j <= Math.min(g.board[i], 2 * lastMove); j++) {
                    Move m = new Move(i, j);
                    if (g.isValid(m)) {
                        if (firstValid == null) {
                            firstValid = m;
                        }
                        g.board[i] -= j;
                        int result = minimax(g, lastMove, 1, alpha, beta, false);
                        result = (result != -100 && result != 100 ? -result : result);
                        if (result > alpha) {
                            alpha = result;
                            bestMove = m;
                            bestMove.setValue(result);
                        }
                        g.board[i] += j;
                    }
                }
            }
        }
        if (!g.isValid(bestMove)) {
            bestMove = firstValid;
        }
        return bestMove;
    }

    private int maxDepth;

    private int minimax(Game g, int lastMove, int depth, int alpha, int beta, boolean isMax) {
        if (g.isOver()) {
            return isMax ? -100 : 100;
        }
        int sum = 0;
        for (int discs : g.board) {
            sum ^= discs;
        }
        if (depth == maxDepth) {
            return sum;
        }
        int bestResult = (isMax ? -2000 : 2000);
        for(int i = 0; i < g.board.length; i++) {
            for (int j = 1; j <= Math.min(g.board[i], 2 * lastMove); j++) {
                if (g.board[i] != 0) {
                    Move m = new Move(i, j);
                    if (g.isValid(m)) {
                        g.board[i] -= j;
                        int result = minimax(g, j, depth + 1, alpha, beta, !isMax);
                        result = (result != -100 && result != 100 ? -result : result);
                        if (isMax) {
                            if (result > bestResult) {
                                bestResult = result;
                            }
                            if (result > alpha) {
                                alpha = result;
                            }
                        }
                        else {
                            if (result < bestResult) {
                                bestResult = result;
                            }
                            if (result < beta) {
                                beta = result;
                            }
                        }
                        g.board[i] += j;
                        if (alpha >= beta) {
                            return bestResult;
                        }
                    }
                }
            }
        }
        return bestResult;
    }

    private boolean isValid(int [] board) {
        Set<Integer> states = new HashSet<>();
        boolean valid = true;

        for (int discs : board) {
            if (discs != 0 && states.contains(discs)) {
                valid = false;
                break;
            }
            states.add(discs);
        }
        return valid;
    }

    private boolean isOver(int [] board) {
        boolean over = true;

        for (int discs : board) {
            if (discs != 0) {
                over = false;
                break;
            }
        }
        return over;
    }
}
