package com.googlecode.fspotcloud.client.useraction.group;

import com.google.common.annotations.GwtCompatible;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.useraction.AbstractBinder;
import com.googlecode.fspotcloud.client.useraction.CategoryDef;
import com.googlecode.fspotcloud.client.useraction.Modes;
import com.googlecode.fspotcloud.keyboardaction.KeyStroke;
import com.googlecode.fspotcloud.keyboardaction.KeyboardBinding;

import static com.googlecode.fspotcloud.keyboardaction.KeyStroke.alt;

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
        configBuilder.register(category, actions.manageUsers, get('M'));
        configBuilder.register(category, actions.newUsergroup, get('C'));
        configBuilder.register(category, actions.deleteUsergroup, get(KeyCodes.KEY_DELETE));
        configBuilder.register(category, actions.editUsergroup, get('E'));
        configBuilder.register(category, actions.focusGroupTable, get(KeyCodes.KEY_ENTER));
        KeyboardBinding binding = KeyboardBinding.bind(alt('S')).withDefaultModes(Modes.EDIT_GROUP);
        configBuilder.register(category, actions.saveGroup, binding);

        binding = KeyboardBinding.bind(alt('S'), alt('A'))
                .withDefaultModes(Modes.MANAGE_USERS);
        configBuilder.register(category, actions.addUser, binding);
        binding = KeyboardBinding.bind(alt('R'), alt(KeyCodes.KEY_DELETE)).withDefaultModes(Modes.MANAGE_USERS);
        configBuilder.register(category, actions.removeUser, binding);
        binding = KeyboardBinding.bind(alt(KeyCodes.KEY_ENTER)).withDefaultModes(Modes.MANAGE_USERS);
        configBuilder.register(category, actions.focusUserTable, binding);
        binding = KeyboardBinding.bind(alt('Z')).withDefaultModes(Modes.MANAGE_USERS);
        configBuilder.register(category, actions.focusEmailField, binding);
    }



    private KeyboardBinding get(int characterCode) {
        return KeyboardBinding.bind(new KeyStroke(characterCode)).withDefaultModes(Modes.MANAGE_GROUPS);
    }

    private KeyboardBinding get(char characterCode) {
        return KeyboardBinding.bind(new KeyStroke(characterCode)).withDefaultModes(Modes.MANAGE_GROUPS);
    }

}
