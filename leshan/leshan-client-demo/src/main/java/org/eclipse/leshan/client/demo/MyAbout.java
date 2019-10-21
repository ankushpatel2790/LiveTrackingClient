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

import org.eclipse.leshan.client.resource.BaseInstanceEnabler;
import org.eclipse.leshan.core.response.ReadResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyAbout extends BaseInstanceEnabler {
    private static final Logger LOG = LoggerFactory.getLogger(MyAbout.class);

    private String macAddress;

    private String deviceName;

    private String deviceIp;

    private String internalCameraMac;

    private String externalCameraMac;

    private Integer deviceState;

    private Integer deviceMode;

    private String FirmwareVersion;

    private String hardwareVersion;

    private String upTime;

    private Boolean postStatus;

    private Integer coapServerPort;

    private Integer deviceLwm2mPort;

    private Integer accelerometerFlag;

    private Integer gpsSensorFlag;

    private Integer ecompassFlag;

    private Integer deviceFtpDataPort;

    private Integer deviceFtpPort;

    private Integer deviceTftpPort;

    private Integer deviceSshPort;

    private String currentTime;

    @Override
    public ReadResponse read(int resourceid) {
        LOG.info("Read on Device Resource " + resourceid);
        switch (resourceid) {
        case 1:
            return ReadResponse.success(resourceid, getMacAddress());
        case 2:
            return ReadResponse.success(resourceid, getDeviceName());
        case 3:
            return ReadResponse.success(resourceid, getDeviceIp());
        case 4:
            return ReadResponse.success(resourceid, getInternalCameraMac());
        case 5:
            return ReadResponse.success(resourceid, getExternalCameraMac());
        case 6:
            return ReadResponse.success(resourceid, getDeviceState());

        case 7:
            return ReadResponse.success(resourceid, getDeviceMode());
        case 8:
            return ReadResponse.success(resourceid, getFirmwareVersion());
        case 9:
            return ReadResponse.success(resourceid, getHardwareVersion());
        case 10:
            return ReadResponse.success(resourceid, getUpTime());

        case 11:
            return ReadResponse.success(resourceid, getPostStatus());
        case 17:
            return ReadResponse.success(resourceid, getAccelerometerFlag());
        case 18:
            return ReadResponse.success(resourceid, getGpsSensorFlag());

        case 19:
            return ReadResponse.success(resourceid, getEcompassFlag());
        case 20:
            return ReadResponse.success(resourceid, getCoapServerPort());
        case 21:
            return ReadResponse.success(resourceid, getDeviceLwm2mPort());
        case 23:
            return ReadResponse.success(resourceid, getDeviceFtpDataPort());
        case 24:
            return ReadResponse.success(resourceid, getDeviceFtpPort());
        case 25:
            return ReadResponse.success(resourceid, getDeviceTftpPort());
        case 26:
            return ReadResponse.success(resourceid, getDeviceSshPort());
        case 31:
            return ReadResponse.success(resourceid, getCurrentTime());
        default:
            return super.read(resourceid);
        }
    }

    public String getMacAddress() {

        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getDeviceName() {
        return deviceName = "Bluewave Simulator";
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceIp() {
        return deviceIp = "10.70.135.58";
    }

    public void setDeviceIp(String deviceIp) {
        this.deviceIp = deviceIp;
    }

    public String getInternalCameraMac() {
        return internalCameraMac = "00:22:4e:d1:00:0c";
    }

    public void setInternalCameraMac(String internalCameraMac) {
        this.internalCameraMac = internalCameraMac;
    }

    public String getExternalCameraMac() {
        return externalCameraMac = "bc:ad:28:67:84:df";
    }

    public void setExternalCameraMac(String externalCameraMac) {
        this.externalCameraMac = externalCameraMac;
    }

    public Integer getDeviceState() {
        return deviceState = 1;
    }

    public void setDeviceState(Integer deviceState) {
        this.deviceState = deviceState;
    }

    public Integer getDeviceMode() {
        return deviceMode = 1;
    }

    public void setDeviceMode(Integer deviceMode) {
        this.deviceMode = deviceMode;
    }

    public String getFirmwareVersion() {
        return FirmwareVersion = "BLUEWAVE_REL_VER_1_01_003";
    }

    public void setFirmwareVersion(String firmwareVersion) {
        FirmwareVersion = firmwareVersion;
    }

    public String getHardwareVersion() {
        return hardwareVersion = "68CB";
    }

    public void setHardwareVersion(String hardwareVersion) {
        this.hardwareVersion = hardwareVersion;
    }

    public String getUpTime() {
        return upTime = "0:0:0";
    }

    public void setUpTime(String upTime) {
        this.upTime = upTime;
    }

    public Boolean getPostStatus() {
        return postStatus = true;
    }

    public void setPostStatus(Boolean postStatus) {
        this.postStatus = postStatus;
    }

    public Integer getCoapServerPort() {
        return coapServerPort = 5683;
    }

    public void setCoapServerPort(Integer coapServerPort) {
        this.coapServerPort = coapServerPort;
    }

    public Integer getDeviceLwm2mPort() {
        return deviceLwm2mPort = 56830;
    }

    public void setDeviceLwm2mPort(Integer deviceLwm2mPort) {
        this.deviceLwm2mPort = deviceLwm2mPort;
    }

    public Integer getAccelerometerFlag() {
        return accelerometerFlag = 1;
    }

    public void setAccelerometerFlag(Integer accelerometerFlag) {
        this.accelerometerFlag = accelerometerFlag;
    }

    public Integer getGpsSensorFlag() {
        return gpsSensorFlag = 1;
    }

    public void setGpsSensorFlag(Integer gpsSensorFlag) {
        this.gpsSensorFlag = gpsSensorFlag;
    }

    public Integer getEcompassFlag() {
        return ecompassFlag = 1;
    }

    public void setEcompassFlag(Integer ecompassFlag) {
        this.ecompassFlag = ecompassFlag;
    }

    public Integer getDeviceFtpDataPort() {
        return deviceFtpDataPort = 20;
    }

    public void setDeviceFtpDataPort(Integer deviceFtpDataPort) {
        this.deviceFtpDataPort = deviceFtpDataPort;
    }

    public Integer getDeviceFtpPort() {
        return deviceFtpPort = 21;
    }

    public void setDeviceFtpPort(Integer deviceFtpPort) {
        this.deviceFtpPort = deviceFtpPort;
    }

    public Integer getDeviceTftpPort() {
        return deviceTftpPort = 69;
    }

    public void setDeviceTftpPort(Integer deviceTftpPort) {
        this.deviceTftpPort = deviceTftpPort;
    }

    public Integer getDeviceSshPort() {
        return deviceSshPort = 22;
    }

    public void setDeviceSshPort(Integer deviceSshPort) {
        this.deviceSshPort = deviceSshPort;
    }

    public String getCurrentTime() {
        return currentTime = new Date().toString();
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public static Logger getLog() {
        return LOG;
    }

}
