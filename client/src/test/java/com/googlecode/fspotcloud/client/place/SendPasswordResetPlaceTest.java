package com.googlecode.fspotcloud.client.place;

import org.junit.Test;

public class SendPasswordResetPlaceTest  {
    private final SendPasswordResetPlace place = new SendPasswordResetPlace();
    private final SendPasswordResetPlace other = new SendPasswordResetPlace();

    @Test
    public void testEquals() throws Exception {
        place.equals(other);

    }


}