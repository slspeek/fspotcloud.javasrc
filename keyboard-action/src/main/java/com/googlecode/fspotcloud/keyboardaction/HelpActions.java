package com.googlecode.fspotcloud.keyboardaction;

import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

import java.util.List;

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
    private final Resources resources;

    public HelpActions(String[] allModes,
                       ConfigBuilder configBuilder,
                       KeyboardPreferences keyboardPreferences,
                       IModeController modeController,
                       HelpContentGenerator helpContentGenerator,
                       Resources resources) {
        this.allModes = allModes;
        this.configBuilder = configBuilder;
        this.keyboardPreferences = keyboardPreferences;
        this.modeController = modeController;
        this.helpContentGenerator = helpContentGenerator;
        this.resources = resources;
        helpActionCategory = configBuilder.createActionCategory(HELP_CATEGORY);
        helpPopup = new HelpPopup(resources);
    }

     void initHelpActions() {
        showHelpBinding = KeyboardBinding.bind(new KeyStroke(Modifiers.SHIFT, 191), new KeyStroke(Modifiers.NONE, 'H')).withDefaultModes(allModes);
        hideHelpBinding = KeyboardBinding.bind(new KeyStroke(Modifiers.NONE, KeyCodes.KEY_ESCAPE)).withDefaultModes(allModes);
        showHelpDef = new ActionDef(SHOW_HELP_ACTION, "Help", "Show a help popup.", resources.helpIcon());
        hideHelpDef = new ActionDef(HIDE_HELP_ACTION, "Hide help popup", "Hide the help popup.");
        configBuilder.addBinding(helpActionCategory, showHelpDef, new IActionHandler() {
            @Override
            public void performAction(String actionId) {
                helpPopup.setTitle("Keyboard help");
                helpPopup.setText(getHelpText());
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

    SafeHtml getHelpText() {
        List<ActionCategory> categoryList = configBuilder.getActionCategoryList();
        SafeHtmlBuilder safeHtmlBuilder = new SafeHtmlBuilder();
        for(ActionCategory actionCategory: categoryList) {
            safeHtmlBuilder.append(helpContentGenerator.getHelp(actionCategory));
        }
         return safeHtmlBuilder.toSafeHtml();
    }

}
