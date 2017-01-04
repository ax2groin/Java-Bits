import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toList;

public final class MinesweeperBoard {

    static final int BOARD_MIN = 0;

    static final char EMPTY = ' ';
    static final char BOMB = '*';

    final List<String> inputBoard;

    final int widthBound;
    final int heightMax;

    public MinesweeperBoard(List<String> inputBoard) {
        this.inputBoard = validateBoard(inputBoard);
        heightMax = inputBoard.size() - 1;
        widthBound = inputBoard.isEmpty() ? BOARD_MIN : inputBoard.get(0).length() - 1;
    }

    public List<String> getAnnotatedRepresentation() {
        List<StringBuilder> annotated = inputBoard.stream()
                                                  .map(StringBuilder::new)
                                                  .collect(toList());

        findBombs(inputBoard).entrySet()
                             .forEach(e -> e.getValue().forEach(x -> updateNeighbors(annotated, x, e.getKey())));

        return annotated.stream().map(StringBuilder::toString).collect(toList());
    }

    private static List<String> validateBoard(List<String> board) {
        if (isNull(board))
            throw new IllegalArgumentException("Input board may not be null.");

        validateRowLengths(board);
        board.forEach(MinesweeperBoard::validateRow);

        return board;
    }

    private static void validateRow(String row) {
        if (row.replaceAll("\\*", "").trim().length() > 0)
            throw new IllegalArgumentException("Input board can only contain the characters ' ' and '*'.");
    }

    private static void validateRowLengths(List<String> board) {
        if (board.stream().map(String::length).collect(Collectors.toSet()).size() > 1)
            throw new IllegalArgumentException("Input board rows must all have the same number of columns.");
    }

    private HashMap<Integer, List<Integer>> findBombs(List<String> board) {
        HashMap<Integer, List<Integer>> bombMap = new HashMap<>();
        for (int h = BOARD_MIN; h < board.size(); h++) {
            ArrayList<Integer> positions = new ArrayList<>();
            String row = board.get(h);
            int bomb = BOARD_MIN;
            while ((bomb = row.indexOf(BOMB, bomb)) >= BOARD_MIN) {
                positions.add(bomb);
                bomb++;
            }
            bombMap.put(h, positions);
        }
        return bombMap;
    }

    private void updateNeighbors(List<StringBuilder> board, int x, int y) {
        IntStream.rangeClosed(Math.max(BOARD_MIN, x - 1), Math.min(x + 1, widthBound))
                 .forEach(xPos ->
                         IntStream.rangeClosed(Math.max(BOARD_MIN, y - 1), Math.min(y + 1, heightMax))
                                  .forEach(yPos -> incrementPosition(board, xPos, yPos)));
    }

    private void incrementPosition(List<StringBuilder> board, int x, int y) {
        StringBuilder row = board.get(y);
        char spot = row.charAt(x);
        if (spot == EMPTY)
            row.setCharAt(x, '1');
        else if (spot != BOMB)
            row.setCharAt(x, (char) (spot + 1));
    }
}
