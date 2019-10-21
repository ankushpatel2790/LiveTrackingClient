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

import org.eclipse.leshan.client.resource.BaseInstanceEnabler;
import org.eclipse.leshan.core.node.LwM2mResource;
import org.eclipse.leshan.core.response.ReadResponse;
import org.eclipse.leshan.core.response.WriteResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bluewaves.model.BluewavesOjbects;

public class Lwm2mStats extends BaseInstanceEnabler {
    private static final Logger LOG = LoggerFactory.getLogger(Lwm2mStats.class);

    private int bootstrapWriteMsg;
    private int unBootstrapWtResp;
    private int boostrapDelMsg;
    private int unBootstrapDelResp;
    private int registerMsg;
    private int unRegisterResp;
    private int updateMsg;
    private int unUpdatedMsgResp;
    private int deRegisterMsg;
    private int unDeRegisterResp;
    private int readMsg;
    private int unReadMsgResp;
    private int discoverMsg;
    private int unDiscoverMsgResp;
    private int writeMsg;
    private int unWriteMsgResp;
    private int writeAttributeMsg;
    private int executeMsg;
    private int unExecuteMsgResp;
    private int createMsg;
    private int unCreateMsgResp;
    private int deleteMsg;
    private int unDeleteMsgResp;
    private int observeMsg;
    private int unObserveMsgResp;
    private int notifyMsg;
    private int unNotifyMsgResp;
    private int cancelObservationMsg;
    private int unCancelObsMsgResp;
    private String time;
    private SensorControlFlag controlFlag;

    public Lwm2mStats(SensorControlFlag controlFlag) {
        super();
        this.controlFlag = controlFlag;
        bootstrapWriteMsg = 1;
        unBootstrapWtResp = 6;
        boostrapDelMsg = 0;
        unBootstrapDelResp = 0;
        registerMsg = 1;
        unRegisterResp = 4;
        updateMsg = 3;
        unUpdatedMsgResp = 0;
        deRegisterMsg = 0;
        unDeRegisterResp = 0;
        readMsg = 2160;
        unReadMsgResp = 0;
        discoverMsg = 0;
        unDiscoverMsgResp = 0;
        writeMsg = 2;
        unWriteMsgResp = 3;
        writeAttributeMsg = 0;
        executeMsg = 0;
        unExecuteMsgResp = 0;
        createMsg = 0;
        unCreateMsgResp = 0;
        deleteMsg = 0;
        unDeleteMsgResp = 0;
        observeMsg = 0;
        unObserveMsgResp = 10;
        notifyMsg = 0;
        unNotifyMsgResp = 0;
        cancelObservationMsg = 0;
        unCancelObsMsgResp = 0;
        time = new Date().toString();
    }

    @Override
    public ReadResponse read(int resourceid) {
        LOG.info("Read on SystemManagement Resource " + resourceid);

        switch (resourceid) {

        case 1:
            return ReadResponse.success(resourceid, getBootstrapWriteMsg());

        case 2:
            return ReadResponse.success(resourceid, getUnBootstrapWtResp());
        case 3:
            return ReadResponse.success(resourceid, getBoostrapDelMsg());
        case 4:
            return ReadResponse.success(resourceid, getUnBootstrapDelResp());
        case 5:
            return ReadResponse.success(resourceid, getRegisterMsg());
        case 6:
            return ReadResponse.success(resourceid, getUnRegisterResp());
        case 7:
            return ReadResponse.success(resourceid, getUpdateMsg());
        case 8:
            return ReadResponse.success(resourceid, getUnUpdatedMsgResp());
        case 9:
            return ReadResponse.success(resourceid, getDeRegisterMsg());
        case 10:
            return ReadResponse.success(resourceid, getUnDeRegisterResp());
        case 11:
            return ReadResponse.success(resourceid, getReadMsg());
        case 12:
            return ReadResponse.success(resourceid, getUnReadMsgResp());

        case 13:
            return ReadResponse.success(resourceid, getDiscoverMsg());
        case 14:
            return ReadResponse.success(resourceid, getUnDiscoverMsgResp());
        case 15:
            return ReadResponse.success(resourceid, getWriteMsg());
        case 16:
            return ReadResponse.success(resourceid, getUnWriteMsgResp());
        case 17:
            return ReadResponse.success(resourceid, getWriteAttributeMsg());
        case 18:
            return ReadResponse.success(resourceid, getExecuteMsg());
        case 19:
            return ReadResponse.success(resourceid, getUnExecuteMsgResp());

        case 20:
            return ReadResponse.success(resourceid, getCreateMsg());
        case 21:
            return ReadResponse.success(resourceid, getUnCreateMsgResp());

        case 22:
            return ReadResponse.success(resourceid, getDeleteMsg());
        case 23:
            return ReadResponse.success(resourceid, getUnDeleteMsgResp());
        case 24:
            return ReadResponse.success(resourceid, getObserveMsg());
        case 25:
            return ReadResponse.success(resourceid, getUnObserveMsgResp());
        case 26:
            return ReadResponse.success(resourceid, getNotifyMsg());

        case 27:
            return ReadResponse.success(resourceid, getUnNotifyMsgResp());
        case 28:
            return ReadResponse.success(resourceid, getCancelObservationMsg());

        case 29:
            return ReadResponse.success(resourceid, getUnCancelObsMsgResp());
        case 30:
            return ReadResponse.success(resourceid, getTime());

        }
        return super.read(resourceid);
    }

