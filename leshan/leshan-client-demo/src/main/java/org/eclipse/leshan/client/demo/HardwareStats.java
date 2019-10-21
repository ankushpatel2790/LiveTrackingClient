/*******************************************************************************
 * Copyright (c) 2017 Sierra Wireless and others.
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

public class HardwareStats extends BaseInstanceEnabler {
    private static final Logger LOG = LoggerFactory.getLogger(HardwareStats.class);

    private int cpuUtilization;
    private int ramUsages;
    private int coreTemeprature;
    private String time;
    private boolean lastRebootCause;
    private Random random;
    private SensorControlFlag controlFlag;

    public HardwareStats(SensorControlFlag controlFlag) {
        super();
        this.cpuUtilization = 5;
        this.ramUsages = 134704;
        this.coreTemeprature = 28;
        this.time = new Date().toString();
        this.lastRebootCause = true;
        this.random = new Random();
        this.controlFlag = controlFlag;
    }

    public ReadResponse read(int resourceid) {
        LOG.info("Read on HardwareStats Resource " + resourceid);

        switch (resourceid) {

        case 1:
            return ReadResponse.success(resourceid, getCpuUtilization());

        case 2:
            return ReadResponse.success(resourceid, getRamUsages());
        case 3:
            return ReadResponse.success(resourceid, getCoreTemeprature());
        case 4:
            return ReadResponse.success(resourceid, getTime());
        case 5:
            return ReadResponse.success(resourceid, getLastRebootCause());

        }
        return super.read(resourceid);
    }

    @Override
    public WriteResponse write(int resourceid, LwM2mResource value) {
        LOG.info("Inside write method resouce id :{}with value :{}", resourceid, value.getValue());

        switch (resourceid) {

        case 1:

            moveCpuUtilization(BluewaveClientUtils.paserStringToInteger((value.getValue().toString())));
            return WriteResponse.success();

        case 2:

            moveRamUsages(BluewaveClientUtils.paserStringToInteger(value.getValue().toString()));
            return WriteResponse.success();

        case 3:
            moveCoreTemeprature(BluewaveClientUtils.paserStringToInteger(value.getValue().toString()));

            return WriteResponse.success();
        case 4:
            moveTime(value.getValue().toString());

            return WriteResponse.success();
        case 5:
            moveLastReboot(BluewaveClientUtils.paserStringToBoolean(value.getValue().toString()));

            return WriteResponse.success();

        default:
            return super.write(resourceid, value);
        }

    }

    public void moveCpuUtilization(int value) {
        this.cpuUtilization = value;
        fireResourcesChange(new int[] { 1, BluewavesOjbects.HARDWARE_STATS_OJBECT });
    }

    public void moveRamUsages(int value) {
        this.ramUsages = value;
        fireResourcesChange(new int[] { 2, BluewavesOjbects.HARDWARE_STATS_OJBECT });
    }

    public void moveCoreTemeprature(int value) {
        this.coreTemeprature = value;
        fireResourcesChange(new int[] { 3, BluewavesOjbects.HARDWARE_STATS_OJBECT });
    }

    public void moveTime(String value) {
        this.time = value;
        fireResourcesChange(new int[] { 4, BluewavesOjbects.HARDWARE_STATS_OJBECT });
    }

    public void moveLastReboot(boolean value) {
        this.lastRebootCause = value;
        fireResourcesChange(new int[] { 5, BluewavesOjbects.HARDWARE_STATS_OJBECT });
    }

    public int getCpuUtilization() {
        return cpuUtilization;
    }

    public void setCpuUtilization(int cpuUtilization) {
        this.cpuUtilization = cpuUtilization;
    }

    public int getRamUsages() {
        return ramUsages;
    }

    public void setRamUsages(int ramUsages) {
        this.ramUsages = ramUsages;
    }

    public int getCoreTemeprature() {
        return coreTemeprature;
    }

    public void setCoreTemeprature(int coreTemeprature) {
        this.coreTemeprature = coreTemeprature;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean getLastRebootCause() {
        return lastRebootCause;
    }

    public void setLastRebootCause(boolean lastRebootCause) {
        this.lastRebootCause = lastRebootCause;
    }

    public Random getRandom() {
        return random;
    }

    public void setRandom(Random random) {
        this.random = random;
    }

    public SensorControlFlag getControlFlag() {
        return controlFlag;
    }

    public void setControlFlag(SensorControlFlag controlFlag) {
        this.controlFlag = controlFlag;
    }

}
