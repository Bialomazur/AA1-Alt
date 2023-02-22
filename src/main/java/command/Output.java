package command;

public enum Output {
    BARN_SPOILS_IN("Barn (spoils in %d turns)"),
    LINE_SEPARATOR("\n"),
    PLANT_QUANTITY_OVERIEW_TYPE_POSTFIX(":"),
    PLANT_QUANTITY_OVERIEW_QUANTITY_PREFIX(" "),

    ;

    private final String template;

    Output(String template) {
        this.template = template;
    }

    public String format(Object... args) {
        return String.format(this.template, args);
    }
}
