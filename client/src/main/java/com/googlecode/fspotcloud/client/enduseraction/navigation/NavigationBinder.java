package com.googlecode.fspotcloud.client.enduseraction.navigation;

import com.google.gwt.event.dom.client.KeyCodes;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.enduseraction.AbstractBinder;
import com.googlecode.fspotcloud.client.enduseraction.CategoryDef;
import com.googlecode.fspotcloud.client.enduseraction.Flags;
import com.googlecode.fspotcloud.client.place.BasePlace;
import com.googlecode.fspotcloud.keyboardaction.ActionUIDef;
import com.googlecode.fspotcloud.keyboardaction.FlagsRule;
import com.googlecode.fspotcloud.keyboardaction.KeyStroke;
import com.googlecode.fspotcloud.keyboardaction.Relevance;

import static com.googlecode.fspotcloud.keyboardaction.FlagsRule.excluding;
import static com.googlecode.fspotcloud.keyboardaction.FlagsRule.needing;
import static com.googlecode.fspotcloud.keyboardaction.KeyStroke.*;

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
        bind(navigationActions.home, get(HOME, needing(Flags.CAN_GO_HOME.name()), alt(HOME)));
        bind(navigationActions.page_up, get(PAGEUP, needing(Flags.CAN_GO_PREV_PAGE.name()),alt(PAGEUP)));
        bind(navigationActions.row_up, get(UP, needing(Flags.CAN_GO_PREV_ROW.name()),alt(UP)));
        bind(navigationActions.back, get(KeyStroke.LEFT, needing(Flags.CAN_GO_PREV_IMAGE.name()),plain('K')));
        bind(navigationActions.next, get(KeyStroke.RIGHT, needing(Flags.CAN_GO_NEXT_IMAGE.name()),plain('J')));
        bind(navigationActions.row_down, get(DOWN, needing(Flags.CAN_GO_NEXT_ROW.name()),alt(DOWN)));
        bind(navigationActions.page_down, get(PAGEDOWN, needing(Flags.CAN_GO_NEXT_PAGE.name()),alt(PAGEDOWN)));
        bind(navigationActions.end, get(END, needing(Flags.CAN_GO_END.name()), alt(END)));
        Relevance relevance = new Relevance(BasePlace.class).addDefaultKeys(alt('A'));
        bind(navigationActions.all_photos, relevance);
        bind(navigationActions.rss_feed, goRssFeedHandler, get(KeyStroke.DELETE));
    }

    private Relevance get(KeyStroke pageup) {
        return (new Relevance(BasePlace.class)).addDefaultKeys(pageup);
    }

    public void bind(ActionUIDef actionUIDef, Relevance keyBinding) {
        super.bind(actionUIDef, navigationActionHandler, keyBinding);
    }

    private Relevance get(KeyStroke stroke, FlagsRule rule, KeyStroke nonConflicting) {
        FlagsRule focusRule = new FlagsRule(rule).needs(Flags.TREE_FOCUS.name());
        FlagsRule nonFocusRule = new FlagsRule(rule).excludes(Flags.TREE_FOCUS.name());
        //return new Relevance(rule, BasePlace.class).addDefaultKeys(stroke, nonConflicting);
        return new Relevance(rule, BasePlace.class).addDefaultKeys(stroke, nonConflicting)
                .addRule(BasePlace.class, nonFocusRule, stroke, nonConflicting)
                .addRule(BasePlace.class, focusRule, nonConflicting);
    }

}
