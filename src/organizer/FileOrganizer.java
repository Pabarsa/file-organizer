package organizer;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Orquesta el proceso completo: recoge los archivos de la carpeta,
 * determina su categoría y delega el movimiento en FileMover.
 */
public class FileOrganizer {

    private final CliArgs args;
    private final FileTypeRegistry registry;
    private final FileMover mover;

    public FileOrganizer(CliArgs args) {
        this.args = args;
        this.registry = new FileTypeRegistry();
        this.mover = new FileMover(args.isDryRun());
    }

    public void run() throws IOException {
        Path folder = args.getFolder();
        List<Path> files = collectFiles(folder);

        if (files.isEmpty()) {
            System.out.println("No hay archivos que organizar en: " + folder);
            return;
        }

        if (args.isDryRun()) {
            System.out.println("Modo simulación — no se moverá ningún archivo.\n");
        }

        List<String> errors = new ArrayList<>();

        for (Path file : files) {
            try {
                String ext      = FileTypeRegistry.extractExtension(file.getFileName().toString());
                String category = registry.categoryFor(ext);
                Path targetDir  = folder.resolve(category);
                mover.move(file, targetDir);
            } catch (IOException e) {
                // Un fallo en un archivo no interrumpe el resto
                errors.add(file.getFileName() + ": " + e.getMessage());
            }
        }

        int ok = files.size() - errors.size();
        System.out.printf("%n%s %d archivo(s).%n",
                args.isDryRun() ? "Se moverian" : "Organizados", ok);

        if (!errors.isEmpty()) {
            System.err.printf("No se pudo mover %d archivo(s):%n", errors.size());
            errors.forEach(msg -> System.err.println("  - " + msg));
        }
    }

    /**
     * Recoge solo los archivos regulares de la raíz, ignorando las
     * subcarpetas que la propia herramienta gestiona.
     */
    private List<Path> collectFiles(Path folder) throws IOException {
        List<Path> files = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(folder)) {
            for (Path entry : stream) {
                if (isManagedSubfolder(entry)) continue;
                if (Files.isRegularFile(entry))  files.add(entry);
            }
        }
        return files;
    }

    private boolean isManagedSubfolder(Path entry) {
        return Files.isDirectory(entry)
                && FileTypeRegistry.MANAGED_CATEGORIES.contains(entry.getFileName().toString());
    }
}
