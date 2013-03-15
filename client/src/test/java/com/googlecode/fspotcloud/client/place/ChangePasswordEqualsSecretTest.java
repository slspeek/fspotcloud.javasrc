package com.googlecode.fspotcloud.client.place;

import com.googlecode.fspotcloud.test.EqualsTest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ChangePasswordEqualsSecretTest extends EqualsTest<ChangePasswordPlace> {


    public static final String RMS_FSF_ORG = "rms@fsf.org";
    public static final String SECRET = "42";

    @Override
    protected ChangePasswordPlace getOne() {
        return new ChangePasswordPlace(RMS_FSF_ORG, SECRET);
    }

    @Override
    protected ChangePasswordPlace getTheOther() {
        return new ChangePasswordPlace(RMS_FSF_ORG, SECRET);
    }

    @Override
    protected ChangePasswordPlace getDifferentOne() {
        return new ChangePasswordPlace(RMS_FSF_ORG, "");
    }
}
