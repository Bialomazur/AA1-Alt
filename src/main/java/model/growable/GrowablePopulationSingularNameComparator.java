package model.growable;

import java.util.Comparator;


//TODO: Error-prone, tests needed.
public class GrowablePopulationSingularNameComparator implements Comparator<Growable> {
    @Override
    public int compare(final Growable growable1, final Growable growable2) {
        if (growable1.getPopulation() == growable2.getPopulation()) {
            return growable1.getPlantType().getSingularName().compareTo(growable2.getPlantType().getSingularName());
        }

        return growable1.getPopulation() > growable2.getPopulation() ? -1 : 1; //TODO: Verify on forum whether this is correct.
    }
}
