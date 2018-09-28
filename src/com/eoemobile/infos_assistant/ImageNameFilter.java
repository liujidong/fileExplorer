package com.eoemobile.infos_assistant;

import java.io.File;
import java.io.FilenameFilter;

public class ImageNameFilter implements FilenameFilter {

	@Override
	public boolean accept(File dir, String filename) {
//        if (dir.isDirectory()) {
//            return true;
//        }
        return filename.endsWith("jpg")
        		|| filename.endsWith("gif");
	}

}
