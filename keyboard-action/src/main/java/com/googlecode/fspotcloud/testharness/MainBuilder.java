package com.googlecode.fspotcloud.testharness;

import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.place.shared.PlaceController;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.keyboardaction.*;

import static com.googlecode.fspotcloud.keyboardaction.FlagsRule.excluding;
import static com.googlecode.fspotcloud.keyboardaction.FlagsRule.needing;
import static com.googlecode.fspotcloud.testharness.MainFactory.outputMesg;

public class MainBuilder implements UIRegistrationBuilder {
    public static final String FLAG_LOGGED_ON = "FLAG_LOGGED_ON";
    public static final String SINGLE_COLUMN_HELP = "help";
    public static final String TWO_COLUMN_HELP = "help-2c";

    public static final String MODE_ONE = "MODE_ONE";
    public static final String MODE_TWO = "MODE_TWO";
    public static final String MODE_THREE = "MODE_THREE";
    public static final String[] MODES = {MODE_ONE, MODE_TWO, MODE_THREE};

    public static final String LOGIN = "LOGIN";
    public static final String HOME = "HOME";
    public static final String GO_OUT = "GO_OUT";
    public static final String LOGOUT = "LOGOUT";
    public static final String DEMO = "DEMO";

    public static final ActionUIDef LOGIN_DEF = new ActionUIDef(LOGIN, "Login", "Logon");
    public static final ActionUIDef HOME_DEF = new ActionUIDef(HOME, "Home", "Go home");
    public static final ActionUIDef GO_OUT_DEF = new ActionUIDef(GO_OUT, "Go out", "Go out place");
    public static final ActionUIDef LOGOUT_DEF = new ActionUIDef(LOGOUT, "Logout", "Logout");
    public static final ActionUIDef DEMO_DEF = new ActionUIDef(DEMO, "Demo", "Play a demo");

    final KeyStroke SHIFT_A = new KeyStroke(Modifiers.SHIFT, 'A');
    final KeyStroke KEY_C = new KeyStroke('C');
    final KeyStroke KEY_Q = new KeyStroke('Q');
    final KeyStroke KEY_G = new KeyStroke('G');
    final KeyStroke KEY_3 = new KeyStroke('3');
    final KeyStroke KEY_7 = new KeyStroke('7');


    Relevance ALLWAYS_SHIFT_A;
    Relevance C_BINDING;
    Relevance Q_BINDING;
    Relevance LOGOUT_BINDING;
    Relevance DEMO_BINDING;

    private ActionUIDef stopDemoDef = new ActionUIDef("quit-demo", "Quit demo", "Stops all demos");
    ;

    @Inject
    ConfigBuilder configBuilder;
    @Inject
    IModeController modeController;
    @Inject
    HelpActionsFactory helpActionsFactory;
    @Inject
    DemoBuilderFactory demoBuilderFactory;
    @Inject
    PlaceController placeController;


