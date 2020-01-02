package be.ida_mediafoundry.jetpack.componentinsight.service;

import be.ida_mediafoundry.jetpack.componentinsight.model.JcrComponent;
import be.ida_mediafoundry.jetpack.componentinsight.repository.ComponentRepository;
import be.ida_mediafoundry.jetpack.componentinsight.repository.ContentRepository;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.*;

@Component(
        name = "jetpack.componentInsight.ComponentUsageService",
        service = ComponentUsageService.class)
public class ComponentUsageService {

    @Reference
    protected ComponentRepository componentRepository;

    @Reference
    protected ContentRepository contentRepository;

    public Map<String, Map<String, Integer>> getComponentUsagesPerBrand() {
        Map<String, Map<String, Integer>> componentUsagesPerBrand = new TreeMap();
        List<JcrComponent> components = componentRepository.getAllCustom();
        List<String> contentRoots = contentRepository.getAllContentRoots();

        for (JcrComponent component : components) {
            Map<String, Integer> componentUsagesMap = new TreeMap();
            boolean usagesFound = false;
            for (String contentRoot : contentRoots) {

                List<String> componentUsages = contentRepository.getComponentUsages(contentRoot, component);
                if (componentUsages.size() > 0) {
                    usagesFound = true;
                }
                componentUsagesMap.put(contentRoot, componentUsages.size());
            }
            if (usagesFound) {
                componentUsagesPerBrand.put(component.getPath(), componentUsagesMap);
            }
        }

        return componentUsagesPerBrand;
    }

    public List<String> getAllContentRoots() {
        return contentRepository.getAllContentRoots();
    }
}
