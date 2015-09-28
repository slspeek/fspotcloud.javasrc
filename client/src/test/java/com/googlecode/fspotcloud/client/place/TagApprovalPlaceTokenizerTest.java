package com.googlecode.fspotcloud.client.place;

public class TagApprovalPlaceTokenizerTest
		extends
			TokenizerTest<TagApprovalPlace> {
	public TagApprovalPlaceTokenizerTest() {
		super(new TagApprovalPlace.Tokenizer(), new TagApprovalPlace("1"), "1");
	}
}
