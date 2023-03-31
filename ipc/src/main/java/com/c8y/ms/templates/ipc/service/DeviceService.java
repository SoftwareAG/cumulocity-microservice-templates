package com.c8y.ms.templates.ipc.service;

import java.net.URI;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cumulocity.microservice.api.CumulocityClientProperties;
import com.cumulocity.microservice.context.ContextService;
import com.cumulocity.microservice.context.credentials.MicroserviceCredentials;
import com.cumulocity.microservice.context.credentials.UserCredentials;
import com.cumulocity.microservice.subscription.service.MicroserviceSubscriptionsService;
import com.cumulocity.sdk.client.Platform;
import com.cumulocity.sdk.client.inventory.InventoryApi;
import com.cumulocity.sdk.client.inventory.ManagedObjectCollection;

/**
 * This is an example service. This should be removed for your real project!
 *
 * @author APES
 */
@Service
public class DeviceService {

    private static final Logger LOG = LoggerFactory.getLogger(DeviceService.class);

    private final TemplatesBasicClient templateBasicClient;
    
    @Autowired
    public DeviceService(TemplatesBasicClient templateBasicClient) {
        this.templateBasicClient = templateBasicClient;
    }

    /**
     * Query a list of devices from the Basic Microservice REST API
     **/
    public List<String> getAllDeviceNames() {
    		
    	List<String> deviceNames = templateBasicClient.getDeviceNames();
    	
        return deviceNames;
    }

}
