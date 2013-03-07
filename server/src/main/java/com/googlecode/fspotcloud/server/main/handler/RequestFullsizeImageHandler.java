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
import com.google.inject.Provider;
import com.googlecode.botdispatch.controller.dispatch.ControllerDispatchAsync;
import com.googlecode.fspotcloud.server.control.callback.FullsizePhotoCallback;
import com.googlecode.fspotcloud.server.image.ImageHelper;
import com.googlecode.fspotcloud.server.mail.IMail;
import com.googlecode.fspotcloud.server.model.api.Photo;
import com.googlecode.fspotcloud.server.model.api.PhotoDao;
import com.googlecode.fspotcloud.server.model.tag.IUserGroupHelper;
import com.googlecode.fspotcloud.shared.main.FullsizeImageResult;
import com.googlecode.fspotcloud.shared.main.RequestFullsizeImageAction;
import com.googlecode.fspotcloud.shared.peer.GetFullsizePhotoAction;
import com.googlecode.fspotcloud.user.UserService;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.server.SimpleActionHandler;
import net.customware.gwt.dispatch.shared.DispatchException;

import static com.google.common.collect.Sets.newHashSet;


public class RequestFullsizeImageHandler extends SimpleActionHandler<RequestFullsizeImageAction, FullsizeImageResult> {
    public static final String SUCCESSFULLY_MAILED = "Successfully mailed the image, you can check your mail";
    public static final String SUCCESSFULLY_SCHEDULED = "Successfully scheduled your request, it may take a couple of days";
    public static final String NOT_ALLOWED = "You are not allowed to view this image";
    public static final String IMAGE_NOT_FOUND = "Image was not found";
    public static final String LOGON_FIRST = "Failed, you have to logon first";
    @Inject
    private ControllerDispatchAsync controllerAsyc;
    @Inject
    private PhotoDao photoDao;
    @Inject
    private ImageHelper imageHelper;
    @Inject
    private UserService userService;
    @Inject
    private Provider<IMail> mailerProvider;
    @Inject
    private IUserGroupHelper userGroupHelper;

    @Override
    public FullsizeImageResult execute(RequestFullsizeImageAction action,
                                       ExecutionContext context) throws DispatchException {
        String message = "";
        if (userService.isUserLoggedIn()) {
            final String caller = userService.getEmail();
            final String imageId = action.getImageId();
            final Photo photo = photoDao.find(imageId);

            if (photo != null) {
                if (userService.isUserAdmin() ||
                        userGroupHelper.containsOneOf(newHashSet(photo.getTagList()))) {
                    byte[] fsImage = imageHelper.getImage(photo,
                            ImageHelper.Type.FULLSIZE);

                    if (fsImage != null) {
                        mailerProvider.get().send(caller, "Your requested image: " + imageId,
                                "Dear " + caller + ",\nYour requested image: " +
                                        imageId + " is in the attachment", fsImage);
                        message = SUCCESSFULLY_MAILED;
                    } else {
                        controllerAsyc.execute(new GetFullsizePhotoAction(imageId),
                                new FullsizePhotoCallback(caller, null, null));
                        message = SUCCESSFULLY_SCHEDULED;
                    }
                } else {
                    message = NOT_ALLOWED;
                }
            } else {
                message = IMAGE_NOT_FOUND;
            }
        } else {
            message = LOGON_FIRST;
        }

        return new FullsizeImageResult(message);
    }
}
