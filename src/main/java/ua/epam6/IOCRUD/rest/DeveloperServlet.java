package ua.epam6.IOCRUD.rest;

import com.google.gson.Gson;
import org.apache.log4j.Logger;
import ua.epam6.IOCRUD.model.Developer;
import ua.epam6.IOCRUD.service.DeveloperService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class DeveloperServlet extends HttpServlet {

    private static final Logger log = Logger.getLogger(DeveloperServlet.class);
    private DeveloperService developerService;
    private Gson gson;

    public DeveloperServlet() {
        developerService = new DeveloperService();
        gson = new Gson();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if(request.getParameter("type") != null) {
            log.debug("Request with type parameter (POST)");
            switch (request.getParameter("type")) {
                case "create":
                    break;
                case "read":
                    this.doGet(request, response);
                    break;
                case "update":
                    this.doPut(request, response);
                    break;
                case "delete":
                    this.doDelete(request, response);
                    break;
                default:
                    log.warn("Invalid parameter type given by POST request");
                    response.sendError(400, "invalid parameter type");
            }
        }
        log.debug("Request create (POST)");
        try {
            developerService.create(gson.fromJson(request.getReader(), Developer.class));
        } catch (Exception e) {
            log.error("Error in creation request (POST)", e);
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.debug("Request to read (GET)");
        response.setContentType("application/json");
        PrintWriter writer = response.getWriter();
        try {
            if(request.getParameter("id") == null || !request.getParameter("id").matches("\\d+")) {
                log.debug("Request to get all");
                writer.println(gson.toJson(developerService.getAll()));
                log.debug("Sent JSON response");
            } else {
                log.debug("Request to get by ID");
                writer.println(gson.toJson(developerService.getById(Long.parseLong(request.getParameter("id")))));
                log.debug("Sent JSON response");
            }
        } catch (Exception e) {
            log.error("Error in reading entry", e);
            e.printStackTrace();
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) {
        log.debug("Request to update (PUT)");
        try {
            developerService.update(gson.fromJson(request.getReader(), Developer.class));
        } catch (Exception e) {
            log.error("Error in updating entry", e);
            e.printStackTrace();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
        log.debug("Request to delete (DELETE)");
        try {
            if(request.getParameter("id") == null || !request.getParameter("id").matches("\\d+")) {
                log.warn("Invalid id parameter given by DELETE request");
                response.sendError(400, "Invalid parameter ID");
            } else {
                developerService.delete(Long.parseLong(request.getParameter("id")));
            }
        } catch (Exception e) {
            log.error("Error in deleting entry", e);
            e.printStackTrace();
        }
    }
}