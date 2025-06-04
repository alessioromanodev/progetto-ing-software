package zip.utils.lib;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Utils {
    public static void deleteIfExists(String filename) {
        Path path = Paths.get("./" + filename);
        try {
            if (Files.exists(path)) {
                Files.delete(path);
                System.out.println("Eliminato il file" + path);
            }
        } catch (IOException e) {
            System.err.println("Impossibile eliminare il file " + filename + ": " + e.getMessage());
        }
    }
}
