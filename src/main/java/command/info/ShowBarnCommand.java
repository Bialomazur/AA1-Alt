package command.info;

import command.GameCommand;
import command.Output;
import model.Game;
import model.Player;
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
    private static final boolean NO_ARGUMENT_CONTENT_VERIFICATION = true;
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
    protected void validateArgumentsContent(List<String> args) {
        // No argument content verification
    }


    private int getTotalGrowablesCount(final List<Growable> inventory) {
        int totalGrowablesCount = 0;
        for (final Growable growable : inventory) {
            totalGrowablesCount += growable.getPopulation();
        }
        return totalGrowablesCount;
    }

    private String buildInventoryReportLine(Growable growable, int lineLength) {
        final StringBuilder line = new StringBuilder();
        final int quantity = growable.getPopulation();
        final String plantTypePluralLowerCase = growable.getPlantType().getPluralName().toLowerCase();

        line.append(Output.LINE_SEPARATOR.format());
        line.append(plantTypePluralLowerCase);
        line.append(Output.REPORT_ATTRIBUTE_POSTFIX.format());

        while (line.length() < lineLength - String.valueOf(quantity).length()) {
            line.append(Output.REPORT_EMPTY_SPACE.format());
        }
        line.append(Output.REPORT_EMPTY_SPACE.format());

        StringBuilder populationString = new StringBuilder();
        populationString.append(quantity);


        line.append(growable.getPopulation());
        return line.toString();
    }

    private String buildAttributeReportLine(String attribute, int value, int lineLength) {
        final StringBuilder line = new StringBuilder();

        line.append(attribute);
        line.append(Output.REPORT_ATTRIBUTE_POSTFIX.format());

        while (line.length() < lineLength - String.valueOf(value).length()) {
            line.append(Output.REPORT_EMPTY_SPACE.format());
        }

        line.append(value);
        return line.toString();
    }

    private String buildSeparatorLine(String separator, int lineLength) {
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

    private String buildInventoryReportSection(Barn barn, int lineLength) {
        final StringBuilder line = new StringBuilder();

        return line.toString();
    }
    //TODO: Consider renaming local variables to be more descriptive
    @Override
    public void execute() {
        final StringBuilder output = new StringBuilder();
        final Player player = this.getGame().getCurrentPlayer();

        final int gold = player.getGold();
        final int barnSpoilsIn = 6; // TODO: Figure out how to get the number of turns until the barn spoils
        final Barn barn = player.getTileMap().getBarn();

        final List<Growable> growables = new ArrayList<>(barn.getInventory());
        Collections.sort(growables, new GrowablePopulationSingularNameComparator());
        final int lineLength = this.getLineLength(growables, gold);

        output.append(Output.BARN_SPOILS_IN.format(barnSpoilsIn));

        for (final Growable growable : growables) {
            output.append(this.buildInventoryReportLine(growable, lineLength));
        }

        output.append(this.buildSeparatorLine(Output.LINE_SEPARATOR.format(), DEFAULT_LINE_SEPARATOR_LENGTH));
        output.append(this.buildSeparatorLine(Output.REPORT_SECTION_SEPARATOR.format(), lineLength));
        output.append(this.buildSeparatorLine(Output.LINE_SEPARATOR.format(), DEFAULT_LINE_SEPARATOR_LENGTH));
        output.append(this.buildAttributeReportLine(Output.BARN_OVERVIEW_FOOTER_SUM.format(), this.getTotalGrowablesCount(growables), lineLength));
        output.append(this.buildSeparatorLine(Output.LINE_SEPARATOR.format(), GOLD_LINE_SEPARATOR_LENGTH));
        output.append(buildAttributeReportLine(Output.BARN_OVERVIEW_FOOTER_GOLD.format(), gold, lineLength));
        this.setOutput(output.toString());
    }
}
