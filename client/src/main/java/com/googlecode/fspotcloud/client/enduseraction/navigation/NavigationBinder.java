package com.googlecode.fspotcloud.client.enduseraction.navigation;

import com.google.gwt.event.dom.client.KeyCodes;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.enduseraction.AbstractBinder;
import com.googlecode.fspotcloud.client.enduseraction.CategoryDef;
import com.googlecode.fspotcloud.client.enduseraction.Modes;
import com.googlecode.fspotcloud.keyboardaction.ActionUIDef;
import com.googlecode.fspotcloud.keyboardaction.KeyStroke;
import com.googlecode.fspotcloud.keyboardaction.KeyboardBinding;

public class NavigationBinder extends AbstractBinder {

    private final NavigationActionHandler navigationActionHandler;
    private final NavigationActions navigationActions;
    private final GoRssFeedHandler goRssFeedHandler;

    @Inject
    public NavigationBinder(
            CategoryDef categoryDef, NavigationActionHandler navigationActionHandler,
            NavigationActions navigationActions, GoRssFeedHandler goRssFeedHandler) {
        super(categoryDef.NAVIGATION);
        this.navigationActionHandler = navigationActionHandler;
        this.navigationActions = navigationActions;
        this.goRssFeedHandler = goRssFeedHandler;
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
        bind(navigationActions.rss_feed, goRssFeedHandler, get(KeyCodes.KEY_DELETE));
    }

    public void bind(ActionUIDef actionUIDef, KeyboardBinding keyBinding) {
        super.bind(actionUIDef, navigationActionHandler, keyBinding);
    }

    private KeyboardBinding get(int character) {
        return KeyboardBinding.bind(new KeyStroke(character)).withDefaultModes(Modes.TAG_VIEW);
    }

}
