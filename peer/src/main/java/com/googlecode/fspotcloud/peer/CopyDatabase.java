/*
 * Copyright 2010-2012 Steven L. Speek.
 * This program is free software; you can redistribute it
                and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free
                Software Foundation; either version 2
 * of the License, or any later version.
 *
 * This program is
                distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied
                warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public
                License for more details.
 * You should have received a copy of the GNU General Public License
 * along
                with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330,
                Boston, MA 02111-1307, USA.
 *
 */

package com.googlecode.fspotcloud.peer;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import java.io.*;
import java.util.logging.Logger;


public class CopyDatabase implements Provider<String> {
    @Inject
    Logger log;
    @Inject
    @Named("DatabasePath")
    private String srcPath;
    @Inject
    @Named("WorkDir")
    private String pwd;

    public String copyDatabase() throws IOException {
        log.info("src: " + srcPath);

        File srcFile = new File(srcPath);
        File targetDir = new File(pwd + "/runtime");

        if (!targetDir.exists()) {
            targetDir.mkdir();
        }

        File targetFile = new File(targetDir.getAbsolutePath() + "/copy.db");
        InputStream in = new FileInputStream(srcFile);
        OutputStream out = new FileOutputStream(targetFile);
        byte[] buf = new byte[1024];
        int len;

        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }

        in.close();
        out.close();

        String url = "jdbc:sqlite:" + targetFile.getAbsolutePath();
        log.info("url: " + url);

        return url;
    }

    @Override
    public String get() {
        String destPath = null;

        try {
            destPath = copyDatabase();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return destPath;
    }
}
