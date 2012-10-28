package com.googlecode.fspotcloud.client.useraction.navigation;

import com.google.gwt.event.dom.client.KeyCodes;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.useraction.AbstractBinder;
import com.googlecode.fspotcloud.client.useraction.CategoryDef;
import com.googlecode.fspotcloud.client.useraction.Modes;
import com.googlecode.fspotcloud.keyboardaction.ActionDef;
import com.googlecode.fspotcloud.keyboardaction.KeyStroke;
import com.googlecode.fspotcloud.keyboardaction.KeyboardBinding;

public class NavigationBinder extends AbstractBinder {

    private final NavigationActionHandler navigationActionHandler;

    @Inject
    public NavigationBinder(
            CategoryDef categoryDef, NavigationActionHandler navigationActionHandler) {
        super(categoryDef.NAVIGATION);
        this.navigationActionHandler = navigationActionHandler;
    }


    @Override
    public void build() {
        KeyboardBinding homeBinding = KeyboardBinding.bind(new KeyStroke(KeyCodes.KEY_HOME)).withDefaultModes(Modes.TAG_VIEW);
        bind(NavigationActions.HOME_DEF, homeBinding);

        KeyboardBinding pageUpBinding = KeyboardBinding.bind(new KeyStroke(KeyCodes.KEY_PAGEUP)).withDefaultModes(Modes.TAG_VIEW);
        bind(NavigationActions.PAGE_UP_DEF, pageUpBinding);

        KeyboardBinding rowUpBinding = KeyboardBinding.bind(new KeyStroke(KeyCodes.KEY_UP)).withDefaultModes(Modes.TAG_VIEW);
        bind(NavigationActions.ROW_UP_DEF, rowUpBinding);

        KeyboardBinding backBinding = KeyboardBinding.bind(new KeyStroke(KeyCodes.KEY_LEFT)).withDefaultModes(Modes.TAG_VIEW);
        bind(NavigationActions.BACK_DEF, backBinding);

        KeyboardBinding nextBinding = KeyboardBinding.bind(new KeyStroke(KeyCodes.KEY_RIGHT)).withDefaultModes(Modes.TAG_VIEW);
        bind(NavigationActions.NEXT_DEF, nextBinding);

        KeyboardBinding rowDownBinding = KeyboardBinding.bind(new KeyStroke(KeyCodes.KEY_DOWN)).withDefaultModes(Modes.TAG_VIEW);
        bind(NavigationActions.ROW_DOWN_DEF, rowDownBinding);

        KeyboardBinding pageDownBinding = KeyboardBinding.bind(new KeyStroke(KeyCodes.KEY_PAGEDOWN)).withDefaultModes(Modes.TAG_VIEW);
        bind(NavigationActions.PAGE_DOWN_DEF, pageDownBinding);

        KeyboardBinding endBinding = KeyboardBinding.bind(new KeyStroke(KeyCodes.KEY_END)).withDefaultModes(Modes.TAG_VIEW);
        bind(NavigationActions.END_DEF, endBinding);
    }

    public void bind(ActionDef actionDef, KeyboardBinding keyBinding) {
        super.bind(actionDef, navigationActionHandler, keyBinding);
    }
}
