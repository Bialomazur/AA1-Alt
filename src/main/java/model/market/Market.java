package model.market;

import model.game.Player;
import model.growable.PlantType;

import java.util.HashSet;
import java.util.Set;

public class Market {
    private final Set<PriceIndicator> priceIndicators = new HashSet<>();

    public Market() {
    }

    public int getGrowablePrice(final PlantType plantType) {
        return 0;
    }

    public void addPriceIndicator(final PriceIndicator priceIndicator) {
        this.priceIndicators.add(priceIndicator);
    }

    public void buy(final PlantType plantType, final Player player) {

    }

    public void sell(final PlantType plantType, final Player player) {

    }

    public void updatePrices() {
        for (final PriceIndicator priceIndicator : this.priceIndicators) {
            //TODO: Update price indicators
        }
    }

}
