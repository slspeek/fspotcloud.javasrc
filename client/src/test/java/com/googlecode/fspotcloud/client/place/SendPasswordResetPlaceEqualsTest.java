package com.googlecode.fspotcloud.client.place;

import com.googlecode.fspotcloud.test.EqualsTest;

public class SendPasswordResetPlaceEqualsTest extends EqualsTest<SendConfirmationPlace> {

    @Override
    protected SendConfirmationPlace getOne() {
        return new SendConfirmationPlace();
    }

    @Override
    protected SendConfirmationPlace getDifferentOne() {
        return null;
    }
}
