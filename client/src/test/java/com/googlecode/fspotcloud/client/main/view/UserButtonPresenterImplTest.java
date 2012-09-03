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

import com.google.gwt.resources.client.ImageResource;
import com.googlecode.fspotcloud.client.main.view.api.UserButtonView;
import com.googlecode.fspotcloud.client.main.view.api.UserButtonViewFactory;
import com.googlecode.fspotcloud.client.view.action.KeyStroke;
import com.googlecode.fspotcloud.client.view.action.api.UserAction;
import junit.framework.TestCase;
import org.jmock.Expectations;
import org.jmock.Mockery;


public class UserButtonPresenterImplTest extends TestCase {
    Mockery context;
    UserAction action;
    UserButtonView view;
    UserButtonViewFactory viewFactory;
    UserButtonView.UserButtonPresenter presenter;
    ImageResource fakeImage;

    @Override
    protected void setUp() throws Exception {
        context = new Mockery();
        action = context.mock(UserAction.class);
        view = context.mock(UserButtonView.class);
        viewFactory = context.mock(UserButtonViewFactory.class);
        fakeImage = context.mock(ImageResource.class);
        super.setUp();
    }

    public void testConstructor() {
        presenter = new UserButtonPresenterImpl(action, viewFactory);
        context.assertIsSatisfied();
    }

    public void testInitWithoutIcon() {
        testConstructor();
        context.checking(new Expectations() {

            {
                oneOf(viewFactory).get(action);
                will(returnValue(view));
                oneOf(action).getIcon();
                will(returnValue(null));
                exactly(2).of(action).getCaption();
                will(returnValue("Ape"));
                oneOf(view).setCaption("Ape");
                oneOf(view).setTooltip("Ape ( A )");
                oneOf(view).setPresenter(presenter);
                oneOf(action).getId();
                will(returnValue("ID"));
                oneOf(action).getKey();
                will(returnValue(new KeyStroke('A')));
                oneOf(action).getAlternateKey();
                will(returnValue(null));
                oneOf(view).setDebugId("ID");
            }
        });
        presenter.init();
        context.assertIsSatisfied();
    }

    public void testInitWithIcon() {
        testConstructor();
        context.checking(new Expectations() {

            {
                oneOf(viewFactory).get(action);
                will(returnValue(view));
                oneOf(action).getIcon();
                will(returnValue(fakeImage));
                oneOf(view).setTooltip("Ape ( A )");
                exactly(2).of(action).getCaption();
                will(returnValue("Ape"));
                oneOf(view).setPresenter(presenter);
                oneOf(action).getId();
                will(returnValue("ID"));
                oneOf(action).getKey();
                will(returnValue(new KeyStroke('A')));
                oneOf(action).getAlternateKey();
                will(returnValue(null));
                oneOf(view).setDebugId("ID");
            }
        });
        presenter.init();
        context.assertIsSatisfied();
    }

    public void testButtonClicked() {
        testConstructor();
        context.checking(new Expectations() {

            {
                oneOf(action).run();
            }
        });
        presenter.buttonClicked();
        context.assertIsSatisfied();
    }
}
