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

public class SystemManagement extends BaseInstanceEnabler {
    private static final Logger LOG = LoggerFactory.getLogger(SystemManagement.class);

    public int udpPackRecieve;
    public int udpPackRcvUnknowPort;
    public int udpPackRcvError;
    public int udpPackSent;
    public int udpRcvbufErrors;
    public int udpSndbufErrors;

    public int udpInCsumErrors;
    public int tcpPackRecieve;
    public int tcpPackSent;
    public int tcpBadSegmentRcv;
    public int tcpPackRetransmit;
    public int tcpInCsumErrors;
    public int ipPackRcv;

    public int ipPackForwarded;
    public int ipPackDiscarded;
    public int ipPackDelivered;
    public int ipPackDropped;
    public int icmpMessageRcv;
    public int icmpMessageSent;
    public int icmpMessageFailed;
    public String timeStamp;
    private Random random;
    private SensorControlFlag controlFlag;

    public SystemManagement(SensorControlFlag controlFlag) {
        super();
        this.random = new Random();
        this.udpPackRecieve = 817666;
        this.udpPackRcvUnknowPort = 0;
        this.udpPackRcvError = 270366;
        this.udpPackSent = 1104002;
        this.udpRcvbufErrors = 0;
        this.udpSndbufErrors = 0;
        this.udpInCsumErrors = 0;
        this.tcpPackRecieve = 83714;
        this.tcpPackSent = 84603;
        this.tcpBadSegmentRcv = 4;
        this.tcpPackRetransmit = 0;
        this.tcpInCsumErrors = 0;
        this.ipPackRcv = 1172134;
        this.ipPackForwarded = 519;
        this.ipPackDiscarded = 0;
        this.ipPackDelivered = 0;
        this.ipPackDropped = 1171613;
        this.icmpMessageRcv = 555;
        this.icmpMessageSent = 18;
        this.icmpMessageFailed = 0;
        this.timeStamp = new Date().toString();
        this.controlFlag = controlFlag;
    }

    @Override
    public ReadResponse read(int resourceid) {
        LOG.info("Read on SystemManagement Resource " + resourceid);

        switch (resourceid) {

        case 1:
            return ReadResponse.success(resourceid, getUdpPackRecieve());

        case 2:
            return ReadResponse.success(resourceid, getUdpPackRcvUnknowPort());
        case 3:
            return ReadResponse.success(resourceid, getUdpPackRcvError());
        case 4:
            return ReadResponse.success(resourceid, getUdpPackSent());
        case 5:
            return ReadResponse.success(resourceid, getUdpRcvbufErrors());
        case 6:
            return ReadResponse.success(resourceid, getUdpSndbufErrors());
        case 7:
            return ReadResponse.success(resourceid, getUdpInCsumErrors());
        case 8:
            return ReadResponse.success(resourceid, getTcpPackRecieve());
        case 9:
            return ReadResponse.success(resourceid, getTcpPackSent());
        case 10:
            return ReadResponse.success(resourceid, getTcpPackRetransmit());
        case 11:
            return ReadResponse.success(resourceid, getTcpBadSegmentRcv());
        case 12:
            return ReadResponse.success(resourceid, getTcpInCsumErrors());

        case 13:
            return ReadResponse.success(resourceid, getIpPackRcv());
        case 14:
            return ReadResponse.success(resourceid, getIpPackForwarded());
        case 15:
            return ReadResponse.success(resourceid, getIpPackDiscarded());
        case 16:
            return ReadResponse.success(resourceid, getIpPackDelivered());
        case 17:
            return ReadResponse.success(resourceid, getIpPackDropped());
        case 18:
            return ReadResponse.success(resourceid, getIcmpMessageRcv());
        case 19:
            return ReadResponse.success(resourceid, getIcmpMessageSent());

        case 20:
            return ReadResponse.success(resourceid, getIcmpMessageFailed());
        case 21:
            return ReadResponse.success(resourceid, getTimeStamp());

        }
        return super.read(resourceid);
    }

