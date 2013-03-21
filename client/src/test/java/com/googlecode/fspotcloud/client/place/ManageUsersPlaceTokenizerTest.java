package com.googlecode.fspotcloud.client.place;

public class ManageUsersPlaceTokenizerTest extends TokenizerTest<ManageUsersPlace> {
    public ManageUsersPlaceTokenizerTest() {
        super(new ManageUsersPlace.Tokenizer(), new ManageUsersPlace(100l), "100");
    }
}
