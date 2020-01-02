package be.ida_mediafoundry.jetpack.componentinsight.servlet;

import be.ida_mediafoundry.jetpack.componentinsight.model.JcrComponent;
import be.ida_mediafoundry.jetpack.componentinsight.service.ComponentUsageService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component(
        service = { Servlet.class },
        property = {
                ServletResolverConstants.SLING_SERVLET_PATHS + "=/services/insight/usage",
                Constants.SERVICE_DESCRIPTION + "=Component Usage servlet",
                Constants.SERVICE_VENDOR + ":String=" + "IDA",
        })
public class ComponentUsageServlet extends SlingAllMethodsServlet {

    private static final Logger LOG = LoggerFactory.getLogger(ComponentUsageServlet.class);

    @Reference
    private ComponentUsageService componentUsageService;

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
        Map<String, Map<String,Integer>> result = componentUsageService.getComponentUsagesPerBrand();

        Gson gson = new Gson();
        response.getWriter().write(gson.toJson(result));
        response.setStatus(HttpServletResponse.SC_OK);
    }
}