package com.googlecode.fspotcloud.testharness;

import com.google.common.annotations.GwtCompatible;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.keyboardaction.*;

import java.util.logging.Level;
import java.util.logging.Logger;

@GwtCompatible
public class MainFactory {

    public final Logger log = Logger.getLogger(MainFactory.class.getName());
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

    final KeyboardActionFactory keyboardActionFactory;
    final ConfigBuilder configBuilder;
    final IModeController modeController;
    final DemoBuilderFactory demoBuilderFactory;
    final HelpActionsFactory helpActionsFactory;

    final KeyStroke SHIFT_A = new KeyStroke(Modifiers.SHIFT, 'A');
    final KeyStroke KEY_C = new KeyStroke('C');
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
    final KeyboardBinding G_BINDING = KeyboardBinding.bind(KEY_G, CTRL_M).withDefaultModes(MODES);
    final KeyboardBinding THREE_BINDING = KeyboardBinding.bind(KEY_3, ALT_M).withDefaultModes(MODES).override(MODE_THREE).override(MODE_ONE, SHIFT_CTRL_ALT_R);
    final KeyboardBinding DEMO_BINDING = KeyboardBinding.bind(KEY_7).withDefaultModes(MODES);
    TextArea messageBoard = new TextArea();

    void outputMesg(String msg) {
        log.log(Level.FINEST, msg);
        messageBoard.setText(msg);
    }

    @Inject
    public MainFactory(KeyboardActionFactory keyboardActionFactory, DemoBuilderFactory demoBuilderFactory, HelpActionsFactory helpActionsFactory) {
        this.keyboardActionFactory = keyboardActionFactory;
        this.demoBuilderFactory = demoBuilderFactory;
        this.helpActionsFactory = helpActionsFactory;
        helpActionsFactory.initHelpActions();
        this.configBuilder = keyboardActionFactory.getConfigBuilder();
        this.modeController = keyboardActionFactory.getModeController();


        ActionCategory modeTwoSetters = configBuilder.createActionCategory("Mode 2 setters");
        ActionCategory otherModeSetters = configBuilder.createActionCategory("Other mode setters");
        messageBoard.setVisibleLines(20);
        messageBoard.setCharacterWidth(100);
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

        DemoBuilder demoBuilder = demoBuilderFactory.get(DEMO_DEF);
        demoBuilder.addStep(OK, 3000);
        demoBuilder.addStep(CANCEL, 2000);
        demoBuilder.addStep(HelpActionsFactory.SHOW_HELP_ACTION, 2000);
        demoBuilder.addStep(HelpActionsFactory.HIDE_HELP_ACTION, 1000);

        configBuilder.addBinding(helpActionsFactory.helpActionCategory,
                DEMO_DEF,
                demoBuilder.getDemo(),
                DEMO_BINDING);


        ActionMenu menu = keyboardActionFactory.getMenu("First menu!");
        menu.add(HelpActionsFactory.SHOW_HELP_ACTION);
        menu.add(OK);
        menu.add(CANCEL);
        ActionToolbar toolbar = keyboardActionFactory.getToolBar();
        toolbar.add(OK);
        toolbar.add(TRY);
        toolbar.add(menu);

        toolbar.add(CANCEL);
        toolbar.add(THREE);
        toolbar.add(keyboardActionFactory.getButton(HelpActionsFactory.SHOW_HELP_ACTION));
        toolbar.add(keyboardActionFactory.getButton(DEMO));
        RootPanel.get().add(messageBoard);
        RootPanel.get().add(toolbar);
        modeController.initButtonEnableStates();
    }


}
