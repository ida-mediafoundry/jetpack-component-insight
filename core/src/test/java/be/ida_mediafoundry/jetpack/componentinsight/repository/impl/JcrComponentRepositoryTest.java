package be.ida_mediafoundry.jetpack.componentinsight.repository.impl;

import be.ida_mediafoundry.jetpack.componentinsight.model.JcrComponent;
import io.wcm.testing.mock.aem.junit.AemContext;
import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class JcrComponentRepositoryTest {

    @Rule
    public final AemContext context = new AemContext();

    @InjectMocks
    @Spy
    private JcrComponentRepository componentRepository;

    @Mock
    private ResourceResolverFactory resourceResolverFactory;

    @Before
    public void setUp() throws PersistenceException {
        context.addModelsForPackage("be.ida_mediafoundry.jetpack");
        context.addModelsForClasses(JcrComponent.class);
        context.load().json("/components.json", "/apps");
    }

    @Test
    public void getAllComponentsTest() {
        doReturn(getTestResources()).when(componentRepository).getAllComponents();
        context.registerInjectActivateService(componentRepository);

        List<JcrComponent> components = componentRepository.getAll();
        assertThat(components).hasSize(11);
    }

    private List<Resource> getTestResources() {
        List<Resource> list = new ArrayList<>();

        Resource appsJetpackResource = context.resourceResolver().getResource("/apps/jetpack/components");
        appsJetpackResource.getChildren().forEach(childResource -> childResource.getChildren().forEach(list::add));

        Resource appsAdobeResource = context.resourceResolver().getResource("/apps/adobe/components");
        appsAdobeResource.getChildren().forEach(childResource -> childResource.getChildren().forEach(list::add));
        return list;
    }

}
