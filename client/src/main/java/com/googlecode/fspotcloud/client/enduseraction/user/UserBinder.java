package com.googlecode.fspotcloud.client.enduseraction.user;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.enduseraction.AbstractBinder;
import com.googlecode.fspotcloud.client.enduseraction.CategoryDef;
import com.googlecode.fspotcloud.client.enduseraction.Modes;
import com.googlecode.fspotcloud.keyboardaction.KeyStroke;
import com.googlecode.fspotcloud.keyboardaction.KeyboardBinding;

import static com.googlecode.fspotcloud.keyboardaction.KeyStroke.alt;
import static com.googlecode.fspotcloud.keyboardaction.KeyStroke.ctrl;

public class UserBinder extends AbstractBinder {

    private final UserActions actions;


    @Inject
    public UserBinder(CategoryDef categoryDef,
                      UserActions actions) {
        super(categoryDef.USER);
        this.actions = actions;
    }


    @Override
    public void build() {
        KeyboardBinding binding = KeyboardBinding.bind(alt('N'), alt('G')).withDefaultModes(Modes.LOGIN, Modes.TAG_VIEW);
        configBuilder.register(category, actions.otherLogin, binding);
        binding = KeyboardBinding.bind(KeyStroke.alt('S')).withDefaultModes(Modes.TAG_VIEW, Modes.TREE_VIEW, Modes.LOGIN);
        configBuilder.register(category, actions.goSignUp, binding);
        binding = KeyboardBinding.bind(KeyStroke.alt('R')).withDefaultModes(Modes.TAG_VIEW, Modes.TREE_VIEW, Modes.LOGIN);
        configBuilder.register(category, actions.goResetPassword, binding);
        binding = KeyboardBinding.bind(KeyStroke.alt('C')).withDefaultModes(Modes.TAG_VIEW, Modes.TREE_VIEW, Modes.LOGIN);
        configBuilder.register(category, actions.goResendConfirmation, binding);
        binding = KeyboardBinding.bind(KeyStroke.alt('Z')).withDefaultModes(Modes.LOGIN);
        configBuilder.register(category, actions.doLogin, binding);


    }


    private KeyboardBinding get(int characterCode) {
        return KeyboardBinding.bind(ctrl(characterCode)).withDefaultModes(Modes.LOGIN, Modes.TAG_VIEW);
    }

    private KeyboardBinding get(char characterCode) {
        return KeyboardBinding.bind(ctrl(characterCode)).withDefaultModes(Modes.LOGIN, Modes.TAG_VIEW);
    }


}
