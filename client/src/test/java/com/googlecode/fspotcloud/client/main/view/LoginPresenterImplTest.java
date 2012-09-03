/*
 * Copyright 2010-2012 Steven L. Speek.
 * This program is free software; you can redistribute it
                and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free
                Software Foundation; either version 2
 * of the License, or any later version.
 *
 * This program is
                distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied
                warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public
                License for more details.
 * You should have received a copy of the GNU General Public License
 * along
                with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330,
                Boston, MA 02111-1307, USA.
 *
 */

package com.googlecode.fspotcloud.client.main.view;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.googlecode.fspotcloud.client.main.view.api.LoginView;
import com.googlecode.fspotcloud.shared.main.AuthenticationAction;
import com.googlecode.fspotcloud.shared.main.AuthenticationResult;
import com.googlecode.fspotcloud.shared.main.GetUserInfo;
import com.googlecode.fspotcloud.shared.main.UserInfo;
import net.customware.gwt.dispatch.client.DispatchAsync;
import net.customware.gwt.dispatch.shared.Action;
import net.customware.gwt.dispatch.shared.Result;
import org.jukito.JukitoRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;

import javax.inject.Inject;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(JukitoRunner.class)
public class LoginPresenterImplTest {
    public static final String SECRET = "Secret";
    public static final String ADMIN = "Admin";
    @Inject
    LoginPresenterImpl presenter;

    @Before
    public void train(LoginView loginView) throws Exception {
        when(loginView.getPasswordField()).thenReturn(SECRET);
        when(loginView.getUserNameField()).thenReturn(ADMIN);
    }

    @Test
    public void testStart(LoginView loginView, DispatchAsync dispatch,
                          AcceptsOneWidget panel, ArgumentCaptor<AsyncCallback<UserInfo>> captor,
                          ArgumentCaptor<Action> actionCaptor) throws Exception {
        presenter.start(panel, null);
        verify(dispatch).execute(actionCaptor.capture(), captor.capture());

        GetUserInfo action = (GetUserInfo) actionCaptor.getValue();
        assertEquals("post-login", action.getDestinationUrl());
        verify(loginView).setPresenter(presenter);
        verify(loginView).focusUserNameField();
        verify(panel).setWidget(loginView);
        verifyNoMoreInteractions(panel, loginView, dispatch);
    }

    @Test
    public void testOnUserFieldKeyUp(LoginView loginView,
                                     DispatchAsync dispatch, ArgumentCaptor<AsyncCallback<UserInfo>> captor,
                                     ArgumentCaptor<Action> actionCaptor) throws Exception {
        presenter.onUserFieldKeyUp(11);

        verifyNoMoreInteractions(loginView, dispatch);
        presenter.onUserFieldKeyUp(13);
        verify(loginView).focusPasswordField();
        verifyNoMoreInteractions(loginView, dispatch);
    }

    @Test
    public void testOnPasswordFieldKeyUp(LoginView loginView,
                                         DispatchAsync dispatch, ArgumentCaptor<AsyncCallback<?>> captor,
                                         ArgumentCaptor<Action> actionCaptor) throws Exception {
        presenter.onPasswordFieldKeyUp(11);
        verifyNoMoreInteractions(loginView, dispatch);
    }

    @Test
    public void testSubmitError(LoginView loginView, DispatchAsync dispatch,
                                ArgumentCaptor<AsyncCallback<? extends Result>> captor,
                                ArgumentCaptor<Action> actionCaptor) throws Exception {
        presenter.onPasswordFieldKeyUp(13);

        verify(dispatch).execute(actionCaptor.capture(), captor.capture());

        verifyActionParameters(actionCaptor);

        verify(loginView).getUserNameField();
        verify(loginView).getPasswordField();
        verifyNoMoreInteractions(dispatch, loginView);

        AsyncCallback<AuthenticationResult> callback = (AsyncCallback<AuthenticationResult>) captor.getValue();
        callback.onFailure(new RuntimeException("Boom"));
        verify(loginView)
                .setStatusText(LoginPresenterImpl.AN_ERROR_OCCURRED_MAKING_THE_AUTHENTICATION_REQUEST);

        verifyNoMoreInteractions(loginView, dispatch);
    }

    private void verifyActionParameters(ArgumentCaptor<Action> actionCaptor) {
        AuthenticationAction action = (AuthenticationAction) actionCaptor.getValue();
        assertEquals(SECRET, action.getPassword());
        assertEquals(ADMIN, action.getUserName());
    }

    @Test
    public void testSubmitFailure(LoginView loginView, DispatchAsync dispatch,
                                  ArgumentCaptor<AsyncCallback<? extends Result>> captor,
                                  ArgumentCaptor<Action> actionCaptor) throws Exception {
        presenter.onPasswordFieldKeyUp(13);
        verify(dispatch).execute(actionCaptor.capture(), captor.capture());

        verifyActionParameters(actionCaptor);

        verify(loginView).getUserNameField();
        verify(loginView).getPasswordField();
        verifyNoMoreInteractions(dispatch, loginView);

        AsyncCallback<AuthenticationResult> callback = (AsyncCallback<AuthenticationResult>) captor.getValue();
        callback.onSuccess(new AuthenticationResult(false));
        verify(loginView)
                .setStatusText(LoginPresenterImpl.NOT_A_VALID_USERNAME_AND_PASSWORD_COMBINATION);

        verifyNoMoreInteractions(loginView, dispatch);
    }

    @Test
    public void testSubmitSuccess(LoginView loginView, DispatchAsync dispatch,
                                  ArgumentCaptor<AsyncCallback<? extends Result>> captor,
                                  ArgumentCaptor<Action> actionCaptor) throws Exception {
        presenter.onPasswordFieldKeyUp(13);
        verify(dispatch).execute(actionCaptor.capture(), captor.capture());

        verifyActionParameters(actionCaptor);

        verify(loginView).getUserNameField();
        verify(loginView).getPasswordField();
        verifyNoMoreInteractions(dispatch, loginView);

        AsyncCallback<AuthenticationResult> callback = (AsyncCallback<AuthenticationResult>) captor.getValue();
        callback.onSuccess(new AuthenticationResult(true));
        verify(loginView).setStatusText(LoginPresenterImpl.LOGGED_IN);

        verifyNoMoreInteractions(loginView, dispatch);
    }
}
