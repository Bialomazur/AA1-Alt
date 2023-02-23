package model.growable;

import java.util.Comparator;

public class GrowablePluralNameLengthComparator implements Comparator<Growable> {
    @Override
    public int compare(final Growable growable1, final Growable growable2) {
        final int nameLength1 = growable1.getPlantType().getPluralName().length();
        final int nameLength2 = growable2.getPlantType().getPluralName().length();
        return Integer.compare(nameLength1, nameLength2);
    }
}
