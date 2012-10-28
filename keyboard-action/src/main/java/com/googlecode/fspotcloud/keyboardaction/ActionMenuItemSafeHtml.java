package com.googlecode.fspotcloud.keyboardaction;

import com.google.common.base.Joiner;
import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeUri;
import com.google.inject.Inject;

public class ActionMenuItemSafeHtml {
    private final KeyboardActionResources.Style style;
    private static final MyTemplates TEMPLATES = GWT.create(MyTemplates.class);
    private final KeyboardPreferences keyboardPreferences;

    @Inject
    private ActionMenuItemSafeHtml(KeyboardActionResources res, KeyboardPreferences keyboardPreferences) {
        super();
        this.keyboardPreferences = keyboardPreferences;
        this.style = res.style();
    }

    public interface MyTemplates extends SafeHtmlTemplates {


        @Template("<span class=\"{2}\"><img src=\"{1}\" class=\"{4}\"></img><span class=\"{3}\">{0}</span><span class=\"{5}\">{6}</span></span>")
        SafeHtml menuItemIcon(String message, SafeUri uri, String outerStyle, String captionStyle, String iconStyle, String shortcutStyle, String shortcut);

        @Template("<span class=\"{1}\"><img  class=\"{3}\"></img><span class=\"{2}\">{0}</span><span class=\"{4}\">{5}</span></span>")
        SafeHtml menuItem(String message, String outerStyle, String captionStyle, String iconStyle, String shortcutStyle, String shortcut);
    }

    SafeHtml get(ActionDef actionDef) {
        SafeHtmlBuilder builder = new SafeHtmlBuilder();
        KeyStroke[] keysForAction = keyboardPreferences.getDefaultKeysForAction(actionDef.getId());
        Joiner joiner = Joiner.on(" or ");
        String keyboardShortcuts = "(" + joiner.join(keysForAction) + ")";

        final String description = actionDef.getName();
        if (actionDef.getIcon() != null) {

            return TEMPLATES.menuItemIcon(description,
                    actionDef.getIcon().getSafeUri(),
                    style.menuItem(),
                    style.menuItemText(),
                    style.helpActionIcon(),
                    style.menuItemShortcut(),
                    keyboardShortcuts);
        } else {
            return TEMPLATES.menuItem(description,
                    style.menuItem(),
                    style.menuItemText(),
                    style.helpActionIcon(),
                    style.menuItemShortcut(),
                    keyboardShortcuts);
        }
    }


}
