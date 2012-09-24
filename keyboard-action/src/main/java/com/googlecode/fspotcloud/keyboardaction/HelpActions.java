package com.googlecode.fspotcloud.keyboardaction;

import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.safehtml.shared.SafeHtml;

import java.util.Map;

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
    private final HelpPopup helpPopup = new HelpPopup();
    private final KeyboardPreferences keyboardPreferences;
    private final IModeController modeController;

    public HelpActions(String[] allModes, ConfigBuilder configBuilder, KeyboardPreferences keyboardPreferences, IModeController modeController) {
        this.allModes = allModes;
        this.configBuilder = configBuilder;
        this.keyboardPreferences = keyboardPreferences;
        this.modeController = modeController;
        helpActionCategory = configBuilder.createActionCategory(HELP_CATEGORY);
    }

     void initHelpActions() {
        showHelpBinding = KeyboardBinding.bind(new KeyStroke(Modifiers.SHIFT, '?'), new KeyStroke(Modifiers.NONE, 'H')).withModes(allModes);
        hideHelpBinding = KeyboardBinding.bind(new KeyStroke(Modifiers.NONE, KeyCodes.KEY_ESCAPE)).withModes(allModes);
        showHelpDef = new ActionDef(SHOW_HELP_ACTION, "Help", "Show a help popup.");
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

    String getHelpText() {
        StringBuffer result = new StringBuffer();
        Map<String,ActionCategory> categoryMap = configBuilder.getActionCategoryMap();
        for(ActionCategory actionCategory: categoryMap.values()) {
            result.append("<br>");
            result.append("<b>");
            result.append(actionCategory.getName());
            result.append("</b>");
            result.append("<br>");
            for (ActionDef actionDef: actionCategory.getActions()) {
                KeyStroke[] keysForAction = keyboardPreferences.getKeysForAction(modeController.getMode(), actionDef.getId());
                if (keysForAction.length == 0) {
                    break;
                }
                result.append(actionDef.getName());
                result.append(" - ");
                result.append(actionDef.getDescription());
                result.append(" : ");
                result.append(newArrayList(keysForAction));
                result.append("<br>");
            }
        }
        return result.toString();
    }

}
