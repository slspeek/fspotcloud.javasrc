package com.googlecode.fspotcloud.client.enduseraction.application;

import com.google.gwt.event.dom.client.KeyCodes;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.enduseraction.AbstractBinder;
import com.googlecode.fspotcloud.client.enduseraction.CategoryDef;
import com.googlecode.fspotcloud.client.enduseraction.Flags;
import com.googlecode.fspotcloud.client.enduseraction.application.handler.*;
import com.googlecode.fspotcloud.client.place.*;
import com.googlecode.fspotcloud.keyboardaction.*;

import static com.googlecode.fspotcloud.keyboardaction.FlagsRule.needing;
import static com.googlecode.fspotcloud.keyboardaction.KeyStroke.*;

public class ApplicationBinder extends AbstractBinder {


    private final AboutHandlerFactory aboutHandlerFactory;
    private final DashboardHandler dashboardHandler;
    private final LoginHandler loginHandler;
    private final LogoutHandler logoutHandler;
    private final ZoomInHandler zoomInHandler;
    private final ZoomOutHandler zoomOutHandler;
    private final HelpActionsFactory helpActionsFactory;
    private final UsersHelpHandler usersHelpHandler;
    private final ApplicationActions actions;

    @Inject
    public ApplicationBinder(AboutHandlerFactory handlerFactory,
                             DashboardHandler dashboardHandler,
                             LoginHandler loginHandler,
                             LogoutHandler logoutHandler,
                             ZoomInHandler zoomInHandler,
                             ZoomOutHandler zoomOutHandler,
                             CategoryDef categoryDef,
                             HelpActionsFactory helpActionsFactory,
                             UsersHelpHandler usersHelpHandler,
                             ApplicationActions actions) {
        super(categoryDef.APPLICATION);
        this.aboutHandlerFactory = handlerFactory;
        this.dashboardHandler = dashboardHandler;
        this.loginHandler = loginHandler;
        this.logoutHandler = logoutHandler;
        this.zoomInHandler = zoomInHandler;
        this.zoomOutHandler = zoomOutHandler;
        this.helpActionsFactory = helpActionsFactory;
        this.usersHelpHandler = usersHelpHandler;
        this.actions = actions;
    }


    @Override
    public void build() {
        Relevance helpBinding = new Relevance(BasePlace.class, SlideshowPlace.class)
                .addDefaultKeys(new KeyStroke('H'), shift(KeyStroke.KEY_FORWARD_SLASH));
        bind(actions.show_help, usersHelpHandler, helpBinding);

        Relevance hideHelpBinding = new Relevance().addDefaultKeys(new KeyStroke(KeyCodes.KEY_ESCAPE));
        bind(actions.hide_help, helpActionsFactory.getCloseHelp(), hideHelpBinding);

        Relevance aboutBinding = new Relevance(BasePlace.class, HomePlace
                .class).addDefaultKeys(new KeyStroke('A'));
        bind(actions.about, aboutHandlerFactory.getAboutHandler(), aboutBinding);

        FlagsRule admin = new FlagsRule().needs(Flags.ADMIN.name());
        Relevance binding = new Relevance(admin, BasePlace.class).addDefaultKeys(new KeyStroke('D'))
                .addRule(ManageGroupsPlace.class, admin, alt('D'), ESC)
                .addRule(ManageUsersPlace.class, admin, alt('D'))
                .addRule(TagApprovalPlace.class, admin,  ESC);

        bind(actions.dashboard, dashboardHandler, binding);
        binding = new Relevance(FlagsRule.excluding(Flags.LOGGED_ON.name()), BasePlace.class)
                .addDefaultKeys(KeyStroke.N);
        bind(actions.login, loginHandler, binding);
        binding = new Relevance(needing(Flags.LOGGED_ON.name()),
                BasePlace.class,
                LoginPlace.class,
                DashboardPlace.class,
                UserAccountPlace.class).addDefaultKeys(new KeyStroke('O'));
        bind(actions.logout, logoutHandler, binding);
        bind(actions.zoom_in, zoomInHandler, get(KeyStroke.KEY_NUM_PAD_PLUS));
        bind(actions.zoom_out, zoomOutHandler, get(KeyStroke.KEY_NUM_PAD_MINUS));

        //these action's handlers have such heavy dependencies, they are bound later,
        //in UserActionFactoryBinder (out of good names ..)
        binding = new Relevance(needing(Flags.TREE_FOCUS.name()),
                BasePlace.class).addDefaultKeys(KeyStroke.F, KeyStroke.ESC);

        configBuilder.register(category, actions.hide_controls, binding);
        configBuilder.register(category, actions.demo, get('7'));
        binding = new Relevance(FlagsRule.excluding(Flags.TREE_FOCUS.name()),
                BasePlace.class).addDefaultKeys(KeyStroke.ENTER, KeyStroke.ESC);
        configBuilder.register(category, actions.tree_focus, binding);
        configBuilder.register(category, actions.reloadTree, get('R'));
        binding = new Relevance(BasePlace.class, LoginPlace.class, DashboardPlace.class).addDefaultKeys(alt('L'));

        configBuilder.register(category, actions.goToLatest, binding);
        binding = new Relevance().addDefaultKeys(alt(KEY_FORWARD_SLASH));
        configBuilder.register(category, actions.show_shortcuts, binding);
        configBuilder.register(category, actions.toggleAutoHide, get('9'));
    }



    private Relevance get(int characterCode) {
        return new Relevance(BasePlace.class).addDefaultKeys(new KeyStroke(characterCode));
    }

    private Relevance get(char characterCode) {
        return new Relevance(BasePlace.class).addDefaultKeys(new KeyStroke(characterCode));
    }


}
