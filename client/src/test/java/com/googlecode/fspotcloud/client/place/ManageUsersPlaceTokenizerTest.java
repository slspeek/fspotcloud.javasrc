package com.googlecode.fspotcloud.client.place;

import com.google.gwt.place.shared.PlaceTokenizer;

public class ManageUsersPlaceTokenizerTest extends TokenizerTest<ManageUsersPlace> {
    public ManageUsersPlaceTokenizerTest() {
        super(new ManageUsersPlace.Tokenizer(), new ManageUsersPlace(100l), "100");
    }
}
