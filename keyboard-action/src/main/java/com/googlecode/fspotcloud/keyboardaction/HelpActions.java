package com.googlecode.fspotcloud.keyboardaction;

import com.google.gwt.event.dom.client.KeyCodes;

import static com.google.common.collect.Lists.newArrayList;

public class HelpActions {

    public static final String SHOW_HELP_ACTION = "SHOW_HELP_ACTION";
    public static final String HIDE_HELP_ACTION = "HIDE_HELP_ACTION";
    public static final String HELP_CATEGORY = "Help";

    private ActionDef showHelpDef, hideHelpDef;
    private KeyboardBinding showHelpBinding;
    private KeyboardBinding hideHelpBinding;
    private final ActionCategory helpActionCategory;

    private final String[] allModes;
    private final ConfigBuilder configBuilder;
    private final HelpPopup helpPopup;
    private final KeyboardPreferences keyboardPreferences;
    private final IModeController modeController;
    private final HelpContentGenerator helpContentGenerator;
    private final KeyboardActionResources keyboardActionResources;

    public HelpActions(String[] allModes,
                       ConfigBuilder configBuilder,
                       KeyboardPreferences keyboardPreferences,
                       IModeController modeController,
                       HelpContentGenerator helpContentGenerator,
                       KeyboardActionResources keyboardActionResources) {
        this.allModes = allModes;
        this.configBuilder = configBuilder;
        this.keyboardPreferences = keyboardPreferences;
        this.modeController = modeController;
        this.helpContentGenerator = helpContentGenerator;
        this.keyboardActionResources = keyboardActionResources;
        helpActionCategory = configBuilder.createActionCategory(HELP_CATEGORY);
        helpPopup = new HelpPopup(keyboardActionResources);
    }

     void initHelpActions() {
        showHelpBinding = KeyboardBinding.bind(new KeyStroke(Modifiers.SHIFT, 191), new KeyStroke(Modifiers.NONE, 'H')).withDefaultModes(allModes);
        hideHelpBinding = KeyboardBinding.bind(new KeyStroke(Modifiers.NONE, KeyCodes.KEY_ESCAPE)).withDefaultModes(allModes);
        showHelpDef = new ActionDef(SHOW_HELP_ACTION, "Help", "Show a help popup.", keyboardActionResources.helpIcon());
        hideHelpDef = new ActionDef(HIDE_HELP_ACTION, "Hide help popup", "Hide the help popup.");
        configBuilder.addBinding(helpActionCategory, showHelpDef, new IActionHandler() {
            @Override
            public void performAction(String actionId) {
                helpPopup.setTitle("Keyboard help");
                helpPopup.setText(helpContentGenerator.getHelpText(configBuilder.getActionCategoryList()));
                helpPopup.setGlassEnabled(true);
                helpPopup.center();
                helpPopup.show();
            }
        }, showHelpBinding);
         configBuilder.addBinding(helpActionCategory, hideHelpDef, new IActionHandler() {
             @Override
             public void performAction(String actionId) {

                 helpPopup.hide();
             }
         }, hideHelpBinding);

    }



}
