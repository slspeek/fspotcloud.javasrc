package com.googlecode.fspotcloud.test;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.googlecode.fspotcloud.server.model.api.User;
import com.googlecode.fspotcloud.server.model.api.UserDao;

import java.util.logging.Logger;

public class Fixture implements Runnable {

    UserDao userDao;

    @Inject
    public Fixture(Injector injector) {
        userDao = injector.getInstance(UserDao.class);
    }

    @Override
    public void run() {
        createRMS();
        createSLS();
    }

    private void createSLS() {
        User rms = userDao.findOrNew(ILogin.SLS);
        rms.setCredentials(ILogin.SLS_CRED);
        rms.setRegistered(true);
        rms.setEnabled(true);
        userDao.save(rms);
        Logger.getAnonymousLogger().info(ILogin.SLS + " saved.");
    }

    private void createRMS() {
        User rms = userDao.findOrNew(ILogin.RMS_FSF_ORG);
        rms.setCredentials(ILogin.CREDENTIALS);
        rms.setRegistered(true);
        rms.setEnabled(true);
        userDao.save(rms);
        Logger.getAnonymousLogger().info(ILogin.RMS_FSF_ORG + " saved.");
    }

}