    @Override
    public WriteResponse write(int resourceid, LwM2mResource value) {
        LOG.info("Inside write method resouce id :{}with value :{}", resourceid, value.getValue());

        switch (resourceid) {

        case 1:

            moveUdpPackRcv(BluewaveClientUtils.paserStringToInteger((value.getValue().toString())));
            return WriteResponse.success();

        case 2:

            moveUdpPackRcvUnknowPort(BluewaveClientUtils.paserStringToInteger(value.getValue().toString()));
            return WriteResponse.success();

        case 3:
            moveUdpPackRcvError(BluewaveClientUtils.paserStringToInteger(value.getValue().toString()));

            return WriteResponse.success();
        case 4:
            moveUdpPackSent(BluewaveClientUtils.paserStringToInteger(value.getValue().toString()));

            return WriteResponse.success();
        case 5:
            moveUdpRcvbufErrors(BluewaveClientUtils.paserStringToInteger(value.getValue().toString()));

            return WriteResponse.success();
        case 6:
            moveUdpSndbufErrors(BluewaveClientUtils.paserStringToInteger(value.getValue().toString()));
            return WriteResponse.success();
        case 7:
            moveUdpInCsumErrors(BluewaveClientUtils.paserStringToInteger(value.getValue().toString()));
            return WriteResponse.success();
        case 8:
            moveTcpPackRecieve(BluewaveClientUtils.paserStringToInteger(value.getValue().toString()));
            return WriteResponse.success();
        case 9:
            moveTcpPackSent(BluewaveClientUtils.paserStringToInteger(value.getValue().toString()));
            return WriteResponse.success();
        case 10:

            moveTcpPackRetransmit(BluewaveClientUtils.paserStringToInteger(value.getValue().toString()));
            return WriteResponse.success();

        case 11:
            moveTcpBadSegmentRcv(BluewaveClientUtils.paserStringToInteger(value.getValue().toString()));
            return WriteResponse.success();
        case 12:
            moveTcpInCsumErrors(BluewaveClientUtils.paserStringToInteger(value.getValue().toString()));
            return WriteResponse.success();

        case 13:
            moveIpPackRcv(BluewaveClientUtils.paserStringToInteger(value.getValue().toString()));
            return WriteResponse.success();

        case 14:

            moveIpPackForwarded(BluewaveClientUtils.paserStringToInteger(value.getValue().toString()));
            return WriteResponse.success();

        case 15:

            moveIpPackDiscarded(BluewaveClientUtils.paserStringToInteger((value.getValue().toString())));
            return WriteResponse.success();

        case 16:

            moveIpPackDelivered(BluewaveClientUtils.paserStringToInteger(value.getValue().toString()));
            return WriteResponse.success();

        case 17:
            moveIpPackDropped(BluewaveClientUtils.paserStringToInteger(value.getValue().toString()));

            return WriteResponse.success();
        case 18:
            moveIcmpMessageRcv(BluewaveClientUtils.paserStringToInteger(value.getValue().toString()));

            return WriteResponse.success();
        case 19:
            moveIcmpMessageSent(BluewaveClientUtils.paserStringToInteger(value.getValue().toString()));

            return WriteResponse.success();
        case 20:
            moveIcmpMessageFailed(BluewaveClientUtils.paserStringToInteger(value.getValue().toString()));
            return WriteResponse.success();

        default:
            return super.write(resourceid, value);
        }

    }

    public void moveUdpPackRcv(int delta) {
        this.udpPackRecieve = delta;
        fireResourcesChange(new int[] { 1, BluewavesOjbects.SYSTEM_MANAGEMENT });
    }

    public void moveUdpPackRcvUnknowPort(int delta) {

        this.udpPackRcvUnknowPort = delta;
        fireResourcesChange(new int[] { 2, BluewavesOjbects.SYSTEM_MANAGEMENT });
    }

    public void moveUdpPackRcvError(int delta) {

        this.udpPackRcvError = delta;
        fireResourcesChange(new int[] { 3, BluewavesOjbects.SYSTEM_MANAGEMENT });
    }

