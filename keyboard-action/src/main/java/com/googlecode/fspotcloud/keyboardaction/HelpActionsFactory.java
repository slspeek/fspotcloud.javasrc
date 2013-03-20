package com.googlecode.fspotcloud.keyboardaction;

import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.Window;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.googlecode.fspotcloud.keyboardaction.gwt.ShortcutsPopup;
import com.googlecode.fspotcloud.keyboardaction.gwt.SingleColumnHelpPopup;
import com.googlecode.fspotcloud.keyboardaction.gwt.TwoColumnHelpPopup;

import java.util.Set;

public class HelpActionsFactory {

    public static final String SHOW_HELP_ACTION = "help";
    public static final String HIDE_HELP_ACTION = "hide-help";

    private final TwoColumnHelpPopup twoColumnHelpPopup;
    private final SingleColumnHelpPopup singleColumnHelpPopup;
    private final ShortcutsPopup shortcutsPopup;
    private final KeyboardPreferences keyboardPreferences;
    private final IHelpContentGenerator helpContentGenerator;
    private final Provider<PlaceContext> placeContextProvider;

    @Inject
    private HelpActionsFactory(
            TwoColumnHelpPopup twoColumnHelpPopup,
            SingleColumnHelpPopup singleColumnHelpPopup,
            ShortcutsPopup shortcutsPopup,
            HelpResources helpResources,
            KeyboardPreferences keyboardPreferences,
            IHelpContentGenerator helpContentGenerator,
            Provider<PlaceContext> placeContextProvider) {
        this.helpContentGenerator = helpContentGenerator;
        this.placeContextProvider = placeContextProvider;
        this.singleColumnHelpPopup = singleColumnHelpPopup;
        this.shortcutsPopup = shortcutsPopup;
        this.keyboardPreferences = keyboardPreferences;
        this.twoColumnHelpPopup = twoColumnHelpPopup;
        helpResources.style().ensureInjected();
        setHelpResources(helpResources);
    }

    public void setHelpResources(HelpResources helpResources) {
        helpContentGenerator.setStyle(helpResources.style());
    }

    public IActionHandler getHelpAction(final HelpConfig helpConfig) {
        IActionHandler result = new IActionHandler() {
            @Override
            public void performAction(String actionId) {
                final SafeHtml firstColumn, secondColumn;
                firstColumn = helpContentGenerator.getHelpText(helpConfig.getFirstColumn());
                if (helpConfig.getSecondColumn().isEmpty()) {
                    if (singleColumnHelpPopup.isShowing()) {
                        singleColumnHelpPopup.hide();
                    } else {
                        singleColumnHelpPopup.setHelpConfig(helpConfig);
                        singleColumnHelpPopup.setLeft(firstColumn);
                        final SafeHtml optionalContent = helpConfig.getOptionalContent();
                        if (optionalContent != null) {
                            singleColumnHelpPopup.setOptionalContentDiv(optionalContent);
                        }
                        singleColumnHelpPopup.setGlassEnabled(true);
                        singleColumnHelpPopup.center();
                        singleColumnHelpPopup.show();
                    }
                } else {
                    secondColumn = helpContentGenerator.getHelpText(helpConfig.getSecondColumn());
                    if (twoColumnHelpPopup.isShowing()) {
                        twoColumnHelpPopup.hide();
                    } else {
                        twoColumnHelpPopup.setHelpConfig(helpConfig);
                        twoColumnHelpPopup.setLeft(firstColumn);
                        twoColumnHelpPopup.setRight(secondColumn);
                        twoColumnHelpPopup.setGlassEnabled(true);
                        twoColumnHelpPopup.center();
                        twoColumnHelpPopup.show();
                    }
                }
            }
        };
        return result;
    }

    public IActionHandler getShortcutsAction() {
        IActionHandler result = new IActionHandler() {
            @Override
            public void performAction(String actionId) {
                PlaceContext placeContext = placeContextProvider.get();
                Set<String> actions = keyboardPreferences.allRelevantActions(placeContext);
                 SafeHtml content = helpContentGenerator.getShortcuts(actions, placeContext);
                shortcutsPopup.setSafeHtml(content);
                if (shortcutsPopup.isShowing()) {
                    shortcutsPopup.hide();
                } else {
                    shortcutsPopup.show();

                    int width = shortcutsPopup.getOffsetWidth();
                    int height = shortcutsPopup.getOffsetHeight();
                    int wWidth = Window.getClientWidth();
                    int wHeight = Window.getClientHeight();
                    shortcutsPopup.setPopupPosition(wWidth - width, wHeight - height);
                }

            }
        };
        return result;
    }

    public IActionHandler getCloseHelp() {
        return new IActionHandler() {
            @Override
            public void performAction(String actionId) {
                twoColumnHelpPopup.hide();
                singleColumnHelpPopup.hide();
                shortcutsPopup.hide();
            }
        };
    }
}
