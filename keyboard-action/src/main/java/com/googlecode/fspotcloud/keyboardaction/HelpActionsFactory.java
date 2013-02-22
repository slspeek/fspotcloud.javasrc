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
    private final ShortCutsPopup shortCutsPopup;

    private final HelpContentGenerator helpContentGenerator;
    private final KeyboardActionResources keyboardActionResources;
    private final IModeController modeController;
    private final KeyboardPreferences keyboardPreferences;

    @Inject
    private HelpActionsFactory(ModesProvider allModes,
                               ConfigBuilder configBuilder,
                               HelpContentGenerator helpContentGenerator,
                               TwoColumnHelpPopup twoColumnHelpPopup,
                               SingleColumnHelpPopup singleColumnHelpPopup,
                               ShortCutsPopup shortCutsPopup,
                               KeyboardActionResources keyboardActionResources,
                               IModeController modeController,
                               KeyboardPreferences keyboardPreferences) {
        this.singleColumnHelpPopup = singleColumnHelpPopup;
        this.shortCutsPopup = shortCutsPopup;
        this.keyboardActionResources = keyboardActionResources;
        this.modeController = modeController;
        this.keyboardPreferences = keyboardPreferences;
        this.allModes = allModes.getModes();
        this.configBuilder = configBuilder;
        this.helpContentGenerator = helpContentGenerator;
        this.twoColumnHelpPopup = twoColumnHelpPopup;
    }

    public IActionHandler getHelpAction(final HelpConfig helpConfig) {
        IActionHandler result = new IActionHandler() {
            @Override
            public void performAction(String actionId) {
                final SafeHtml firstColumn, secondColumn;
                firstColumn = helpContentGenerator.getHelpText(helpConfig.getFirstColumn());
                if (helpConfig.getSecondColumn().isEmpty()) {
                    singleColumnHelpPopup.setHelpConfig(helpConfig);
                    singleColumnHelpPopup.setGlassEnabled(true);
                    singleColumnHelpPopup.setLeft(firstColumn);
                    singleColumnHelpPopup.center();
                    singleColumnHelpPopup.show();
                    final SafeHtml optionalContent = helpConfig.getOptionalContent();
                    if (optionalContent != null) {
                        singleColumnHelpPopup.setOptionalContentDiv(optionalContent);
                    }
                } else {
                    secondColumn = helpContentGenerator.getHelpText(helpConfig.getSecondColumn());
                    twoColumnHelpPopup.setHelpConfig(helpConfig);
                    twoColumnHelpPopup.setLeft(firstColumn);
                    twoColumnHelpPopup.setRight(secondColumn);
                    twoColumnHelpPopup.setGlassEnabled(true);
                    twoColumnHelpPopup.center();
                    twoColumnHelpPopup.show();
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
                shortCutsPopup.setSafeHtml(content);
                shortCutsPopup.show();
                int width = shortCutsPopup.getOffsetWidth();
                int height = shortCutsPopup.getOffsetHeight();
                int wWidth= Window.getClientWidth();
                int wHeight = Window.getClientHeight();
                shortCutsPopup.setPopupPosition(wWidth-width, wHeight - height);

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
                shortCutsPopup.hide();
            }
        };
    }
}
