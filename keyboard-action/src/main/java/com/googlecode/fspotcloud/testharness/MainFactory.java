package com.googlecode.fspotcloud.testharness;

import com.google.common.annotations.GwtCompatible;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
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

    public static final ActionDef OK_DEF = new ActionDef(OK, "Ok", "Okey");
    public static final ActionDef CANCEL_DEF = new ActionDef(CANCEL, "Cancel", "Cancel this");
    public static final ActionDef TRY_DEF = new ActionDef(TRY, "Try it", "Please try this");
    public static final ActionDef THREE_DEF = new ActionDef(THREE, "3", "3 this");

    final KeyboardActionFactory keyboardActionFactory = new KeyboardActionFactory(MODES);
    final ConfigBuilder configBuilder = keyboardActionFactory.getConfigBuilder();
    final IModeController modeController = keyboardActionFactory.getModeController();


    final KeyStroke SHIFT_A = new KeyStroke(Modifiers.SHIFT, 'A');
    final KeyStroke KEY_C = new KeyStroke('C');
    final KeyStroke KEY_B = new KeyStroke('B');
    final KeyStroke KEY_D = new KeyStroke('D');
    final KeyStroke KEY_G = new KeyStroke('G');
    final KeyStroke KEY_3 = new KeyStroke('3');
    final KeyStroke ALT_M = new KeyStroke(Modifiers.ALT, 'M');
    final KeyStroke CTRL_M = new KeyStroke(Modifiers.CTRL, 'M');


    final KeyboardBinding ALLWAYS_SHIFT_A = KeyboardBinding.bind(SHIFT_A, KEY_D).withModes(MODES).override(MODE_TWO);
    final KeyboardBinding C_BINDING = KeyboardBinding.bind(KEY_C).override(MODE_TWO, KEY_B).withModes(MODE_ONE);
    final KeyboardBinding G_BINDING = KeyboardBinding.bind(KEY_G, CTRL_M).withModes(MODES);
    final KeyboardBinding THREE_BINDING = KeyboardBinding.bind(KEY_3, ALT_M).withModes(MODES).override(MODE_THREE).override(MODE_ONE);
    TextArea messageBoard = new TextArea();

    void outputMesg(String msg) {
        log.log(Level.FINEST, msg);
        messageBoard.setText(msg);
    }

    public MainFactory() {
        ActionCategory modeTwoSetters = configBuilder.createActionCategory("Mode 2 setters");
        ActionCategory otherModeSetters = configBuilder.createActionCategory("Other mode setters");
        messageBoard.setVisibleLines(20);
        messageBoard.setCharacterWidth(100);
        configBuilder.addBinding(modeTwoSetters,OK_DEF, new IActionHandler() {
            @Override
            public void performAction(String actionId) {
                modeController.setMode(MODE_TWO);
                String msg = "Running OK action " + actionId;
                outputMesg(msg);

            }
        }, ALLWAYS_SHIFT_A);
        configBuilder.addBinding(otherModeSetters,CANCEL_DEF, new IActionHandler() {
            @Override
            public void performAction(String actionId) {
                modeController.setMode(MODE_ONE);
                String msg = "Running CANCEL action " + actionId;
                outputMesg(msg);
            }
        }, C_BINDING);
        configBuilder.addBinding(modeTwoSetters, TRY_DEF, new IActionHandler() {
            @Override
            public void performAction(String actionId) {
                modeController.setMode(MODE_TWO);
                String msg = "Running TRY action " + actionId;
                outputMesg(msg);
            }
        }, G_BINDING);
        configBuilder.addBinding(otherModeSetters, THREE_DEF, new IActionHandler() {
            @Override
            public void performAction(String actionId) {
                modeController.setMode(MODE_THREE);
                outputMesg("Running 3 action " + actionId);
            }
        }, THREE_BINDING);
        ActionToolbar toolbar = new ActionToolbar();
        ActionButton okButton = keyboardActionFactory.getButton(OK);
        toolbar.add(okButton);
        ActionButton tryButton = keyboardActionFactory.getButton(TRY);
        toolbar.add(tryButton);
        ActionButton cancelButton = keyboardActionFactory.getButton(CANCEL);
        toolbar.add(cancelButton);
        ActionButton _3Button = keyboardActionFactory.getButton(THREE);
        toolbar.add(_3Button);
        RootPanel.get().add(messageBoard);
        RootPanel.get().add(toolbar);
        modeController.initButtonEnableStates();
    }


}
