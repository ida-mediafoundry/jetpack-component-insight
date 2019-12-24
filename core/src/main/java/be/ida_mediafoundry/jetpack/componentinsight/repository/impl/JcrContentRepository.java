package be.ida_mediafoundry.jetpack.componentinsight.repository.impl;

import be.ida_mediafoundry.jetpack.componentinsight.model.JcrComponent;
import be.ida_mediafoundry.jetpack.componentinsight.repository.ComponentRepository;
import be.ida_mediafoundry.jetpack.componentinsight.repository.ContentRepository;
import be.ida_mediafoundry.jetpack.componentinsight.util.ServiceUserUtil;
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
        name = "jetpack.componentInsight.JCRContentRepository",
        service = ContentRepository.class)
public class JcrContentRepository implements ContentRepository {

    private static final Logger LOG = LoggerFactory.getLogger(JcrContentRepository.class);
    private static final String CHILD_PAGE_QUERY = "SELECT * FROM [cq:Page] AS s WHERE ISCHILDNODE([%s])";
    private static final String COMPONENT_USAGE_QUERY = "SELECT * FROM [nt:unstructured] AS s WHERE ISDESCENDANTNODE([%s]) AND [sling:resourceType] = '%s'";

    @Reference
    protected ResourceResolverFactory resourceResolverFactory;

    @Override
    public List<String> getAllContentRoots() {
        List<String> brands = new ArrayList<>();
        try {
            resourceResolverFactory.getServiceResourceResolver(ServiceUserUtil.getServiceUserCredentials())
                                   .findResources(String.format(CHILD_PAGE_QUERY, "/content"), Query.JCR_SQL2)
                                   .forEachRemaining(resource -> brands.add(resource.getPath()));
        } catch (LoginException e) {
            LOG.error("Could not retrieve list of components, Check if service user [" + ServiceUserUtil.DEFAULT_USER + "] is correctly installed", e);
        }
        return brands;
    }

    @Override
    public List<String> getComponentUsages(String contentRoot, JcrComponent component) {
        List<String> usages = new ArrayList<>();
        try {
            resourceResolverFactory.getServiceResourceResolver(ServiceUserUtil.getServiceUserCredentials())
                                   .findResources(String.format(COMPONENT_USAGE_QUERY, contentRoot, component.getGeneralResourceType()), Query.JCR_SQL2)
                                   .forEachRemaining( resource ->  usages.add(resource.getPath()));
        } catch (LoginException e) {
            LOG.error("Could not retrieve list of components, Check if service user [" + ServiceUserUtil.DEFAULT_USER + "] is correctly installed", e);
        }
        return usages;
    }
}
