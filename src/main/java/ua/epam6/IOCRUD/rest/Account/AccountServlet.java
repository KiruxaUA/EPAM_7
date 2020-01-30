package ua.epam6.IOCRUD.rest.Account;

import com.google.gson.Gson;
import org.apache.log4j.Logger;
import ua.epam6.IOCRUD.model.Account;
import ua.epam6.IOCRUD.service.AccountService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "AccountServlet", urlPatterns = "/api/v1/accounts")
public class AccountServlet extends HttpServlet {

    private final static Logger log = Logger.getLogger(AccountServlet.class);
    private Gson gson;
    private AccountService accountService;

    public AccountServlet() {
        gson = new Gson();
        accountService = new AccountService();
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
                    response.sendError(400, "Invalid parameter type");
            }
        }
        log.debug("Request create (POST)");
        try {
            accountService.create(gson.fromJson(request.getReader(), Account.class));
        } catch (IOException e) {
            log.error("Error in creation request (POST)");
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
                writer.println(accountService.getAll());
                log.debug("Sent JSON response");
            } else {
                log.debug("Request to get by ID");
                writer.println(gson.toJson(accountService.getById(Long.parseLong(request.getParameter("id")))));
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
            accountService.update(gson.fromJson(request.getReader(), Account.class));
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
                accountService.delete(Long.parseLong(request.getParameter("id")));
            }
        } catch (Exception e) {
            log.error("Error in deleting entry", e);
            e.printStackTrace();
        }
    }
}