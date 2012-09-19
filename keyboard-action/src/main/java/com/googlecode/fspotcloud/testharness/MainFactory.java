package com.googlecode.fspotcloud.testharness;

import com.google.common.annotations.GwtCompatible;
import com.google.gwt.user.client.ui.RootPanel;
import com.googlecode.fspotcloud.keyboardaction.*;

import java.util.logging.Logger;

@GwtCompatible
public class MainFactory {

    public final Logger log = Logger.getLogger(MainFactory.class.getName());
    public static final String MODE_ONE = "MODE_ONE";
    public static final String MODE_TWO = "MODE_TWO";
    public static final String[] MODES = {MODE_ONE, MODE_TWO};

    public static final String OK = "OK";
    public static final String CANCEL = "CANCEL";
    public static final String TRY = "TRY";

    public static final ActionDef OK_DEF = new ActionDef(OK, "Ok", "Okey");
    public static final ActionDef CANCEL_DEF = new ActionDef(CANCEL, "Cancel", "Cancel this");
    public static final ActionDef TRY_DEF = new ActionDef(TRY, "Try it", "Please try this");

    final KeyboardActionFactory keyboardActionFactory = new KeyboardActionFactory(MODES);
    final ConfigBuilder configBuilder = keyboardActionFactory.getConfigBuilder();
    final IModeController modeController = keyboardActionFactory.getModeController();


    final KeyStroke SHIFT_A = new KeyStroke(true, 'A');
    final KeyStroke KEY_C = new KeyStroke('C');
    final KeyStroke KEY_B = new KeyStroke('B');
    final KeyStroke KEY_D = new KeyStroke('D');
    final KeyStroke KEY_G = new KeyStroke('G');


    final KeyboardBinding ALLWAYS_SHIFT_A = KeyboardBinding.bind(SHIFT_A, KEY_D).withModes(MODES);
    final KeyboardBinding C_BINDING = KeyboardBinding.bind(KEY_C).override(MODE_TWO, KEY_B).withModes(MODE_ONE);
    final KeyboardBinding G_BINDING = KeyboardBinding.bind(KEY_G).withModes(MODES);

    public MainFactory() {
        //modeController.setMode(MODE_ONE);
        configBuilder.addBinding(OK_DEF, new IActionHandler() {
            @Override
            public void performAction(String actionId) {
                modeController.setMode(MODE_TWO);
                log.info("Running OK action " + actionId);
            }
        }, ALLWAYS_SHIFT_A);
        configBuilder.addBinding(CANCEL_DEF, new IActionHandler() {
            @Override
            public void performAction(String actionId) {
                modeController.setMode(MODE_ONE);
                log.info("Running CANCEL action " + actionId);
            }
        }, C_BINDING);
        configBuilder.addBinding(TRY_DEF, new IActionHandler() {
            @Override
            public void performAction(String actionId) {
                modeController.setMode(MODE_TWO);
                log.info("Running TRY action " + actionId);
            }
        }, G_BINDING);
        RootPanel.get().add(keyboardActionFactory.getButton(OK));
        RootPanel.get().add(keyboardActionFactory.getButton(TRY));
        RootPanel.get().add(keyboardActionFactory.getButton(CANCEL));
    }



}