    public void moveUdpPackSent(int delta) {
        this.udpPackSent = delta;
        fireResourcesChange(new int[] { 4, BluewavesOjbects.SYSTEM_MANAGEMENT });
    }

    public void moveUdpRcvbufErrors(int delta) {

        this.udpRcvbufErrors = delta;
        fireResourcesChange(new int[] { 5, BluewavesOjbects.SYSTEM_MANAGEMENT });
    }

    public void moveUdpSndbufErrors(int delta) {

        this.udpSndbufErrors = delta;
        fireResourcesChange(new int[] { 6, BluewavesOjbects.SYSTEM_MANAGEMENT });
    }

    public void moveUdpInCsumErrors(int delta) {

        this.udpInCsumErrors = delta;
        fireResourcesChange(new int[] { 7, BluewavesOjbects.SYSTEM_MANAGEMENT });
    }

    public void moveTcpPackRecieve(int delta) {
        this.tcpPackRecieve = delta;
        fireResourcesChange(new int[] { 8, BluewavesOjbects.SYSTEM_MANAGEMENT });
    }

    public void moveTcpPackSent(int delta) {
        this.tcpPackSent = delta;
        fireResourcesChange(new int[] { 9, BluewavesOjbects.SYSTEM_MANAGEMENT });
    }

    public void moveTcpPackRetransmit(int delta) {

        this.tcpPackRetransmit = delta;
        fireResourcesChange(new int[] { 10, BluewavesOjbects.SYSTEM_MANAGEMENT });
    }

    public void moveTcpBadSegmentRcv(int delta) {
        this.tcpBadSegmentRcv = delta;
        fireResourcesChange(new int[] { 11, BluewavesOjbects.SYSTEM_MANAGEMENT });
    }

    public void moveTcpInCsumErrors(int delta) {
        this.tcpInCsumErrors = delta;
        fireResourcesChange(new int[] { 12, BluewavesOjbects.SYSTEM_MANAGEMENT });
    }

    public void moveIpPackRcv(int delta) {
        this.ipPackRcv = delta;
        fireResourcesChange(new int[] { 13, BluewavesOjbects.SYSTEM_MANAGEMENT });
    }

    public void moveIpPackForwarded(int delta) {
        this.ipPackForwarded = delta;
        fireResourcesChange(new int[] { 14, BluewavesOjbects.SYSTEM_MANAGEMENT });
    }

    public void moveIpPackDiscarded(int delta) {
        this.ipPackDiscarded = delta;
        fireResourcesChange(new int[] { 15, BluewavesOjbects.SYSTEM_MANAGEMENT });
    }

    public void moveIpPackDelivered(int delta) {

        this.ipPackDelivered = delta;
        fireResourcesChange(new int[] { 16, BluewavesOjbects.SYSTEM_MANAGEMENT });
    }

    public void moveIpPackDropped(int delta) {
        this.ipPackDropped = delta;
        fireResourcesChange(new int[] { 17, BluewavesOjbects.SYSTEM_MANAGEMENT });
    }

    public void moveIcmpMessageRcv(int delta) {

        this.icmpMessageRcv = delta;
        fireResourcesChange(new int[] { 18, BluewavesOjbects.SYSTEM_MANAGEMENT });
    }

    public void moveIcmpMessageSent(int delta) {

        this.icmpMessageSent = delta;
        fireResourcesChange(new int[] { 19, BluewavesOjbects.SYSTEM_MANAGEMENT });
    }

    public void moveIcmpMessageFailed(int delta) {
        this.icmpMessageFailed = delta;
        fireResourcesChange(new int[] { 20, BluewavesOjbects.SYSTEM_MANAGEMENT });
    }

    public void moveTimeStamp(String date) {
        this.timeStamp = date;
        fireResourcesChange(new int[] { 21, BluewavesOjbects.SYSTEM_MANAGEMENT });
    }

    public int getUdpPackRecieve() {
        return udpPackRecieve;
    }

    public void setUdpPackRecieve(int udpPackRecieve) {
        this.udpPackRecieve = udpPackRecieve;
    }

    public int getUdpPackRcvUnknowPort() {
        return udpPackRcvUnknowPort;
    }

