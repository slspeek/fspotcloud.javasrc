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
import com.googlecode.fspotcloud.client.main.view.api.SignUpView;
import com.googlecode.fspotcloud.shared.main.SignUpAction;
import com.googlecode.fspotcloud.shared.main.SignUpResult;
import net.customware.gwt.dispatch.client.DispatchAsync;
import org.jukito.JukitoRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;

import javax.inject.Inject;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(JukitoRunner.class)
public class SignUpPresenterImplTest {
    public static final String SECRET = "Secret";
    public static final String ADMIN = "Admin";
    public static final String RMS_FSF_ORG = "rms@example.com";
    @Inject
    SignUpPresenterImpl presenter;

    @Before
    public void train(SignUpView signUpView) throws Exception {
        when(signUpView.getPasswordField()).thenReturn(SECRET);
        when(signUpView.getPasswordAgainField()).thenReturn(SECRET);
        when(signUpView.getEmailField()).thenReturn(RMS_FSF_ORG);
    }

    @Test
    public void testStart(SignUpView signUpView, DispatchAsync dispatch,
                          AcceptsOneWidget panel) throws Exception {
        presenter.start(panel, null);
        verify(signUpView).setPresenter(presenter);
        verify(signUpView).focusEmailField();
        verify(panel).setWidget(signUpView);
        verifyNoMoreInteractions(panel, signUpView, dispatch);
    }

    @Test
    public void testSignUp(SignUpView signUpView, DispatchAsync dispatch,
                           ArgumentCaptor<AsyncCallback<SignUpResult>> callbackArgumentCaptor,
                           ArgumentCaptor<SignUpAction> actionArgumentCaptor)
            throws Exception {
        presenter.signUp();
        verify(signUpView).getEmailField();
        verify(signUpView).getPasswordField();
        verify(signUpView).getPasswordAgainField();

        verify(dispatch)
                .execute(actionArgumentCaptor.capture(),
                        callbackArgumentCaptor.capture());

        SignUpAction action = actionArgumentCaptor.getValue();
        assertEquals(SECRET, action.getPassword());
        assertEquals(RMS_FSF_ORG, action.getEmail());
    }
}
