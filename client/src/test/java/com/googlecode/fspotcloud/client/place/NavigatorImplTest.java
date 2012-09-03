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

package com.googlecode.fspotcloud.client.place;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.googlecode.fspotcloud.client.data.DataManager;
import com.googlecode.fspotcloud.client.data.DataManagerImpl;
import com.googlecode.fspotcloud.client.main.DispatchAsyncTestImpl;
import com.googlecode.fspotcloud.client.place.api.Navigator;
import com.googlecode.fspotcloud.client.place.api.Navigator.Direction;
import com.googlecode.fspotcloud.client.place.api.Navigator.Unit;
import com.googlecode.fspotcloud.client.place.api.PlaceGoTo;
import com.googlecode.fspotcloud.client.place.api.PlaceWhere;
import junit.framework.TestCase;
import org.jmock.Expectations;
import org.jmock.Mockery;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class NavigatorImplTest extends TestCase {
    DataManager dataManager = new DataManagerImpl(new DispatchAsyncTestImpl());
    PlaceCalculator placeCalculator = new PlaceCalculator();
    Navigator navigator;
    Mockery context;
    BasePlace aute = new BasePlace("1", "2");
    BasePlace daniel = new BasePlace("1", "1");
    BasePlace danielRaster = new BasePlace("1", "1", 1, 2);
    BasePlace jan = new BasePlace("1", "3");
    BasePlace janRaster = new BasePlace("1", "3", 1, 2);
    BasePlace snowie = new BasePlace("4", "11");
    BasePlace siepie = new BasePlace("4", "12");
    BasePlace woefje = new BasePlace("5", "21",
            placeCalculator.getRasterWidth(), placeCalculator.getRasterHeight());
    BasePlace r1_3 = new BasePlace("6", "101", 1, 3);
    BasePlace r1_3_zoomed_in = new BasePlace("6", "101", 1, 1);
    BasePlace r1_3next = new BasePlace("6", "103", 1, 3);

    @Override
    protected void setUp() throws Exception {
        context = new Mockery();
        super.setUp();
    }

    public Navigator get(PlaceGoTo goTo) {
        return new NavigatorImpl(null, goTo, placeCalculator, dataManager);
    }

    public Navigator get(PlaceWhere where, PlaceGoTo goTo) {
        return new NavigatorImpl(where, goTo, placeCalculator, dataManager);
    }

    public void testGoLast() {
        final PlaceGoTo goTo = context.mock(PlaceGoTo.class);
        final PlaceWhere where = context.mock(PlaceWhere.class);
        context.checking(new Expectations() {

            {
                oneOf(where).where();
                will(returnValue(daniel));

                oneOf(goTo).goTo(with(jan));
            }
        });
        navigator = get(where, goTo);
        navigator.goAsync(Direction.FORWARD, Unit.BORDER);
        context.assertIsSatisfied();
    }

    public void testGoLastWithoutPlace() {
        final PlaceGoTo goTo = context.mock(PlaceGoTo.class);
        final PlaceWhere where = context.mock(PlaceWhere.class);
        context.checking(new Expectations() {

            {
                oneOf(where).where();
                will(returnValue(daniel));
                oneOf(goTo).goTo(with(jan));
            }
        });
        navigator = get(where, goTo);
        navigator.goAsync(Direction.FORWARD, Unit.BORDER);
        context.assertIsSatisfied();
    }

    public void testGoFirst() {
        final PlaceGoTo goTo = context.mock(PlaceGoTo.class);
        final PlaceWhere where = context.mock(PlaceWhere.class);
        context.checking(new Expectations() {

            {
                oneOf(where).where();
                will(returnValue(jan));
                oneOf(goTo).goTo(with(daniel));
            }
        });
        navigator = get(where, goTo);
        navigator.goAsync(Direction.BACKWARD, Unit.BORDER);
        context.assertIsSatisfied();
    }

    public void testGoForward() throws InterruptedException {
        final PlaceGoTo goTo = context.mock(PlaceGoTo.class);
        final PlaceWhere where = context.mock(PlaceWhere.class);
        context.checking(new Expectations() {

            {
                oneOf(where).where();
                will(returnValue(snowie));
                oneOf(goTo).goTo(with(siepie));
            }
        });
        navigator = get(where, goTo);
        navigator.goAsync(Direction.FORWARD, Unit.PAGE);
        context.assertIsSatisfied();
    }

    public void testGoBackward() {
        final PlaceGoTo goTo = context.mock(PlaceGoTo.class);
        final PlaceWhere where = context.mock(PlaceWhere.class);
        context.checking(new Expectations() {

            {
                oneOf(where).where();
                will(returnValue(siepie));
                oneOf(goTo).goTo(with(snowie));
            }
        });
        navigator = get(where, goTo);
        navigator.goAsync(Direction.BACKWARD, Unit.SINGLE);
        context.assertIsSatisfied();
    }

    public void testGetPageCount() {
        final AsyncCallback<Integer> result = context.mock(AsyncCallback.class);

        context.checking(new Expectations() {

            {
                oneOf(result).onSuccess(with(2));
            }
        });
        navigator = get(null);
        navigator.getPageCountAsync("1", 2, result);
        context.assertIsSatisfied();
    }

    public void testToggleZoomView() {
        final PlaceGoTo goTo = context.mock(PlaceGoTo.class);
        final PlaceWhere where = context.mock(PlaceWhere.class);
        context.checking(new Expectations() {

            {
                oneOf(where).where();
                will(returnValue(r1_3));
                oneOf(goTo).goTo(with(r1_3_zoomed_in));
            }
        });
        navigator = get(where, goTo);
        navigator.toggleZoomViewAsync(r1_3.getTagId(), r1_3.getPhotoId());
        context.assertIsSatisfied();
    }

    public void testGoToLatestTag() {
        final PlaceGoTo goTo = mock(PlaceGoTo.class);
        navigator = get(goTo);
        navigator.goToLatestTag();
        verify(goTo).goTo(woefje);
    }

    public void testGetPagingInfo() {
        final PlaceGoTo goTo = mock(PlaceGoTo.class);
        navigator = get(goTo);
        navigator.goToLatestTag();
        verify(goTo).goTo(woefje);
    }
}
