package com.googlecode.fspotcloud.client.place;

public class UserAccountPlaceTokenizerTest extends TokenizerTest<UserAccountPlace> {
    public UserAccountPlaceTokenizerTest() {
        super(new UserAccountPlace.Tokenizer(), new UserAccountPlace(), "");
    }
}
