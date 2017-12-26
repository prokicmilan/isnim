package etf.nim.pm160695d;

/**
 * Created by Proka on 12/22/2017.
 */
public class JednostavanIgrac implements Igrac {
    public JednostavanIgrac(int maxDepth) {
        this.maxDepth = maxDepth;
    }

    @Override
    public int getMaxDepth() {
        return maxDepth;
    }

    public void setMaxDepth(int maxDepth) {
        this.maxDepth = maxDepth;
    }

    public Move makeMove(Game g) {
        int bestResult = -2000;
        int lastMove = g.getLastMove();
        Move bestMove = new Move(0, 1);
        Move firstValid = null;

        for (int i = 0; i < g.board.length; i++) {
            if (g.board[i] != 0) {
                for (int j = 1; j <= Math.min(g.board[i], 2 * lastMove); j++) {
                    Move m = new Move(i, j);
                    if (g.isValid(m)) {
                        if (firstValid == null) {
                            firstValid = m;
                        }
                        g.board[i] -= j;
                        int result = minimax(g, 1, j, false);
                        result = (result != -100 && result != 100 ? -result : result);
                        if (result >= bestResult) {
                            bestResult = result;
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

    private int minimax(Game g, int depth, int lastMove, boolean isMax) {
        if (g.isOver()) {
            return isMax ? -100 : 100;
        }
        int sum = 0;
        for (int discs : g.board) {
            sum = sum ^ discs;
        }
        if (depth == maxDepth) {
            return sum;
        }
        int bestResult = (isMax ? -2000 : 2000);
        for (int i = 0; i < g.board.length; i++) {
            if (g.board[i] != 0) {
                for (int j = 1; j <= Math.min(g.board[i], 2 * lastMove); j++) {
                    Move m = new Move(i, j);
                    if (g.isValid(m)) {
                        g.board[i] -= j;
                        int result = minimax(g, depth + 1, j, !isMax);
                        result = (result != -100 && result != 100 ? -result : result);
                        if (isMax) {
                            if (result >= bestResult) {
                                bestResult = result;
                            }
                        }
                        else {
                            if (result <= bestResult) {
                                bestResult = result;
                            }
                        }
                        g.board[i] += j;
                    }
                }
            }
        }
        return bestResult;
    }
}
