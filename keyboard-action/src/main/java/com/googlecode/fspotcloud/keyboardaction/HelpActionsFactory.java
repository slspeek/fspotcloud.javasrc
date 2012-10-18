package com.googlecode.fspotcloud.keyboardaction;

import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.inject.Inject;

public class HelpActionsFactory {

    public static final String SHOW_HELP_ACTION = "help";
    public static final String HIDE_HELP_ACTION = "hide-help";
    public static final String HELP_CATEGORY = "Help";

    private ActionDef showHelpDef, hideHelpDef;
    private KeyboardBinding showHelpBinding;
    private KeyboardBinding hideHelpBinding;
    public final ActionCategory helpActionCategory;

    private final String[] allModes;
    private final ConfigBuilder configBuilder;
    private final HelpPopup helpPopup;
    private final HelpContentGenerator helpContentGenerator;
    private final KeyboardActionResources keyboardActionResources;

    @Inject
    private HelpActionsFactory(ModesProvider allModes,
                               ConfigBuilder configBuilder,
                               HelpContentGenerator helpContentGenerator,
                               HelpPopup helpPopup, KeyboardActionResources keyboardActionResources) {
        this.keyboardActionResources = keyboardActionResources;
        this.allModes = allModes.getModes();
        this.configBuilder = configBuilder;
        this.helpContentGenerator = helpContentGenerator;
        helpActionCategory = configBuilder.createActionCategory(HELP_CATEGORY);
        this.helpPopup = helpPopup;
        //initHelpActions();
    }

    public void  initHelpActions() {
        showHelpBinding = KeyboardBinding.bind(new KeyStroke(Modifiers.SHIFT, 191), new KeyStroke(Modifiers.NONE, 'H')).withDefaultModes(allModes);
        hideHelpBinding = KeyboardBinding.bind(new KeyStroke(Modifiers.NONE, KeyCodes.KEY_ESCAPE)).withDefaultModes(allModes);
        showHelpDef = new ActionDef(SHOW_HELP_ACTION, "Help", "Show a help popup.", keyboardActionResources.helpIcon());
        hideHelpDef = new ActionDef(HIDE_HELP_ACTION, "Hide help", "Hide the help popup.");
        configBuilder.addBinding(helpActionCategory, showHelpDef, new IActionHandler() {
            @Override
            public void performAction(String actionId) {
                helpPopup.setTitle("Keyboard help");
                helpPopup.setLeft(helpContentGenerator.getHelpText(configBuilder.getActionCategoryList()));
                helpPopup.setGlassEnabled(true);
                helpPopup.center();
                helpPopup.show();
                helpPopup.focus();
            }
        }, showHelpBinding);
        configBuilder.addBinding(helpActionCategory, hideHelpDef, new IActionHandler() {
            @Override
            public void performAction(String actionId) {
                helpPopup.hide();
            }
        }, hideHelpBinding);

    }

    public IActionHandler getHelpAction(final HelpConfig helpConfig) {


        IActionHandler result = new IActionHandler() {
            @Override
            public void performAction(String actionId) {
                final SafeHtml firstColumn, secondColumn;
                firstColumn = helpContentGenerator.getHelpText(helpConfig.getFirstColumn());
                secondColumn = helpContentGenerator.getHelpText(helpConfig.getSecondColumn());
                helpPopup.setTitle(helpConfig.getTitle());
                helpPopup.setLeft(firstColumn);
                helpPopup.setRight(secondColumn);
                helpPopup.setGlassEnabled(true);
                helpPopup.center();
                helpPopup.show();
                helpPopup.focus();

            }
        };
        return result;
    }

    public IActionHandler getCloseHelp() {
        return new IActionHandler() {
            @Override
            public void performAction(String actionId) {
                helpPopup.hide();
            }
        };
    }
}
