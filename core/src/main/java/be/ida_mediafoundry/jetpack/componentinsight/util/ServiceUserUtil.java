package be.ida_mediafoundry.jetpack.componentinsight.util;

import org.apache.sling.api.resource.ResourceResolverFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Developer: Ben Oeyen
 * Date: 2019-12-22
 */
public class ServiceUserUtil {

    public static final String DEFAULT_USER = "jetpack-component-insight";
    public static final String DEFAULT_SERVICE = "be.ida_mediafoundry.jetpack.component-insight.core";

    public static Map<String, Object> getServiceUserCredentials() {
        Map<String, Object> credentials = new HashMap<>();
        credentials.put(ResourceResolverFactory.USER, DEFAULT_USER);
        credentials.put(ResourceResolverFactory.SUBSERVICE, DEFAULT_SERVICE);
        return credentials;
    }
}
