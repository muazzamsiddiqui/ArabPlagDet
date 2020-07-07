package Common;

import java.io.File;
import java.io.FileFilter;

/*
* TypeFilter.java
* accepts files of a given type
*/
public enum FileTypeFilter implements FileFilter {

    FILE, DIR, ALL;

    @Override
    public boolean accept(final File file) {
        return file != null && (this == ALL || this == FILE && file.isFile() || this == DIR && file.isDirectory());
    }
}
