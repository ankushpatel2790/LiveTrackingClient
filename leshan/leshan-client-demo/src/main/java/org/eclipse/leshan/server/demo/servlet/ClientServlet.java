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
 *     Sierra Wireless - initial API and implementation
 *******************************************************************************/
package org.eclipse.leshan.server.demo.servlet;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.eclipse.leshan.client.californium.LeshanClient;
import org.eclipse.leshan.client.demo.BluewaveClientUtils;
import org.eclipse.leshan.client.demo.ClientRegistryInfo;
import org.eclipse.leshan.client.demo.LeshanClientDemo;
import org.eclipse.leshan.client.servers.RegistrationEngine;
import org.eclipse.leshan.core.node.LwM2mSingleResource;
import org.eclipse.leshan.core.response.LwM2mResponse;
import org.eclipse.leshan.core.response.ReadResponse;
import org.eclipse.leshan.core.response.WriteResponse;
import org.eclipse.leshan.util.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bluewave.client.ClientRegistryService;
import com.bluewave.client.ClientRegistryServiceImpl;
import com.bluewaves.model.BluewavesOjbects;
import com.bluewaves.model.Client;
import com.bluewaves.model.Client.Builder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Service HTTP REST API calls.
 */
public class ClientServlet extends HttpServlet {

    private static final String FORMAT_PARAM = "format";
    GsonBuilder gsonBuilder = new GsonBuilder();

    private final static String DEFAULT_ENDPOINT = "BluewaveClientDemo";

    private static final Logger LOG = LoggerFactory.getLogger(ClientServlet.class);

    private static final long TIMEOUT = 5000; // ms

    private static final long serialVersionUID = 1L;

    private Gson gson;

    private ClientRegistryService clientRegistryService;

    public ClientServlet() {

        GsonBuilder builder = new GsonBuilder();
        this.gson = builder.create();

        clientRegistryService = new ClientRegistryServiceImpl();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // Handle android mobile request with latitude and longitude

        boolean stop = BluewaveClientUtils.paserStringToBoolean(req.getParameter("stop"));
        if (stop) {
            LOG.info("Request for client stop with status :{}", stop);
            if (LeshanClientDemo.previousClient != null)
                LeshanClientDemo.previousClient.stop(false);
            return;
        }
        if (req.getParameter("latitude") != null) {
            // String macAddress = req.getParameter("macAddress");
            //
            // Client client = clientRegistryService.findClientByMacId(macAddress);

            LOG.info("Latitude:{} Longitude:{}", req.getParameter("latitude"), req.getParameter("longitude"));
            // client == null ||
            if (req.getParameter("latitude").isEmpty() || req.getParameter("longitude").isEmpty()) {
                LOG.info("Empty latitude and longitude with mac id :{} will not send to the server");// , macAddress);
                return;
            }

            LeshanClientDemo.MYLOCATION
                    .setLatitude(BluewaveClientUtils.paserStringToFloat(req.getParameter("latitude")));
            LeshanClientDemo.MYLOCATION
                    .setLongitude(BluewaveClientUtils.paserStringToFloat(req.getParameter("longitude")));
            LeshanClientDemo.observation.sendLocationValues();
            return;
        }

        // all registered clients
        if (req.getPathInfo() == null) {
            Collection<Client> registrations = clientRegistryService.getAllRegistration();

            String json = this.gson.toJson(registrations.toArray(new Client[] {}));
            resp.setContentType("application/json");
            resp.getOutputStream().write(json.getBytes("UTF-8"));
            resp.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        String[] path = StringUtils.split(req.getPathInfo(), '/');
        if (path.length < 1) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid path");
            return;
        }

        String clientEndpoint = path[0];

        // /endPoint : get client
        if (path.length == 1) {
            Client registration = clientRegistryService.getClientByMacAddressId();
            if (registration != null) {
                resp.setContentType("application/json");
                resp.getOutputStream().write(this.gson.toJson(registration).getBytes("UTF-8"));
                resp.setStatus(HttpServletResponse.SC_OK);
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().format("no registered client with id '%s'", clientEndpoint).flush();
            }
            return;
        }

        String objectId = path[0];
        int resourceid = BluewaveClientUtils.paserStringToInteger(path[2]);
        ReadResponse cResponse = null;
        switch (objectId) {
        case "" + BluewavesOjbects.GPS:
            cResponse = LeshanClientDemo.gps.read(resourceid);
            processDeviceResponse(req, resp, cResponse);
            return;
        case "" + BluewavesOjbects.LOCATION:
            cResponse = LeshanClientDemo.MYLOCATION.read(resourceid);
            processDeviceResponse(req, resp, cResponse);
            return;
        case "" + BluewavesOjbects.ACCELEROMETER:
            cResponse = LeshanClientDemo.accelerometer.read(resourceid);
            processDeviceResponse(req, resp, cResponse);
            return;

        case "" + BluewavesOjbects.SYSTEM_MANAGEMENT:
            cResponse = LeshanClientDemo.systemManagement.read(resourceid);
            processDeviceResponse(req, resp, cResponse);
            return;

        case "" + BluewavesOjbects.LWM2M_STATS_OBJECT:
            cResponse = LeshanClientDemo.LWM2M_STATS.read(resourceid);
            processDeviceResponse(req, resp, cResponse);
            return;

        case "" + BluewavesOjbects.HARDWARE_STATS_OJBECT:
            cResponse = LeshanClientDemo.HARDWARE_STATS.read(resourceid);
            processDeviceResponse(req, resp, cResponse);
            return;

        case "" + BluewavesOjbects.SENSOR_CONTROL_PARAM:
            cResponse = LeshanClientDemo.sensorControlFlag.read(resourceid);
            processDeviceResponse(req, resp, cResponse);
            return;

        }
    }

