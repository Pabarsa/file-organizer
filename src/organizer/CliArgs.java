package organizer;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Parsea y valida los argumentos de línea de comandos.
 * Lanza IllegalArgumentException con mensajes claros si algo falla.
 */
public class CliArgs {

    private final Path folder;
    private final boolean dryRun;

    private CliArgs(Path folder, boolean dryRun) {
        this.folder = folder;
        this.dryRun = dryRun;
    }

    public static CliArgs parse(String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("Debes indicar la ruta de la carpeta a organizar.");
        }

        Path folder = null;
        boolean dryRun = false;

        for (String arg : args) {
            if (arg.equals("--dry-run")) {
                dryRun = true;
            } else if (folder == null) {
                // Paths.get("") resuelve silenciosamente al directorio de trabajo; lo rechazamos explicitamente.
                if (arg.isBlank()) {
                    throw new IllegalArgumentException("La ruta no puede estar vacia.");
                }
                folder = Paths.get(arg);
            } else {
                throw new IllegalArgumentException("Argumento no reconocido: \"" + arg + "\"");
            }
        }

        if (folder == null) {
            throw new IllegalArgumentException("Debes indicar la ruta de la carpeta a organizar.");
        }
        validate(folder);
        return new CliArgs(folder, dryRun);
    }

    private static void validate(Path folder) {
        if (!Files.exists(folder)) {
            throw new IllegalArgumentException("La carpeta no existe: " + folder);
        }
        if (!Files.isDirectory(folder)) {
            throw new IllegalArgumentException("La ruta indicada no es una carpeta: " + folder);
        }
        if (!Files.isReadable(folder) || !Files.isWritable(folder)) {
            throw new IllegalArgumentException("Sin permisos suficientes sobre la carpeta: " + folder);
        }
    }

    public Path getFolder() { return folder; }
    public boolean isDryRun() { return dryRun; }
}
