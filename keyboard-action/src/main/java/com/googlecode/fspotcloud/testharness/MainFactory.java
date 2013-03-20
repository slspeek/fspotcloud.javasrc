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

    static TextArea messageBoard = new TextArea();

    static void outputMesg(String msg) {
        log.log(Level.FINEST, msg);
        messageBoard.setText(msg);
    }


    @Inject
    public MainFactory(){
        MainFactory.messageBoard.setVisibleLines(20);
        MainFactory.messageBoard.setCharacterWidth(100);

    }


}
