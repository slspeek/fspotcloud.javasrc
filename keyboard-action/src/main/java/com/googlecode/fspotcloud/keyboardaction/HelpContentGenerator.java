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
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeUri;
import com.google.inject.Inject;

import java.util.Iterator;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;


public class HelpContentGenerator {
    private final KeyboardActionResources.Style style;
    private static final MyTemplates TEMPLATES = GWT.create(MyTemplates.class);
    private final KeyboardPreferences keyboardPreferences;

    @Inject
    public HelpContentGenerator(KeyboardActionResources res, KeyboardPreferences keyboardPreferences) {
        super();
        this.keyboardPreferences = keyboardPreferences;
        this.style = res.style();
    }

    public interface MyTemplates extends SafeHtmlTemplates {

        @Template("<td class=\"{1}\">{0}</td>")
        SafeHtml td(String message, String style);

        @Template("<td class=\"{1}\">{0}</td>")
        SafeHtml td(SafeHtml content, String style);

        @Template("<span class=\"{1}\">{0}</span>")
        SafeHtml span(String message, String style);

        @Template("<tr class=\"{1}\">{0}</tr>")
        SafeHtml tr(SafeHtml message, String style);

        @Template("<td class=\"{1}\">{0}</td>")
        SafeHtml divText(String name, String s);

        @Template("<td><img src=\"{0}\" class=\"{1}\"></img></td>")
        SafeHtml img(SafeUri url, String style);

        @Template("<table ><tbody>{0}</tbody></table>")
        SafeHtml table(SafeHtml rows);

        @Template("<td/><td/><td><td class=\"{1}\" >{0}</td>")
        SafeHtml trspan5(String name, String s);
    }


    private SafeHtml getKey(String keyDescription) {
        return TEMPLATES.span(keyDescription, style.helpKey());
    }



    private SafeHtml getDescription(String description) {
        return TEMPLATES.td(description, style.helpDescription());
    }

    private SafeHtml getName(String description) {
        return TEMPLATES.td(description, style.helpName());
    }

    private SafeHtml getIcon(SafeUri url) {
        return TEMPLATES.img(url, style.helpActionIcon());
    }
    private SafeHtml getHelpRow(String[] keys, ActionDef actionDef) {
        SafeHtmlBuilder safeHtmlBuilder = new SafeHtmlBuilder();
        ImageResource icon = actionDef.getIcon();
        if (icon != null) {
               safeHtmlBuilder.append(getIcon(actionDef.getIcon().getSafeUri()));
        }  else {
              safeHtmlBuilder.append(getName(actionDef.getName()));
        }


        Iterator<String> it = newArrayList(keys).iterator();
        SafeHtmlBuilder keysBuilder = new SafeHtmlBuilder();
        while (it.hasNext()) {
            String aKey = it.next();
            keysBuilder.append(getKey(aKey));
            if (it.hasNext()) {
                keysBuilder.appendEscaped(" or ");
            }
        }
        safeHtmlBuilder.append(TEMPLATES.td(keysBuilder.toSafeHtml(), style.helpKeys()));
        safeHtmlBuilder.append(TEMPLATES.td(" : ", style.helpSeparator()));
        safeHtmlBuilder.append(getDescription(actionDef.getDescription()));

        SafeHtml row = TEMPLATES.tr(safeHtmlBuilder.toSafeHtml(), style.helpRow());

        return row;
    }

    public SafeHtml getHelp(ActionCategory category) {
        SafeHtmlBuilder safeHtmlBuilder = new SafeHtmlBuilder();
        safeHtmlBuilder.append(TEMPLATES.trspan5(category.getName(), style.helpCategoryTitle()));

        for (ActionDef actionDef : category.getActions()) {
            KeyStroke[] keysForAction = keyboardPreferences.getDefaultKeysForAction(actionDef.getId());
            safeHtmlBuilder.append(getHelpText(actionDef, keysForAction));

        }
        return safeHtmlBuilder.toSafeHtml();
    }


    public SafeHtml getHelpText(ActionDef shortcut, KeyStroke[] keys) {

        List<String> list = newArrayList();
        for (KeyStroke k : keys) {
            list.add(k.toString());
        }
        String[] keyStrings = new String[list.size()];
        keyStrings =  list.toArray(keyStrings);
        return getHelpRow(keyStrings, shortcut);
    }

    SafeHtml getHelpText(List<ActionCategory> categoryList) {
        List<ActionCategory> mCategoryList = newArrayList(categoryList);
        ActionCategory first = mCategoryList.get(0);
        final int lastIndex = mCategoryList.size() - 1;
        ActionCategory last  = mCategoryList.get(lastIndex);
        mCategoryList.set(0, last);
        mCategoryList.set(lastIndex, first);
        SafeHtmlBuilder safeHtmlBuilder = new SafeHtmlBuilder();
        for (ActionCategory actionCategory : mCategoryList) {
            safeHtmlBuilder.append(getHelp(actionCategory));
        }
        return TEMPLATES.table(safeHtmlBuilder.toSafeHtml());
    }
}