    @Override
    public WriteResponse write(int resourceid, LwM2mResource value) {
        LOG.info("Inside write method resouce id :{}with value :{}", resourceid, value.getValue());

        switch (resourceid) {

        case 1:

            moveBootstrapWriteMsg(BluewaveClientUtils.paserStringToInteger((value.getValue().toString())));
            return WriteResponse.success();

        case 2:

            moveUnBootstrapWtResp(BluewaveClientUtils.paserStringToInteger(value.getValue().toString()));
            return WriteResponse.success();

        case 3:
            moveBoostrapDelMsg(BluewaveClientUtils.paserStringToInteger(value.getValue().toString()));

            return WriteResponse.success();
        case 4:
            moveUnBootstrapDelResp(BluewaveClientUtils.paserStringToInteger(value.getValue().toString()));

            return WriteResponse.success();
        case 5:
            moveRegisterMsg(BluewaveClientUtils.paserStringToInteger(value.getValue().toString()));

            return WriteResponse.success();
        case 6:
            moveUnRegisterResp(BluewaveClientUtils.paserStringToInteger(value.getValue().toString()));
            return WriteResponse.success();
        case 7:
            moveUpdateMsg(BluewaveClientUtils.paserStringToInteger(value.getValue().toString()));
            return WriteResponse.success();
        case 8:
            moveUnUpdatedMsgResp(BluewaveClientUtils.paserStringToInteger(value.getValue().toString()));
            return WriteResponse.success();
        case 9:
            moveDeRegisterMsg(BluewaveClientUtils.paserStringToInteger(value.getValue().toString()));
            return WriteResponse.success();
        case 10:

            moveUnDeRegisterResp(BluewaveClientUtils.paserStringToInteger(value.getValue().toString()));
            return WriteResponse.success();

        case 11:
            moveReadMsg(BluewaveClientUtils.paserStringToInteger(value.getValue().toString()));
            return WriteResponse.success();
        case 12:
            moveUnReadMsgResp(BluewaveClientUtils.paserStringToInteger(value.getValue().toString()));
            return WriteResponse.success();

        case 13:
            moveDiscoverMsg(BluewaveClientUtils.paserStringToInteger(value.getValue().toString()));
            return WriteResponse.success();

        case 14:

            moveUnDiscoverMsgResp(BluewaveClientUtils.paserStringToInteger(value.getValue().toString()));
            return WriteResponse.success();

        case 15:

            moveWriteMsg(BluewaveClientUtils.paserStringToInteger((value.getValue().toString())));
            return WriteResponse.success();

        case 16:

            moveUnWriteMsgResp(BluewaveClientUtils.paserStringToInteger(value.getValue().toString()));
            return WriteResponse.success();

        case 17:
            moveWriteAttributeMsg(BluewaveClientUtils.paserStringToInteger(value.getValue().toString()));

            return WriteResponse.success();
        case 18:
            moveExecuteMsg(BluewaveClientUtils.paserStringToInteger(value.getValue().toString()));

            return WriteResponse.success();
        case 19:
            moveUnExecuteMsgResp(BluewaveClientUtils.paserStringToInteger(value.getValue().toString()));

            return WriteResponse.success();
        case 20:
            moveCreateMsg(BluewaveClientUtils.paserStringToInteger(value.getValue().toString()));
            return WriteResponse.success();

        case 21:
            moveUnCreateMsgResp(BluewaveClientUtils.paserStringToInteger(value.getValue().toString()));
            return WriteResponse.success();
        case 22:
            moveDeleteMsg(BluewaveClientUtils.paserStringToInteger(value.getValue().toString()));
            return WriteResponse.success();

        case 23:
            moveUnDeleteMsgResp(BluewaveClientUtils.paserStringToInteger(value.getValue().toString()));
            return WriteResponse.success();

        case 24:

            moveObserveMsg(BluewaveClientUtils.paserStringToInteger(value.getValue().toString()));
            return WriteResponse.success();

        case 25:

            moveUnObserveMsgResp(BluewaveClientUtils.paserStringToInteger((value.getValue().toString())));
            return WriteResponse.success();

        case 26:

            moveNotifyMsg(BluewaveClientUtils.paserStringToInteger(value.getValue().toString()));
            return WriteResponse.success();

        case 27:
            moveUnNotifyMsgResp(BluewaveClientUtils.paserStringToInteger(value.getValue().toString()));

            return WriteResponse.success();
        case 28:
            moveCancelObservationMsg(BluewaveClientUtils.paserStringToInteger(value.getValue().toString()));

            return WriteResponse.success();
        case 29:
            moveUnCancelObsMsgResp(BluewaveClientUtils.paserStringToInteger(value.getValue().toString()));

            return WriteResponse.success();
        case 30:
            moveTime(value.getValue().toString());
            return WriteResponse.success();

        default:
            return super.write(resourceid, value);
        }

    }

