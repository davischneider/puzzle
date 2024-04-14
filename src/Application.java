import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Application {

    public static void main(String[] args) {
        int[][] firstState = {{7, 2, 4}, {5, 0, 6}, {8, 3, 1}};
        int[][] goalState = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}};
//        int[][] goalState = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};

        Puzzle puzzle = new Puzzle(firstState);

        HashSet<String> closed = new HashSet<>();
        Queue<Puzzle> open = new LinkedList<>();
        open.add(puzzle);

        while (!open.isEmpty()) {
            Puzzle currentState = open.poll();
            closed.add(Arrays.deepToString(currentState.state()));

            if (Arrays.deepEquals(currentState.state(), goalState)) {
                System.out.println("Found in: " + closed.size() + " possibilities");
                System.out.println("Solution found: " + Arrays.deepToString(currentState.state()));
                break;
            }

            List<Puzzle> nextStates = currentState.nextStates();
            nextStates.forEach(puz -> {
                if (!closed.contains(Arrays.deepToString(puz.state()))) {
                    open.add(puz);
                }
            });

            if (open.isEmpty()) {
                System.out.println("There is no solution!");
            }

        }
    }
}
