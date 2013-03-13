package com.googlecode.fspotcloud.client.enduseraction.group;

import com.google.common.annotations.GwtCompatible;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.enduseraction.AbstractBinder;
import com.googlecode.fspotcloud.client.enduseraction.CategoryDef;
import com.googlecode.fspotcloud.client.place.EditUserGroupPlace;
import com.googlecode.fspotcloud.client.place.ManageUserGroupsPlace;
import com.googlecode.fspotcloud.client.place.ManageUsersPlace;
import com.googlecode.fspotcloud.client.place.TagApprovalPlace;
import com.googlecode.fspotcloud.keyboardaction.KeyStroke;
import com.googlecode.fspotcloud.keyboardaction.Relevance;

import static com.googlecode.fspotcloud.keyboardaction.KeyStroke.alt;

@GwtCompatible
public class GroupBinder extends AbstractBinder {

    private final GroupActions actions;


    @Inject
    public GroupBinder(CategoryDef categoryDef,
                       GroupActions actions) {
        super(categoryDef.USERGROUP);
        this.actions = actions;
    }

    @Override
    public void build() {
        configBuilder.register(category, actions.manageUsers, get('M'));
        configBuilder.register(category, actions.newUsergroup, get('C'));
        Relevance binding = new Relevance(ManageUserGroupsPlace.class)
                .addDefaultKeys(KeyStroke.X, KeyStroke.DELETE);
        configBuilder.register(category, actions.deleteUsergroup, binding);
        configBuilder.register(category, actions.editUsergroup, get('E'));
        configBuilder.register(category, actions.focusGroupTable, get(KeyCodes.KEY_ENTER));
        binding = new Relevance(EditUserGroupPlace.class).addDefaultKeys(alt('S'));
        configBuilder.register(category, actions.saveGroup, binding);
        binding = new Relevance(ManageUsersPlace.class).addDefaultKeys(alt('S'), alt('A'), KeyStroke.ENTER);

        //.addRule(Modes.MANAGE_USERS_NO_INPUT, KeyStroke.A, KeyStroke.INSERT);
        configBuilder.register(category, actions.addUser, binding);
        binding = new Relevance(ManageUsersPlace.class).addDefaultKeys(alt('X'), alt(KeyCodes.KEY_DELETE));

        //.addRule(Modes.MANAGE_USERS_NO_INPUT, KeyStroke.DELETE, KeyStroke.X);
        configBuilder.register(category, actions.removeUser, binding);
        binding = new Relevance(ManageUsersPlace.class).addDefaultKeys(alt(KeyCodes.KEY_ENTER));
        //.addRule(Modes.MANAGE_USERS_NO_INPUT, KeyStroke.ENTER);
        configBuilder.register(category, actions.focusUserTable, binding);
        binding = new Relevance(ManageUsersPlace.class).addDefaultKeys(alt('Z'));
        //.addRule(Modes.MANAGE_USERS_NO_INPUT, KeyStroke.I);
        configBuilder.register(category, actions.focusEmailField, binding);

        binding = new Relevance(TagApprovalPlace.class).addDefaultKeys(KeyStroke.J);
        configBuilder.register(category, actions.focusGrantedTable, binding);
        binding = new Relevance(TagApprovalPlace.class).addDefaultKeys(KeyStroke.K);
        configBuilder.register(category, actions.focusRevokedTable, binding);
        binding = new Relevance(TagApprovalPlace.class).addDefaultKeys(KeyStroke.I, KeyStroke.INSERT);
        configBuilder.register(category, actions.grantGroup, binding);
        binding = new Relevance(TagApprovalPlace.class).addDefaultKeys(KeyStroke.DELETE, KeyStroke.X);
        configBuilder.register(category, actions.revokeGroup, binding);


    }

    private Relevance get(int characterCode) {
        return new Relevance(ManageUserGroupsPlace.class).addDefaultKeys(new KeyStroke(characterCode));
    }

    private Relevance get(char characterCode) {
        return new Relevance(ManageUserGroupsPlace.class).addDefaultKeys(new KeyStroke(characterCode));
    }

}
