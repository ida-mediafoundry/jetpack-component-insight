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
        assertThat(componentTree).hasSize(3);

    }
}