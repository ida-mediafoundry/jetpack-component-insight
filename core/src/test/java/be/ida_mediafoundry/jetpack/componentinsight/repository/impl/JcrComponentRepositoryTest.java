package be.ida_mediafoundry.jetpack.componentinsight.repository.impl;

import be.ida_mediafoundry.jetpack.componentinsight.model.JcrComponent;
import be.ida_mediafoundry.jetpack.componentinsight.repository.ComponentRepository;
import io.wcm.testing.mock.aem.junit.AemContext;
import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class JcrComponentRepositoryTest {

    @Rule
    public final AemContext context = new AemContext(ResourceResolverType.JCR_OAK);

    @Mock
    protected ResourceResolver resourceResolver;

    @InjectMocks
    protected ComponentRepository componentRepository = new JcrComponentRepository();

    @Before
    public void setUp() throws PersistenceException {
        context.addModelsForPackage("be.ida_mediafoundry.jetpack");
        context.load().json("/components.json", "/apps");
        context.resourceResolver().commit();
    }

    @Test
    public void getAllComponentsTest() {
        List<JcrComponent> components = componentRepository.getAll();
        assertThat(components).hasSize(11);
    }

}
