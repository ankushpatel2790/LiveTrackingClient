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
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import org.eclipse.leshan.client.resource.SimpleInstanceEnabler;
import org.eclipse.leshan.core.node.LwM2mResource;
import org.eclipse.leshan.core.response.ReadResponse;
import org.eclipse.leshan.core.response.WriteResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ObservationSpec extends SimpleInstanceEnabler {
    private static final Logger LOG = LoggerFactory.getLogger(ObservationSpec.class);
    Accelerometer accelerometer;
    Gps gps;
    SensorControlFlag sensorControlFlag;
    Timer timer;
    MyLocation location;

    private Random random;
    private int observationTime;

    public ObservationSpec(Accelerometer accelerometer, Gps gps, SensorControlFlag sensorControlFlag,
            MyLocation location) {
        this.accelerometer = accelerometer;
        this.location = location;
        this.gps = gps;
        this.random = new Random();
        this.sensorControlFlag = sensorControlFlag;
        this.observationTime = ConfigUtil.parseStringToInt("OBSERVATION_TIME");
        this.timer = new Timer();
        // moveObservationTime(ConfigUtil.parseStringToInt("OBSERVATION_TIME"));

    }

    public ReadResponse read(int resourceid) {
        LOG.info("Read on Temperature Resource " + resourceid);
        switch (resourceid) {

        case 0:
            return ReadResponse.success(resourceid, getObservationTime());

        }
        return super.read(resourceid);
    }

    @Override
    public WriteResponse write(int resourceid, LwM2mResource value) {
        LOG.info("Write on Device Resource " + resourceid + " valuRe " + value);
        switch (resourceid) {

        case 0:
            moveObservationTime(Integer.valueOf(value.getValue().toString()));
            return WriteResponse.success();
        default:
            return super.write(resourceid, value);
        }
    }

    public int getObservationTime() {
        return observationTime;
    }

    public void moveObservationTime(int delta) {
        this.observationTime = delta;
        timer.cancel();
        timer = new Timer();
        timer.schedule(new ExecutorTask(), 0, TimeUnit.SECONDS.toMillis(observationTime));
    }

    public class ExecutorTask extends TimerTask {

        @Override
        public void run() {
            LOG.info("Scheduler run at time :{}", observationTime);
            if (ConfigUtil.parseStringToBoolean("SCHEDULER_STATUS")) {

                sendGpsValues();

            }

        }

    }

    class ObservationLishner extends TimerTask {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            // System.out.println(
            // "Observation lishner -------------------------------------" + ObjectResource.objectUriPath.size());
            // if (ObjectResource.objectUriPath != null && !ObjectResource.objectUriPath.isEmpty()) {
            // moveObservationTime(ConfigUtil.parseStringToInt("OBSERVATION_TIME"));
            // }
        }

    }

    public void observationLishner() {
        timer.schedule(new ObservationLishner(), 0, 10000);
    }

    public void sendGpsValues() {
        LOG.info("Notify new latitude :{} and longitude :{}", gps.getLatitude(), gps.getLongitude());
        gps.moveTimeStamp(new Date().toString());
    }

    public void sendLocationValues() {
        LOG.info("Notify new latitude :{} and longitude :{}", location.getLatitude(), location.getLongitude());
        location.moveTimeStamp(new Date());
    }

    public void sendAccelerometerValues() {
        accelerometer.moveTemperature(
                Float.valueOf(random.nextInt(ConfigUtil.parseStringToInt("TEMPERATURE"))).floatValue());

        accelerometer.moveRoll(Float.valueOf(random.nextInt(ConfigUtil.parseStringToInt("ROLL"))).floatValue());
        accelerometer.moveTilt(Float.valueOf(random.nextInt(ConfigUtil.parseStringToInt("TILT"))).floatValue());
        accelerometer.moveAccelerationXaxisConfig(random.nextInt(6));
        accelerometer.moveAccelerationYaxisConfig(random.nextInt(5));
        accelerometer.moveInclinationXaxisConfig(random.nextInt(2));
        accelerometer.moveInclinationYaxisConfig(random.nextInt(3));
        accelerometer.moveXacceleration(random.nextInt(45));
        accelerometer.moveYacceleration(random.nextInt(60));
        accelerometer.moveRoll(random.nextInt(50));
        accelerometer.moverollConfig(random.nextInt(1));
        accelerometer.moveGlobalCmd(random.nextInt(5));
        accelerometer.moveSensorStatus(accelerometer.getSensorStatus());

    }

}
