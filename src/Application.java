import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;

public class Application {

    public static void main(String[] args) {
        int[][] firstState = {{2, 1, 3}, {8, 0, 4}, {7, 5, 6}};
        int[][] goalState = {{1, 2, 3}, {8, 0, 4}, {7, 6, 5}};

        Puzzle puzzle = new Puzzle(firstState, null, goalState);

        HashSet<String> closed = new HashSet<>();
        PriorityQueue<Puzzle> open = new PriorityQueue<>(Comparator.comparingInt(Puzzle::getFinalDistance));
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
