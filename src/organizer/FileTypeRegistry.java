package organizer;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Clasifica archivos por extensión y expone las categorías que gestiona
 * la herramienta, para que el organizador pueda ignorarlas al iterar.
 */
public class FileTypeRegistry {

    public static final Set<String> MANAGED_CATEGORIES = Set.of(
            "Imagenes", "Documentos", "Video", "Audio", "Comprimidos", "Otros"
    );

    private static final Map<String, String> EXTENSION_TO_CATEGORY = new HashMap<>();

    static {
        for (String ext : new String[]{"jpg", "jpeg", "png", "gif", "webp", "bmp", "svg"})
            EXTENSION_TO_CATEGORY.put(ext, "Imagenes");

        for (String ext : new String[]{"pdf", "doc", "docx", "txt", "odt", "xls", "xlsx", "ppt", "pptx"})
            EXTENSION_TO_CATEGORY.put(ext, "Documentos");

        for (String ext : new String[]{"mp4", "mkv", "avi", "mov", "webm"})
            EXTENSION_TO_CATEGORY.put(ext, "Video");

        for (String ext : new String[]{"mp3", "wav", "flac", "ogg", "m4a"})
            EXTENSION_TO_CATEGORY.put(ext, "Audio");

        for (String ext : new String[]{"zip", "rar", "7z", "tar", "gz"})
            EXTENSION_TO_CATEGORY.put(ext, "Comprimidos");
    }

    public String categoryFor(String extension) {
        return EXTENSION_TO_CATEGORY.getOrDefault(extension.toLowerCase(), "Otros");
    }

    /** Extrae la extensión de un nombre de archivo, sin el punto. */
    public static String extractExtension(String filename) {
        int dot = filename.lastIndexOf('.');
        return dot > 0 ? filename.substring(dot + 1) : "";
    }
}
