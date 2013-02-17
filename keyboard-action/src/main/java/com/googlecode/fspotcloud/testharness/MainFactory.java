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

    public static final Logger log = Logger.getLogger(MainFactory.class.getName());
    final KeyboardActionFactory keyboardActionFactory;
    final IModeController modeController;
    final DemoBuilderFactory demoBuilderFactory;
    final HelpActionsFactory helpActionsFactory;

    static TextArea messageBoard = new TextArea();

    static void outputMesg(String msg) {
        log.log(Level.FINEST, msg);
        messageBoard.setText(msg);
    }


    @Inject
    public MainFactory(KeyboardActionFactory keyboardActionFactory,
                       DemoBuilderFactory demoBuilderFactory,
                       HelpActionsFactory helpActionsFactory) {
        this.keyboardActionFactory = keyboardActionFactory;
        this.demoBuilderFactory = demoBuilderFactory;
        this.helpActionsFactory = helpActionsFactory;
        this.modeController = keyboardActionFactory.getModeController();
        MainFactory.messageBoard.setVisibleLines(20);
        MainFactory.messageBoard.setCharacterWidth(100);


        ActionMenu menu = keyboardActionFactory.getMenu("First menu!");
        menu.add(HelpActionsFactory.SHOW_HELP_ACTION);
        menu.add(MainBuilder.OK);
        menu.add(MainBuilder.CANCEL);
        ActionToolbar toolbar = keyboardActionFactory.getToolBar();
        toolbar.add(MainBuilder.OK);
        toolbar.add(MainBuilder.TRY);
        toolbar.add(menu);

        toolbar.add(MainBuilder.CANCEL);
        toolbar.add(MainBuilder.THREE);
        toolbar.add(keyboardActionFactory.getButton(HelpActionsFactory.SHOW_HELP_ACTION));
        toolbar.add(keyboardActionFactory.getButton(MainBuilder.DEMO));
        RootPanel.get().add(messageBoard);
        RootPanel.get().add(toolbar);
        modeController.initButtonEnableStates();
    }


}
