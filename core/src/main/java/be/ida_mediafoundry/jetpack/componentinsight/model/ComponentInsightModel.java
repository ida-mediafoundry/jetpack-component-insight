package be.ida_mediafoundry.jetpack.componentinsight.model;

import be.ida_mediafoundry.jetpack.componentinsight.service.ComponentTreeService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;

import javax.inject.Inject;

@Model(adaptables = SlingHttpServletRequest.class, resourceType = "jetpack/componentinsight/components/component-insight-component", defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Exporter(name = "jackson", extensions = "json")
public class ComponentInsightModel {

    @Inject
    ComponentTreeService componentTreeService;

    public String getPrettyComponentTreeJson() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(componentTreeService.getComponentTree());
    }

    public String getComponentTreeJson() {
        Gson gson = new Gson();
        return gson.toJson(componentTreeService.getComponentTree());
    }


}