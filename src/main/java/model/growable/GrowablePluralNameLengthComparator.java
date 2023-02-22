package model.growable;

import java.util.Comparator;

public class GrowableSingularNameLengthComparator implements Comparator<Growable> {
    @Override
    public int compare(final Growable growable1, final Growable growable2) {
        final int nameLength1 = growable1.getPlantType().getSingularName().length();
        final int nameLength2 = growable2.getPlantType().getSingularName().length();
        return Integer.compare(nameLength1, nameLength2);
    }
}
