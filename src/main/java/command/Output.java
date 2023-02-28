package command;

public enum Output {
    BARN_SPOILS_IN("Barn (spoils in %d turns)"), //TODO: Consider case of plural and singular
    BARN("Barn"),
    LINE_SEPARATOR("\n"),
    REPORT_ATTRIBUTE_POSTFIX(":"),
    REPORT_EMPTY_SPACE(" "),
    REPORT_SECTION_SEPARATOR("-"),
    SUM("Sum"),
    GOLD("Gold"),
    EMPTY_OUTPUT(""),
    ;

    private final String template;

    Output(final String template) {
        this.template = template;
    }

    public String format(final Object... args) {
        return String.format(this.template, args);
    }
}
