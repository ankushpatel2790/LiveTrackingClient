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
import org.eclipse.leshan.core.node.LwM2mResource;
import org.eclipse.leshan.core.response.ReadResponse;
import org.eclipse.leshan.core.response.WriteResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bluewaves.model.BluewavesOjbects;

public class MyLocation extends BaseInstanceEnabler {
    private static final Logger LOG = LoggerFactory.getLogger(MyLocation.class);

    private float latitude;
    private float longitude;
    private Date date;

    public MyLocation() {

        this.latitude = 0.0f;
        this.longitude = 0.0f;
        date = new Date();
    }

    public ReadResponse read(int resourceid) {
        LOG.info("Read on Location Resource " + resourceid);
        switch (resourceid) {
        case 0:
            return ReadResponse.success(resourceid, getLatitude());
        case 1:
            return ReadResponse.success(resourceid, getLongitude());

        case 5:
            return ReadResponse.success(resourceid, getDate());
        }
        return super.read(resourceid);
    }

    @Override
    public WriteResponse write(int resourceid, LwM2mResource value) {
        LOG.info("Inside write method resouce id :{} with value :{}", resourceid, value.getValue());

        switch (resourceid) {

        case 0:

            moveLatitude(Float.parseFloat("" + value.getValue()));
            return WriteResponse.success();

        case 1:
            moveLongitude(Float.parseFloat("" + value.getValue()));
            return WriteResponse.success();

        case 5:
            moveTimeStamp((Date) value.getValue());
            return WriteResponse.success();
        default:
            return super.write(resourceid, value);
        }
    }

    public void moveTimeStamp(Date date) {
        this.date = date;
        fireResourcesChange(new int[] { 5, BluewavesOjbects.LOCATION });
    }

    public void moveLatitude(float delta) {
        this.latitude = delta;
        fireResourcesChange(new int[] { 0, BluewavesOjbects.LOCATION });
    }

    public void moveLongitude(float delta) {
        this.longitude = delta;
        fireResourcesChange(new int[] { 1, BluewavesOjbects.LOCATION });
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
