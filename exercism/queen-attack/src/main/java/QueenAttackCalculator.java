import static java.util.Objects.isNull;

public final class QueenAttackCalculator {

    final BoardCoordinate black;
    final BoardCoordinate white;

    public QueenAttackCalculator(BoardCoordinate black, BoardCoordinate white) {
        if (isNull(black) || isNull(white))
            throw new IllegalArgumentException("You must supply valid board coordinates for both Queens.");
        if (black.rank == white.rank && black.file == white.file)
            throw new IllegalArgumentException("Queens may not occupy the same board coordinate.");

        this.black = black;
        this.white = white;
    }

    public boolean canQueensAttackOneAnother() {
        return black.rank == white.rank
                || black.file == white.file
                || Math.abs(white.rank - black.rank) == Math.abs(white.file - black.file);
    }
}