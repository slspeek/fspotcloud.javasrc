package com.googlecode.fspotcloud.server.inject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.googlecode.fspotcloud.test.Fixture;

@Singleton
public class FixtureRunServlet extends HttpServlet {

    @Inject
    Fixture fixture;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        fixture.run();
    }
}
