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

import java.util.Date;
import java.util.Random;

import org.eclipse.leshan.client.resource.BaseInstanceEnabler;
import org.eclipse.leshan.core.node.LwM2mResource;
import org.eclipse.leshan.core.response.ReadResponse;
import org.eclipse.leshan.core.response.WriteResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bluewaves.model.BluewavesOjbects;

public class Accelerometer extends BaseInstanceEnabler {
    private static final Logger LOG = LoggerFactory.getLogger(Accelerometer.class);
    private Random random;
    private float roll;
    private float tilt;
    private float temperature;

    public int status;
    public float xAcceleration;
    public float yAcceleration;
    public float accelerationXaxisConfig;
    public float accelerationYaxisConfig;
    public float inclinationXaxisConfig;
    public float inclinationYaxisConfig;
    public float rollConfig;
    public float globalCmd;
    public long ts = new Date().getTime();
    public int sensorStatus;
    public Float extraRoll;

    private SensorControlFlag controlFlag;

    public Accelerometer(SensorControlFlag controlFlag) {

        this.random = new Random();
        this.roll = Float.valueOf(this.random.nextInt(ConfigUtil.parseStringToInt("ROLL"))).floatValue();
        this.tilt = Float.valueOf(this.random.nextInt(ConfigUtil.parseStringToInt("TILT"))).floatValue();
        this.temperature = Float.valueOf(this.random.nextInt(ConfigUtil.parseStringToInt("TEMPERATURE"))).floatValue();
        this.inclinationXaxisConfig = 2;
        this.inclinationYaxisConfig = 3;
        this.xAcceleration = 40.44f;
        this.yAcceleration = 54.77f;
        this.accelerationXaxisConfig = 6;
        this.accelerationYaxisConfig = 5;
        this.extraRoll = 45.34f;
        this.rollConfig = 1;
        this.globalCmd = 5;
        this.sensorStatus = 1;
        this.controlFlag = controlFlag;
    }

    public ReadResponse read(int resourceid) {
        LOG.info("Read on Accelerometer Resource " + resourceid);

        switch (resourceid) {

        case 1:
            return ReadResponse.success(resourceid, getTilt());
        case 2:
            return ReadResponse.success(resourceid, getRoll());

        case 3:
            return ReadResponse.success(resourceid, getInclinationXaxisConfig());
        case 4:
            return ReadResponse.success(resourceid, getInclinationYaxisConfig());
        case 5:
            return ReadResponse.success(resourceid, getxAcceleration());
        case 6:
            return ReadResponse.success(resourceid, getyAcceleration());
        case 7:
            return ReadResponse.success(resourceid, getAccelerationXaxisConfig());
        case 8:
            return ReadResponse.success(resourceid, getAccelerationYaxisConfig());
        case 9:
            return ReadResponse.success(resourceid, getExtraRoll());
        case 10:
            return ReadResponse.success(resourceid, getRollConfig());
        case 11:
            return ReadResponse.success(resourceid, getTemperature());
        case 12:
            return ReadResponse.success(resourceid, getGlobalCmd());
        case 14:
            return ReadResponse.success(resourceid, getSensorStatus());

        }
        return super.read(resourceid);
    }

    @Override
    public WriteResponse write(int resourceid, LwM2mResource value) {
        LOG.info("Inside write method resouce id :{}with value :{}", resourceid, value.getValue());
        if (controlFlag.getAccelerometerSensor() == 0) {

            return super.write(resourceid, value);
        }
        switch (resourceid) {

        case 1:

            moveTilt(Float.parseFloat("" + value.getValue()));
            return WriteResponse.success();

        case 2:
            moveRoll(Float.parseFloat("" + value.getValue()));
            return WriteResponse.success();

        case 3:
            moveInclinationXaxisConfig(Float.parseFloat("" + value.getValue()));
            return WriteResponse.success();
        case 4:
            moveInclinationYaxisConfig(Float.parseFloat("" + value.getValue()));
            return WriteResponse.success();
        case 5:
            moveXacceleration(Float.parseFloat("" + value.getValue()));
            return WriteResponse.success();
        case 6:
            moveYacceleration(Float.parseFloat("" + value.getValue()));
            return WriteResponse.success();
        case 7:
            moveAccelerationXaxisConfig(Float.parseFloat("" + value.getValue()));
            return WriteResponse.success();
        case 8:
            moveAccelerationYaxisConfig(Float.parseFloat("" + value.getValue()));
            return WriteResponse.success();
        case 9:
            moveExtraRoll(Float.parseFloat("" + value.getValue()));
            return WriteResponse.success();
        case 10:
            moverollConfig(Float.parseFloat("" + value.getValue()));
            return WriteResponse.success();
        case 12:
            moveGlobalCmd(Float.parseFloat("" + value.getValue()));
            return WriteResponse.success();
        case 14:
            moveSensorStatus(Integer.parseInt("" + value.getValue()));
            return WriteResponse.success();

        case 11:
            moveTemperature(Float.parseFloat("" + value.getValue()));
            return WriteResponse.success();

        default:
            return super.write(resourceid, value);
        }

    }