    private void processDeviceResponse(HttpServletRequest req, HttpServletResponse resp, LwM2mResponse cResponse)
            throws IOException {
        String response = null;
        if (cResponse == null) {
            LOG.warn(String.format("Request %s%s timed out.", req.getServletPath(), req.getPathInfo()));
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().append("Request timeout").flush();
        } else {
            response = this.gson.toJson(cResponse);
            resp.setContentType("application/json");
            resp.getOutputStream().write(response.getBytes());
            resp.setStatus(HttpServletResponse.SC_OK);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ClientRegistryInfo info = null;
        try {

            info = gson.fromJson(new InputStreamReader(req.getInputStream()), ClientRegistryInfo.class);
            LOG.debug("New security info for end-point :{}: {}", info.getEndpoint(), info);

            String endpoint;
            if (info.getEndpoint() != null) {
                endpoint = info.getEndpoint();
            } else {
                try {

                    endpoint = InetAddress.getLocalHost().getHostName();

                } catch (UnknownHostException e) {
                    endpoint = DEFAULT_ENDPOINT;
                }
            }

            // Get server URI
            String serverURI;
            if (info.getIpAddress() != null && !info.getIpAddress().isEmpty()) {
                if (info.getIdentity() != null) {
                    if (info.getBootstrap())
                        serverURI = BluewaveClientUtils.getSecureCoapUrl(info.getIpAddress(), "BOOTSTRAP_SEC_PORT");
                    else
                        serverURI = BluewaveClientUtils.getSecureCoapUrl(info.getIpAddress(), "LWM2M_SERVER_SEC_PORT");

                } else {
                    if (info.getBootstrap())
                        serverURI = BluewaveClientUtils.getNoSecCoapUrl(info.getIpAddress(), "BOOTSTRAP_NO_SEC_PORT");

                    else
                        serverURI = BluewaveClientUtils.getNoSecCoapUrl(info.getIpAddress(),
                                "LWM2M_SERVER_NO_SEC_PORT");
                }
            } else {
                if (info.getIdentity() != null) {
                    if (info.getBootstrap())
                        serverURI = BluewaveClientUtils.getSecureCoapUrl("localhost", "BOOTSTRAP_SEC_PORT");
                    else
                        serverURI = BluewaveClientUtils.getSecureCoapUrl("localhost", "LWM2M_SERVER_SEC_PORT");
                } else {
                    if (info.getBootstrap())
                        serverURI = BluewaveClientUtils.getNoSecCoapUrl("localhost", "BOOTSTRAP_NO_SEC_PORT");

                    else
                        serverURI = BluewaveClientUtils.getNoSecCoapUrl("localhost", "LWM2M_SERVER_NO_SEC_PORT");
                }
            }

            // get security info
            byte[] pskIdentity = null;
            byte[] pskKey = null;
            if (info.getIdentity() != null && info.getPreSharedKey() != null) {
                char[] encodeHex = Hex.encodeHex(info.getPreSharedKey().getBytes());
                pskIdentity = info.getIdentity().getBytes();

                pskKey = Hex.decodeHex(encodeHex);
            }
            // startClient(endpoint, info, serverURI, pskIdentity, pskKey);

            if (LeshanClientDemo.previousClient != null) {
                clientRegistryService.removeClient(LeshanClient.macAddress);
                LeshanClientDemo.previousClient.stop(true);
                RegistrationEngine.registraionId = null;

            }

            LeshanClientDemo.createAndStartClient(endpoint, null, 0, null, 0, info.getBootstrap(), serverURI,
                    pskIdentity, pskKey);

            Thread.currentThread();
            Thread.sleep(7000);

            if (info.getBootstrap() && !RegistrationEngine.isbootstrap) {
                RegistrationEngine.isbootstrap = false;
                resp.getWriter().format("Bootstrap failed").flush();

            } else if (RegistrationEngine.registraionId != null) {
                createClient(RegistrationEngine.registraionId, endpoint);

                LOG.info("Registration id:{}", RegistrationEngine.registraionId);
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter().format("SUCCESS").flush();

            } else {
                resp.getWriter().format("Registration failed").flush();
            }

            resp.getWriter().flush();

        } catch (Exception e) {
            LOG.warn("unexpected error for request " + req.getPathInfo(), e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

    }

    public void startClient(final String endpoint, final ClientRegistryInfo info, final String serverURI,
            final byte[] pskIdentity, final byte[] pskKey) {
        new Thread(new Runnable() {

            @Override
            public void run() {

                LeshanClientDemo.createAndStartClient(endpoint, null, 0, null, 0, info.getBootstrap(), serverURI,
                        pskIdentity, pskKey);

            }
        }).start();

    }

    private void createClient(String registraionId, String endpoint) {
        InetSocketAddress inetSocket = new InetSocketAddress(5684);

        Builder builder = new Client.Builder(registraionId, endpoint, inetSocket.getAddress(), 0, inetSocket);
        Client registration = builder.build();
        clientRegistryService.addClient(registration);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("===========---=============================" + req);

        String[] path = StringUtils.split(req.getPathInfo(), '/');
        String clientEndpoint = path[0];

        if (path.length < 3) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid path");
            return;
        }
        try {
            String target = StringUtils.removeStart(req.getPathInfo(), "/" + clientEndpoint);
            String[] details = req.getPathInfo().split("/");

            String objectId = details[2];
            int resourceid = Integer.parseInt(details[4]);
            WriteResponse writeResponse = null;
            LwM2mSingleResource lwM2mSingleResource = getLwm2mSingleResource(resourceid, details[5]);

            switch (objectId) {
            case "" + BluewavesOjbects.GPS:
                writeResponse = LeshanClientDemo.gps.write(resourceid, lwM2mSingleResource);
                processDeviceResponse(req, resp, writeResponse);
                return;

            case "" + BluewavesOjbects.ACCELEROMETER:
                writeResponse = LeshanClientDemo.accelerometer.write(resourceid, lwM2mSingleResource);
                processDeviceResponse(req, resp, writeResponse);
                return;

            case "" + BluewavesOjbects.SYSTEM_MANAGEMENT:
                writeResponse = LeshanClientDemo.systemManagement.write(resourceid, lwM2mSingleResource);
                processDeviceResponse(req, resp, writeResponse);
                return;
            case "" + BluewavesOjbects.LWM2M_STATS_OBJECT:
                writeResponse = LeshanClientDemo.LWM2M_STATS.write(resourceid, lwM2mSingleResource);
                processDeviceResponse(req, resp, writeResponse);
                return;
            case "" + BluewavesOjbects.HARDWARE_STATS_OJBECT:
                writeResponse = LeshanClientDemo.HARDWARE_STATS.write(resourceid, lwM2mSingleResource);
                processDeviceResponse(req, resp, writeResponse);
                return;

            case "" + BluewavesOjbects.SENSOR_CONTROL_PARAM:
                writeResponse = LeshanClientDemo.sensorControlFlag.write(resourceid, lwM2mSingleResource);
                processDeviceResponse(req, resp, writeResponse);
                return;
            }

        } catch (Exception e) {
            LOG.error("While putting sensor values :{}", e);
        }
    }

    public LwM2mSingleResource getLwm2mSingleResource(int resourceid, String value) {
        LwM2mSingleResource lwM2mSingleResource = null;
        if (BluewaveClientUtils.isDigit(value)) {
            if (BluewaveClientUtils.isInteger(value))
                lwM2mSingleResource = LwM2mSingleResource.newIntegerResource(resourceid, Integer.parseInt(value));
            else
                lwM2mSingleResource = LwM2mSingleResource.newFloatResource(resourceid, Float.parseFloat(value));
        } else {
            lwM2mSingleResource = LwM2mSingleResource.newStringResource(resourceid, value);
        }
        return lwM2mSingleResource;
    }

}
