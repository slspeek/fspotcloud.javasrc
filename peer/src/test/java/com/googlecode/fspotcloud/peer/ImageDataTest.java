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

import junit.framework.TestCase;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.logging.Logger;


public class ImageDataTest extends TestCase {
    @SuppressWarnings("unused")
    private final Logger log = Logger.getLogger(ImageDataTest.class.getName());
    private ImageData target;
    String cwd;

    protected void setUp() throws Exception {
        super.setUp();
        target = new ImageData();
        cwd = System.getProperty("user.dir");
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public final void testGetScaledImageData() throws Exception {
        String imgPath = "/src/test/resources/images/img_0659 (Gewijzigd).jpg";
        String path = cwd + imgPath;
        System.out.println(path);

        Dimension size = new Dimension(200, 100);
        byte[] data = target.getScaledImageData(path, size);
        InputStream dataStream = new ByteArrayInputStream(data);
        BufferedImage img = ImageIO.read(dataStream);
        int w = img.getWidth();
        int h = img.getHeight();
        assertEquals(133, w);
        assertEquals(100, h);
    }

    public final void testGetScaledImageDataPortrait()
            throws Exception {
        String urlString = cwd +
                "/src/test/resources/Photos/2010/06/04/Mac-classic.jpg";
        Dimension size = new Dimension(200, 100);
        byte[] data = target.getScaledImageData(urlString, size);
        InputStream dataStream = new ByteArrayInputStream(data);
        BufferedImage img = ImageIO.read(dataStream);
        int w = img.getWidth();
        int h = img.getHeight();
        assertEquals(75, w);
        assertEquals(100, h);
    }
}
