package com.googlecode.fspotcloud.client.place;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ChangePasswordPlaceTest {


    public static final String RMS_FSF_ORG = "rms@fsf.org";
    public static final String SECRET = "42";
    public static final String EXPECTED = RMS_FSF_ORG + ":" + SECRET;

    private ChangePasswordPlace place = new ChangePasswordPlace(RMS_FSF_ORG, SECRET);
    @Test
    public void testGetSecret() throws Exception {
        assertEquals(SECRET, place.getSecret());
    }

    @Test
    public void testGetEmail() throws Exception {
        assertEquals(RMS_FSF_ORG, place.getEmail());
    }

    @Test
    public void testTokenizerGetToken() throws Exception {
        ChangePasswordPlace.Tokenizer tokenizer = new ChangePasswordPlace.Tokenizer();
        String token = tokenizer.getToken(place);
        assertEquals(EXPECTED, token);
    }

    @Test
    public void testTokenizerGetPlace() throws Exception {
        ChangePasswordPlace.Tokenizer tokenizer = new ChangePasswordPlace.Tokenizer();
        assertEquals(place, tokenizer.getPlace(EXPECTED));
    }
}
