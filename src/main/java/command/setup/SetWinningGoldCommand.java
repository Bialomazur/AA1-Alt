package command.setup;

import command.GameCommand;
import model.Game;

import java.util.List;


//TODO: Fix error message dry-violation
public class SetWinningGoldCommand extends GameCommand {

    private static final int MIN_ARGUMENT_COUNT = 1;
    private static final int MAX_ARGUMENT_COUNT = 1;
    private static final int GOLD_INDEX = 0;
    private static final int MIN_WINNING_GOLD = 1;

    private static final String WINNING_GOLD_MUST_AT_LEAST_BE = "Winning gold must at least be %d.";
    private static final String WINNING_GOLD_MUST_BE_AN_INTEGER = "Winning gold must be an integer.";


    public SetWinningGoldCommand(final Game game) {
        super(game);
    }

    @Override
    protected int getMinArgumentCount() {
        return MIN_ARGUMENT_COUNT;
    }

    @Override
    protected int getMaxArgumentCount() {
        return MAX_ARGUMENT_COUNT;
    }

    @Override
    protected void validateArgumentsContent(final List<String> args) {
        int gold = 0;
        try {
            gold = Integer.parseInt(args.get(GOLD_INDEX));
        } catch (final NumberFormatException e) {
            throw new IllegalArgumentException(WINNING_GOLD_MUST_BE_AN_INTEGER);
        }

        if (gold < MIN_WINNING_GOLD) {
            throw new IllegalArgumentException(WINNING_GOLD_MUST_AT_LEAST_BE.formatted(MIN_WINNING_GOLD));
        }
    }

    @Override
    public void execute() {
        this.getGame().setWinningGold(Integer.parseInt(this.getArgs().get(GOLD_INDEX)));
    }
}
