package com.googlecode.fspotcloud.test;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provider;
import com.googlecode.fspotcloud.server.model.api.UserDao;

public class UserDaoProvider implements Provider<UserDao> {

    private Injector injector;

    @Inject
    private FscServer serverMain;

    @Override
    public UserDao get() {
        Injector injector = serverMain.getInjector();
        return  injector.getInstance(UserDao.class);
    }
}
