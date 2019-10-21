/*******************************************************************************
 * Copyright (c) 2013-2015 Sierra Wireless and others.
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
 *     Zebra Technologies - initial API and implementation
 *     Sierra Wireless, - initial API and implementation
 *     Bosch Software Innovations GmbH, - initial API and implementation
 *******************************************************************************/

package org.eclipse.leshan.client.demo;

import static org.eclipse.leshan.LwM2mId.DEVICE;
import static org.eclipse.leshan.LwM2mId.SECURITY;
import static org.eclipse.leshan.LwM2mId.SERVER;
import static org.eclipse.leshan.client.object.Security.noSec;
import static org.eclipse.leshan.client.object.Security.noSecBootstap;
import static org.eclipse.leshan.client.object.Security.psk;
import static org.eclipse.leshan.client.object.Security.pskBootstrap;

import java.util.List;

import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.leshan.client.californium.LeshanClient;
import org.eclipse.leshan.client.californium.LeshanClientBuilder;
import org.eclipse.leshan.client.object.Server;
import org.eclipse.leshan.client.resource.LwM2mInstanceEnabler;
import org.eclipse.leshan.client.resource.ObjectsInitializer;
import org.eclipse.leshan.core.request.BindingMode;
import org.eclipse.leshan.server.demo.servlet.ClientServlet;
import org.eclipse.leshan.server.demo.servlet.ObjectSpecServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bluewaves.model.BluewavesOjbects;
import com.bluewaves.model.StandardModelProvider;

public class LeshanClientDemo {
    public static LeshanClient previousClient = null;

    private static final Logger LOG = LoggerFactory.getLogger(LeshanClientDemo.class);

    // private final static String DEFAULT_ENDPOINT = "LeshanClientDemo";
    private final static String DEFAULT_ENDPOINT = "BluewaveClientDemo";
    // private final static MyLocationOri locationInstance = new MyLocationOri();
    private final static String USAGE = "java -jar leshan-client-demo.jar [OPTION]";

    // private static final MyTemp temperatureInstance = new MyTemp();
    // private static final MyAccl acclInstance = new MyAccl();
    public static final SensorControlFlag sensorControlFlag = new SensorControlFlag();
    public static final MyLocation MYLOCATION = new MyLocation();
    private static final MyAbout aboutInstance = new MyAbout();
    public static final Accelerometer accelerometer = new Accelerometer(sensorControlFlag);
    // public static final CoordinatesUtils COORDINATES = new CoordinatesUtils();
    public static final Gps gps = new Gps();
    private static final Weather weather = new Weather();

    public static final SystemManagement systemManagement = new SystemManagement(sensorControlFlag);

    public static final HardwareStats HARDWARE_STATS = new HardwareStats(sensorControlFlag);

    public static final Lwm2mStats LWM2M_STATS = new Lwm2mStats(sensorControlFlag);
    // private static final MyLocationOri locationInstance = new MyLocationOri();
    public static final ObservationSpec observation = new ObservationSpec(accelerometer, gps, sensorControlFlag,
            MYLOCATION);

    public static void main(final String[] args) {

        int webPort = ConfigUtil.parseStringToInt("JETTY_SERVER_PORT");

        org.eclipse.jetty.server.Server server = new org.eclipse.jetty.server.Server(webPort);
        WebAppContext root = new WebAppContext();
        root.setContextPath("/");
        root.setResourceBase(LeshanClientDemo.class.getClassLoader().getResource("webapp").toExternalForm());
        root.setParentLoaderPriority(true);
        server.setHandler(root);

        ServletHolder clientServletHolder = new ServletHolder(new ClientServlet());
        root.addServlet(clientServletHolder, "/api/clients/*");

        ServletHolder objectSpecServletHolder = new ServletHolder(new ObjectSpecServlet(new StandardModelProvider()));
        root.addServlet(objectSpecServletHolder, "/api/objectspecs/*");

        try {
            server.start();

            LOG.info("Bluewave server started with url :{}", server.getURI());
        } catch (Exception e) {
            LOG.error("Exception occured start bluewave server :{}", e);
        }

        // createAndStartClient(endpoint, localAddress, localPort, secureLocalAddress, secureLocalPort,
        // cl.hasOption("b"),
        // serverURI, pskIdentity, pskKey);
    }

