package com.googlecode.fspotcloud.testharness;

import com.googlecode.fspotcloud.keyboardaction.*;

import java.util.logging.Logger;

public class MainFactory {

    public final Logger log = Logger.getLogger(MainFactory.class.getName());
    public static final String MODE_ONE = "MODE_ONE";
    public static final String MODE_TWO = "MODE_TWO";
    public static final String[] MODES = {MODE_ONE, MODE_TWO};

    public static final String OK = "OK";
    public static final String CANCEL = "CANCEL";
    public static final String[] ACTIONS = {OK, CANCEL};

    public static final ActionDef OK_DEF = new ActionDef(OK, "Ok", "Okey");
    public static final ActionDef CANCEL_DEF = new ActionDef(CANCEL, "Cancel", "Cancel this");

    KeyboardActionFactory keyboardActionFactory = new KeyboardActionFactory(MODES);
    ConfigBuilder configBuilder = keyboardActionFactory.getConfigBuilder();
    IModeController modeController = keyboardActionFactory.getModeController();

    KeyStroke SHIFT_A = new KeyStroke(true, 'a');
    KeyStroke KEY_C = new KeyStroke('c');
    KeyStroke KEY_B = new KeyStroke('b');


    KeyboardBinding ALLWAYS_SHIFT_A = KeyboardBinding.bind(SHIFT_A);
    KeyboardBinding C_BINDING = KeyboardBinding.bind(KEY_C).override(MODE_TWO, KEY_B);

    public MainFactory() {
        configBuilder.addBinding(OK_DEF, new IActionHandler() {
            @Override
            public void performAction(String actionId) {
                modeController.setMode(MODE_TWO);
                log.info("OK action " + actionId);
            }
        }, ALLWAYS_SHIFT_A);
        configBuilder.addBinding(CANCEL_DEF, new IActionHandler() {
            @Override
            public void performAction(String actionId) {
                modeController.setMode(MODE_ONE);
                log.info("CANCEL action " + actionId);
            }
        }, C_BINDING);
    }



}
