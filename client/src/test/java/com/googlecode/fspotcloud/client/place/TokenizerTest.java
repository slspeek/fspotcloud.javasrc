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

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class TokenizerTest<P extends Place>  {
    final private PlaceTokenizer<P> tokenizer;
    final private P place;
    final private String token;

    public TokenizerTest(PlaceTokenizer<P> tokenizer, P place, String token) {
        this.tokenizer = tokenizer;
        this.place = place;
        this.token = token;
    }

    @Test
    public void testGetPlace() {
        P actualPlace = tokenizer.getPlace(token);
        assertEquals(place, actualPlace);
        
    }

    @Test
    public void testGetToken() {
        String actualToken = tokenizer.getToken(place);
        assertEquals(token, actualToken);
    }
}
