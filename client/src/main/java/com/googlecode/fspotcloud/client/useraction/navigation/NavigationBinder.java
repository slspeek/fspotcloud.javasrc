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
    private final NavigationActions navigationActions;

    @Inject
    public NavigationBinder(
            CategoryDef categoryDef, NavigationActionHandler navigationActionHandler,
            NavigationActions navigationActions) {
        super(categoryDef.NAVIGATION);
        this.navigationActionHandler = navigationActionHandler;
        this.navigationActions = navigationActions;
    }


    @Override
    public void build() {
        bind(navigationActions.home, get(KeyCodes.KEY_HOME));
        bind(navigationActions.page_up, get(KeyCodes.KEY_PAGEUP));
        bind(navigationActions.row_up, get(KeyCodes.KEY_UP));
        bind(navigationActions.back, get(KeyCodes.KEY_LEFT));
        bind(navigationActions.next, get(KeyCodes.KEY_RIGHT));
        bind(navigationActions.row_down, get(KeyCodes.KEY_DOWN));
        bind(navigationActions.page_down, get(KeyCodes.KEY_PAGEDOWN));
        bind(navigationActions.end, get(KeyCodes.KEY_END));
    }

    public void bind(ActionDef actionDef, KeyboardBinding keyBinding) {
        super.bind(actionDef, navigationActionHandler, keyBinding);
    }

    private KeyboardBinding get(int character) {
        return KeyboardBinding.bind(new KeyStroke(character)).withDefaultModes(Modes.TAG_VIEW);
    }

}
