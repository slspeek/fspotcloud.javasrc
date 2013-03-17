package com.googlecode.fspotcloud.client.place;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.enduseraction.Flags;
import com.googlecode.fspotcloud.client.place.api.Navigator;
import com.googlecode.fspotcloud.keyboardaction.IModeController;

public class NavigationFlagsHelper 
{
    
    @Inject
    private Navigator navigator;
    @Inject
    private IModeController modeController;
    
    private class CallbackHelper implements AsyncCallback<Boolean> {
        
        private String flag;
        CallbackHelper(String flag) {
            this.flag = flag;            
        }

        @Override
        public void onFailure(Throwable caught) {
            
        }

        @Override
        public void onSuccess(Boolean result) {
            modeController.setFlag(flag, result);
        }
    }
    
    public void update() {
        navigator.canGoAsync(Navigator.Direction.FORWARD,
                Navigator.Unit.SINGLE,
                new CallbackHelper(Flags.CAN_GO_NEXT_IMAGE.name()));
        navigator.canGoAsync(Navigator.Direction.FORWARD,
                Navigator.Unit.ROW,
                new CallbackHelper(Flags.CAN_GO_NEXT_ROW.name()));
        navigator.canGoAsync(Navigator.Direction.FORWARD,
                Navigator.Unit.PAGE,
                new CallbackHelper(Flags.CAN_GO_NEXT_PAGE.name()));
        navigator.canGoAsync(Navigator.Direction.FORWARD,
                Navigator.Unit.BORDER,
                new CallbackHelper(Flags.CAN_GO_END.name()));

        navigator.canGoAsync(Navigator.Direction.BACKWARD,
                Navigator.Unit.SINGLE,
                new CallbackHelper(Flags.CAN_GO_PREV_IMAGE.name()));
        navigator.canGoAsync(Navigator.Direction.BACKWARD,
                Navigator.Unit.ROW,
                new CallbackHelper(Flags.CAN_GO_PREV_ROW.name()));
        navigator.canGoAsync(Navigator.Direction.BACKWARD,
                Navigator.Unit.PAGE,
                new CallbackHelper(Flags.CAN_GO_PREV_PAGE.name()));
        navigator.canGoAsync(Navigator.Direction.BACKWARD,
                Navigator.Unit.BORDER,
                new CallbackHelper(Flags.CAN_GO_HOME.name()));
        
    }
    
}
