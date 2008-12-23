package org.jruby.plugins;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class WarFile {
    private File warFile;

    public WarFile(String warFileLocation) {
        warFile = new File(warFileLocation);
    }

    public boolean exists() {
        return warFile.exists();
    }

    public boolean contains(String entryName) throws IOException {
        ZipInputStream zip = new ZipInputStream(new FileInputStream(warFile));
        ZipEntry entry;
        while ((entry = zip.getNextEntry()) != null) {
            if (entry.getName().equals(entryName)) {
                return true;
            }
        }
        return false;
    }
}
