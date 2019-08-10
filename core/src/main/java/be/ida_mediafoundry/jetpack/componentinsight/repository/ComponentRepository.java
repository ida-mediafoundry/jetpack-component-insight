package be.ida_mediafoundry.jetpack.componentinsight.repository;

import be.ida_mediafoundry.jetpack.componentinsight.model.JcrComponent;

import java.util.List;

public interface ComponentRepository {

    List<JcrComponent> getAll();

}

