package com.googlecode.fspotcloud.keyboardaction;

import com.google.gwt.safehtml.shared.SafeHtml;

import java.util.List;
import java.util.Set;

public interface IHelpContentGenerator {
    void setStyle(HelpResources.Style style);

    SafeHtml getHelpRow(List<String> keys, ActionUIDef actionUIDef, String description);

    SafeHtml getHelp(ActionCategory category);

    SafeHtml getHelpText(ActionUIDef shortcut, List<KeyStroke> keys, String description);

    SafeHtml getHelpText(ActionUIDef shortcut, List<KeyStroke> keys);

    SafeHtml getShortcuts(Set<String> actions, PlaceContext placeContext);

    SafeHtml getHelpText(List<ActionCategory> column);
}
