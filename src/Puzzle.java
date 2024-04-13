import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public record Puzzle(int[][] state) {

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
            children.add(new Puzzle(moveUp(emptyRow, emptyColumn, state)));
        }

        if (emptyRow < 2) {
            children.add(new Puzzle(moveDown(emptyRow, emptyColumn, state)));
        }

        if (emptyColumn > 0) {
            children.add(new Puzzle(moveLeft(emptyRow, emptyColumn, state)));
        }

        if (emptyColumn < 2) {
            children.add(new Puzzle(moveRight(emptyRow, emptyColumn, state)));
        }

        return children;
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
