package io;

public enum CommandWord {
    QUIT("quit"),
    SELL("sell"),
    BUY("buy"),
    SHOW("show"),
    HARVEST("harvest"),
    PLANT("plant"),
    ADD_PLAYER("add player"),
    END_TURN("end turn");

    private final String word;

    CommandWord(String word) {
        this.word = word;
    }

    public String getWord() {
        return this.word;
    }
}
