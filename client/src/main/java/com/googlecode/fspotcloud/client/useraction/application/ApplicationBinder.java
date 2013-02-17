package com.googlecode.fspotcloud.client.useraction.application;

import com.google.gwt.event.dom.client.KeyCodes;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.useraction.AbstractBinder;
import com.googlecode.fspotcloud.client.useraction.CategoryDef;
import com.googlecode.fspotcloud.client.useraction.Modes;
import com.googlecode.fspotcloud.client.useraction.application.handler.*;
import com.googlecode.fspotcloud.keyboardaction.*;

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
        KeyboardBinding helpBinding = KeyboardBinding.bind(new KeyStroke('H')).withDefaultModes(Modes.TAG_VIEW, Modes.TREE_VIEW, Modes.SLIDESHOW);
        bind(actions.show_help, getHelpHandler(), helpBinding);

        KeyboardBinding hideHelpBinding = KeyboardBinding.bind(new KeyStroke(KeyCodes.KEY_ESCAPE)).withDefaultModes(Modes.TAG_VIEW, Modes.TREE_VIEW, Modes.SLIDESHOW);
        bind(actions.hide_help, helpActionsFactory.getCloseHelp(), hideHelpBinding);

        KeyboardBinding aboutBinding = KeyboardBinding.bind(new KeyStroke('A')).withDefaultModes(Modes.TAG_VIEW, Modes.TREE_VIEW, Modes.SLIDESHOW);
        bind(actions.about, aboutHandlerFactory.getAboutHandler(), aboutBinding);
        bind(actions.dashboard, dashboardHandler, get('D'));
        bind(actions.login, loginHandler, get('N'));
        bind(actions.logout, logoutHandler, get('O'));
        bind(actions.zoom_in, zoomInHandler, get(KeyStroke.KEY_NUM_PAD_PLUS));
        bind(actions.zoom_out, zoomOutHandler, get(KeyStroke.KEY_NUM_PAD_MINUS));

        //these action's handlers have such heavy dependencies, they are bound later,
        //in UserActionFactoryBinder (out of good names ..)
        configBuilder.register(category, actions.hide_controls, get('F'));
        configBuilder.register(category, actions.demo, get('7'));
        configBuilder.register(category, actions.tree_focus, get(KeyCodes.KEY_ENTER));
        configBuilder.register(category, actions.reloadTree, get('R'));
    }

    private IActionHandler getHelpHandler() {
        final HelpConfig helpConfig = new HelpConfig("Keyboard help");
        helpConfig.addToFirstColumn(categoryDef.NAVIGATION, categoryDef.RASTER);
        helpConfig.addToSecondColumn(categoryDef.APPLICATION, categoryDef.SLIDESHOW);
        return helpActionsFactory.getHelpAction(helpConfig);
    }

    private KeyboardBinding get(int characterCode) {
        return KeyboardBinding.bind(new KeyStroke(characterCode)).withDefaultModes(Modes.TAG_VIEW, Modes.TREE_VIEW);
    }

    private KeyboardBinding get(char characterCode) {
        return KeyboardBinding.bind(new KeyStroke(characterCode)).withDefaultModes(Modes.TAG_VIEW, Modes.TREE_VIEW);
    }


}
