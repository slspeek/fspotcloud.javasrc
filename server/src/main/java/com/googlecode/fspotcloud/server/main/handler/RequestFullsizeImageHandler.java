/*
 * Copyright 2010-2012 Steven L. Speek.
 * This program is free software; you can redistribute it
                and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free
                Software Foundation; either version 2
 * of the License, or any later version.
 *
 * This program is
                distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied
                warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public
                License for more details.
 * You should have received a copy of the GNU General Public License
 * along
                with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330,
                Boston, MA 02111-1307, USA.
 *
 */

package com.googlecode.fspotcloud.server.main.handler;

import com.google.inject.Inject;
import com.googlecode.botdispatch.controller.dispatch.ControllerDispatchAsync;
import com.googlecode.fspotcloud.server.control.callback.FullsizePhotoCallback;
import com.googlecode.fspotcloud.server.image.ImageHelper;
import com.googlecode.fspotcloud.server.mail.IMail;
import com.googlecode.fspotcloud.server.model.api.Photo;
import com.googlecode.fspotcloud.server.model.api.PhotoDao;
import com.googlecode.fspotcloud.shared.dashboard.VoidResult;
import com.googlecode.fspotcloud.shared.main.RequestFullsizeImageAction;
import com.googlecode.fspotcloud.shared.peer.GetFullsizePhotoAction;
import com.googlecode.fspotcloud.user.UserService;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.server.SimpleActionHandler;
import net.customware.gwt.dispatch.shared.DispatchException;


public class RequestFullsizeImageHandler extends SimpleActionHandler<RequestFullsizeImageAction, VoidResult> {
    @Inject
    private ControllerDispatchAsync controllerAsyc;
    @Inject
    private PhotoDao photoDao;
    @Inject
    private ImageHelper imageHelper;
    @Inject
    private UserService userService;
    @Inject
    private IMail mailer;

    @Override
    public VoidResult execute(RequestFullsizeImageAction action,
                              ExecutionContext context) throws DispatchException {
        if (userService.isUserLoggedIn()) {
            final String caller = userService.getEmail();
            final String imageId = action.getImageId();
            final Photo photo = photoDao.find(imageId);

            if (photo != null) {
                byte[] fsImage = imageHelper.getImage(photo,
                        ImageHelper.Type.FULLSIZE);

                if (fsImage != null) {
                    mailer.send(caller, "Your requested image: " + imageId,
                            "Dear " + caller + ",\nYour requested image: " +
                                    imageId + " is in the attachment", fsImage);
                } else {
                    controllerAsyc.execute(new GetFullsizePhotoAction(imageId),
                            new FullsizePhotoCallback(caller, null, null));
                }
            }
        }

        return new VoidResult();
    }
}
