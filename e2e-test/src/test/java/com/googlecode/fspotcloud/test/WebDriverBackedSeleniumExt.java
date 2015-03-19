package com.googlecode.fspotcloud.test;

import com.google.common.base.Supplier;
import org.openqa.selenium.WebDriver;
import com.thoughtworks.selenium.webdriven.WebDriverBackedSelenium;

/**
 * Created with IntelliJ IDEA.
 * User: steven
 * Date: 23-6-13
 * Time: 22:33
 * To change this template use File | Settings | File Templates.
 */
public class WebDriverBackedSeleniumExt extends WebDriverBackedSelenium {
    private String baseUrl;

    public WebDriverBackedSeleniumExt(Supplier<WebDriver> maker, String baseUrl) {
        super(maker, baseUrl);
        this.baseUrl = baseUrl;
    }

    public WebDriverBackedSeleniumExt(WebDriver baseDriver, String baseUrl) {
        super(baseDriver, baseUrl);
        this.baseUrl = baseUrl;
    }

    public void open(String url) {
        super.open(baseUrl + url);
    }
}