    @Override
    public void build() {
        Relevance HOME_ONLY_G = new Relevance(needing(MainBuilder.FLAG_LOGGED_ON), HomePlace.class);
        HOME_ONLY_G.addDefaultKeys(KEY_G);

        C_BINDING = new Relevance(OutPlace.class);
        C_BINDING.addDefaultKeys(KeyStroke.K, KeyStroke.HOME);


        ALLWAYS_SHIFT_A = new Relevance(excluding(MainBuilder.FLAG_LOGGED_ON));
        ALLWAYS_SHIFT_A.addDefaultKeys(SHIFT_A);

        DEMO_BINDING = new Relevance();
        DEMO_BINDING.addDefaultKeys(KEY_7);

        Q_BINDING = new Relevance(needing(BuiltinFlags.DEMOING));
        Q_BINDING.addDefaultKeys(KEY_Q, KEY_G, KeyStroke.ESC);

        LOGOUT_BINDING = new Relevance(needing(MainBuilder.FLAG_LOGGED_ON));
        LOGOUT_BINDING.addDefaultKeys(KEY_3);

        ActionCategory modeTwoSetters = configBuilder.createCategory("Mode 2 setters");
        ActionCategory otherModeSetters = configBuilder.createCategory("Other mode setters");
        ActionCategory helpCategory = configBuilder.createCategory("Help");
        configBuilder.addBinding(modeTwoSetters, LOGIN_DEF, new IActionHandler() {
            @Override
            public void performAction(String actionId) {
                modeController.setFlag(FLAG_LOGGED_ON);
                String msg = "Running LOGIN.";
                outputMesg(msg);

            }
        }, ALLWAYS_SHIFT_A);
        configBuilder.addBinding(otherModeSetters, HOME_DEF, new IActionHandler() {
            @Override
            public void performAction(String actionId) {

                String msg = "Running HOME.";
                placeController.goTo(new HomePlace());
                outputMesg(msg);
            }
        }, C_BINDING);
        configBuilder.addBinding(modeTwoSetters, GO_OUT_DEF, new IActionHandler() {
            @Override
            public void performAction(String actionId) {

                String msg = "Running GO_OUT action. ";
                placeController.goTo(new OutPlace());
                outputMesg(msg);
            }
        }, HOME_ONLY_G);
        configBuilder.addBinding(otherModeSetters, LOGOUT_DEF, new IActionHandler() {
            @Override
            public void performAction(String actionId) {
                modeController.unsetFlag(FLAG_LOGGED_ON);
                outputMesg("Running logout. ");
            }
        }, LOGOUT_BINDING);
        ActionUIDef showHelpDef = new ActionUIDef(SINGLE_COLUMN_HELP, "Help", "Show a help popup.");
        ActionUIDef shortcutsDef = new ActionUIDef("shortcuts", "Shortcuts", "Show a shortcuts popup.");
        ActionUIDef show2cHelpDef = new ActionUIDef(TWO_COLUMN_HELP, "Help 2c", "Show a help 2-column popup.");
        ActionUIDef hideHelpDef = new ActionUIDef("hide-help", "Hide help", "Hide the help popup.");
        Relevance showHelpBinding = new Relevance(HomePlace.class).addDefaultKeys(new KeyStroke(Modifiers.SHIFT, 191), new KeyStroke(Modifiers.NONE, 'H'));
        FlagsRule cond = new FlagsRule();
        cond.needs(FLAG_LOGGED_ON);
        showHelpBinding = showHelpBinding.addRule(OutPlace.class, cond, KeyStroke.H);
        Relevance show2cHelpBinding = new Relevance().addDefaultKeys(new KeyStroke(Modifiers.SHIFT, 'H'));
        Relevance scBinding = new Relevance().addDefaultKeys(new KeyStroke(KeyStroke.KEY_FORWARD_SLASH));
        Relevance hideHelpBinding = new Relevance().addDefaultKeys(new KeyStroke(Modifiers.NONE, KeyCodes.KEY_ESCAPE));

        configBuilder.addBinding(helpCategory, hideHelpDef, helpActionsFactory.getCloseHelp(), hideHelpBinding);
        HelpConfig helpConfig = new HelpConfig("Shortcuts");
        HelpConfig help2cConfig = new HelpConfig("Shortcuts in two columns");
        helpConfig.addToFirstColumn(helpCategory, modeTwoSetters, otherModeSetters);
        help2cConfig.addToFirstColumn(helpCategory, modeTwoSetters);
        help2cConfig.addToSecondColumn(otherModeSetters);
        configBuilder.addBinding(helpCategory, showHelpDef, helpActionsFactory.getHelpAction(helpConfig), showHelpBinding);
        configBuilder.addBinding(helpCategory, show2cHelpDef, helpActionsFactory.getHelpAction(help2cConfig), show2cHelpBinding);
        configBuilder.addBinding(helpCategory, stopDemoDef, demoBuilderFactory.getStopDemoHandler(), Q_BINDING);
        configBuilder.addBinding(helpCategory, shortcutsDef, helpActionsFactory.getShortcutsAction(), scBinding);

        DemoBuilder demoBuilder = demoBuilderFactory.get(DEMO_DEF);
        demoBuilder.addStep(LOGIN, 3000);
        demoBuilder.addStep(HOME, 2000);
        demoBuilder.addStep(HelpActionsFactory.SHOW_HELP_ACTION, 2000);
        demoBuilder.addStep(HelpActionsFactory.HIDE_HELP_ACTION, 1000);

        configBuilder.addBinding(
                helpCategory,
                DEMO_DEF,
                demoBuilder.getDemo(),
                DEMO_BINDING);


    }
}
