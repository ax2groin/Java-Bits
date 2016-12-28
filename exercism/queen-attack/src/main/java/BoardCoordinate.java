public final class BoardCoordinate {

    static final int MIN_POSITION = 0;
    static final int MAX_POSITION = 7;

    final int rank;
    final int file;

    public BoardCoordinate(int rank, int file) {
        // NOTE: Unit tests expect "positive" message, but validates "non-negative".
        if (rank < MIN_POSITION)
            throw new IllegalArgumentException("Coordinate must have positive rank.");
        if (file < MIN_POSITION)
            throw new IllegalArgumentException("Coordinate must have positive file.");
        if (MAX_POSITION < rank)
            throw new IllegalArgumentException("Coordinate must have rank <= 7.");
        if (MAX_POSITION < file)
            throw new IllegalArgumentException("Coordinate must have file <= 7.");

        this.rank = rank;
        this.file = file;
    }
}