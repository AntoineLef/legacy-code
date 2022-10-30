package com.antoineleuf;

import jakarta.ws.rs.core.Application;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.antoineleuf.telephony.ws.api.calllog.CallLogResource;
import com.antoineleuf.telephony.ws.api.calllog.CallLogResourceImpl;
import com.antoineleuf.telephony.ws.api.contact.ContactResource;
import com.antoineleuf.telephony.ws.api.contact.ContactResourceImpl;
import com.antoineleuf.telephony.ws.domain.calllog.CallLog;
import com.antoineleuf.telephony.ws.domain.calllog.CallLogAssembler;
import com.antoineleuf.telephony.ws.domain.calllog.CallLogRepository;
import com.antoineleuf.telephony.ws.domain.calllog.CallLogService;
import com.antoineleuf.telephony.ws.domain.contact.Contact;
import com.antoineleuf.telephony.ws.domain.contact.ContactAssembler;
import com.antoineleuf.telephony.ws.domain.contact.ContactRepository;
import com.antoineleuf.telephony.ws.domain.contact.ContactService;
import com.antoineleuf.telephony.ws.http.CORSResponseFilter;
import com.antoineleuf.telephony.ws.infrastructure.calllog.CallLogDevDataFactory;
import com.antoineleuf.telephony.ws.infrastructure.calllog.CallLogRepositoryInMemory;
import com.antoineleuf.telephony.ws.infrastructure.contact.ContactDevDataFactory;
import com.antoineleuf.telephony.ws.infrastructure.contact.ContactRepositoryInMemory;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * RESTApi setup without using DI or spring
 */
@SuppressWarnings("all")
public class TelephonyWsMain {
  private static final Logger LOGGER = LoggerFactory.getLogger(TelephonyWsMain.class);

  public static boolean isDev = true; // Would be a JVM argument or in a .property file

  public static void main(String[] args) throws Exception {
    LOGGER.info("Setup resources (API)");
    ContactResource contactResource = createContactResource();
    CallLogResource callLogResource = createCallLogResource();

    LOGGER.info("Setup API context (JERSEY + JETTY)");
    ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
    context.setContextPath("/api/");
    ResourceConfig resourceConfig = ResourceConfig.forApplication(new Application() {
      @Override
      public Map<String, Object> getProperties() {
        return Map.of("jersey.config.server.wadl.disableWadl", "true");
      }

      @Override
      public Set<Object> getSingletons() {
        HashSet<Object> resources = new HashSet<>();
        LOGGER.info("Add resources to context");
        return Set.of(contactResource, callLogResource);
      }
    });
    resourceConfig.register(CORSResponseFilter.class);

    ServletContainer servletContainer = new ServletContainer(resourceConfig);
    ServletHolder servletHolder = new ServletHolder(servletContainer);
    context.addServlet(servletHolder, "/*");

    LOGGER.info("Setup static file context (WEBAPP)");
    WebAppContext webapp = new WebAppContext();
    webapp.setResourceBase("src/main/webapp");
    webapp.setContextPath("/");

    LOGGER.info("Setup http server");
    ContextHandlerCollection contexts = new ContextHandlerCollection();
    contexts.setHandlers(new Handler[]{context, webapp});
    Server server = new Server(8080);
    server.setHandler(contexts);

    try {
      LOGGER.info("Staring server");
      server.start();
      server.join();
    } catch (Exception e) {
      LOGGER.error("Error during server start", e);
      server.stop();
      server.destroy();
    }
  }

  private static ContactResource createContactResource() {
    LOGGER.info("Setup resources' dependencies (DOMAIN + INFRASTRUCTURE)");
    ContactRepository contactRepository = new ContactRepositoryInMemory();

    // For development ease
    if (isDev) {
      ContactDevDataFactory contactDevDataFactory = new ContactDevDataFactory();
      List<Contact> contacts = contactDevDataFactory.createMockData();
      contacts.stream().forEach(contactRepository::save);
    }

    ContactAssembler contactAssembler = new ContactAssembler();
    ContactService contactService = new ContactService(contactRepository, contactAssembler);

    return new ContactResourceImpl(contactService);
  }

  private static CallLogResource createCallLogResource() {
    LOGGER.info("Setup resources' dependencies (DOMAIN + INFRASTRUCTURE)");
    CallLogRepository callLogRepository = new CallLogRepositoryInMemory();

    // For development ease
    if (isDev) {
      CallLogDevDataFactory callLogDevDataFactory = new CallLogDevDataFactory();
      List<CallLog> callLogs = callLogDevDataFactory.createMockData();
      callLogs.stream().forEach(callLogRepository::save);
    }

    CallLogAssembler callLogAssembler = new CallLogAssembler();
    CallLogService callLogService = new CallLogService(callLogRepository, callLogAssembler);

    return new CallLogResourceImpl(callLogService);
  }
}
