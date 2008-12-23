package org.jruby.plugins;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class WarFile {
    private File warFileLocation;

    public WarFile(File warFileLocation) {
        this.warFileLocation = warFileLocation;
    }

    public boolean exists() {
        return warFileLocation.exists();
    }

    public boolean contains(String entryName) {
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(warFileLocation);
            ZipInputStream zip = new ZipInputStream(inputStream);
            ZipEntry entry;
            while ((entry = zip.getNextEntry()) != null) {
                if (entry.getName().equals(entryName)) {
                    return true;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return false;
    }
}
