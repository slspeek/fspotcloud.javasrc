package com.googlecode.fspotcloud.test;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.googlecode.fspotcloud.server.model.api.User;
import com.googlecode.fspotcloud.server.model.api.UserDao;

import java.util.logging.Logger;

import static com.googlecode.fspotcloud.server.util.DigestTool.hash;

public class ThreeUsersFixture implements Runnable {

    UserDao userDao;

    @Inject
    public ThreeUsersFixture(Injector injector) {
        userDao = injector.getInstance(UserDao.class);
    }

    @Override
    public void run() {
        createRMS();
        createSLS();
        createNeedsConfirmation();
    }

    private void createSLS() {
        User sls = userDao.findOrNew(ILogin.SLS);
        sls.setCredentials(hash(ILogin.SLS, ILogin.SLS_CRED));
        sls.setRegistered(true);
        sls.setEnabled(true);
        sls.setEmailVerificationSecret(ILogin.SLS_EMAIL_VERIFICATION_SECRET);
        userDao.save(sls);
        Logger.getAnonymousLogger().info(ILogin.SLS + " saved.");
    }

    private void createRMS() {
        User rms = userDao.findOrNew(ILogin.RMS_FSF_ORG);
        rms.setCredentials(hash(ILogin.RMS_FSF_ORG, ILogin.RMS_CRED));
        rms.setRegistered(true);
        rms.setEnabled(true);
        userDao.save(rms);
        Logger.getAnonymousLogger().info(ILogin.RMS_FSF_ORG + " saved.");
    }

    private void createNeedsConfirmation() {
        User user = userDao.findOrNew(ILogin.NEEDS_CONFIRMATION);
        user.setCredentials(hash(ILogin.NEEDS_CONFIRMATION, ILogin.NEEDS_CRED));
        user.setRegistered(true);
        user.setEnabled(false);
        user.setEmailVerificationSecret(ILogin.NEEDS_SECRET);
        userDao.save(user);
        Logger.getAnonymousLogger().info(ILogin.NEEDS_CONFIRMATION + " saved.");
    }

}
