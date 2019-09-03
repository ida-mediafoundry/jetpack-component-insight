package be.ida_mediafoundry.jetpack.componentinsight.service;

import be.ida_mediafoundry.jetpack.componentinsight.model.JcrComponent;
import be.ida_mediafoundry.jetpack.componentinsight.model.TreeNode;
import be.ida_mediafoundry.jetpack.componentinsight.repository.ComponentRepository;
import be.ida_mediafoundry.jetpack.componentinsight.repository.impl.JcrComponentRepository;
import io.wcm.testing.mock.aem.junit.AemContext;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ComponentTreeServiceTest {

    @Rule
    public final AemContext context = new AemContext(ResourceResolverType.JCR_OAK);

    @InjectMocks
    private ComponentTreeService componentTreeService = new ComponentTreeService();

    @InjectMocks
    private ComponentRepository componentRepository = new JcrComponentRepository();

    @Before
    public void setUp() {
        context.addModelsForPackage("be.ida_mediafoundry.jetpack");
        context.registerInjectActivateService(componentRepository);
        context.registerInjectActivateService(componentTreeService);
    }

    @Test
    public void getComponentTree() {
        //Given that the repository returns a flat list of components with possible parent references
        context.load().json("/components.json", "/apps");

        //When the component tree is calculated
        List<TreeNode> componentTree = componentTreeService.getComponentTree();

        //Then expect a component tree structure
        assertThat(componentTree).isNotNull();
        assertThat(componentTree).isNotEmpty();
        assertThat(componentTree).hasSize(4);

        assertTreeNode(componentTree.get(0), "/apps/adobe/components/general/text-component", 1);
        assertTreeNode(componentTree.get(0).getChildren().get(0), "/apps/jetpack/components/general/text-component", 0);

        assertTreeNode(componentTree.get(1), "/apps/adobe/components/general/image-component", 1);
        assertTreeNode(componentTree.get(1).getChildren().get(0), "/apps/jetpack/components/general/image-component", 1);
        assertTreeNode(componentTree.get(1).getChildren().get(0).getChildren().get(0), "/apps/jetpack/components/catalog/product-component", 0);

        assertTreeNode(componentTree.get(2), "cq/components/general/master-component", 3);
        assertTreeNode(componentTree.get(2).getChildren().get(0), "/apps/jetpack/components/general/master-component", 4);
        assertTreeNode(componentTree.get(2).getChildren().get(0).getChildren().get(0), "/apps/jetpack/components/layout/section-component", 0);
        assertTreeNode(componentTree.get(2).getChildren().get(0).getChildren().get(1), "/apps/jetpack/components/layout/column-component", 0);
        assertTreeNode(componentTree.get(2).getChildren().get(0).getChildren().get(2), "/apps/jetpack/components/checkout/order-component", 0);
        assertTreeNode(componentTree.get(2).getChildren().get(0).getChildren().get(3), "/apps/jetpack/components/checkout/basket-component", 0);
        assertTreeNode(componentTree.get(2).getChildren().get(1), "/apps/adobe/components/layout/section-component", 0);
        assertTreeNode(componentTree.get(2).getChildren().get(2), "/apps/adobe/components/layout/column-component", 0);

        assertTreeNode(componentTree.get(3), "/apps/cq/components/general/test-component", 0);
    }

    private void assertTreeNode(TreeNode treeNode0, String expectedPath, int expectedChildren) {
        assertThat(treeNode0).isNotNull();
        assertThat(treeNode0.getComponent()).isNotNull();
        assertThat(treeNode0.getComponent().getPath()).isEqualTo(expectedPath);
        assertThat(treeNode0.getChildren()).hasSize(expectedChildren);
    }
}