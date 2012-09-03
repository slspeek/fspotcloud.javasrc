package com.googlecode.fspotcloud.server.model.api;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: steven
 * Date: 16-8-12
 * Time: 19:48
 * To change this template use File | Settings | File Templates.
 */
public class DateUtil {
    public static Date cloneDate(Date date) {
        Date result = null;
        if (date != null) {
            result = new Date(date.getTime());
        }
        return result;
    }
}