    public void moveBootstrapWriteMsg(int delta) {
        this.bootstrapWriteMsg = delta;
        fireResourcesChange(new int[] { 1, BluewavesOjbects.LWM2M_STATS_OBJECT });
    }

    public void moveUnBootstrapWtResp(int delta) {
        this.unBootstrapWtResp = delta;
        fireResourcesChange(new int[] { 2, BluewavesOjbects.LWM2M_STATS_OBJECT });
    }

    public void moveBoostrapDelMsg(int delta) {
        this.boostrapDelMsg = delta;
        fireResourcesChange(new int[] { 3, BluewavesOjbects.LWM2M_STATS_OBJECT });
    }

    public void moveUnBootstrapDelResp(int delta) {
        this.unBootstrapDelResp = delta;
        fireResourcesChange(new int[] { 4, BluewavesOjbects.LWM2M_STATS_OBJECT });
    }

    public void moveRegisterMsg(int delta) {
        this.registerMsg = delta;
        fireResourcesChange(new int[] { 5, BluewavesOjbects.LWM2M_STATS_OBJECT });
    }

    public void moveUnRegisterResp(int delta) {
        this.unRegisterResp = delta;
        fireResourcesChange(new int[] { 6, BluewavesOjbects.LWM2M_STATS_OBJECT });
    }

    public void moveUpdateMsg(int delta) {
        this.updateMsg = delta;
        fireResourcesChange(new int[] { 7, BluewavesOjbects.LWM2M_STATS_OBJECT });
    }

    public void moveUnUpdatedMsgResp(int delta) {
        this.unUpdatedMsgResp = delta;
        fireResourcesChange(new int[] { 8, BluewavesOjbects.LWM2M_STATS_OBJECT });
    }

    public void moveDeRegisterMsg(int delta) {
        this.deRegisterMsg = delta;
        fireResourcesChange(new int[] { 9, BluewavesOjbects.LWM2M_STATS_OBJECT });
    }

    public void moveUnDeRegisterResp(int delta) {
        this.unDeRegisterResp = delta;
        fireResourcesChange(new int[] { 10, BluewavesOjbects.LWM2M_STATS_OBJECT });
    }

    public void moveReadMsg(int delta) {
        this.readMsg = delta;
        fireResourcesChange(new int[] { 11, BluewavesOjbects.LWM2M_STATS_OBJECT });
    }

    public void moveUnReadMsgResp(int delta) {
        this.unReadMsgResp = delta;
        fireResourcesChange(new int[] { 12, BluewavesOjbects.LWM2M_STATS_OBJECT });
    }

    public void moveDiscoverMsg(int delta) {
        this.discoverMsg = delta;
        fireResourcesChange(new int[] { 13, BluewavesOjbects.LWM2M_STATS_OBJECT });
    }

    public void moveUnDiscoverMsgResp(int delta) {
        this.unDiscoverMsgResp = delta;
        fireResourcesChange(new int[] { 14, BluewavesOjbects.LWM2M_STATS_OBJECT });
    }

    public void moveWriteMsg(int delta) {
        this.writeMsg = delta;
        fireResourcesChange(new int[] { 15, BluewavesOjbects.LWM2M_STATS_OBJECT });
    }

    public void moveUnWriteMsgResp(int delta) {
        this.unWriteMsgResp = delta;
        fireResourcesChange(new int[] { 16, BluewavesOjbects.LWM2M_STATS_OBJECT });
    }

