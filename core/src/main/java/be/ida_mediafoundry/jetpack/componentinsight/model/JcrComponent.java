package be.ida_mediafoundry.jetpack.componentinsight.model;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.annotation.PostConstruct;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class JcrComponent implements Comparable{

    @Self
    private transient Resource resource;

    private String path;

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

    @PostConstruct
    public void setup() {
        this.path = resource.getPath();
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

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

    public String getGeneralResourceType() {
        if (resourceType != null) {
            return resourceType.replace("/apps/", "").replace("/libs/", "");
        } else {
            return path.replace("/apps/", "").replace("/libs/", "");
        }
    }

    public String getResourceSuperType() {
        return resourceSuperType;
    }

    @Override
    public int compareTo(Object o) {
        if(o instanceof JcrComponent){
            return path.compareTo(((JcrComponent)o).path);
        }
        return 0;
    }

    @Override
    public String toString() {
        return path;
    }
}
