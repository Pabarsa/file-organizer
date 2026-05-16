package organizer;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        try {
            CliArgs cliArgs = CliArgs.parse(args);
            new FileOrganizer(cliArgs).run();
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
            System.err.println("Uso:   java -cp out organizer.Main <ruta> [--dry-run]");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Error de E/S: " + e.getMessage());
            System.exit(2);
        }
    }
}
