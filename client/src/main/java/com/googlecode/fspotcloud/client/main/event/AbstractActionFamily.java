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

package com.googlecode.fspotcloud.client.main.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public abstract class AbstractActionFamily implements ActionFamily {
    Map<String, ActionMap> familyMap = new HashMap<String, ActionMap>();
    private final String description;

    public AbstractActionFamily(String description) {
        this.description = description;
    }

    @Override
    public List<ActionMap> allMaps() {
        return new ArrayList<ActionMap>(familyMap.values());
    }

    @Override
    public ActionMap get(String name) {
        return familyMap.get(name);
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void addMap(AbstractActionMap actionMap) {
        actionMap.buildMap();
        familyMap.put(actionMap.getDescription(), actionMap);
    }
}
