package command;

public enum Output {
    BARN_SPOILS_IN("Barn (spoils in %d turns)"), //TODO: Consider case of plural and singular
    LINE_SEPARATOR("\n"),
    REPORT_ATTRIBUTE_POSTFIX(":"),
    REPORT_EMPTY_SPACE(" "),
    REPORT_SECTION_SEPARATOR("-"),
    BARN_OVERVIEW_FOOTER_SUM("Sum"),
    BARN_OVERVIEW_FOOTER_GOLD("Gold"),
    EMPTY_OUTPUT(""),
    ;

    private final String template;

    Output(String template) {
        this.template = template;
    }

    public String format(Object... args) {
        return String.format(this.template, args);
    }
}
