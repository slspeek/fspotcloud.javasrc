package com.googlecode.fspotcloud.testharness;

import com.google.gwt.event.dom.client.KeyCodes;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.keyboardaction.*;

import static com.googlecode.fspotcloud.testharness.MainFactory.outputMesg;

public class MainBuilder implements UIRegistrationBuilder {
    public static final String SINGLE_COLUMN_HELP = "help";
    public static final String TWO_COLUMN_HELP = "help-2c";

    public static final String MODE_ONE = "MODE_ONE";
    public static final String MODE_TWO = "MODE_TWO";
    public static final String MODE_THREE = "MODE_THREE";
    public static final String[] MODES = {MODE_ONE, MODE_TWO, MODE_THREE};

    public static final String OK = "OK";
    public static final String CANCEL = "CANCEL";
    public static final String TRY = "TRY";
    public static final String THREE = "THREE";
    public static final String DEMO = "DEMO";

    public static final ActionDef OK_DEF = new ActionDef(OK, "Ok", "Okey");
    public static final ActionDef CANCEL_DEF = new ActionDef(CANCEL, "Cancel", "Cancel this");
    public static final ActionDef TRY_DEF = new ActionDef(TRY, "Try it", "Please try this");
    public static final ActionDef THREE_DEF = new ActionDef(THREE, "3", "3 this");
    public static final ActionDef DEMO_DEF = new ActionDef(DEMO, "Demo", "Play a demo");

    final KeyStroke SHIFT_A = new KeyStroke(Modifiers.SHIFT, 'A');
    final KeyStroke KEY_C = new KeyStroke('C');
    final KeyStroke KEY_Q = new KeyStroke('Q');
    final KeyStroke KEY_B = new KeyStroke('B');
    final KeyStroke KEY_D = new KeyStroke('D');
    final KeyStroke KEY_G = new KeyStroke('G');
    final KeyStroke KEY_3 = new KeyStroke('3');
    final KeyStroke KEY_7 = new KeyStroke('7');
    final KeyStroke ALT_M = new KeyStroke(Modifiers.ALT, 'M');
    final KeyStroke CTRL_M = new KeyStroke(Modifiers.CTRL, 'M');
    final KeyStroke SHIFT_CTRL_ALT_R = new KeyStroke(new Modifiers(true, true, true), 'R');


    final KeyboardBinding ALLWAYS_SHIFT_A = KeyboardBinding.bind(SHIFT_A, KEY_D).withDefaultModes(MODES).override(MODE_TWO);
    final KeyboardBinding C_BINDING = KeyboardBinding.bind(KEY_C).override(MODE_TWO, KEY_B).withDefaultModes(MODE_ONE);
    final KeyboardBinding Q_BINDING = KeyboardBinding.bind(KEY_Q).withDefaultModes(MODES);
    final KeyboardBinding G_BINDING = KeyboardBinding.bind(KEY_G, CTRL_M).withDefaultModes(MODES);
    final KeyboardBinding THREE_BINDING = KeyboardBinding.bind(KEY_3, ALT_M).withDefaultModes(MODES).override(MODE_THREE).override(MODE_ONE, SHIFT_CTRL_ALT_R);
    final KeyboardBinding DEMO_BINDING = KeyboardBinding.bind(KEY_7).withDefaultModes(MODES);

    private ActionDef stopDemoDef = new ActionDef("quit-demo", "Quit demo", "Stops all demos");
    ;

    @Inject
    ConfigBuilder configBuilder;
    @Inject
    IModeController modeController;
    @Inject
    HelpActionsFactory helpActionsFactory;
    @Inject
    DemoBuilderFactory demoBuilderFactory;

    @Override
    public void build() {


        ActionCategory modeTwoSetters = configBuilder.createActionCategory("Mode 2 setters");
        ActionCategory otherModeSetters = configBuilder.createActionCategory("Other mode setters");
        ActionCategory helpCategory = configBuilder.createActionCategory("Help");
        configBuilder.addBinding(modeTwoSetters, OK_DEF, new IActionHandler() {
            @Override
            public void performAction(String actionId) {
                modeController.setMode(MODE_TWO);
                String msg = "Running OK.";
                outputMesg(msg);

            }
        }, ALLWAYS_SHIFT_A);
        configBuilder.addBinding(otherModeSetters, CANCEL_DEF, new IActionHandler() {
            @Override
            public void performAction(String actionId) {
                modeController.setMode(MODE_ONE);
                String msg = "Running CANCEL.";
                outputMesg(msg);
            }
        }, C_BINDING);
        configBuilder.addBinding(modeTwoSetters, TRY_DEF, new IActionHandler() {
            @Override
            public void performAction(String actionId) {
                modeController.setMode(MODE_TWO);
                String msg = "Running TRY action. ";
                outputMesg(msg);
            }
        }, G_BINDING);
        configBuilder.addBinding(otherModeSetters, THREE_DEF, new IActionHandler() {
            @Override
            public void performAction(String actionId) {
                modeController.setMode(MODE_THREE);
                outputMesg("Running 3-action. ");
            }
        }, THREE_BINDING);
        ActionDef showHelpDef = new ActionDef(SINGLE_COLUMN_HELP, "Help", "Show a help popup.");
        ActionDef show2cHelpDef = new ActionDef(TWO_COLUMN_HELP, "Help 2c", "Show a help 2-column popup.");
        ActionDef hideHelpDef = new ActionDef("hide-help", "Hide help", "Hide the help popup.");
        KeyboardBinding showHelpBinding = KeyboardBinding.bind(new KeyStroke(Modifiers.SHIFT, 191), new KeyStroke(Modifiers.NONE, 'H')).withDefaultModes(MODES);
        KeyboardBinding show2cHelpBinding = KeyboardBinding.bind(new KeyStroke(Modifiers.SHIFT, 'H')).withDefaultModes(MODES);
        KeyboardBinding hideHelpBinding = KeyboardBinding.bind(new KeyStroke(Modifiers.NONE, KeyCodes.KEY_ESCAPE)).withDefaultModes(MODES);

        configBuilder.addBinding(helpCategory, hideHelpDef, helpActionsFactory.getCloseHelp(), hideHelpBinding);
        HelpConfig helpConfig = new HelpConfig("Shortcuts");
        HelpConfig help2cConfig = new HelpConfig("Shortcuts in two columns");
        helpConfig.addToFirstColumn(helpCategory, modeTwoSetters, otherModeSetters);
        help2cConfig.addToFirstColumn(helpCategory, modeTwoSetters);
        help2cConfig.addToSecondColumn(otherModeSetters);
        configBuilder.addBinding(helpCategory, showHelpDef, helpActionsFactory.getHelpAction(helpConfig), showHelpBinding);
        configBuilder.addBinding(helpCategory, show2cHelpDef, helpActionsFactory.getHelpAction(help2cConfig), show2cHelpBinding);
        configBuilder.addBinding(helpCategory, stopDemoDef, demoBuilderFactory.getStopDemoHandler(), Q_BINDING);

        DemoBuilder demoBuilder = demoBuilderFactory.get(DEMO_DEF);
        demoBuilder.addStep(OK, 3000);
        demoBuilder.addStep(CANCEL, 2000);
        demoBuilder.addStep(HelpActionsFactory.SHOW_HELP_ACTION, 2000);
        demoBuilder.addStep(HelpActionsFactory.HIDE_HELP_ACTION, 1000);

        configBuilder.addBinding(helpCategory


                ,
                DEMO_DEF,
                demoBuilder.getDemo(),
                DEMO_BINDING);


    }
}
