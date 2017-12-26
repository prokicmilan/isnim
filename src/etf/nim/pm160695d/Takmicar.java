package etf.nim.pm160695d;


import java.util.Arrays;
import java.util.HashMap;

public class Takmicar implements Igrac {
    @Override
    public int getMaxDepth() {
        return 0;
    }

    @Override
    public Move makeMove(Game g) {
        Game currentState = new Game(Arrays.copyOf(g.board, g.board.length), g.lastMove);
        if (knownStates.containsKey(currentState) && knownStates.get(currentState).getValue() == 100) {
            Move m = knownStates.get(currentState);
            int discsOnPile = currentState.board[m.getPos()];
            int pos = 0;
            for (int i = 0; i < g.board.length; i++) {
                if (g.board[i] == discsOnPile) {
                    pos = i;
                    break;
                }
            }
            m.setPos(pos);
            m.setValue(m.getIsMax() == true ? m.getValue() : -m.getValue());
            return m;
        }
        int bestResult = -2000;
        int discsOnPile = 0;
        Move bestMove = null;
        for (int i = 0; i < g.board.length; i++) {
            if (g.board[i] != 0) {
                for (int j = 1; j <= Math.min(g.board[i], 2 * g.lastMove); j++) {
                    Move m = new Move(i, j);
                    if (g.isValid(m)) {
                        if (bestMove == null) {
                            bestMove = m;
                        }
                        g.board[i] -= j;
                        int result = minimax(g, j, false);
                        if (result > bestResult) {
                            bestResult = result;
                            discsOnPile = g.board[i] + j;
                            bestMove = m;
                        }
                        g.board[i] += j;
                        if (bestResult == 100) {
                            discsOnPile = g.board[i];
                            Move savedMove = new Move(findSortedPos(discsOnPile, currentState), bestMove.getNumDiscs(), true);
                            savedMove.setValue(bestResult);
                            bestMove.setValue(bestResult);
                            knownStates.put(currentState, savedMove);
                            return bestMove;
                        }
                    }
                }
            }
        }
        Move savedMove = new Move(findSortedPos(discsOnPile, currentState), bestMove.getNumDiscs(), true);
        savedMove.setValue(bestResult);
        bestMove.setValue(bestResult);
        knownStates.put(currentState, savedMove);
        return bestMove;
    }
    private HashMap<Game, Move> knownStates = new HashMap<>();

    private int minimax(Game g, int lastMove, boolean isMax) {
        Game currentState = new Game(Arrays.copyOf(g.board, g.board.length), lastMove);
        if (knownStates.containsKey(currentState)) {
            Move m = knownStates.get(currentState);
            return (isMax == m.getIsMax() ? m.getValue() : -m.getValue());
        }
        if (g.isOver()) {
            return (isMax ? -100 : 100);
        }
        int bestResult = (isMax ? -2000 : 2000);
        int discsOnPile = 0;
        Move bestMove = null;
        for (int i = 0; i < g.board.length; i++) {
            if (g.board[i] != 0) {
                for (int j = 1; j <= Math.min(g.board[i], 2 * lastMove); j++) {
                    Move m = new Move(i, j, isMax);
                    if (g.isValid(m)) {
                        if (bestMove == null) {
                            discsOnPile = g.board[i];
                            bestMove = m;
                        }
                        g.board[i] -= j;
                        int result = minimax(g, j, !isMax);
                        if (result == (isMax ? 100 : -100)) {
                            bestMove = m;
                            g.board[i] += j;
                            discsOnPile = g.board[i];
                            bestMove.setPos(findSortedPos(discsOnPile, currentState));
                            bestMove.setValue(result);
                            knownStates.put(currentState, bestMove);
                            return result;
                        }
                        if (isMax) {
                            if (result > bestResult) {
                                bestResult = result;
                                discsOnPile = g.board[i] + j;
                                bestMove = m;
                            }
                        }
                        else {
                            if (result < bestResult) {
                                discsOnPile = g.board[i] + j;
                                bestResult = result;
                                bestMove = m;
                            }
                        }
                        g.board[i] += j;
                    }
                }
            }
        }
        bestMove.setValue(bestResult);
        bestMove.setPos(findSortedPos(discsOnPile, currentState));
        knownStates.put(currentState, bestMove);
        return bestResult;
    }

    private int findSortedPos(int discsOnPile, Game state) {
        int []sortedBoard = state.getBoard();

        for (int i = 0; i < sortedBoard.length; i++) {
            if (sortedBoard[i] == discsOnPile) {
                return i;
            }
        }
        return 0;
    }
}
