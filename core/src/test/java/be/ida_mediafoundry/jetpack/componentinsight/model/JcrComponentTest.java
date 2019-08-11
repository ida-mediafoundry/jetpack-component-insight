package be.ida_mediafoundry.jetpack.componentinsight.model;

import be.ida_mediafoundry.jetpack.componentinsight.repository.ComponentRepository;
import be.ida_mediafoundry.jetpack.componentinsight.repository.impl.JcrComponentRepository;
import io.wcm.testing.mock.aem.junit.AemContext;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Developer: Ben Oeyen
 * Date: 2019-08-09
 */
@RunWith(MockitoJUnitRunner.class)
public class JcrComponentTest {

    @Rule
    public final AemContext context = new AemContext();

    @Before
    public void setUp() {
        context.addModelsForPackage("be.ida_mediafoundry.jetpack");
        context.load().json("/components.json", "/apps");
    }

    @Test
    public void adaptToJcrComponent() {
        // Given a resource containing properties
        Resource componentResource = context.resourceResolver().getResource("/apps/jetpack/components/general/text-component");

        // When adapting the resource to a JCRComponent
        JcrComponent jcrComponent = componentResource.adaptTo(JcrComponent.class);

        // Expect all properties to be mapped and accessible
        assertThat(jcrComponent).isNotNull();
        assertThat(jcrComponent.getPrimaryType()).isEqualTo("cq:Component");
        assertThat(jcrComponent.getResourceType()).isEqualTo("jetpack/components/general/text-component");
        assertThat(jcrComponent.getResourceSuperType()).isEqualTo("adobe/components/general/text-component");
        assertThat(jcrComponent.getTitle()).isEqualTo("Text Component");
        assertThat(jcrComponent.getComponentGroup()).isEqualTo("Jetpack - General");
    }
}
