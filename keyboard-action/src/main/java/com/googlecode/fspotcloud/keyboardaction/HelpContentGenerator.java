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

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.google.common.collect.Lists.newArrayList;


public class HelpContentGenerator {
    private final Logger log = Logger.getLogger(HelpContentGenerator.class.getName());
    private final KeyboardActionResources.Style style;
    private static final MyTemplates TEMPLATES = GWT.create(MyTemplates.class);
    private final KeyboardPreferences keyboardPreferences;
    private final ActionUIRegistry actionUIRegistry;

    @Inject
    private HelpContentGenerator(KeyboardActionResources res,
                                 KeyboardPreferences keyboardPreferences,
                                 ActionUIRegistry actionUIRegistry) {
        super();
        this.keyboardPreferences = keyboardPreferences;
        this.actionUIRegistry = actionUIRegistry;
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

    public SafeHtml getDemoContent(ActionUIDef actionUIDef, KeyStroke[] keys) {
        SafeHtml helpRow = getHelpText(actionUIDef, keys);
        return TEMPLATES.table(helpRow);
    }

    public SafeHtml getDemoContent(ActionUIDef actionUIDef, KeyStroke[] keys, String description) {
        SafeHtml helpRow = getHelpText(actionUIDef, keys, description);
        return TEMPLATES.table(helpRow);
    }

    public SafeHtml getHelpRow(String[] keys, ActionUIDef actionUIDef, String description) {
        SafeHtmlBuilder safeHtmlBuilder = new SafeHtmlBuilder();
        ImageResource icon = actionUIDef.getIcon();
        if (icon != null) {
            safeHtmlBuilder.append(getIcon(actionUIDef.getIcon().getSafeUri()));
        } else {
            safeHtmlBuilder.append(getName(actionUIDef.getName()));
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
        safeHtmlBuilder.append(getDescription(description));

        SafeHtml row = TEMPLATES.tr(safeHtmlBuilder.toSafeHtml(), style.helpRow());

        return row;
    }

    public SafeHtml getHelp(ActionCategory category) {
        SafeHtmlBuilder safeHtmlBuilder = new SafeHtmlBuilder();
        safeHtmlBuilder.append(TEMPLATES.trspan5(category.getName(), style.helpCategoryTitle()));
        for (ActionUIDef actionUIDef : category.getActions()) {
            KeyStroke[] keysForAction = keyboardPreferences.getDefaultKeysForAction(actionUIDef.getId());
            safeHtmlBuilder.append(getHelpText(actionUIDef, keysForAction));

        }
        return safeHtmlBuilder.toSafeHtml();
    }


    public SafeHtml getHelpText(ActionUIDef shortcut, KeyStroke[] keys, String description) {

        List<String> list = newArrayList();
        for (KeyStroke k : keys) {
            list.add(k.toString());
        }
        String[] keyStrings = new String[list.size()];
        keyStrings = list.toArray(keyStrings);
        return getHelpRow(keyStrings, shortcut, description);
    }

    public SafeHtml getHelpText(ActionUIDef shortcut, KeyStroke[] keys) {
        return getHelpText(shortcut, keys, shortcut.getDescription());
    }

    SafeHtml getHelpText(List<ActionCategory> categoryList) {
        List<ActionCategory> mCategoryList = newArrayList(categoryList);
        ActionCategory first = mCategoryList.get(0);
        final int lastIndex = mCategoryList.size() - 1;
        ActionCategory last = mCategoryList.get(lastIndex);
        mCategoryList.set(0, last);
        mCategoryList.set(lastIndex, first);
        SafeHtmlBuilder safeHtmlBuilder = new SafeHtmlBuilder();
        for (ActionCategory actionCategory : mCategoryList) {
            safeHtmlBuilder.append(getHelp(actionCategory));
        }
        return TEMPLATES.table(safeHtmlBuilder.toSafeHtml());
    }

    public SafeHtml getShortcuts(Set<String> actions, String mode) {
        log.log(Level.FINEST, "mode: " + mode + " actionIds: " + actions);
        SafeHtmlBuilder builder = new SafeHtmlBuilder();
        List<ActionUIDef> actionUIDefs = newArrayList();


        for (String actionId: actions)  {
            actionUIDefs.add(actionUIRegistry.getAction(actionId));
        }
        Collections.sort(actionUIDefs, new Comparator<ActionUIDef>() {
            @Override
            public int compare(ActionUIDef o1, ActionUIDef o2) {
                return o1.getName().compareToIgnoreCase(o2.getName());
            }
        });

        for (ActionUIDef actionUIDef: actionUIDefs) {
            KeyStroke[] keys = keyboardPreferences.getKeysForAction(mode, actionUIDef.getId());
            builder.append(getShortcut(keys, actionUIDef));
        }
        return TEMPLATES.table(builder.toSafeHtml());
    }

    private SafeHtml getShortcut(KeyStroke[] keys, ActionUIDef actionUIDef) {
        SafeHtmlBuilder safeHtmlBuilder = new SafeHtmlBuilder();
        List<String> list = newArrayList();
        for (KeyStroke k : keys) {
            list.add(k.toString());
        }
        Iterator<String> it = list.iterator();
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
        safeHtmlBuilder.append(getDescription(actionUIDef.getName()));
        return TEMPLATES.tr(safeHtmlBuilder.toSafeHtml(), style.helpRow());
    }

}

