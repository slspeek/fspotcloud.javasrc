package com.googlecode.fspotcloud.client.place;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EditUserGroupPlaceTest {

    public static final long id = 1l;
    private static final String EXPECTED = String.valueOf(id);
    private EditUserGroupPlace place = new EditUserGroupPlace(id);

    @Test
    public void testGetUserGroupId() throws Exception {
        EditUserGroupPlace place = new EditUserGroupPlace(1l);
        assertEquals(1l, place.getUserGroupId());
    }

    @Test
    public void testTokenizerGetToken() throws Exception {
        EditUserGroupPlace.Tokenizer tokenizer = new EditUserGroupPlace.Tokenizer();
        String token = tokenizer.getToken(place);
        assertEquals(EXPECTED, token);
    }

    @Test
    public void testTokenizerGetPlace() throws Exception {
        EditUserGroupPlace.Tokenizer tokenizer = new EditUserGroupPlace.Tokenizer();
        assertEquals(place, tokenizer.getPlace(EXPECTED));
    }
}
