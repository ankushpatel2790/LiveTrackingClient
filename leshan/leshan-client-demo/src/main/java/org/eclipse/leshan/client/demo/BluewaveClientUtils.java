/*******************************************************************************
 * Copyright (c) 2016 Sierra Wireless and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v1.0 which accompany this distribution.
 * 
 * The Eclipse Public License is available at
 *    http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 *    http://www.eclipse.org/org/documents/edl-v10.html.
 * 
 * Contributors:
 *     Sierra Wireless - initial API and implementation
 *******************************************************************************/
package org.eclipse.leshan.client.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BluewaveClientUtils {

    private static final Logger LOG = LoggerFactory.getLogger(BluewaveClientUtils.class);

    public static Integer paserStringToInteger(String value) {
        try {
            return (value != null) ? Integer.parseInt(value) : 0;
        } catch (NumberFormatException e) {
            LOG.error("While parsing string to integer :{} with value", e, value);
            return 0;
        }
    }

    public static Float paserStringToFloat(String value) {
        try {
            return (value != null) ? Float.parseFloat(value) : 0f;
        } catch (NumberFormatException e) {
            LOG.error("While parsing string to float :{} with value", e, value);
            return 0f;
        }
    }

    public static boolean paserStringToBoolean(String value) {
        try {
            return (value != null) ? Boolean.parseBoolean(value) : false;
        } catch (NumberFormatException e) {
            LOG.error("While parsing string to boolean :{} with value", e, value);
            return false;
        }
    }

    public static boolean isDigit(String value) {
        if (value != null && !value.isEmpty()) {
            return Character.isDigit(value.charAt(0));
        }
        return false;

    }

    public static boolean isInteger(String value) {
        if (value != null && !value.isEmpty()) {
            try {
                Integer.parseInt(value);
                return true;
            } catch (Exception e) {

            }
        }
        return false;

    }

    public static String getSecureCoapUrl(String ipAddress, String key) {
        StringBuilder bs = new StringBuilder();
        try {
            bs.append("coaps://").append(ipAddress).append(":").append(ConfigUtil.parseStringToInt(key));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bs.toString();
    }

    public static String getNoSecCoapUrl(String ipAddress, String key) {
        StringBuilder bs = new StringBuilder();
        try {
            bs.append("coap://").append(ipAddress).append(":").append(ConfigUtil.parseStringToInt(key));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bs.toString();
    }
}
