package controller;

import dao.CategoryDAO;
import dao.ICategoryDAO;
import dao.IProductDAO;
import dao.ProductDAO;
import model.Product;

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

@WebServlet(name = "productServlet", value = "/products")
public class ProductServlet extends HttpServlet {
    IProductDAO iProductDAO;
    ICategoryDAO iCategoryDAO;
    private String errors;

    @Override
    public void init() throws ServletException {
        iProductDAO = new ProductDAO();
        iCategoryDAO = new CategoryDAO();
        if (this.getServletContext().getAttribute("listCategories") == null) {
            this.getServletContext().setAttribute("listCategories", iCategoryDAO.selectAllCategory());
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
                    deleteProduct(request, response);
                    break;
                default:
                    showListProduct(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void showListProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String search = "";
        if (request.getParameter("search") != null) {
            search = request.getParameter("search");
        }
        List<Product> listProduct = iProductDAO.selectAllProductSearch(search);
        request.setAttribute("products", listProduct);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/product/list.jsp");
        requestDispatcher.forward(request, response);
    }

    private void showFormEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Product existingProduct = iProductDAO.selectProduct(id);
        request.setAttribute("product", existingProduct);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/product/edit.jsp");
        dispatcher.forward(request, response);
    }

    private void showFormCreate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/product/create.jsp");
        requestDispatcher.forward(request, response);
    }

    private void deleteProduct(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        iProductDAO.deleteProduct(id);
        response.sendRedirect("products");
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
                    insertProduct(request, response);
                    break;
                case "edit":
                    updateProduct(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void insertProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        Product product = null;
        HashMap<String, List<String>> errors = new HashMap<>();
        try {
            String name = request.getParameter("name");
            double price = 0;
            if (request.getParameter("price") != "") {
                price = Double.parseDouble(request.getParameter("price"));
            }
            int quantity = 0;
            if (request.getParameter("quantity") != "") {
                quantity = Integer.parseInt(request.getParameter("quantity"));
            }
            String color = request.getParameter("color");
            String description = request.getParameter("description");
            int idCategory = Integer.parseInt(request.getParameter("idCategory"));

            product = new Product(name, price, quantity, color, description, idCategory);

            ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
            Validator validator = validatorFactory.getValidator();
            Set<ConstraintViolation<Product>> constraintViolations = validator.validate(product);
            if (!constraintViolations.isEmpty()) {
                request.setAttribute("errors", "MUST NOT BE BRANDED!");
                errors = getErrorFromContraint(constraintViolations);
                request.setAttribute("errors", errors);
                request.setAttribute("product", product);
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/product/create.jsp");
                requestDispatcher.forward(request, response);
            } else {
                iProductDAO.insertProduct(product);
                response.sendRedirect("products");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NumberFormatException ex) {
            System.out.println(this.getClass() + " NumberFormatException: User info from request: " + product);
            List<String> listErrors = Arrays.asList("Number format not right!");
            errors.put("Exception", listErrors);

            request.setAttribute("product", product);
            request.setAttribute("errors", errors);
            request.getRequestDispatcher("/WEB-INF/product/create.jsp").forward(request, response);
        } catch (Exception ex) {
        }
    }


    private void updateProduct(HttpServletRequest request, HttpServletResponse response) throws
            IOException, SQLException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        double price = Double.parseDouble(request.getParameter("price"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        String color = request.getParameter("color");
        String description = request.getParameter("description");
        int idCategory = Integer.parseInt(request.getParameter("idCategory"));
        Product newProduct = new Product(id, name, price, quantity, color, description, idCategory);
        iProductDAO.updateProduct(newProduct);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/product/edit.jsp");
        requestDispatcher.forward(request, response);
    }

    private HashMap<String, List<String>> getErrorFromContraint(Set<ConstraintViolation<Product>> constraintViolations) {
        HashMap<String, List<String>> hashMap = new HashMap<>();
        for (ConstraintViolation<Product> c : constraintViolations) {
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
