package com.googlecode.fspotcloud.client.enduseraction.dashboard;

import com.google.gwt.event.dom.client.KeyCodes;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.enduseraction.AbstractBinder;
import com.googlecode.fspotcloud.client.enduseraction.CategoryDef;
import com.googlecode.fspotcloud.client.enduseraction.Modes;
import com.googlecode.fspotcloud.keyboardaction.KeyStroke;
import com.googlecode.fspotcloud.keyboardaction.KeyboardBinding;
import com.googlecode.fspotcloud.keyboardaction.Modifiers;

public class DashboardBinder extends AbstractBinder {

    private final DashboardActions actions;


    @Inject
    public DashboardBinder(CategoryDef categoryDef,
                           DashboardActions actions) {
        super(categoryDef.DASHBOARD);
        this.actions = actions;
    }


    @Override
    public void build() {
        configBuilder.register(category, actions.reloadTree, get('R'));
        configBuilder.register(category, actions.toPhotos, get('F'));
        KeyboardBinding binding = KeyboardBinding.bind(new KeyStroke('M'))
                .withDefaultModes(Modes.DASHBOARD, Modes.TAG_ACCESS)
                .override(Modes.EDIT_GROUP, KeyStroke.ESC)
                .override(Modes.MANAGE_USERS, new KeyStroke(KeyCodes.KEY_ESCAPE), KeyStroke.alt('M'))
                .override(Modes.MANAGE_USERS_NO_INPUT, new KeyStroke(KeyCodes.KEY_ESCAPE), KeyStroke.M);
        configBuilder.register(category, actions.manageGroups, binding);
        final KeyStroke SHIFT_CTRL_ALT_R = new KeyStroke(new Modifiers(true, true, true), 'R');
        binding = KeyboardBinding.bind(SHIFT_CTRL_ALT_R).withDefaultModes(Modes.DASHBOARD);
        configBuilder.register(category, actions.deleteAll, binding);
        configBuilder.register(category, actions.synchronize, get('S'));

        configBuilder.register(category, actions.deleteCommands,
                KeyboardBinding.bind(new KeyStroke(Modifiers.CTRL, 'C')).withDefaultModes(Modes.DASHBOARD));

        configBuilder.register(category, actions.importTag, get('I'));
        configBuilder.register(category, actions.manageAccess, get('A'));
        configBuilder.register(category, actions.focusTree, get(KeyCodes.KEY_ENTER));
    }


    private KeyboardBinding get(int characterCode) {
        return KeyboardBinding.bind(new KeyStroke(characterCode)).withDefaultModes(Modes.DASHBOARD);
    }

    private KeyboardBinding get(char characterCode) {
        return KeyboardBinding.bind(new KeyStroke(characterCode)).withDefaultModes(Modes.DASHBOARD);
    }

}
