import io.session.ConfigurationSession;
import io.session.PlaySession;
import model.game.Game;

import java.util.Scanner;

public class Application {
    private static final String NO_ARGUMENTS_EXPECTED = "No arguments expected";

    public static void main(final String[] args) {

        if (args.length != 0) {
            System.out.println(NO_ARGUMENTS_EXPECTED);
            return;
        }

        final Scanner scanner = new Scanner(System.in);
        final Game game = new Game();
        final ConfigurationSession configurationSession = new ConfigurationSession(game, scanner);
        configurationSession.start();

        if (!configurationSession.hasBeenTerminatedPrematurily()) {
            new PlaySession(game, scanner).start();
        }
    }
}
