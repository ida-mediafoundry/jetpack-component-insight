package be.ida_mediafoundry.jetpack.componentinsight.repository.impl;

import be.ida_mediafoundry.jetpack.componentinsight.model.JcrComponent;
import be.ida_mediafoundry.jetpack.componentinsight.repository.ComponentRepository;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.query.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component(
        name = "jetpack.componentInsight.JCRComponentRepository",
        service = ComponentRepository.class)
public class JcrComponentRepository implements ComponentRepository {

    private static final Logger LOG = LoggerFactory.getLogger(JcrComponentRepository.class);
    private static final String DEFAULT_USER = "jetpack-component-insight";
    private static final String DEFAULT_SERVICE = "be.ida_mediafoundry.jetpack.component-insight.core";
    private static final String COMPONENT_QUERY = "SELECT * FROM [cq:Component] AS s WHERE (ISDESCENDANTNODE([/apps]) OR ISDESCENDANTNODE([/libs])) and  s.[jcr:primaryType] = 'cq:Component'";

    @Reference
    protected ResourceResolverFactory resourceResolverFactory;

    @Override
    public List<JcrComponent> getAll() {
        List<JcrComponent> components = new ArrayList<>();
        try {
            resourceResolverFactory.getServiceResourceResolver(getServiceUserCredentials())
                                   .findResources(COMPONENT_QUERY, Query.JCR_SQL2)
                                   .forEachRemaining(resource -> components.add(resource.adaptTo(JcrComponent.class)));
        } catch (LoginException e) {
            LOG.error("Could not retrieve list of components, Check if service user [" + DEFAULT_USER + "] is correctly installed", e);
        }
        return components;
    }

    private Map<String, Object> getServiceUserCredentials() {
        Map<String, Object> credentials = new HashMap<>();
        credentials.put(ResourceResolverFactory.USER, DEFAULT_USER);
        credentials.put(ResourceResolverFactory.SUBSERVICE, DEFAULT_SERVICE);
        return credentials;
    }

}
