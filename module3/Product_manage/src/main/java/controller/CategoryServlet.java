package controller;

import dao.CategoryDAO;
import dao.ICategoryDAO;
import model.Category;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "categoryServlet", value = "/categorys")
public class CategoryServlet extends HttpServlet {
    ICategoryDAO iCategoryDAO;

    @Override
    public void init() throws ServletException {
        iCategoryDAO = new CategoryDAO();
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
                    deleteCategory(request, response);
                    break;
                default:
                    showListCategory(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void showListCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Category> listCategory = iCategoryDAO.selectAllCategory();
        request.setAttribute("listCategory", listCategory);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/category/list.jsp");
        requestDispatcher.forward(request, response);
    }

    private void deleteCategory(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException {
        int id = Integer.parseInt(request.getParameter("id"));
        iCategoryDAO.deleteCategory(id);
        List<Category> listCategory = iCategoryDAO.selectAllCategory();
        request.setAttribute("listCategory", listCategory);
        RequestDispatcher dispatcher = request.getRequestDispatcher("category/list.jsp");
        dispatcher.forward(request, response);
    }

    private void showFormEdit(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Category existingCategory= iCategoryDAO.selectCategory(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("category/edit.jsp");
        request.setAttribute("category", existingCategory);
        dispatcher.forward(request, response);
    }

    private void showFormCreate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/category/create.jsp");
        requestDispatcher.forward(request, response);
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
                    insertCategory(request, response);
                    break;
                case "edit":
                    updateCategory(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void updateCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        Category newCategory = new Category(id, name);
        iCategoryDAO.updateCategory(newCategory);
        response.sendRedirect("/categorys");
//        RequestDispatcher dispatcher = request.getRequestDispatcher("category/edit.jsp");
//        dispatcher.forward(request, response);
    }

    private void insertCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        String name = request.getParameter("name");
        Category newCategory = new Category(name);
        iCategoryDAO.insertCategory(newCategory);
        response.sendRedirect("/categorys");
//        RequestDispatcher dispatcher = request.getRequestDispatcher("category/create.jsp");
//        dispatcher.forward(request, response);
    }
}
