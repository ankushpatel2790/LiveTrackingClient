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

import org.eclipse.leshan.client.resource.BaseInstanceEnabler;
import org.eclipse.leshan.core.node.LwM2mResource;
import org.eclipse.leshan.core.response.ReadResponse;
import org.eclipse.leshan.core.response.WriteResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SensorControlFlag extends BaseInstanceEnabler {
    private static final Logger LOG = LoggerFactory.getLogger(SensorControlFlag.class);
    private int gpsSensor;
    private int accelerometerSensor;
    private int weatherSensor;
    private int deregisterFlag;

    public SensorControlFlag() {
        this.gpsSensor = ConfigUtil.parseStringToInt("GPS_SENSOR");
        this.accelerometerSensor = ConfigUtil.parseStringToInt("ACCELEROMETER_SENSOR");
        this.weatherSensor = ConfigUtil.parseStringToInt("WEATHER_SENSOR");
        this.deregisterFlag = 1;
    }

    public ReadResponse read(int resourceid) {
        LOG.info("Read on sensor control Resource " + resourceid);
        switch (resourceid) {
        case 0:
            return ReadResponse.success(resourceid, getWeatherSensor());
        case 6:
            return ReadResponse.success(resourceid, getAccelerometerSensor());
        case 7:
            return ReadResponse.success(resourceid, getGpsSensor());

        case 3:
            return ReadResponse.success(resourceid, getDeregisterFlag());

        }
        return super.read(resourceid);
    }

    @Override
    public WriteResponse write(int resourceid, LwM2mResource value) {
        LOG.info("Write on Device Resource " + resourceid + " valuRe " + value);
        switch (resourceid) {

        case 0:
            setWeatherSensor(Integer.valueOf(value.getValue().toString()));
            return WriteResponse.success();
        case 6:
            setAccelerometerSensor(Integer.valueOf(value.getValue().toString()));
            return WriteResponse.success();
        case 7:
            setGpsSensor(Integer.valueOf(value.getValue().toString()));
            return WriteResponse.success();
        case 3:
            setDeregisterFlag(Integer.valueOf(value.getValue().toString()));
            return WriteResponse.success();

        default:
            return super.write(resourceid, value);
        }
    }

    public int getDeregisterFlag() {
        return deregisterFlag;
    }

    public void setDeregisterFlag(int deregisterFlag) {
        this.deregisterFlag = deregisterFlag;
    }

    public int getGpsSensor() {
        return gpsSensor;
    }

    public void setGpsSensor(int gpsSensor) {
        this.gpsSensor = gpsSensor;
    }

    public int getAccelerometerSensor() {
        return accelerometerSensor;
    }

    public void setAccelerometerSensor(int accelerometerSensor) {
        this.accelerometerSensor = accelerometerSensor;
    }

    public int getWeatherSensor() {
        return weatherSensor;
    }

    public void setWeatherSensor(int weatherSensor) {
        this.weatherSensor = weatherSensor;
    }

}
