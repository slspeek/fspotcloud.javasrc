package com.googlecode.fspotcloud.client.place;

public class TagPlaceTokenizerTest extends TokenizerTest<TagPlace> {
    public TagPlaceTokenizerTest() {
        super(new TagPlace.Tokenizer(), new TagPlace("1"), "1");
    }
}
