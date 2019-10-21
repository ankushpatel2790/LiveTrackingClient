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

import org.eclipse.leshan.client.resource.SimpleInstanceEnabler;
import org.eclipse.leshan.core.response.ReadResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Weather extends SimpleInstanceEnabler {
    private static final Logger LOG = LoggerFactory.getLogger(Weather.class);
    private float temperature;

    // private int sensorStatus;

    public Weather() {

        this.temperature = 23.0F;
        // this.sensorStatus = ConfigUtil.parseStringToInt("WEATHER_SENSOR");

    }

    public ReadResponse read(int resourceid) {
        LOG.info("Read on Temperature Resource " + resourceid);
        switch (resourceid) {
        case 0:
            return ReadResponse.success(resourceid, getTemperature());
        // case 1:
        // return ReadResponse.success(resourceid, getSensorStatus());

        }
        return super.read(resourceid);
    }

    /*
     * @Override public WriteResponse write(int resourceid, LwM2mResource value) { LOG.info("Write on Device Resource "
     * + resourceid + " valuRe " + value); switch (resourceid) {
     * 
     * case 1: moveSensorStatus(Integer.valueOf(value.getValue().toString())); return WriteResponse.success();
     * 
     * default: return super.write(resourceid, value); } }
     */

    public void moveTemperature(float delta) {

        this.temperature = delta;
        fireResourcesChange(new int[] { 0, 30001 });
    }

    public float getTemperature() {
        return this.temperature;

    }

    // public void moveSensorStatus(int delta) {
    // this.sensorStatus = delta;
    // }
    //
    // public int getSensorStatus() {
    // return this.sensorStatus;
    // }

}
