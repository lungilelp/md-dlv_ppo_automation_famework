package base;

import org.openqa.selenium.remote.RemoteWebDriver;

/**
 * @author Jonathan Mbuyi Tshitenda [ ABJT439 ]
 * @date created 8/26/2019
 * @package com.jmt.framework.base
 */
public class LocalDriverContext {

    /**To be the local thread of the framework ,for us to have the option to run our tests in parallel
     *This will now be used across our entire framework
     * This allows us not to use the static web-driver variable*/

    private static ThreadLocal<RemoteWebDriver> remoteWebDriverThreadLocal = new ThreadLocal<>();


    public static RemoteWebDriver getRemoteWebDriver()
    {
        return remoteWebDriverThreadLocal.get();
    }

    static void setRemoteWebDriverThreadLocal(RemoteWebDriver driverThreadLocal)
    {
        remoteWebDriverThreadLocal.set(driverThreadLocal);
    }
}
