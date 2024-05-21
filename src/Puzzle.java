import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

public record Puzzle(int[][] state, Puzzle parent, int[][] goalState) {

    private static final int ROW_INDEX = 0;
    private static final int COLUMN_INDEX = 1;


    public List<Puzzle> nextStates() {
        List<Puzzle> children = new ArrayList<>();
        int[] emptySpace = getEmptyPosition();
        if (emptySpace == null) {
            throw new IllegalArgumentException("0 not found!");
        }
        int emptyRow = emptySpace[ROW_INDEX];
        int emptyColumn = emptySpace[COLUMN_INDEX];

        if (emptyRow > 0) {
            children.add(new Puzzle(moveUp(emptyRow, emptyColumn, state), this, goalState));
        }

        if (emptyRow < 2) {
            children.add(new Puzzle(moveDown(emptyRow, emptyColumn, state), this, goalState));
        }

        if (emptyColumn > 0) {
            children.add(new Puzzle(moveLeft(emptyRow, emptyColumn, state), this, goalState));
        }

        if (emptyColumn < 2) {
            children.add(new Puzzle(moveRight(emptyRow, emptyColumn, state), this, goalState));
        }

        return children;
    }

    public List<Puzzle> getSuccesPath(Puzzle successPuzzle) {
        List<Puzzle> puzzles = new ArrayList<>();
        Puzzle inAnalisys = successPuzzle;
        while (!Objects.isNull(inAnalisys)) {
            puzzles.add(inAnalisys);
            inAnalisys = inAnalisys.parent();
        }
        return puzzles;
    }

    private int[] getEmptyPosition() {
        return IntStream.range(0, state.length)
                .boxed()
                .flatMap(row -> IntStream.range(0, state[row].length)
                        .filter(column -> state[row][column] == 0)
                        .mapToObj(res -> new int[]{row, res}))
                .findFirst()
                .orElse(null);
    }

    private int[][] moveUp(int emptyRow, int emptyColumn, int[][] parentState) {
        int upRow = emptyRow - 1;
        int upColumn = emptyColumn;
        int upValue = parentState[upRow][upColumn];

        int[][] childState = getClonedState(parentState);
        childState[upRow][upColumn] = 0;
        childState[emptyRow][emptyColumn] = upValue;

        return childState;
    }

    private int[][] moveDown(int emptyRow, int emptyColumn, int[][] parentState) {
        int downRow = emptyRow + 1;
        int downColumn = emptyColumn;
        int downValue = parentState[downRow][downColumn];

        int[][] childState = getClonedState(parentState);
        childState[downRow][downColumn] = 0;
        childState[emptyRow][emptyColumn] = downValue;
        return childState;
    }

    private int[][] moveLeft(int emptyRow, int emptyColumn, int[][] parentState) {
        int leftRow = emptyRow;
        int leftColumn = emptyColumn - 1;
        int leftValue = parentState[leftRow][leftColumn];

        int[][] childState = getClonedState(parentState);
        childState[leftRow][leftColumn] = 0;
        childState[emptyRow][emptyColumn] = leftValue;
        return childState;
    }

    private int[][] moveRight(int emptyRow, int emptyColumn, int[][] parentState) {
        int rightRow = emptyRow;
        int rightColumn = emptyColumn + 1;
        int rightValue = parentState[rightRow][rightColumn];

        int[][] childState = getClonedState(parentState);
        childState[rightRow][rightColumn] = 0;
        childState[emptyRow][emptyColumn] = rightValue;
        return childState;
    }

    private int[][] getClonedState(int[][] state) {
        return Arrays.stream(state).map(int[]::clone).toArray(int[][]::new);
    }

    public int getFinalDistance() {
        return manhattanDistance() + getPathLength();
    }

    private int manhattanDistance() {
        int distance = 0;
        for (int row = 0; row < state.length; row++) {
            for (int col = 0; col < state[row].length; col++) {
                int value = state[row][col];
                if (value != 0) {
                    int[] goalPosition = findPosition(value, goalState);
                    distance += Math.abs(row - goalPosition[0]) + Math.abs(col - goalPosition[1]);
                }
            }
        }
        return distance;
    }

    private int[] findPosition(int value, int[][] state) {
        for (int row = 0; row < state.length; row++) {
            for (int col = 0; col < state[row].length; col++) {
                if (state[row][col] == value) {
                    return new int[]{row, col};
                }
            }
        }
        throw new IllegalArgumentException("Value " + value + " not found in the given state");
    }

    private int getPathLength() {
        int length = 0;
        Puzzle current = this;
        while (current.parent != null) {
            length++;
            current = current.parent;
        }
        return length;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Puzzle puzzle = (Puzzle) o;

        return Arrays.deepEquals(state, puzzle.state);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(state);
    }

    @Override
    public String toString() {
        return "Puzzle{" +
                "state=" + Arrays.toString(state) +
                '}';
    }
}
