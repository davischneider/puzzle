import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Application {

    public static void main(String[] args) {
        int[][] firstState = {{2, 1, 3}, {8, 0, 4}, {7, 5, 6}};
        int[][] goalState = {{1, 2, 3}, {8, 0, 4}, {7, 6, 5}};
//        int[][] goalState = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};

        Puzzle puzzle = new Puzzle(firstState, null);

        HashSet<String> closed = new HashSet<>();
        Queue<Puzzle> open = new LinkedList<>();
        open.add(puzzle);

        while (!open.isEmpty()) {
            Puzzle currentState = open.poll();
            closed.add(Arrays.deepToString(currentState.state()));

            if (Arrays.deepEquals(currentState.state(), goalState)) {
                System.out.println("Found in: " + closed.size() + " possibilities");
                System.out.println("Solution found: " + Arrays.deepToString(currentState.state()));
                System.out.println("Success path: ");
                currentState.getSuccesPath(currentState).forEach(parentPuzzle -> System.out.println(Arrays.deepToString(parentPuzzle.state())));
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
