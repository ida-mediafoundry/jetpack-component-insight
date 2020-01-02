package be.ida_mediafoundry.jetpack.componentinsight.model;

import be.ida_mediafoundry.jetpack.componentinsight.service.ComponentTreeService;
import be.ida_mediafoundry.jetpack.componentinsight.service.ComponentUsageService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Model(adaptables = SlingHttpServletRequest.class, resourceType = "jetpack/componentinsight/components/component-insight-component", defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Exporter(name = "jackson", extensions = "json")
public class ComponentInsightModel {

    @Inject
    ComponentTreeService componentTreeService;

    @Inject
    ComponentUsageService componentUsageService;

    public String getPrettyComponentTreeJson() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(componentTreeService.getComponentTree());
    }

    public String getComponentTreeJson() {
        Gson gson = new Gson();
        return gson.toJson(componentTreeService.getComponentTree());
    }

    public Map<String, Map<String,Integer>> getComponentUsagePerBrand(){
        return componentUsageService.getComponentUsagesPerBrand();
    }

    public List<String> getAllContentRoots(){
        return componentUsageService.getAllContentRoots();
    }

    public String getComponentUsagePerBrandJson() {
        Gson gson = new Gson();
        return gson.toJson(getComponentUsagePerBrand());
    }

}