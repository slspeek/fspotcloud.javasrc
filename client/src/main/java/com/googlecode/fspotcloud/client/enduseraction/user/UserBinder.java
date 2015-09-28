package com.googlecode.fspotcloud.client.enduseraction.user;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.enduseraction.AbstractBinder;
import com.googlecode.fspotcloud.client.enduseraction.CategoryDef;
import com.googlecode.fspotcloud.client.enduseraction.Flags;
import com.googlecode.fspotcloud.client.place.*;
import com.googlecode.fspotcloud.keyboardaction.KeyStroke;
import com.googlecode.fspotcloud.keyboardaction.Relevance;

import static com.googlecode.fspotcloud.keyboardaction.FlagsRule.excluding;
import static com.googlecode.fspotcloud.keyboardaction.KeyStroke.alt;

public class UserBinder extends AbstractBinder {

	private final UserActions actions;

	@Inject
	public UserBinder(CategoryDef categoryDef, UserActions actions) {
		super(categoryDef.USER);
		this.actions = actions;
	}

	@Override
	public void build() {
		Relevance binding = new Relevance(excluding(Flags.LOGGED_ON.name()),
				LoginPlace.class, BasePlace.class).addDefaultKeys(alt('N'),
				alt('G'));
		configBuilder.register(category, actions.otherLogin, binding);
		binding = new Relevance(BasePlace.class, LoginPlace.class)
				.addDefaultKeys(KeyStroke.alt('S'));
		configBuilder.register(category, actions.goSignUp, binding);
		binding = new Relevance(BasePlace.class, LoginPlace.class)
				.addDefaultKeys(KeyStroke.alt('R'));
		configBuilder.register(category, actions.goResetPassword, binding);
		binding = new Relevance(BasePlace.class, LoginPlace.class)
				.addDefaultKeys(KeyStroke.alt('C'));
		configBuilder.register(category, actions.goResendConfirmation, binding);
		binding = new Relevance(LoginPlace.class).addDefaultKeys(
				KeyStroke.alt('Z'), KeyStroke.alt('S'));
		configBuilder.register(category, actions.doLogin, binding);
		binding = new Relevance(MailFullsizePlace.class)
				.addDefaultKeys(KeyStroke.M);
		configBuilder.register(category, actions.doMailFullsize, binding);
		binding = new Relevance().addDefaultKeys(alt('P'));
		configBuilder.register(category, actions.goAccountPage, binding);
		binding = new Relevance(UserAccountPlace.class)
				.addDefaultKeys(alt('S'));
		configBuilder.register(category, actions.doChangePassword, binding);
		binding = new Relevance(SendConfirmationPlace.class)
				.addDefaultKeys(alt('S'));
		configBuilder.register(category, actions.doSendEmailConfirmation,
				binding);
		binding = new Relevance(SendPasswordResetPlace.class).addDefaultKeys(
				alt('S'), alt('R'));
		configBuilder.register(category, actions.doRequestPasswordReset,
				binding);
		binding = new Relevance(ChangePasswordPlace.class).addDefaultKeys(
				alt('S'), alt('R'));
		configBuilder.register(category, actions.doPasswordReset, binding);
		binding = new Relevance(SignUpPlace.class).addDefaultKeys(alt('S'));
		configBuilder.register(category, actions.doSignUp, binding);
	}
}
