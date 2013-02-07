package com.googlecode.fspotcloud.client.data;

import com.google.gwt.user.client.rpc.AsyncCallback;
import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(JukitoRunner.class)
public class MemoProcAsyncTest {

    public static final String SAMPLE = "Sample";
    SampleMemoProcAsync sampleMemoProcAsync = new SampleMemoProcAsync();

    @Test
    public void testOneCall(AsyncCallback<String> one) throws Exception {
        sampleMemoProcAsync.getAsync(one);
        assertEquals(1, sampleMemoProcAsync.getCallCount());
        sampleMemoProcAsync.doCallback();
        verify(one).onSuccess(SAMPLE);
        verifyNoMoreInteractions(one);
    }


    @Test
    public void testTwoConsequetiveCall(AsyncCallback<String> one) throws Exception {
        sampleMemoProcAsync.getAsync(one);
        assertEquals(1, sampleMemoProcAsync.getCallCount());
        sampleMemoProcAsync.doCallback();
        verify(one).onSuccess(SAMPLE);

        sampleMemoProcAsync.setCallCOunt(0);
        SampleCallback two = new SampleCallback();
        sampleMemoProcAsync.getAsync(two);
        assertEquals(0, sampleMemoProcAsync.getCallCount());

        sampleMemoProcAsync.doCallback();

        assertEquals(SAMPLE, two.getResult());
        verifyNoMoreInteractions(one);
    }

    @Test
    public void testReset(AsyncCallback<String> one) throws Exception {
        sampleMemoProcAsync.getAsync(one);
        assertEquals(1, sampleMemoProcAsync.getCallCount());
        sampleMemoProcAsync.doCallback();
        verify(one).onSuccess(SAMPLE);
        sampleMemoProcAsync.reset();
        SampleCallback two = new SampleCallback();
        sampleMemoProcAsync.getAsync(two);
        assertEquals(2, sampleMemoProcAsync.getCallCount());
        sampleMemoProcAsync.doCallback();
        assertEquals(SAMPLE, two.getResult());
    }
}


