package com.googlecode.fspotcloud.keyboardaction.gwt;

import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.ui.HasVisibility;
import com.googlecode.fspotcloud.keyboardaction.Demo;

public interface DemoPopupView extends HasVisibility {

    void setSafeHtml(SafeHtml text);

    void setTitle(String text);

    void setPopupPosition(int i, int i1);

    void show();

    void hide();

    void setDemo(Demo demo);
}
