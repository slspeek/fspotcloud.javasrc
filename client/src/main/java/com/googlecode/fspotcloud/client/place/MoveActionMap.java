package com.googlecode.fspotcloud.client.place;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.enduseraction.Flags;
import com.googlecode.fspotcloud.client.enduseraction.navigation.NavigationActions;
import com.googlecode.fspotcloud.client.place.api.Navigator;

import java.util.Map;

import static com.google.common.collect.Maps.newHashMap;

public class MoveActionMap {


    private final Map<Move, String> actionMap = newHashMap();

    @Inject
    public MoveActionMap(NavigationActions navigationActions) {
        put(Navigator.Direction.FORWARD, Navigator.Unit.SINGLE, Flags.CAN_GO_NEXT_IMAGE);
        put(Navigator.Direction.FORWARD, Navigator.Unit.ROW, Flags.CAN_GO_NEXT_ROW);
        put(Navigator.Direction.FORWARD, Navigator.Unit.PAGE, Flags.CAN_GO_NEXT_PAGE);
        put(Navigator.Direction.FORWARD, Navigator.Unit.BORDER, Flags.CAN_GO_END);
        put(Navigator.Direction.BACKWARD, Navigator.Unit.SINGLE, Flags.CAN_GO_PREV_IMAGE);
        put(Navigator.Direction.BACKWARD, Navigator.Unit.ROW, Flags.CAN_GO_PREV_ROW);
        put(Navigator.Direction.BACKWARD, Navigator.Unit.PAGE, Flags.CAN_GO_PREV_PAGE);
        put(Navigator.Direction.BACKWARD, Navigator.Unit.BORDER, Flags.CAN_GO_HOME);
    }

    private void put(Navigator.Direction direction, Navigator.Unit unit, Flags flag) {
        actionMap.put(new Move(direction, unit), flag.name());
    }

    public String getActionId(Move move) {
        return actionMap.get(move);
    }

}
