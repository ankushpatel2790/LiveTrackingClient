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
package com.bluewave.client;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bluewaves.model.Client;

public class ClientRegistryServiceImpl implements ClientRegistryService {

    Logger logger = LoggerFactory.getLogger(ClientRegistryServiceImpl.class);
    private Map<String, Client> clientRegistry = new ConcurrentHashMap<>();

    @Override
    public Collection<Client> getAllRegistration() {
        logger.info("Going to get all register devices");
        try {

            return Collections.unmodifiableCollection(clientRegistry.values());
        } catch (Exception e) {
            logger.error("While getting all registered devices :{}", e);
        }
        return null;

    }

    @Override
    public Client getClientByMacAddressId() {
        logger.info("Get client by macAddress");
        if (clientRegistry != null && clientRegistry.keySet().iterator().hasNext()) {
            String macAddress = clientRegistry.keySet().iterator().next();
            return clientRegistry.get(macAddress);

        }
        return null;
    }

    @Override
    public void removeClient(String macAddress) {
        logger.info("Remove client by registration id :{}", macAddress);
        try {
            clientRegistry.remove(macAddress);
        } catch (Exception e) {
            logger.error("While removing client by registration id :{}", e);
        }

    }

    @Override
    public void addClient(Client client) {
        logger.info("Add client with registration id :{}", client.getEndpoint());
        try {
            clientRegistry.put(client.getEndpoint(), client);
        } catch (Exception e) {
            logger.error("While adding client :{}", e);
        }

    }

    @Override
    public Client findClientByMacId(String macAddress) {
        return clientRegistry.get(macAddress);
    }
}
