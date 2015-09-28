package com.googlecode.fspotcloud.client.enduseraction.dashboard;

import com.google.gwt.event.dom.client.KeyCodes;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.enduseraction.AbstractBinder;
import com.googlecode.fspotcloud.client.enduseraction.CategoryDef;
import com.googlecode.fspotcloud.client.enduseraction.Flags;
import com.googlecode.fspotcloud.client.place.*;
import com.googlecode.fspotcloud.keyboardaction.FlagsRule;
import com.googlecode.fspotcloud.keyboardaction.KeyStroke;
import com.googlecode.fspotcloud.keyboardaction.Modifiers;
import com.googlecode.fspotcloud.keyboardaction.Relevance;

public class DashboardBinder extends AbstractBinder {

	private final DashboardActions actions;

	@Inject
	public DashboardBinder(CategoryDef categoryDef, DashboardActions actions) {
		super(categoryDef.DASHBOARD);
		this.actions = actions;
	}

	@Override
	public void build() {
		configBuilder.register(category, actions.reloadTree, get('R'));
		Relevance binding = new Relevance(DashboardPlace.class)
				.addDefaultKeys(KeyStroke.F)
				.addRule(MailFullsizePlace.class, KeyStroke.ESC)
				.addRule(LoginPlace.class, KeyStroke.ESC)
				.addRule(UserAccountPlace.class, KeyStroke.ESC)
				.addRule(SendPasswordResetPlace.class, KeyStroke.ESC)
				.addRule(SendConfirmationPlace.class, KeyStroke.ESC)
				.addRule(SignUpPlace.class, KeyStroke.ESC)
				.addRule(ChangePasswordPlace.class, KeyStroke.ESC);
		configBuilder.register(category, actions.toPhotos, binding);
		binding = new Relevance(DashboardPlace.class, TagApprovalPlace.class)
				.addDefaultKeys(new KeyStroke('M'))

				.addRule(EditUserGroupPlace.class, KeyStroke.ESC)
				.addRule(ManageUsersPlace.class,
						new KeyStroke(KeyCodes.KEY_ESCAPE), KeyStroke.alt('M'));
		//.addRule(Modes.MANAGE_USERS_NO_INPUT, new KeyStroke(KeyCodes.KEY_ESCAPE), KeyStroke.M);
		configBuilder.register(category, actions.manageGroups, binding);
		final KeyStroke SHIFT_CTRL_ALT_R = new KeyStroke(new Modifiers(true,
				true, true), 'R');
		binding = new Relevance(DashboardPlace.class)
				.addDefaultKeys(SHIFT_CTRL_ALT_R);
		configBuilder.register(category, actions.deleteAll, binding);
		configBuilder.register(category, actions.synchronize, get('S'));

		configBuilder.register(category, actions.deleteCommands, new Relevance(
				DashboardPlace.class).addDefaultKeys(new KeyStroke(
				Modifiers.CTRL, 'C')));

		configBuilder.register(category, actions.importTag, get('I'));
		configBuilder.register(category, actions.manageAccess, get('A'));
		configBuilder.register(category, actions.focusTree,
				get(KeyCodes.KEY_ENTER));
		binding = new Relevance(FlagsRule.excluding(Flags.TEXT_INPUT.name()),
				DashboardPlace.class, TagApprovalPlace.class,
				ManageUsersPlace.class, ManageGroupsPlace.class)
				.addDefaultKeys(KeyStroke.H);
		configBuilder.register(category, actions.adminHelp, binding);
	}

	private Relevance get(int characterCode) {
		return new Relevance(DashboardPlace.class)
				.addDefaultKeys(new KeyStroke(characterCode));
	}

	private Relevance get(char characterCode) {
		return new Relevance(DashboardPlace.class)
				.addDefaultKeys(new KeyStroke(characterCode));
	}

}
