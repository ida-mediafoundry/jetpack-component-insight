package be.ida_mediafoundry.jetpack.componentinsight.servlet;

import be.ida_mediafoundry.jetpack.componentinsight.model.TreeNode;
import be.ida_mediafoundry.jetpack.componentinsight.service.ComponentTreeService;
import com.google.gson.Gson;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.ServletResolverConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@Component(
        service = { Servlet.class },
        property = {
                ServletResolverConstants.SLING_SERVLET_PATHS + "=/services/insight/tree",
                Constants.SERVICE_DESCRIPTION + "=Component Tree servlet",
                Constants.SERVICE_VENDOR + ":String=" + "IDA",
        })
public class ComponentTreeServlet extends SlingAllMethodsServlet {

    private static final Logger LOG = LoggerFactory.getLogger(ComponentTreeServlet.class);

    @Reference
    private ComponentTreeService componentTreeService;

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) {
        response.setContentType("application/json");
        try {
            process(response);
        } catch (Exception e) {
            LOG.error("Error during getting result", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void process(SlingHttpServletResponse response) throws IOException {
        List<TreeNode> result = componentTreeService.getComponentTree();

        Gson gson = new Gson();
        response.getWriter().write(gson.toJson(result));
        response.setStatus(HttpServletResponse.SC_OK);
    }
}