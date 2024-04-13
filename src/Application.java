import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Application {

    public static void main(String[] args) {
        int[] firstState = {7, 2, 4, 5, 0, 6, 8, 3, 1};
        int[] goalState = {0, 1, 2, 3, 4, 5, 6, 7, 8};

        Puzzle puzzle = new Puzzle(firstState);

        HashSet<String> closed = new HashSet<>();
        Queue<Puzzle> open = new LinkedList<>();
        open.add(puzzle);

        while (!open.isEmpty()) {
            Puzzle currentState = open.poll();

            if (Arrays.equals(currentState.getState(), goalState)) {
                System.out.println("Solução Encontrada: " + Arrays.toString(currentState.getState()));
                break;
            }

            // Gerar filhos


        }
    }
}