    public void moveWriteAttributeMsg(int delta) {
        this.writeAttributeMsg = delta;
        fireResourcesChange(new int[] { 17, BluewavesOjbects.LWM2M_STATS_OBJECT });
    }

    public void moveExecuteMsg(int delta) {
        this.executeMsg = delta;
        fireResourcesChange(new int[] { 18, BluewavesOjbects.LWM2M_STATS_OBJECT });
    }

    public void moveUnExecuteMsgResp(int delta) {
        this.unExecuteMsgResp = delta;
        fireResourcesChange(new int[] { 19, BluewavesOjbects.LWM2M_STATS_OBJECT });
    }

    public void moveCreateMsg(int delta) {
        this.createMsg = delta;
        fireResourcesChange(new int[] { 20, BluewavesOjbects.LWM2M_STATS_OBJECT });
    }

    public void moveUnCreateMsgResp(int delta) {
        this.unCreateMsgResp = delta;
        fireResourcesChange(new int[] { 21, BluewavesOjbects.LWM2M_STATS_OBJECT });
    }

    public void moveDeleteMsg(int delta) {
        this.deleteMsg = delta;
        fireResourcesChange(new int[] { 22, BluewavesOjbects.LWM2M_STATS_OBJECT });
    }

    public void moveUnDeleteMsgResp(int delta) {
        this.unDeleteMsgResp = delta;
        fireResourcesChange(new int[] { 23, BluewavesOjbects.LWM2M_STATS_OBJECT });
    }

    public void moveObserveMsg(int delta) {
        this.observeMsg = delta;
        fireResourcesChange(new int[] { 24, BluewavesOjbects.LWM2M_STATS_OBJECT });
    }

    public void moveUnObserveMsgResp(int delta) {
        this.unObserveMsgResp = delta;
        fireResourcesChange(new int[] { 25, BluewavesOjbects.LWM2M_STATS_OBJECT });
    }

    public void moveNotifyMsg(int delta) {
        this.notifyMsg = delta;
        fireResourcesChange(new int[] { 26, BluewavesOjbects.LWM2M_STATS_OBJECT });
    }

    public void moveUnNotifyMsgResp(int delta) {
        this.unNotifyMsgResp = delta;
        fireResourcesChange(new int[] { 27, BluewavesOjbects.LWM2M_STATS_OBJECT });
    }

    public void moveCancelObservationMsg(int delta) {
        this.cancelObservationMsg = delta;
        fireResourcesChange(new int[] { 28, BluewavesOjbects.LWM2M_STATS_OBJECT });
    }

    public void moveUnCancelObsMsgResp(int delta) {
        this.unCancelObsMsgResp = delta;
        fireResourcesChange(new int[] { 29, BluewavesOjbects.LWM2M_STATS_OBJECT });
    }

    public void moveTime(String delta) {
        this.time = delta;
        fireResourcesChange(new int[] { 30, BluewavesOjbects.LWM2M_STATS_OBJECT });
    }

    public int getBootstrapWriteMsg() {
        return bootstrapWriteMsg;
    }

    public void setBootstrapWriteMsg(int bootstrapWriteMsg) {
        this.bootstrapWriteMsg = bootstrapWriteMsg;
    }

    public int getBoostrapDelMsg() {
        return boostrapDelMsg;
    }

    public void setBoostrapDelMsg(int boostrapDelMsg) {
        this.boostrapDelMsg = boostrapDelMsg;
    }

    public int getUnBootstrapWtResp() {
        return unBootstrapWtResp;
    }

    public void setUnBootstrapWtResp(int unBootstrapWtResp) {
        this.unBootstrapWtResp = unBootstrapWtResp;
    }

    public int getUnBootstrapDelResp() {
        return unBootstrapDelResp;
    }

    public void setUnBootstrapDelResp(int unBootstrapDelResp) {
        this.unBootstrapDelResp = unBootstrapDelResp;
    }

    public int getRegisterMsg() {
        return registerMsg;
    }

    public void setRegisterMsg(int registerMsg) {
        this.registerMsg = registerMsg;
    }

    public int getUnRegisterResp() {
        return unRegisterResp;
    }

    public void setUnRegisterResp(int unRegisterResp) {
        this.unRegisterResp = unRegisterResp;
    }

    public int getUpdateMsg() {
        return updateMsg;
    }

    public void setUpdateMsg(int updateMsg) {
        this.updateMsg = updateMsg;
    }

    public int getUnUpdatedMsgResp() {
        return unUpdatedMsgResp;
    }

