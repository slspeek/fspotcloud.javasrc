package com.googlecode.fspotcloud.client.place;

public class SlideshowTokenizerTest extends TokenizerTest<SlideshowPlace> {
	public SlideshowTokenizerTest() {
		super(new SlideshowPlace.Tokenizer(), new SlideshowPlace("1", "2"),
				"1:2");
	}
}
