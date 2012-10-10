package com.googlecode.fspotcloud.client.useraction.application;

import com.google.gwt.event.dom.client.KeyCodes;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.useraction.AbstractBinder;
import com.googlecode.fspotcloud.client.useraction.CategoryDef;
import com.googlecode.fspotcloud.client.useraction.Modes;
import com.googlecode.fspotcloud.client.useraction.application.handler.*;
import com.googlecode.fspotcloud.keyboardaction.KeyStroke;
import com.googlecode.fspotcloud.keyboardaction.KeyboardBinding;

public class ApplicationBinder extends AbstractBinder {


    private final AboutAction aboutAction;
    private final DashboardAction dashboardAction;
    //private final HideControlsAction hideControlsAction;
    private final LoginHandler loginHandler;
    private final LogoutHandler logoutHandler;
    //private final TreeFocusAction treeFocusAction;
    private final ZoomInAction zoomInAction;
    private final ZoomOutAction zoomOutAction;

    @Inject
    public ApplicationBinder(AboutAction aboutAction,
                             DashboardAction dashboardAction,
                             /*HideControlsAction hideControlsAction,*/
                             LoginHandler loginHandler,
                             LogoutHandler logoutHandler,
                             /*TreeFocusAction treeFocusAction,*/
                             ZoomInAction zoomInAction,
                             ZoomOutAction zoomOutAction,
                             CategoryDef categoryDef) {
        super(categoryDef.APPLICATION);
        this.aboutAction = aboutAction;
        this.dashboardAction = dashboardAction;
        //this.hideControlsAction = hideControlsAction;
        this.loginHandler = loginHandler;
        this.logoutHandler = logoutHandler;
        //this.treeFocusAction = treeFocusAction;
        this.zoomInAction = zoomInAction;
        this.zoomOutAction = zoomOutAction;
    }


    @Override
    public void build() {
        KeyboardBinding aboutBinding = KeyboardBinding.bind(new KeyStroke('A')).withDefaultModes(Modes.TAG_VIEW, Modes.TREE_VIEW);
        bind(ApplicationActions.ABOUT, aboutAction, aboutBinding);

        KeyboardBinding dashboardBinding = KeyboardBinding.bind(new KeyStroke('D')).withDefaultModes(Modes.TAG_VIEW, Modes.TREE_VIEW);
        bind(ApplicationActions.DASHBOARD, dashboardAction, dashboardBinding);

        KeyboardBinding hideControlsBinding = KeyboardBinding.bind(new KeyStroke('F')).withDefaultModes(Modes.TAG_VIEW, Modes.TREE_VIEW);
       configBuilder.register(category, ApplicationActions.HIDE_CONTROLS,  hideControlsBinding);

        KeyboardBinding loginBinding = KeyboardBinding.bind(new KeyStroke('N')).withDefaultModes(Modes.TAG_VIEW, Modes.TREE_VIEW);
        bind(ApplicationActions.LOGIN, loginHandler, loginBinding);

        KeyboardBinding logoutBinding = KeyboardBinding.bind(new KeyStroke('M')).withDefaultModes(Modes.TAG_VIEW, Modes.TREE_VIEW);
        bind(ApplicationActions.LOGOUT, logoutHandler, logoutBinding);

        KeyboardBinding treeFocusBinding = KeyboardBinding.bind(new KeyStroke(KeyCodes.KEY_ENTER)).withDefaultModes(Modes.TAG_VIEW, Modes.TREE_VIEW);
        configBuilder.register(category, ApplicationActions.TREE_FOCUS, treeFocusBinding);

        KeyboardBinding zoomInBinding = KeyboardBinding.bind(new KeyStroke(KeyStroke.KEY_NUM_PAD_PLUS)).withDefaultModes(Modes.TAG_VIEW, Modes.TREE_VIEW);
        bind(ApplicationActions.ZOOM_IN, zoomInAction, zoomInBinding);

        KeyboardBinding zoomOutBinding = KeyboardBinding.bind(new KeyStroke(KeyStroke.KEY_NUM_PAD_MINUS)).withDefaultModes(Modes.TAG_VIEW, Modes.TREE_VIEW);
        bind(ApplicationActions.ZOOM_OUT, zoomOutAction, zoomOutBinding);
    }

}
