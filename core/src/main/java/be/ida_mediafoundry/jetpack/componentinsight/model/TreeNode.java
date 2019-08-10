package be.ida_mediafoundry.jetpack.componentinsight.model;

import java.util.ArrayList;
import java.util.List;

public class TreeNode {

    private JcrComponent component;

    private List<TreeNode> children = new ArrayList<>();

    public TreeNode(JcrComponent component) {
        this.component = component;
    }

    public void addChild(TreeNode treeNode) {
        children.add(treeNode);
    }

    public JcrComponent getComponent() {
        return component;
    }

    public List<TreeNode> getChildren() {
        return children;
    }

}
