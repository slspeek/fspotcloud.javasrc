package com.googlecode.fspotcloud.keyboardaction;

import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.inject.Inject;

public class HelpActionsFactory {

    public static final String SHOW_HELP_ACTION = "help";
    public static final String HIDE_HELP_ACTION = "hide-help";

    private final String[] allModes;
    private final ConfigBuilder configBuilder;
    private final TwoColumnHelpPopup twoColumnHelpPopup;
    private final SingleColumnHelpPopup singleColumnHelpPopup;

    private final HelpContentGenerator helpContentGenerator;
    private final KeyboardActionResources keyboardActionResources;

    @Inject
    private HelpActionsFactory(ModesProvider allModes,
                               ConfigBuilder configBuilder,
                               HelpContentGenerator helpContentGenerator,
                               TwoColumnHelpPopup twoColumnHelpPopup,
                               SingleColumnHelpPopup singleColumnHelpPopup,
                               KeyboardActionResources keyboardActionResources) {
        this.singleColumnHelpPopup = singleColumnHelpPopup;
        this.keyboardActionResources = keyboardActionResources;
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
                    singleColumnHelpPopup.focus();
                } else {
                    secondColumn = helpContentGenerator.getHelpText(helpConfig.getSecondColumn());
                    twoColumnHelpPopup.setHelpConfig(helpConfig);
                    twoColumnHelpPopup.setLeft(firstColumn);
                    twoColumnHelpPopup.setRight(secondColumn);
                    twoColumnHelpPopup.setGlassEnabled(true);
                    twoColumnHelpPopup.center();
                    twoColumnHelpPopup.show();
                    twoColumnHelpPopup.focus();
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
            }
        };
    }
}
