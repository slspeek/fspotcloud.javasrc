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

    private final ConfigBuilder configBuilder;
    private final TwoColumnHelpPopup twoColumnHelpPopup;
    private final SingleColumnHelpPopup singleColumnHelpPopup;
    private final ShortcutsPopup shortcutsPopup;

    private final HelpContentGeneratorFactory helpContentGeneratorFactory;


    private HelpResources helpResources;
    private final IModeController modeController;
    private final KeyboardPreferences keyboardPreferences;
    private HelpContentGenerator helpContentGenerator;
    private final Provider<PlaceContext> placeContextProvider;

    @Inject
    private HelpActionsFactory(
            ConfigBuilder configBuilder,
            HelpContentGeneratorFactory helpContentGeneratorFactory,
            TwoColumnHelpPopup twoColumnHelpPopup,
            SingleColumnHelpPopup singleColumnHelpPopup,
            ShortcutsPopup shortcutsPopup,
            HelpResources helpResources,
            IModeController modeController,
            KeyboardPreferences keyboardPreferences,
            Provider<PlaceContext> placeContextProvider) {
        this.helpContentGeneratorFactory = helpContentGeneratorFactory;
        this.placeContextProvider = placeContextProvider;
        setHelpResources(helpResources);
        this.singleColumnHelpPopup = singleColumnHelpPopup;
        this.shortcutsPopup = shortcutsPopup;
        this.modeController = modeController;
        this.keyboardPreferences = keyboardPreferences;
        this.configBuilder = configBuilder;
        this.twoColumnHelpPopup = twoColumnHelpPopup;
    }

    public void setHelpResources(HelpResources helpResources) {
        this.helpResources = helpResources;
        this.helpContentGenerator = helpContentGeneratorFactory.get(helpResources);
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