    public void setUnUpdatedMsgResp(int unUpdatedMsgResp) {
        this.unUpdatedMsgResp = unUpdatedMsgResp;
    }

    public int getDeRegisterMsg() {
        return deRegisterMsg;
    }

    public void setDeRegisterMsg(int deRegisterMsg) {
        this.deRegisterMsg = deRegisterMsg;
    }

    public int getUnDeRegisterResp() {
        return unDeRegisterResp;
    }

    public void setUnDeRegisterResp(int unDeRegisterResp) {
        this.unDeRegisterResp = unDeRegisterResp;
    }

    public int getReadMsg() {
        return readMsg;
    }

    public void setReadMsg(int readMsg) {
        this.readMsg = readMsg;
    }

    public int getUnReadMsgResp() {
        return unReadMsgResp;
    }

    public void setUnReadMsgResp(int unReadMsgResp) {
        this.unReadMsgResp = unReadMsgResp;
    }

    public int getDiscoverMsg() {
        return discoverMsg;
    }

    public void setDiscoverMsg(int discoverMsg) {
        this.discoverMsg = discoverMsg;
    }

    public int getUnDiscoverMsgResp() {
        return unDiscoverMsgResp;
    }

    public void setUnDiscoverMsgResp(int unDiscoverMsgResp) {
        this.unDiscoverMsgResp = unDiscoverMsgResp;
    }

    public int getWriteMsg() {
        return writeMsg;
    }

    public void setWriteMsg(int writeMsg) {
        this.writeMsg = writeMsg;
    }

    public int getUnWriteMsgResp() {
        return unWriteMsgResp;
    }

    public void setUnWriteMsgResp(int unWriteMsgResp) {
        this.unWriteMsgResp = unWriteMsgResp;
    }

    public int getWriteAttributeMsg() {
        return writeAttributeMsg;
    }

    public void setWriteAttributeMsg(int writeAttributeMsg) {
        this.writeAttributeMsg = writeAttributeMsg;
    }

    public int getExecuteMsg() {
        return executeMsg;
    }

    public void setExecuteMsg(int executeMsg) {
        this.executeMsg = executeMsg;
    }

    public int getUnExecuteMsgResp() {
        return unExecuteMsgResp;
    }

    public void setUnExecuteMsgResp(int unExecuteMsgResp) {
        this.unExecuteMsgResp = unExecuteMsgResp;
    }

    public int getCreateMsg() {
        return createMsg;
    }

    public void setCreateMsg(int createMsg) {
        this.createMsg = createMsg;
    }

    public int getUnCreateMsgResp() {
        return unCreateMsgResp;
    }

    public void setUnCreateMsgResp(int unCreateMsgResp) {
        this.unCreateMsgResp = unCreateMsgResp;
    }

    public int getDeleteMsg() {
        return deleteMsg;
    }

    public void setDeleteMsg(int deleteMsg) {
        this.deleteMsg = deleteMsg;
    }

    public int getUnDeleteMsgResp() {
        return unDeleteMsgResp;
    }

    public void setUnDeleteMsgResp(int unDeleteMsgResp) {
        this.unDeleteMsgResp = unDeleteMsgResp;
    }

    public int getObserveMsg() {
        return observeMsg;
    }

    public void setObserveMsg(int observeMsg) {
        this.observeMsg = observeMsg;
    }

    public int getUnObserveMsgResp() {
        return unObserveMsgResp;
    }

    public void setUnObserveMsgResp(int unObserveMsgResp) {
        this.unObserveMsgResp = unObserveMsgResp;
    }

    public int getNotifyMsg() {
        return notifyMsg;
    }

    public void setNotifyMsg(int notifyMsg) {
        this.notifyMsg = notifyMsg;
    }

    public int getUnNotifyMsgResp() {
        return unNotifyMsgResp;
    }

    public void setUnNotifyMsgResp(int unNotifyMsgResp) {
        this.unNotifyMsgResp = unNotifyMsgResp;
    }

    public int getCancelObservationMsg() {
        return cancelObservationMsg;
    }

    public void setCancelObservationMsg(int cancelObservationMsg) {
        this.cancelObservationMsg = cancelObservationMsg;
    }

    public int getUnCancelObsMsgResp() {
        return unCancelObsMsgResp;
    }

    public void setUnCancelObsMsgResp(int unCancelObsMsgResp) {
        this.unCancelObsMsgResp = unCancelObsMsgResp;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public SensorControlFlag getControlFlag() {
        return controlFlag;
    }

    public void setControlFlag(SensorControlFlag controlFlag) {
        this.controlFlag = controlFlag;
    }

}
