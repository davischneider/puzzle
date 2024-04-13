import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Puzzle {

    private int[] state;
    private static final int ROW = 3;
    private static final int COLUMN = 3;

    public Puzzle(int[] firstState) {
        this.state = firstState;
    }

    public int[] getState() {
        return state;
    }

    public void setState(int[] state) {
        this.state = state;
    }

    public List<Puzzle> nextStates() {
        int emptyState = IntStream.range(0, getState().length)
                .filter(index -> getState()[index] == 0)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("0 not found"));

        List<Puzzle> children = new ArrayList<>();

        if (emptyState % COLUMN > 0) {
        }
        return children;
    }
}
