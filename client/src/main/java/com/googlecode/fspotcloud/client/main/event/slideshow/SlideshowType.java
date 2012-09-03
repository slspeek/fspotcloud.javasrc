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

package com.googlecode.fspotcloud.client.main.event.slideshow;

import com.google.gwt.event.dom.client.KeyCodes;
import com.googlecode.fspotcloud.client.view.action.KeyStroke;
import com.googlecode.fspotcloud.client.view.action.api.ActionDef;

public enum SlideshowType implements ActionDef {
    SLIDESHOW_START("play", "Play", "Start slideshow", new KeyStroke('S'),
            new KeyStroke('G')),
    SLIDESHOW__END("stop", "Stop", "Stop slideshow", new KeyStroke('Q'),
            new KeyStroke(KeyCodes.KEY_ESCAPE)),
    SLIDESHOW_PAUSE("pause", "Pause", "Pause slideshow", new KeyStroke(32),
            new KeyStroke(19)),
    SLIDESHOW_SLOWER("slower", "Slower", "Makes the slideshow go slower",
            new KeyStroke('U'), null),
    SLIDESHOW_FASTER("faster", "Faster", "Makes the slideshow go faster",
            new KeyStroke('I'), null);
    private final KeyStroke key;
    private final KeyStroke alternateKey;
    private final String caption;
    private final String id;
    private final String description;

    private SlideshowType(String id, String caption, String description,
                          KeyStroke key, KeyStroke alternateKey) {
        this.key = key;
        this.alternateKey = alternateKey;
        this.caption = caption;
        this.id = id;
        this.description = description;
    }

    @Override
    public KeyStroke getAlternateKey() {
        return alternateKey;
    }

    @Override
    public KeyStroke getKey() {
        return key;
    }

    @Override
    public String getCaption() {
        return caption;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getId() {
        return id;
    }
}
