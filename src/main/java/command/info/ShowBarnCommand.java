package command.info;

import command.GameCommand;
import command.Output;
import model.game.Game;
import model.growable.Growable;
import model.growable.GrowablePopulationSingularNameComparator;
import model.growable.GrowablePluralNameLengthComparator;
import model.growable.PlantType;
import model.map.Barn;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.lang.Math.max;

public class ShowBarnCommand extends GameCommand {
    private static final int MIN_ARGUMENT_COUNT = 0;
    private static final int MAX_ARGUMENT_COUNT = 0;
    private static final int MIN_LINE_LENGTH = 0;
    private static final int LONGEST_SINGULAR_NAME_CRITERIA_INDEX = 0;
    private static final int DEFAULT_LINE_SEPARATOR_LENGTH = 1;
    private static final int GOLD_LINE_SEPARATOR_LENGTH = 2;


    public ShowBarnCommand(final Game game) {
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
        // No argument content verification
    }


    private int getTotalGrowablesCount(final List<Growable> inventory) {
        int totalGrowablesCount = 0;
        for (final Growable growable : inventory) {
            totalGrowablesCount += growable.getPopulation();
        }
        return totalGrowablesCount;
    }

    private String buildInventoryReportLine(final Growable growable, final int lineLength) {
        final StringBuilder line = new StringBuilder();
        final int population = growable.getPopulation();
        line.append(Output.LINE_SEPARATOR.format());
        line.append(growable.getPlantType().getPluralName().toLowerCase());
        line.append(Output.REPORT_ATTRIBUTE_POSTFIX.format());

        while (line.length() < lineLength - String.valueOf(population).length()) {
            line.append(Output.REPORT_EMPTY_SPACE.format());
        }

        line.append(Output.REPORT_EMPTY_SPACE.format()); //Extra space so that it has offset from the double-point prefix.
        line.append(population);

        return line.toString();
    }

    private String buildAttributeReportLine(final String attribute, final int value, final int lineLength) {
        final StringBuilder line = new StringBuilder();

        line.append(attribute);
        line.append(Output.REPORT_ATTRIBUTE_POSTFIX.format());

        //Do while because every attribute line has at least one space between the attribute-postfix and the value.
        do {
            line.append(Output.REPORT_EMPTY_SPACE.format());
        } while(line.length() < lineLength - String.valueOf(value).length());

        line.append(value);
        return line.toString();
    }

    private String buildSeparatorLine(final String separator, final int lineLength) {
        final StringBuilder line = new StringBuilder();
        for (int i = 0; i < lineLength; i++) {
            line.append(separator);
        }
        return line.toString();
    }

    //TODO: Outsource to a separate class
    //TODO: Test ferociously
    private int getLineLength(final List<Growable> inventory, final int gold) {
        //TODO: Refactor
        if (inventory.size() <= LONGEST_SINGULAR_NAME_CRITERIA_INDEX) {
            return MIN_LINE_LENGTH;
        }

        final List<Growable> inventoryShallowCopy = new ArrayList<>(inventory);
        Collections.sort(inventoryShallowCopy, new GrowablePluralNameLengthComparator());
        final PlantType plantTypeToAlignTo = inventoryShallowCopy.get(inventoryShallowCopy.size() - 1).getPlantType();
        final int maxPluralNameLength = plantTypeToAlignTo.getPluralName().length();
        final int alignDistanceTo = String.valueOf(max(this.getTotalGrowablesCount(inventory), gold)).length();
        final int fixedPlantTypePostfixOffset = Output.REPORT_ATTRIBUTE_POSTFIX.format().length();
        final int fixedGrowableQuantityPrefixOffset = Output.REPORT_EMPTY_SPACE.format().length();
        final int fixedOffset = fixedGrowableQuantityPrefixOffset + fixedPlantTypePostfixOffset;

        return alignDistanceTo + maxPluralNameLength + fixedOffset;
    }

    private String buildInventoryReportLines(final List<Growable> inventory, final int lineLength) {
        final StringBuilder lines = new StringBuilder();
        for (final Growable growable : inventory) {
            lines.append(this.buildInventoryReportLine(growable, lineLength));
        }
        return lines.toString();
    }

    private String buildEmptyBarnReport(final int gold) {
        final StringBuilder output = new StringBuilder();
        output.append(Output.BARN.format());
        output.append(this.buildSeparatorLine(Output.LINE_SEPARATOR.format(), DEFAULT_LINE_SEPARATOR_LENGTH));
        output.append(this.buildAttributeReportLine(Output.GOLD.format(), gold, MIN_LINE_LENGTH));
        return output.toString();
    }

    private String buildDefaultReport(final Barn barn, final int gold) {
        final StringBuilder output = new StringBuilder();
        final List<Growable> growables = new ArrayList<>(barn.getAllStoredGrowables());
        final int lineLength = this.getLineLength(growables, gold);
        final int barnSpoilsIn = barn.getUpdatesLeftUntilAction();

        Collections.sort(growables, new GrowablePopulationSingularNameComparator());
        output.append(Output.BARN_SPOILS_IN.format(barnSpoilsIn));
        output.append(this.buildInventoryReportLines(growables, lineLength));
        output.append(this.buildSeparatorLine(Output.LINE_SEPARATOR.format(), DEFAULT_LINE_SEPARATOR_LENGTH));
        output.append(this.buildSeparatorLine(Output.REPORT_SECTION_SEPARATOR.format(), lineLength));
        output.append(this.buildSeparatorLine(Output.LINE_SEPARATOR.format(), DEFAULT_LINE_SEPARATOR_LENGTH));
        output.append(this.buildAttributeReportLine(Output.SUM.format(), this.getTotalGrowablesCount(growables), lineLength));
        output.append(this.buildSeparatorLine(Output.LINE_SEPARATOR.format(), GOLD_LINE_SEPARATOR_LENGTH));
        output.append(this.buildAttributeReportLine(Output.GOLD.format(), gold, lineLength));

        return output.toString();
    }

    //TODO: Consider renaming local variables to be more descriptive
    @Override
    public void execute() {
        final int gold = this.getGame().getCurrentPlayer().getGold();
        final Barn barn = this.getGame().getCurrentPlayer().getTileMap().getBarn();

        this.setOutput(barn.isEmpty() ? this.buildEmptyBarnReport(gold) : this.buildDefaultReport(barn, gold));
    }
}
