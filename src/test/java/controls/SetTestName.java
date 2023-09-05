package controls;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Jonathan Mbuyi Tshitenda [ ABJT439 ]
 * @date created 9/10/2019
 * @package com.jmt.framework.controls.internals
 */


    @Retention(RetentionPolicy.RUNTIME)
    public @interface SetTestName
    {
        int idx() default 0;
    }

