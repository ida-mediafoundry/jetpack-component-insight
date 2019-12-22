package be.ida_mediafoundry.jetpack.componentinsight.repository.impl;

import be.ida_mediafoundry.jetpack.componentinsight.model.JcrComponent;
import be.ida_mediafoundry.jetpack.componentinsight.repository.ComponentRepository;
import io.wcm.testing.mock.aem.junit.AemContext;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class JcrComponentRepositoryTest {

    @Rule
    public final AemContext context = new AemContext(ResourceResolverType.JCR_OAK);

    @InjectMocks
    private ComponentRepository componentRepository = new JcrComponentRepository();

    @Before
    public void setUp() {
        context.addModelsForPackage("be.ida_mediafoundry.jetpack");
        context.load().json("/components.json", "/apps");
        context.load().json("/out-of-the-box-components.json", "/libs");
    }

    @Test
    public void getAllComponents() {
        // Given app folder with 11 components
        context.registerInjectActivateService(componentRepository);

        // When executing a query to find all components
        List<JcrComponent> components = componentRepository.getAll();

        // Expect a list of 11 components
        assertThat(components).hasSize(14);
    }

    @Test
    public void getAllComponents_WithoutCredentials() throws LoginException {
        context.registerInjectActivateService(componentRepository);
        // Given app folder with 11 components and a resourceResolverFactory that will throw a loginException
        ResourceResolverFactory mockResourceResolverFactory = spy(context.getService(ResourceResolverFactory.class));
        doThrow(new LoginException()).when(mockResourceResolverFactory).getServiceResourceResolver(anyMap());
        ((JcrComponentRepository)componentRepository).resourceResolverFactory = mockResourceResolverFactory;

        // When executing a query to find all components
        List<JcrComponent> components = componentRepository.getAll();

        // Expect an empty list of components
        assertThat(components).isNotNull();
        assertThat(components).isEmpty();
    }

}
