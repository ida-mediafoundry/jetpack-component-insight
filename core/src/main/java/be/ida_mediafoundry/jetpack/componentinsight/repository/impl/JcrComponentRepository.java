package be.ida_mediafoundry.jetpack.componentinsight.repository.impl;

import be.ida_mediafoundry.jetpack.componentinsight.model.JcrComponent;
import be.ida_mediafoundry.jetpack.componentinsight.repository.ComponentRepository;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.osgi.service.component.annotations.Component;

import javax.jcr.query.Query;

import java.util.ArrayList;
import java.util.List;

@Component(service = ComponentRepository.class)
public class JcrComponentRepository implements ComponentRepository {

    @SlingObject
    private ResourceResolver resourceResolver;

    private static final String COMPONENT_QUERY = "SELECT * FROM [cq:Component] AS s WHERE ISDESCENDANTNODE([/apps]) and s.[jcr:primaryType] = 'cq:Component'";

    @Override
    public List<JcrComponent> getAll() {

        List<JcrComponent> components = new ArrayList<>();
        resourceResolver.findResources(COMPONENT_QUERY, Query.JCR_SQL2).forEachRemaining(resource -> {
            components.add(resource.adaptTo(JcrComponent.class));
        });
        return components;
    }

}
