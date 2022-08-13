package controller;

import dao.CountryDAO;
import dao.ICountryDAO;
import dao.IUserDAO;
import dao.UserDAO;
import model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.function.BiConsumer;

@WebServlet(name = "UserServlet", urlPatterns = "/users")
public class UserServlet extends HttpServlet {
    IUserDAO iUserDAO;
    ICountryDAO iCountryDAO;
    private String errors;

    @Override
    public void init() throws ServletException {
        iUserDAO = new UserDAO();
        iCountryDAO = new CountryDAO();
        if (this.getServletContext().getAttribute("listCountry") == null) {
            this.getServletContext().setAttribute("listCountry", iCountryDAO.selectAllCountry());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        try {
            switch (action) {
                case "create":
                    showFormCreate(request, response);
                    break;
                case "edit":
                    showFormEdit(request, response);
                    break;
                case "delete":
                    deleteUser(request, response);
                    break;
                default:
                    showListUserPaging(request, response);
                    break;
            }

        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        iUserDAO.deleteUser(id);
        List<User> listUser = iUserDAO.selectAllUsers();
        request.setAttribute("users", listUser);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/users?action=list");
        dispatcher.forward(request, response);
    }

    private void showFormEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        User existingUser = iUserDAO.selectUser(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/user/edit.jsp");
        request.setAttribute("user", existingUser);
        dispatcher.forward(request, response);
    }

    private void showListUserPaging(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int page = 1;
        int recordsPerPage = 5;
        String search = "";
        if (request.getParameter("search") != null) {
            search = request.getParameter("search");
        }
        if (request.getParameter("page") != null){
            page = Integer.parseInt(request.getParameter("page"));
        }

        List<User> listUser = iUserDAO.selectUserPaging((page - 1) * recordsPerPage, recordsPerPage, search);
        int noOfRecords = iUserDAO.getNoOfRecords();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
        request.setAttribute("users", listUser);
        request.setAttribute("noOfPages", noOfPages);
        request.setAttribute("currentPage", page);
        request.setAttribute("search", search);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/user/list.jsp");
        requestDispatcher.forward(request, response);

    }

    private void showFormCreate(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/user/create.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        try {
            switch (action) {
                case "create":
                    insertUser(request, response);
                    break;
                case "edit":
                    updateUser(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }
    private void updateUser(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String email=request.getParameter("email");
        int idCountry = Integer.parseInt(request.getParameter("idCountry"));
        String password=request.getParameter("password");

        User newUser = new User(id, name,email, idCountry, password);
        iUserDAO.updateUser(newUser);
        response.sendRedirect("/users");
//        RequestDispatcher dispatcher = request.getRequestDispatcher("product/edit.jsp");
//        dispatcher.forward(request, response);
    }

    private void insertUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user =new User();
        boolean flag = true;
        Map<String, String> hashMap = new HashMap<String, String>();
        System.out.println(this.getClass() + " insertUser with validate");
        try {
            String name = request.getParameter("name");
            String email=request.getParameter("email");
            int idCountry = Integer.parseInt(request.getParameter("idCountry"));
            String password=request.getParameter("password");
            user = new User(name,email, idCountry, password);
            System.out.println(this.getClass() + "User info from request: " + user);

            ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
            Validator validator = validatorFactory.getValidator();

            Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);

            System.out.println("User: " + user);

            if (!constraintViolations.isEmpty()) {

                errors  = "<ul>";
                for (ConstraintViolation<User> constraintViolation : constraintViolations) {
                    errors += "<li>"+constraintViolation.getPropertyPath()+ " "  + constraintViolation.getMessage()
                            + "</li>";
                }
                errors += "</ul>";


                request.setAttribute("user", user);
                request.setAttribute("errors", errors);


                System.out.println(this.getClass() + " !constraintViolations.isEmpty()");
                request.getRequestDispatcher("WEB-INF/user/create.jsp").forward(request, response);
            }else{
                if(iCountryDAO.selectCountry(idCountry)==null) {
                    flag = false;
                    hashMap.put("country", "Country value invalid");
                    System.out.println(this.getClass() + " Country invalid");
                }

                if(flag) {
                    // Create user susscess
                    iUserDAO.insertUser(user);
                    request.setAttribute("user", user);
                    request.setAttribute("success","Insert success");
                    RequestDispatcher requestDispatcher=request.getRequestDispatcher("/WEB-INF/user/create.jsp");

                    requestDispatcher.forward(request,response);
                }else {
                    errors = "<ul>";
                    hashMap.forEach(new BiConsumer<String, String>() {
                        @Override
                        public void accept(String keyError, String valueError) {
                            errors += "<li>"  + valueError
                                    + "</li>";

                        }
                    });
                    errors +="</ul>";

                    request.setAttribute("user", user);
                    request.setAttribute("errors", errors);


                    System.out.println(this.getClass() + " !constraintViolations.isEmpty()");
                    request.getRequestDispatcher("WEB-INF/user/create.jsp").forward(request, response);
                }


            }
        }
        catch (NumberFormatException ex) {
            System.out.println(this.getClass() + " NumberFormatException: Product info from request: " + user);
            errors = "<ul>";
            errors += "<li>" + "Input format not right"
                    + "</li>";

            errors += "</ul>";


            request.setAttribute("user", user);
            request.setAttribute("errors", errors);

            request.getRequestDispatcher("WEB-INF/user/create.jsp").forward(request, response);
        }
        catch(Exception ex){

        }
    }

    private HashMap<String, List<String>> getErrorFromContraint(Set<ConstraintViolation<User>> constraintViolations) {
        HashMap<String, List<String>> hashMap = new HashMap<>();
        for (ConstraintViolation<User> c : constraintViolations) {
            if (hashMap.keySet().contains(c.getPropertyPath().toString())) {
                hashMap.get(c.getPropertyPath().toString()).add(c.getMessage());
            } else {
                List<String> listMessages = new ArrayList<>();
                listMessages.add(c.getMessage());
                hashMap.put(c.getPropertyPath().toString(), listMessages);
            }
        }
        return hashMap;
    }
}