    public void moveInclinationXaxisConfig(float delta) {
        this.inclinationXaxisConfig = delta;
        fireResourcesChange(new int[] { 3, BluewavesOjbects.ACCELEROMETER });
    }

    public void moveInclinationYaxisConfig(float delta) {
        this.inclinationYaxisConfig = delta;
        fireResourcesChange(new int[] { 4, BluewavesOjbects.ACCELEROMETER });
    }

    public void moveXacceleration(float delta) {
        this.xAcceleration = delta;
        fireResourcesChange(new int[] { 5, BluewavesOjbects.ACCELEROMETER });
    }

    public void moveYacceleration(float delta) {
        this.yAcceleration = delta;
        fireResourcesChange(new int[] { 6, BluewavesOjbects.ACCELEROMETER });
    }

    public void moveAccelerationXaxisConfig(float delta) {
        this.accelerationXaxisConfig = delta;
        fireResourcesChange(new int[] { 7, BluewavesOjbects.ACCELEROMETER });
    }

    public void moveAccelerationYaxisConfig(float delta) {
        this.accelerationYaxisConfig = delta;
        fireResourcesChange(new int[] { 8, BluewavesOjbects.ACCELEROMETER });
    }

    public void moveExtraRoll(float delta) {
        this.extraRoll = delta;
        fireResourcesChange(new int[] { 9, BluewavesOjbects.ACCELEROMETER });
    }

    public void moverollConfig(float delta) {
        this.rollConfig = delta;
        fireResourcesChange(new int[] { 10, BluewavesOjbects.ACCELEROMETER });
    }

    public void moveGlobalCmd(float delta) {
        this.globalCmd = delta;
        fireResourcesChange(new int[] { 12, BluewavesOjbects.ACCELEROMETER });
    }

    public void moveSensorStatus(int delta) {
        this.sensorStatus = delta;
        fireResourcesChange(new int[] { 14, BluewavesOjbects.ACCELEROMETER });
    }

    public void moveRoll(float delta) {
        this.roll = delta;
        fireResourcesChange(new int[] { 2, BluewavesOjbects.ACCELEROMETER });
    }

    public void moveTilt(float delta) {
        this.tilt = delta;
        fireResourcesChange(new int[] { 1, BluewavesOjbects.ACCELEROMETER });
    }

    public void moveTemperature(float delta) {
        this.temperature = delta;
        fireResourcesChange(new int[] { 11, BluewavesOjbects.ACCELEROMETER });
    }

    public float getRoll() {
        return this.roll;
    }

    public float getTilt() {
        return this.tilt;
    }

    public float getTemperature() {
        return temperature;
    }

    public int getStatus() {
        return status;
    }

    public float getxAcceleration() {
        return xAcceleration;
    }

    public float getyAcceleration() {
        return yAcceleration;
    }

    public float getAccelerationXaxisConfig() {
        return accelerationXaxisConfig;
    }

    public float getAccelerationYaxisConfig() {
        return accelerationYaxisConfig;
    }

    public float getInclinationXaxisConfig() {
        return inclinationXaxisConfig;
    }

    public float getInclinationYaxisConfig() {
        return inclinationYaxisConfig;
    }

    public float getRollConfig() {
        return rollConfig;
    }

    public float getGlobalCmd() {
        return globalCmd;
    }

    public long getTs() {
        return ts;
    }

    public int getSensorStatus() {
        return sensorStatus;
    }

    public Float getExtraRoll() {
        return extraRoll;
    }

}
