package command;

import model.Game;
import model.Player;
import model.growable.Growable;
import model.growable.GrowablePopulationSingularNameComparator;
import model.growable.GrowableSingularNameLengthComparator;
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


    ShowBarnCommand(final Game game, final List<String> args) {
        super(game, args);
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
    protected boolean verifyArgumentsContent() {
        return NO_ARGUMENT_CONTENT_VERIFICATION;
    }


    private int getTotalGrowablesCount(final List<Growable> inventory) {
        int totalGrowablesCount = 0;
        for (final Growable growable : inventory) {
            totalGrowablesCount += growable.getPopulation();
        }
        return totalGrowablesCount;
    }

    //TODO: Outsource to a separate class
    //TODO: Test ferociously
    private int getLineLength(final List<Growable> inventory, final int gold) {
        if (inventory.size() <= LONGEST_SINGULAR_NAME_CRITERIA_INDEX) {
            return MIN_LINE_LENGTH;
        }

        final List<Growable> inventoryShallowCopy = new ArrayList<>(inventory);
        Collections.sort(inventoryShallowCopy, new GrowableSingularNameLengthComparator());

        final PlantType plantTypeToAlingTo = inventoryShallowCopy.get(LONGEST_SINGULAR_NAME_CRITERIA_INDEX).getPlantType();
        final int maxPlantTypeSingularNameLength = plantTypeToAlingTo.getSingularName().length();
        final int alignDistanceTo = max(this.getTotalGrowablesCount(inventory), gold);
        final int fixedPlantTypePostfixOffset = Output.PLANT_QUANTITY_OVERIEW_TYPE_POSTFIX.format().length();
        final int fixedGrowableQuantityPrefixOffset = Output.PLANT_QUANTITY_OVERIEW_QUANTITY_PREFIX.format().length();

        return alignDistanceTo + maxPlantTypeSingularNameLength + fixedPlantTypePostfixOffset + fixedGrowableQuantityPrefixOffset;
    }

    //TODO: Consider renaming local variables to be more descriptive
    @Override
    public void execute() {
        final Player player = this.getGame().getCurrentPlayer();
        final int gold = player.getGold();
        final int barnSpoilsIn = 0; // TODO: Figure out how to get the number of turns until the barn spoils

        final Barn barn = player.getTileMap().getBarn();
        final List<Growable> growables = new ArrayList<>(barn.getInventory());
        Collections.sort(growables, new GrowablePopulationSingularNameComparator());

        final StringBuilder output = new StringBuilder();
        final int lineLength = this.getLineLength(growables, gold); // TODO: Figure out how to determine the length of a line

        output.append(Output.BARN_SPOILS_IN.format(barnSpoilsIn));

        for (final Growable growable : growables) {
            final int quantity = growable.getPopulation();
            final String plantTypePluralLowerCase = growable.getPlantType().getPluralName().toLowerCase();

            output.append(Output.LINE_SEPARATOR.format());
            output.append(plantTypePluralLowerCase);
            output.append(Output.PLANT_QUANTITY_OVERIEW_TYPE_POSTFIX.format());

            for (int i = 0; i < lineLength; i++) {
                output.append(Output.PLANT_QUANTITY_OVERIEW_QUANTITY_PREFIX.format());
            }
        }

        this.setOutput(output.toString());
    }
}
