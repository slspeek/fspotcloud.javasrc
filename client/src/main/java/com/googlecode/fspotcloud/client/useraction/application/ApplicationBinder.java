package com.googlecode.fspotcloud.client.useraction.application;

import com.google.gwt.event.dom.client.KeyCodes;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.useraction.AbstractBinder;
import com.googlecode.fspotcloud.client.useraction.CategoryDef;
import com.googlecode.fspotcloud.client.useraction.Modes;
import com.googlecode.fspotcloud.client.useraction.application.handler.*;
import com.googlecode.fspotcloud.keyboardaction.HelpActionsFactory;
import com.googlecode.fspotcloud.keyboardaction.HelpConfig;
import com.googlecode.fspotcloud.keyboardaction.KeyStroke;
import com.googlecode.fspotcloud.keyboardaction.KeyboardBinding;

public class ApplicationBinder extends AbstractBinder {


    private final AboutHandler aboutHandler;
    private final DashboardHandler dashboardHandler;
    private final LoginHandler loginHandler;
    private final LogoutHandler logoutHandler;
    private final ZoomInHandler zoomInHandler;
    private final ZoomOutHandler zoomOutHandler;
    private final HelpActionsFactory helpActionsFactory;
    private final CategoryDef categoryDef;

    @Inject
    public ApplicationBinder(AboutHandler aboutHandler,
                             DashboardHandler dashboardHandler,
                             LoginHandler loginHandler,
                             LogoutHandler logoutHandler,
                             ZoomInHandler zoomInHandler,
                             ZoomOutHandler zoomOutHandler,
                             CategoryDef categoryDef,
                             HelpActionsFactory helpActionsFactory) {
        super(categoryDef.APPLICATION);
        this.aboutHandler = aboutHandler;
        this.dashboardHandler = dashboardHandler;
        this.loginHandler = loginHandler;
        this.logoutHandler = logoutHandler;
        this.zoomInHandler = zoomInHandler;
        this.zoomOutHandler = zoomOutHandler;
        this.categoryDef = categoryDef;
        this.helpActionsFactory = helpActionsFactory;
    }


    @Override
    public void build() {
        KeyboardBinding helpBinding = KeyboardBinding.bind(new KeyStroke('H')).withDefaultModes(Modes.TAG_VIEW, Modes.TREE_VIEW, Modes.SLIDESHOW);
        final HelpConfig helpConfig = new HelpConfig("800px", "800px", "Keyboard help");
        helpConfig.addToFirstColumn(categoryDef.NAVIGATION, categoryDef.RASTER);
        helpConfig.addToSecondColumn(categoryDef.APPLICATION, categoryDef.SLIDESHOW);
        bind(ApplicationActions.SHOW_HELP, helpActionsFactory.getHelpAction(helpConfig), helpBinding);


        KeyboardBinding hideHelpBinding = KeyboardBinding.bind(new KeyStroke(KeyCodes.KEY_ESCAPE)).withDefaultModes(Modes.TAG_VIEW, Modes.TREE_VIEW, Modes.SLIDESHOW);
        bind(ApplicationActions.HIDE_HELP, helpActionsFactory.getCloseHelp(), hideHelpBinding);

        KeyboardBinding aboutBinding = KeyboardBinding.bind(new KeyStroke('A')).withDefaultModes(Modes.TAG_VIEW, Modes.TREE_VIEW);
        bind(ApplicationActions.ABOUT, aboutHandler, aboutBinding);

        KeyboardBinding dashboardBinding = KeyboardBinding.bind(new KeyStroke('D')).withDefaultModes(Modes.TAG_VIEW, Modes.TREE_VIEW);
        bind(ApplicationActions.DASHBOARD, dashboardHandler, dashboardBinding);

        KeyboardBinding hideControlsBinding = KeyboardBinding.bind(new KeyStroke('F')).withDefaultModes(Modes.TAG_VIEW, Modes.TREE_VIEW);
        configBuilder.register(category, ApplicationActions.HIDE_CONTROLS, hideControlsBinding);

        KeyboardBinding loginBinding = KeyboardBinding.bind(new KeyStroke('N')).withDefaultModes(Modes.TAG_VIEW, Modes.TREE_VIEW);
        bind(ApplicationActions.LOGIN, loginHandler, loginBinding);

        KeyboardBinding logoutBinding = KeyboardBinding.bind(new KeyStroke('O')).withDefaultModes(Modes.TAG_VIEW, Modes.TREE_VIEW);
        bind(ApplicationActions.LOGOUT, logoutHandler, logoutBinding);

        KeyboardBinding treeFocusBinding = KeyboardBinding.bind(new KeyStroke(KeyCodes.KEY_ENTER)).withDefaultModes(Modes.TAG_VIEW, Modes.TREE_VIEW);
        configBuilder.register(category, ApplicationActions.TREE_FOCUS, treeFocusBinding);

        KeyboardBinding zoomInBinding = KeyboardBinding.bind(new KeyStroke(KeyStroke.KEY_NUM_PAD_PLUS)).withDefaultModes(Modes.TAG_VIEW, Modes.TREE_VIEW);
        bind(ApplicationActions.ZOOM_IN, zoomInHandler, zoomInBinding);

        KeyboardBinding zoomOutBinding = KeyboardBinding.bind(new KeyStroke(KeyStroke.KEY_NUM_PAD_MINUS)).withDefaultModes(Modes.TAG_VIEW, Modes.TREE_VIEW);
        bind(ApplicationActions.ZOOM_OUT, zoomOutHandler, zoomOutBinding);
    }

}
