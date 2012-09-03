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

import com.google.common.base.Joiner;

import java.awt.*;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ImageData {
    private final Logger log = Logger.getLogger(ImageData.class.getName());

    public byte[] getScaledImageData(String url, Dimension size)
            throws Exception {
        int width = (int) size.width;
        int height = (int) size.height;

        if (url.startsWith("file://")) {
            url = url.substring(6);
        }

        String[] command = getCommand(url, width, height);

        //log.info("About to run: " + Arrays.asList(command));
        Process convert = Runtime.getRuntime().exec(command);
        InputStream in = convert.getInputStream();
        BufferedInputStream bis = new BufferedInputStream(in);
        ByteArrayOutputStream bas = new ByteArrayOutputStream();

        try {
            int next = bis.read();

            while (next > -1) {
                bas.write(next);
                next = bis.read();
            }

            bas.flush();
        } catch (Exception e) {
            log.log(Level.SEVERE, "getScaledImageData threw: ", e);
        } finally {
            in.close();
            bas.close();
        }

        int returnValue = convert.waitFor();

        if (returnValue != 0) {
            String cmdString = Joiner.on(" ").join(Arrays.asList(command));
            throw new RuntimeException("**error code: " +
                    returnValue + " ** on: " + cmdString);
        }

        byte[] data = bas.toByteArray();

        return data;
    }

    private String[] getCommand(String path, int width, int height) {
        String[] result = new String[10];
        result[0] = "/usr/bin/convert";
        result[1] = "-auto-orient";
        result[2] = "-quality";
        result[3] = "50";
        result[4] = "-compress";
        result[5] = "JPEG";
        result[6] = "-geometry";
        result[7] = width + "x" + height;
        result[8] = path;
        result[9] = "-";

        return result;
    }
}
