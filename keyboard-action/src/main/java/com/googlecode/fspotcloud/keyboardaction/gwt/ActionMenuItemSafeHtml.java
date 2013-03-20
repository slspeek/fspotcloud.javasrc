package com.googlecode.fspotcloud.keyboardaction.gwt;

import com.google.common.base.Joiner;
import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeUri;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.keyboardaction.ActionMenuResources;
import com.googlecode.fspotcloud.keyboardaction.ActionUIDef;
import com.googlecode.fspotcloud.keyboardaction.KeyStroke;
import com.googlecode.fspotcloud.keyboardaction.KeyboardPreferences;

import java.util.List;

public class ActionMenuItemSafeHtml {
    private static final MyTemplates TEMPLATES = GWT.create(MyTemplates.class);
    private final KeyboardPreferences keyboardPreferences;

    @Inject
    private ActionMenuItemSafeHtml(KeyboardPreferences keyboardPreferences) {
        super();
        this.keyboardPreferences = keyboardPreferences;
    }

    public interface MyTemplates extends SafeHtmlTemplates {


        @Template("<span class=\"{2}\"><img src=\"{1}\" class=\"{4}\"></img><span class=\"{3}\">{0}</span><span class=\"{5}\">{6}</span></span>")
        SafeHtml menuItemIcon(String message, SafeUri uri, String outerStyle, String captionStyle, String iconStyle, String shortcutStyle, String shortcut);

        @Template("<span class=\"{1}\"><img  class=\"{3}\"></img><span class=\"{2}\">{0}</span><span class=\"{4}\">{5}</span></span>")
        SafeHtml menuItem(String message, String outerStyle, String captionStyle, String iconStyle, String shortcutStyle, String shortcut);
    }

    SafeHtml get(ActionUIDef actionUIDef, ActionMenuResources resources) {
        ActionMenuResources.Style style = resources.style();
        SafeHtmlBuilder builder = new SafeHtmlBuilder();
        List<KeyStroke> keysForAction = keyboardPreferences.getDefaultKeysForAction(actionUIDef.getId());
        Joiner joiner = Joiner.on(" or ");
        String keyboardShortcuts = "(" + joiner.join(keysForAction) + ")";

        final String description = actionUIDef.getName();
        if (actionUIDef.getIcon() != null) {

            return TEMPLATES.menuItemIcon(description,
                    actionUIDef.getIcon().getSafeUri(),
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
