package com.googlecode.fspotcloud.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({EmailConfirmationITest.class,
		SendEmailConfirmationITest.class, SendPasswordResetITest.class})
public class TotalSuite {
}