    public void setUdpPackRcvUnknowPort(int udpPackRcvUnknowPort) {
        this.udpPackRcvUnknowPort = udpPackRcvUnknowPort;
    }

    public int getUdpPackRcvError() {
        return udpPackRcvError;
    }

    public void setUdpPackRcvError(int udpPackRcvError) {
        this.udpPackRcvError = udpPackRcvError;
    }

    public int getUdpPackSent() {
        return udpPackSent;
    }

    public void setUdpPackSent(int udpPackSent) {
        this.udpPackSent = udpPackSent;
    }

    public int getUdpRcvbufErrors() {
        return udpRcvbufErrors;
    }

    public void setUdpRcvbufErrors(int udpRcvbufErrors) {
        this.udpRcvbufErrors = udpRcvbufErrors;
    }

    public int getUdpSndbufErrors() {
        return udpSndbufErrors;
    }

    public void setUdpSndbufErrors(int udpSndbufErrors) {
        this.udpSndbufErrors = udpSndbufErrors;
    }

    public int getUdpInCsumErrors() {
        return udpInCsumErrors;
    }

    public void setUdpInCsumErrors(int udpInCsumErrors) {
        this.udpInCsumErrors = udpInCsumErrors;
    }

    public int getTcpPackRecieve() {
        return tcpPackRecieve;
    }

    public void setTcpPackRecieve(int tcpPackRecieve) {
        this.tcpPackRecieve = tcpPackRecieve;
    }

    public int getTcpPackSent() {
        return tcpPackSent;
    }

    public void setTcpPackSent(int tcpPackSent) {
        this.tcpPackSent = tcpPackSent;
    }

    public int getTcpBadSegmentRcv() {
        return tcpBadSegmentRcv;
    }

    public void setTcpBadSegmentRcv(int tcpBadSegmentRcv) {
        this.tcpBadSegmentRcv = tcpBadSegmentRcv;
    }

    public int getTcpPackRetransmit() {
        return tcpPackRetransmit;
    }

    public void setTcpPackRetransmit(int tcpPackRetransmit) {
        this.tcpPackRetransmit = tcpPackRetransmit;
    }

    public int getTcpInCsumErrors() {
        return tcpInCsumErrors;
    }

    public void setTcpInCsumErrors(int tcpInCsumErrors) {
        this.tcpInCsumErrors = tcpInCsumErrors;
    }

    public int getIpPackRcv() {
        return ipPackRcv;
    }

    public void setIpPackRcv(int ipPackRcv) {
        this.ipPackRcv = ipPackRcv;
    }

    public int getIpPackForwarded() {
        return ipPackForwarded;
    }

    public void setIpPackForwarded(int ipPackForwarded) {
        this.ipPackForwarded = ipPackForwarded;
    }

    public int getIpPackDiscarded() {
        return ipPackDiscarded;
    }

    public void setIpPackDiscarded(int ipPackDiscarded) {
        this.ipPackDiscarded = ipPackDiscarded;
    }

    public int getIpPackDelivered() {
        return ipPackDelivered;
    }

    public void setIpPackDelivered(int ipPackDelivered) {
        this.ipPackDelivered = ipPackDelivered;
    }

    public int getIpPackDropped() {
        return ipPackDropped;
    }

    public void setIpPackDropped(int ipPackDropped) {
        this.ipPackDropped = ipPackDropped;
    }

    public int getIcmpMessageRcv() {
        return icmpMessageRcv;
    }

    public void setIcmpMessageRcv(int icmpMessageRcv) {
        this.icmpMessageRcv = icmpMessageRcv;
    }

    public int getIcmpMessageSent() {
        return icmpMessageSent;
    }

    public void setIcmpMessageSent(int icmpMessageSent) {
        this.icmpMessageSent = icmpMessageSent;
    }

    public int getIcmpMessageFailed() {
        return icmpMessageFailed;
    }

    public void setIcmpMessageFailed(int icmpMessageFailed) {
        this.icmpMessageFailed = icmpMessageFailed;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
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
