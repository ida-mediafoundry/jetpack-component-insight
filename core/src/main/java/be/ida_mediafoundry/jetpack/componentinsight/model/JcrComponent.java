package be.ida_mediafoundry.jetpack.componentinsight.model;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class JcrComponent {

    @ValueMapValue(name = "jcr:primaryType")
    private String primaryType;

    @ValueMapValue(name = "jcr:title")
    private String title;

    @ValueMapValue(name = "componentGroup")
    @Optional
    private String componentGroup;

    @ValueMapValue(name = "sling:resourceType")
    private String resourceType;

    @ValueMapValue(name = "sling:resourceSuperType")
    private String resourceSuperType;

    public String getPrimaryType() {
        return primaryType;
    }

    public String getTitle() {
        return title;
    }

    public String getComponentGroup() {
        return componentGroup;
    }

    public String getResourceType() {
        return resourceType;
    }

    public String getResourceSuperType() {
        return resourceSuperType;
    }
}
