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

import java.io.Serializable;

import org.eclipse.leshan.util.Validate;

public class ClientRegistryInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    // the client end-point
    private final String endpoint;

    // PSK
    private final String identity;
    private final String preSharedKey;

    private String ipAddress;
    private boolean bootstrap;

    private ClientRegistryInfo(String endpoint, String identity, String preSharedKey, boolean bootstrap,
            String ipAddress) {
        Validate.notEmpty(endpoint);
        this.endpoint = endpoint;
        this.identity = identity;
        this.preSharedKey = preSharedKey;
        this.ipAddress = ipAddress;
        this.bootstrap = bootstrap;

    }

    public String getEndpoint() {
        return endpoint;
    }

    public String getIdentity() {
        return identity;
    }

    public String getPreSharedKey() {
        return preSharedKey;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public boolean getBootstrap() {
        return bootstrap;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public void setBootstrap(boolean bootstrap) {
        this.bootstrap = bootstrap;
    }

}
