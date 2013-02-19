package com.googlecode.fspotcloud.client.useraction.usergroup;

import com.google.common.annotations.GwtCompatible;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.useraction.AbstractBinder;
import com.googlecode.fspotcloud.client.useraction.CategoryDef;
import com.googlecode.fspotcloud.client.useraction.Modes;
import com.googlecode.fspotcloud.keyboardaction.KeyStroke;
import com.googlecode.fspotcloud.keyboardaction.KeyboardBinding;

@GwtCompatible
public class UsergroupBinder extends AbstractBinder {

    private final UsergroupActions actions;


    @Inject
    public UsergroupBinder(CategoryDef categoryDef,
                           UsergroupActions actions) {
        super(categoryDef.USERGROUP);
        this.actions = actions;
    }


    @Override
    public void build() {
        configBuilder.register(category, actions.manageUsers, get('U'));
        configBuilder.register(category, actions.newUsergroup, get('C'));
        configBuilder.register(category, actions.deleteUsergroup, get(KeyCodes.KEY_DELETE));
        configBuilder.register(category, actions.editUsergroup, get('E'));
        configBuilder.register(category, actions.usergroupTableFocus, get(KeyCodes.KEY_ENTER));
        KeyboardBinding binding = KeyboardBinding.bind(KeyStroke.alt('S')).withDefaultModes(Modes.EDIT_GROUP);
        configBuilder.register(category, actions.saveUsergoup, binding);
    }



    private KeyboardBinding get(int characterCode) {
        return KeyboardBinding.bind(new KeyStroke(characterCode)).withDefaultModes(Modes.MANAGE_GROUPS);
    }

    private KeyboardBinding get(char characterCode) {
        return KeyboardBinding.bind(new KeyStroke(characterCode)).withDefaultModes(Modes.MANAGE_GROUPS);
    }

}
