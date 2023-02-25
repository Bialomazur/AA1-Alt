package io;

public enum CommandWord {
    QUIT("quit"),
    SELL("sell"),
    BUY_VEGETABLE("buy vegetable"),
    BUY_LAND("buy land"),
    SHOW_BARN("show barn"),
    SHOW_BOARD("show board"),
    SHOW_MARKET("show market"),
    HARVEST("harvest"),
    PLANT("plant"),
    ADD_PLAYER("add player"),
    END_TURN("end turn"),
    SHUFFLE_TILES("shuffle tiles"),
    SET_WINNING_GOLD("set winning gold"),
    SET_STARTING_GOLD("set starting gold"),
    SET_PLAYER_COUNT("set player count"),

    ;

    private final String word;

    CommandWord(final String word) {
        this.word = word;
    }

    public String getWord() {
        return this.word;
    }
}
