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

package com.googlecode.fspotcloud.keyboardaction;

import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.inject.Inject;

import java.util.Iterator;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;


public class HelpContentGenerator {
    private final Resources.Style style;
    private static final MyTemplates TEMPLATES = GWT.create(MyTemplates.class);
    private final KeyboardPreferences keyboardPreferences;

    @Inject
    public HelpContentGenerator(Resources res, KeyboardPreferences keyboardPreferences) {
        super();
        this.keyboardPreferences = keyboardPreferences;
        this.style = res.style();
    }

    public interface MyTemplates extends SafeHtmlTemplates {
        @Template("<span class=\"{1}\">{0}</span>")
        SafeHtml span(String message, String style);

        @Template("<div class=\"{1}\">{0}</div>")
        SafeHtml div(SafeHtml message, String style);

        @Template("<div class=\"{1}\">{0}</div>")
        SafeHtml divText(String name, String s);
    }


    private SafeHtml getKey(String keyDescription) {
        return TEMPLATES.span(keyDescription, style.helpKey());

    }

    private SafeHtml getDescription(String description) {
        return TEMPLATES.span(description, style.helpDescription());

    }

    private SafeHtml getHelpRow(String[] keys, String description) {
        SafeHtmlBuilder safeHtmlBuilder = new SafeHtmlBuilder();
        Iterator<String> it = newArrayList(keys).iterator();
        while (it.hasNext()) {
            String aKey = it.next();
            safeHtmlBuilder.append(getKey(aKey));
            if (it.hasNext()) {
                safeHtmlBuilder.appendEscaped(" or ");
            }
        }
        safeHtmlBuilder.append(TEMPLATES.span(" : ", style.helpSeparator()));
        safeHtmlBuilder.append(getDescription(description));

        SafeHtml row = TEMPLATES.div(safeHtmlBuilder.toSafeHtml(), style.helpRow());

        return row;
    }

    public SafeHtml getHelp(ActionCategory category) {
        SafeHtmlBuilder safeHtmlBuilder = new SafeHtmlBuilder();
        safeHtmlBuilder.append(TEMPLATES.divText(category.getName(), style.helpCategoryTitle()));

        for (ActionDef actionDef : category.getActions()) {
            KeyStroke[] keysForAction = keyboardPreferences.getDefaultKeysForAction(actionDef.getId());
            safeHtmlBuilder.append(getHelpText(actionDef, keysForAction));

        }
        return TEMPLATES.div(safeHtmlBuilder.toSafeHtml(), style.helpCategory());
    }


    public SafeHtml getHelpText(ActionDef shortcut, KeyStroke[] keys) {

        List<String> list = newArrayList();
        for (KeyStroke k : keys) {
            list.add(k.toString());
        }
        String[] keyStrings = new String[list.size()];
        keyStrings =  list.toArray(keyStrings);
        return getHelpRow(keyStrings, shortcut.getDescription());
    }
}

