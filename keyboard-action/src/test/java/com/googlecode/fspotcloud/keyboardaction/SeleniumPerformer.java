package com.googlecode.fspotcloud.keyboardaction;

import com.google.inject.Inject;
import com.thoughtworks.selenium.Selenium;

public class SeleniumPerformer {

    @Inject
    private Selenium selenium;


    public void performAction(ActionUIDef def) {
        selenium.click("gwt-debug-" + def.getId());
    }
}
