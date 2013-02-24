package com.googlecode.fspotcloud.keyboardaction;

import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.Window;
import com.google.inject.Inject;

import java.util.Set;

public class HelpActionsFactory {

    public static final String SHOW_HELP_ACTION = "help";
    public static final String HIDE_HELP_ACTION = "hide-help";

    private final String[] allModes;
    private final ConfigBuilder configBuilder;
    private final TwoColumnHelpPopup twoColumnHelpPopup;
    private final SingleColumnHelpPopup singleColumnHelpPopup;
    private final ShortcutsPopup shortcutsPopup;

    private final HelpContentGeneratorFactory helpContentGeneratorFactory;


    private HelpResources helpResources;
    private final IModeController modeController;
    private final KeyboardPreferences keyboardPreferences;
    private HelpContentGenerator helpContentGenerator;

    @Inject
    private HelpActionsFactory(ModesProvider allModes,
                               ConfigBuilder configBuilder,
                               HelpContentGeneratorFactory helpContentGeneratorFactory,
                               TwoColumnHelpPopup twoColumnHelpPopup,
                               SingleColumnHelpPopup singleColumnHelpPopup,
                               ShortcutsPopup shortcutsPopup,
                               HelpResources helpResources,
                               IModeController modeController,
                               KeyboardPreferences keyboardPreferences) {
        this.helpContentGeneratorFactory = helpContentGeneratorFactory;
        setHelpResources(helpResources);
        this.singleColumnHelpPopup = singleColumnHelpPopup;
        this.shortcutsPopup = shortcutsPopup;
        this.modeController = modeController;
        this.keyboardPreferences = keyboardPreferences;
        this.allModes = allModes.getModes();
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
                String mode = modeController.getMode();
                Set<String> actions = keyboardPreferences.allRelevantActions(mode);

                SafeHtml content = helpContentGenerator.getShortcuts(actions, mode);
                shortcutsPopup.setSafeHtml(content);
                if (shortcutsPopup.isShowing()) {
                    shortcutsPopup.hide();
                }   else {
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
