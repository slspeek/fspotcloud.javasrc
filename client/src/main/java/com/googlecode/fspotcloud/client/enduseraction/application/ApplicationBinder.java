package com.googlecode.fspotcloud.client.enduseraction.application;

import com.google.gwt.event.dom.client.KeyCodes;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.enduseraction.AbstractBinder;
import com.googlecode.fspotcloud.client.enduseraction.CategoryDef;
import com.googlecode.fspotcloud.client.enduseraction.application.handler.*;
import com.googlecode.fspotcloud.client.place.*;
import com.googlecode.fspotcloud.keyboardaction.*;

import static com.googlecode.fspotcloud.keyboardaction.KeyStroke.*;

public class ApplicationBinder extends AbstractBinder {


    private final AboutHandlerFactory aboutHandlerFactory;
    private final DashboardHandler dashboardHandler;
    private final LoginHandler loginHandler;
    private final LogoutHandler logoutHandler;
    private final ZoomInHandler zoomInHandler;
    private final ZoomOutHandler zoomOutHandler;
    private final HelpActionsFactory helpActionsFactory;
    private final CategoryDef categoryDef;
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
                             ApplicationActions actions) {
        super(categoryDef.APPLICATION);
        this.aboutHandlerFactory = handlerFactory;
        this.dashboardHandler = dashboardHandler;
        this.loginHandler = loginHandler;
        this.logoutHandler = logoutHandler;
        this.zoomInHandler = zoomInHandler;
        this.zoomOutHandler = zoomOutHandler;
        this.categoryDef = categoryDef;
        this.helpActionsFactory = helpActionsFactory;
        this.actions = actions;
    }


    @Override
    public void build() {
        Relevance helpBinding = new Relevance(BasePlace.class, SlideshowPlace.class)
                .addDefaultKeys(new KeyStroke('H'));
        bind(actions.show_help, getHelpHandler(), helpBinding);

        Relevance hideHelpBinding = new Relevance().addDefaultKeys(new KeyStroke(KeyCodes.KEY_ESCAPE));
        bind(actions.hide_help, helpActionsFactory.getCloseHelp(), hideHelpBinding);

        Relevance aboutBinding = new Relevance().addDefaultKeys(new KeyStroke('A'));
        bind(actions.about, aboutHandlerFactory.getAboutHandler(), aboutBinding);

        Relevance binding = new Relevance(BasePlace.class).addDefaultKeys(new KeyStroke('D'))
                .addRule(ManageUserGroupsPlace.class, alt('D'), new KeyStroke(KeyCodes.KEY_ESCAPE))
                .addRule(ManageUsersPlace.class, alt('D'))
                .addRule(TagApprovalPlace.class, ESC);

        bind(actions.dashboard, dashboardHandler, binding);
        bind(actions.login, loginHandler, get('N'));
        binding = new Relevance(BasePlace.class, LoginPlace.class, TagPlace.class).addDefaultKeys(new KeyStroke('O'));
        bind(actions.logout, logoutHandler, binding);
        bind(actions.zoom_in, zoomInHandler, get(KeyStroke.KEY_NUM_PAD_PLUS));
        bind(actions.zoom_out, zoomOutHandler, get(KeyStroke.KEY_NUM_PAD_MINUS));

        //these action's handlers have such heavy dependencies, they are bound later,
        //in UserActionFactoryBinder (out of good names ..)
        configBuilder.register(category, actions.hide_controls, get('F'));
        configBuilder.register(category, actions.demo, get('7'));
        configBuilder.register(category, actions.tree_focus, get(KeyCodes.KEY_ENTER));
        configBuilder.register(category, actions.reloadTree, get('R'));
        binding = new Relevance(BasePlace.class, LoginPlace.class, TagPlace.class).addDefaultKeys(alt('L'));

        configBuilder.register(category, actions.goToLatest, binding);
        binding = new Relevance().addDefaultKeys(alt(KEY_FORWARD_SLASH));
        configBuilder.register(category, actions.show_shortcuts, binding);
        configBuilder.register(category, actions.toggleAutoHide, get('9'));
    }

    private IActionHandler getHelpHandler() {
        final HelpConfig helpConfig = new HelpConfig("Keyboard help");
        helpConfig.addToFirstColumn(categoryDef.NAVIGATION, categoryDef.RASTER);
        helpConfig.addToSecondColumn(categoryDef.APPLICATION, categoryDef.SLIDESHOW);
        return helpActionsFactory.getHelpAction(helpConfig);
    }

    private Relevance get(int characterCode) {
        return new Relevance(BasePlace.class).addDefaultKeys(new KeyStroke(characterCode));
    }

    private Relevance get(char characterCode) {
        return new Relevance(BasePlace.class).addDefaultKeys(new KeyStroke(characterCode));
    }


}