    public static void createAndStartClient(String endpoint, String localAddress, int localPort,
            String secureLocalAddress, int secureLocalPort, boolean needBootstrap, String serverURI, byte[] pskIdentity,
            byte[] pskKey) {
        // ObjectResource.objectUriPath = null;
        int lifetTime = ConfigUtil.parseStringToInt("CLIENT_LIFE_TIME");

        aboutInstance.setMacAddress(endpoint);
        // Initialize object list
        ObjectsInitializer initializer = new ObjectsInitializer();
        if (needBootstrap) {
            if (pskIdentity == null)
                initializer.setInstancesForObject(SECURITY, noSecBootstap(serverURI));
            else
                initializer.setInstancesForObject(SECURITY, pskBootstrap(serverURI, pskIdentity, pskKey));
        } else {
            if (pskIdentity == null) {
                initializer.setInstancesForObject(SECURITY, noSec(serverURI, 123));
                initializer.setInstancesForObject(SERVER, new Server(123, lifetTime, BindingMode.U, false));
            } else {
                initializer.setInstancesForObject(SECURITY, psk(serverURI, 123, pskIdentity, pskKey));
                initializer.setInstancesForObject(SERVER, new Server(123, lifetTime, BindingMode.U, false));
            }
        }
        initializer.setClassForObject(DEVICE, MyDevice.class);
        initializer.setInstancesForObject(BluewavesOjbects.LOCATION, MYLOCATION);
        /************ custome object **********/
        // initializer.setClassForObject(3, MyDevice.class);
        // initializer.setInstancesForObject(30001, new LwM2mInstanceEnabler[] { temperatureInstance });
        // initializer.setInstancesForObject(30004, new LwM2mInstanceEnabler[] { acclInstance });
        // initializer.setInstancesForObject(30005, new LwM2mInstanceEnabler[] { locationInstance });

        // initializer.setClassForObject(10304, Accelerometer.class);

        // initializer.setInstancesForObject(30001, new LwM2mInstanceEnabler[] { weather });
        initializer.setInstancesForObject(BluewavesOjbects.GPS, new LwM2mInstanceEnabler[] { gps });
        // initializer.setInstancesForObject(BluewavesOjbects.ACCELEROMETER, new LwM2mInstanceEnabler[] { accelerometer
        // });
        initializer.setInstancesForObject(BluewavesOjbects.DEVICE_INFO, new LwM2mInstanceEnabler[] { aboutInstance });
        // initializer.setInstancesForObject(30005, new LwM2mInstanceEnabler[] { observation });
        // initializer.setInstancesForObject(BluewavesOjbects.SENSOR_CONTROL_PARAM,
        // new LwM2mInstanceEnabler[] { sensorControlFlag });
        // initializer.setInstancesForObject(BluewavesOjbects.SYSTEM_MANAGEMENT,
        // new LwM2mInstanceEnabler[] { systemManagement });
        // initializer.setInstancesForObject(BluewavesOjbects.LWM2M_STATS_OBJECT,
        // new LwM2mInstanceEnabler[] { LWM2M_STATS });
        // initializer.setInstancesForObject(BluewavesOjbects.HARDWARE_STATS_OJBECT,
        // new LwM2mInstanceEnabler[] { HARDWARE_STATS });
        // List enablers = initializer.create(new int[] { 0, 1, 3, 30001, 30004, 30005, 30006 });
        // List<LwM2mObjectEnabler> enablers = initializer.create(new int[] { 0, 1, 4, 6, BluewavesOjbects.GPS,
        // BluewavesOjbects.ACCELEROMETER, BluewavesOjbects.DEVICE_INFO, 30005,
        // BluewavesOjbects.SENSOR_CONTROL_PARAM, BluewavesOjbects.SYSTEM_MANAGEMENT,
        // BluewavesOjbects.LWM2M_STATS_OBJECT, BluewavesOjbects.HARDWARE_STATS_OJBECT });

        List enablers = initializer.create(
                new int[] { 0, 1, 4, BluewavesOjbects.LOCATION, BluewavesOjbects.GPS, BluewavesOjbects.DEVICE_INFO });
        // Create client
        LeshanClientBuilder builder = new LeshanClientBuilder(endpoint);
        builder.setLocalAddress(localAddress, localPort);
        builder.setLocalSecureAddress(secureLocalAddress, secureLocalPort);
        builder.setObjects(enablers);

        final LeshanClient client = builder.build();

        LOG.info("Press 'w','a','s','d' to change reported Location.");

        client.start();

        previousClient = client;
        // De-register on shutdown and stop client.
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                client.destroy(true); // send de-registration request before destroy
            }
        });

    }

    private static void moveTemp(String val) {
        try {
            accelerometer.moveTemperature(Float.valueOf(val).floatValue());
            LOG.info("Temperature set to temp :{}", val);
        } catch (Exception e) {
            LOG.error("Invalid input format\t" + e.getMessage());
        }
    }

    private static void moveAccelerometer(String roll, String tilt, String temp) {
        try {
            accelerometer.moveRoll(Float.valueOf(roll).floatValue());
            accelerometer.moveTilt(Float.valueOf(tilt).floatValue());
            accelerometer.moveTemperature(Float.valueOf(temp).floatValue());
            LOG.info("Set values of roll:{} tilt:{} temp:{} ", roll, tilt, temp);
        } catch (Exception e) {
            LOG.error("Invalid input format\t" + e.getMessage());
        }
    }

    private static void moveTilt(String tilt) {
        try {
            accelerometer.moveTilt(Float.valueOf(tilt).floatValue());
            LOG.info("Set values of tilt:{}", tilt);
        } catch (Exception e) {
            LOG.error("Invalid input format\t" + e.getMessage());
        }
    }

    private static void moveRoll(String roll) {
        try {
            accelerometer.moveRoll(Float.valueOf(roll).floatValue());
            LOG.info("Set values of roll:{}", roll);
        } catch (Exception e) {
            LOG.error("Invalid input format\t" + e.getMessage());
        }
    }

    private static void moveNetraGps(String lat, String lon, String azimuth) {
        try {
            gps.moveLatitude(Float.valueOf(lat).floatValue());
            gps.moveLongitude(Float.valueOf(lon).floatValue());
            // gps.moveAzimuth(Float.valueOf(azimuth).floatValue());
            LOG.info("Set values of latitude :{} logitude:{} azimuth :{}", lat, lon, azimuth);
        } catch (Exception e) {
            LOG.error("Invalid input format\t" + e.getMessage());
        }
    }

    private static void moveLat(String lat) {
        try {
            gps.moveLatitude(Float.valueOf(lat).floatValue());
            LOG.info("Set values of latitude :{}", lat);
        } catch (Exception e) {
            LOG.error("Invalid input format\t" + e.getMessage());
        }
    }

    private static void moveLong(String lon) {
        try {
            gps.moveLongitude(Float.valueOf(lon).floatValue());
            LOG.info("Set values of logitude:{}", lon);
        } catch (Exception e) {
            LOG.error("Invalid input format\t" + e.getMessage());
        }
    }

    private static void moveAzimuth(String azimuth) {
        try {
            // gps.moveAzimuth(Float.valueOf(azimuth).floatValue());
            LOG.info("Set values of azimuth :{}", azimuth);
        } catch (Exception e) {
            LOG.error("Invalid input format\t" + e.getMessage());
        }
    }

}
