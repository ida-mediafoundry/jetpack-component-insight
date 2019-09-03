package be.ida_mediafoundry.jetpack.componentinsight.service;

import be.ida_mediafoundry.jetpack.componentinsight.model.JcrComponent;
import be.ida_mediafoundry.jetpack.componentinsight.model.TreeNode;
import be.ida_mediafoundry.jetpack.componentinsight.repository.ComponentRepository;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component(
        name = "jetpack.componentInsight.ComponentTreeService",
        service = ComponentTreeService.class)
public class ComponentTreeService {

    @Reference
    protected ComponentRepository componentRepository;

    public List<TreeNode> getComponentTree() {
        Map<String, TreeNode> dictionary = new HashMap<>();
        List<TreeNode> rootNodes = new ArrayList<>();
        List<JcrComponent> components = componentRepository.getAll();

        for (JcrComponent component : components) {
            TreeNode componentTreeNode;
            componentTreeNode = AddComponentToDictionary(dictionary, component);
            if (component.getResourceSuperType() == null) {
                handleComponentIsRoot(rootNodes, componentTreeNode);
            } else {
                handleComponentIsChild(dictionary, rootNodes, component, componentTreeNode);
            }
        }

        return rootNodes;
    }

    private void handleComponentIsChild(Map<String, TreeNode> dictionary, List<TreeNode> rootNodes, JcrComponent component, TreeNode componentTreeNode) {
        String parentPath = component.getResourceSuperType();
        if(!dictionary.containsKey(parentPath)) {
            TreeNode parentTreeNode = new TreeNode(parentPath);
            dictionary.put(parentPath, parentTreeNode);
            rootNodes.add(parentTreeNode);
        }
        TreeNode parentTreeNode = dictionary.get(parentPath);
        parentTreeNode.addChild(componentTreeNode);
        rootNodes.remove(componentTreeNode);
    }

    private void handleComponentIsRoot(List<TreeNode> rootNodes, TreeNode componentTreeNode) {
        if(!rootNodes.contains(componentTreeNode)){
            rootNodes.add(componentTreeNode);
        }
    }

    private TreeNode AddComponentToDictionary(Map<String, TreeNode> dictionary, JcrComponent component) {
        TreeNode componentTreeNode;
        if (dictionaryContainsComponentPlaceholder(dictionary, component)) {
            componentTreeNode = dictionary.get(component.getResourceType());
            componentTreeNode.setComponent(component);
        } else {
            componentTreeNode = new TreeNode(component);
            dictionary.putIfAbsent(component.getResourceType(), componentTreeNode);
        }
        return componentTreeNode;
    }

    private boolean dictionaryContainsComponentPlaceholder(Map<String, TreeNode> dictionary, JcrComponent component) {
        return dictionary.containsKey(component.getResourceType())
                && dictionary.get(component.getResourceType()) != null
                && dictionary.get(component.getResourceType()).getComponent() != null
                && dictionary.get(component.getResourceType()).getComponent().getResource() == null;
    }
}
