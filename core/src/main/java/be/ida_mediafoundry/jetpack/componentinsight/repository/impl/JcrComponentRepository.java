package be.ida_mediafoundry.jetpack.componentinsight.repository.impl;

import be.ida_mediafoundry.jetpack.componentinsight.model.JcrComponent;
import be.ida_mediafoundry.jetpack.componentinsight.repository.ComponentRepository;
import org.apache.commons.collections4.IteratorUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.jcr.query.Query;

import java.util.ArrayList;
import java.util.List;

@Component(
        name = "Jetpack - Jcr Component Repository",
        service = ComponentRepository.class)
public class JcrComponentRepository implements ComponentRepository {

    @Reference
    private ResourceResolverFactory resourceResolverFactory;

    private static final String COMPONENT_QUERY = "SELECT * FROM [cq:Component] AS s WHERE ISDESCENDANTNODE([/apps]) and s.[jcr:primaryType] = 'cq:Component'";

    List<Resource> getAllComponents() {
        ResourceResolver resourceResolver = resourceResolverFactory.getThreadResourceResolver();
        return IteratorUtils.toList(resourceResolver.findResources(COMPONENT_QUERY, Query.JCR_SQL2));
    }

    @Override
    public List<JcrComponent> getAll() {
        List<JcrComponent> components = new ArrayList<>();
        getAllComponents().forEach(resource -> components.add(resource.adaptTo(JcrComponent.class)));
        return components;
    }

}
