package com.googlecode.fspotcloud.client.enduseraction.application;

import com.google.gwt.event.dom.client.KeyCodes;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.enduseraction.AbstractBinder;
import com.googlecode.fspotcloud.client.enduseraction.CategoryDef;
import com.googlecode.fspotcloud.client.enduseraction.Modes;
import com.googlecode.fspotcloud.client.enduseraction.application.handler.*;
import com.googlecode.fspotcloud.keyboardaction.*;

import static com.googlecode.fspotcloud.keyboardaction.KeyStroke.ESC;
import static com.googlecode.fspotcloud.keyboardaction.KeyStroke.alt;

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

        KeyboardBinding binding = KeyboardBinding.bind(new KeyStroke('D'))
                .withDefaultModes(Modes.TAG_VIEW, Modes.TREE_VIEW)
                .override(Modes.MANAGE_GROUPS, alt('D'), new KeyStroke(KeyCodes.KEY_ESCAPE))
                .override(Modes.MANAGE_USERS, alt('D'))
                .override(Modes.TAG_ACCESS, ESC);

        bind(actions.dashboard, dashboardHandler, binding);
        bind(actions.login, loginHandler, get('N'));
        binding = KeyboardBinding.bind(alt('O')).withDefaultModes(Modes.TAG_VIEW, Modes.TREE_VIEW, Modes.LOGIN, Modes.DASHBOARD);
        bind(actions.logout, logoutHandler, binding);
        bind(actions.zoom_in, zoomInHandler, get(KeyStroke.KEY_NUM_PAD_PLUS));
        bind(actions.zoom_out, zoomOutHandler, get(KeyStroke.KEY_NUM_PAD_MINUS));

        //these action's handlers have such heavy dependencies, they are bound later,
        //in UserActionFactoryBinder (out of good names ..)
        configBuilder.register(category, actions.hide_controls, get('F'));
        configBuilder.register(category, actions.demo, get('7'));
        configBuilder.register(category, actions.tree_focus, get(KeyCodes.KEY_ENTER));
        configBuilder.register(category, actions.reloadTree, get('R'));
        binding = KeyboardBinding.bind(alt('L')).withDefaultModes(Modes.TAG_VIEW, Modes.TREE_VIEW, Modes.LOGIN, Modes.DASHBOARD);
        configBuilder.register(category, actions.goToLatest, binding);
        binding = KeyboardBinding.bind(alt(KeyStroke.KEY_FORWARD_SLASH)).withDefaultModes(Modes.ALL_MODES);
        configBuilder.register(category, actions.show_shortcuts, binding);
        configBuilder.register(category, actions.toggleAutoHide, get('9'));
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
