package com.googlecode.fspotcloud.client.enduseraction.navigation;

import com.google.gwt.event.dom.client.KeyCodes;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.enduseraction.AbstractBinder;
import com.googlecode.fspotcloud.client.enduseraction.CategoryDef;
import com.googlecode.fspotcloud.client.enduseraction.Flags;
import com.googlecode.fspotcloud.client.place.BasePlace;
import com.googlecode.fspotcloud.keyboardaction.ActionUIDef;
import com.googlecode.fspotcloud.keyboardaction.KeyStroke;
import com.googlecode.fspotcloud.keyboardaction.Relevance;

import static com.googlecode.fspotcloud.keyboardaction.FlagsRule.excluding;
import static com.googlecode.fspotcloud.keyboardaction.KeyStroke.K;
import static com.googlecode.fspotcloud.keyboardaction.KeyStroke.alt;

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
        bind(navigationActions.home, get(KeyStroke.HOME));
        bind(navigationActions.page_up, get(KeyStroke.PAGEUP));
        bind(navigationActions.row_up, get(KeyStroke.UP));
        bind(navigationActions.back, get(KeyStroke.LEFT));
        bind(navigationActions.next, get(KeyStroke.RIGHT));
        bind(navigationActions.row_down, get(KeyStroke.DOWN));
        bind(navigationActions.page_down, get(KeyStroke.PAGEDOWN));
        bind(navigationActions.end, get(KeyStroke.END));
        Relevance relevance = new Relevance(BasePlace.class).addDefaultKeys(alt('A'));
        bind(navigationActions.all_photos, relevance);
        bind(navigationActions.rss_feed, goRssFeedHandler, get(KeyStroke.DELETE));
    }

    public void bind(ActionUIDef actionUIDef, Relevance keyBinding) {
        super.bind(actionUIDef, navigationActionHandler, keyBinding);
    }

    private Relevance get(KeyStroke stroke) {
        return new Relevance(excluding(Flags.TREE_FOCUS.name()), BasePlace.class).addDefaultKeys(stroke);
    }

}
