package be.ida_mediafoundry.jetpack.componentinsight.model;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Developer: Ben Oeyen
 * Date: 2019-08-09
 */
public class ComponentTest {

    @Test
    public void basictest() {
        Component component = new Component();
        component.setResourceType("abc123");
        assertThat(component.getResourceType()).isEqualTo("abc123");
    }

}