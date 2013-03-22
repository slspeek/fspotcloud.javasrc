package com.googlecode.fspotcloud.client.place;

import com.google.inject.Provider;
import com.googlecode.fspotcloud.client.place.api.Navigator;
import com.googlecode.fspotcloud.test.EqualityTest;
import org.junit.Test;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.junit.Assert.assertEquals;

public class MoveTest extends EqualityTest

{

    private Move move = new Move(Navigator.Direction.FORWARD, Navigator.Unit.BORDER);
    @Test
    public void testGetDirection() throws Exception {
        assertEquals(Navigator.Direction.FORWARD, move.getDirection());

    }

    @Test
    public void testGetUnit() throws Exception {

        assertEquals(Navigator.Unit.BORDER, move.getUnit());
    }

    @Override
    protected List<Provider<Object>> getUniqueObjects() {
        List<Provider<Object>> result = newArrayList();
        result.add(new Provider<Object>() {
            @Override
            public Object get() {
                return new Move(Navigator.Direction.BACKWARD, Navigator.Unit.SINGLE);
            }
        });
        result.add(new Provider<Object>() {
            @Override
            public Object get() {
                return new Move(Navigator.Direction.FORWARD, Navigator.Unit.SINGLE);
            }
        });
        result.add(new Provider<Object>() {
            @Override
            public Object get() {
                return new Move(Navigator.Direction.FORWARD, Navigator.Unit.ROW);
            }
        });
        return result;
    }

    @Test
    public void testToString() throws Exception {
        assertEquals("Move{direction=null, unit=null}", new Move(null, null).toString());

    }
}
