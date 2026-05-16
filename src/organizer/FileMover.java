package organizer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Mueve un archivo a su carpeta destino, resolviendo conflictos de nombre.
 * En modo dry-run imprime la operación sin ejecutarla.
 */
public class FileMover {

    private final boolean dryRun;

    public FileMover(boolean dryRun) {
        this.dryRun = dryRun;
    }

    public void move(Path source, Path targetDir) throws IOException {
        Path destination = resolveDestination(source.getFileName().toString(), targetDir);

        if (dryRun) {
            System.out.printf("  [dry-run]  %-40s  →  %s%n",
                    source.getFileName(), destination);
            return;
        }

        Files.createDirectories(targetDir);
        Files.move(source, destination);
        System.out.printf("  Movido:    %-40s  →  %s%n",
                source.getFileName(), destination);
    }

    /**
     * Si ya existe un archivo con ese nombre en el destino, añade un sufijo
     * numérico incremental: foto.jpg → foto_1.jpg → foto_2.jpg …
     */
    private Path resolveDestination(String filename, Path targetDir) {
        Path candidate = targetDir.resolve(filename);
        if (!Files.exists(candidate)) {
            return candidate;
        }

        String base = baseName(filename);
        String ext  = FileTypeRegistry.extractExtension(filename);
        int counter = 1;

        do {
            String newName = ext.isEmpty()
                    ? base + "_" + counter
                    : base + "_" + counter + "." + ext;
            candidate = targetDir.resolve(newName);
            counter++;
        } while (Files.exists(candidate));

        return candidate;
    }

    private String baseName(String filename) {
        int dot = filename.lastIndexOf('.');
        return dot > 0 ? filename.substring(0, dot) : filename;
    }
}
