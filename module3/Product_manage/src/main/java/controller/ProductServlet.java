package controller;

import dao.CategoryDAO;
import dao.ICategoryDAO;
import dao.IProductDAO;
import dao.ProductDAO;
import model.Product;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.validation.*;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

@WebServlet(name = "productServlet", value = "/products")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2,
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 50)
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
                    showListProductPaging(request,response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }


    private void showListProductPaging(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        int page=1;
        int recordsPerPage=5;
        String search="";
        if (request.getParameter("search")!=null){
            search=request.getParameter("search");
        }
        if (request.getParameter("page")!=null) {
            page = Integer.parseInt(request.getParameter("page"));
        }

        List<Product> listProduct=iProductDAO.selectProductPaging((page-1)*recordsPerPage,recordsPerPage,search);
        int noOfRecords=iProductDAO.getNoOfRecords();
        int noOfPages =(int) Math.ceil(noOfRecords*1.0/recordsPerPage);
        request.setAttribute("products",listProduct);
        request.setAttribute("noOfPages",noOfPages);
        request.setAttribute("currentPage",page);

        request.setAttribute("search",search);

        RequestDispatcher requestDispatcher=request.getRequestDispatcher("/WEB-INF/product/list.jsp");
        requestDispatcher.forward(request,response);
    }

    private void deleteProduct(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        iProductDAO.deleteProduct(id);
        response.sendRedirect("products");
//        RequestDispatcher dispatcher = request.getRequestDispatcher("product/edit.jsp");
//        dispatcher.forward(request, response);
    }
    private void showListProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Product> products = iProductDAO.selectAllProduct();
        request.setAttribute("products", products);
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

    private void updateProduct(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, ServletException {
//        int id=Integer.parseInt(request.getParameter("id"));
//        String name = request.getParameter("name");
//        String img=null;
//        for (Part part : request.getParts()) {
//            System.out.println("Content type of Part" + part.getContentType());
//            System.out.println("Name of Part" + part.getName());
//            if(part.getName().equals("img")){
//                String fileName = extractFileName(part);
//                fileName = new File(fileName).getName();
//                part.write("/Users/macbookpro/Documents/module3/module3/Product_manage/src/main/webapp/images/" + fileName);
//
//                String servletRealPath = this.getServletContext().getRealPath("/") + "/images/" + fileName;
//                System.out.println("servletRealPath: " + servletRealPath);
//                part.write(servletRealPath);
//                img=fileName;
//            }
//        }
//        int quantity = Integer.parseInt(request.getParameter("quantity"));
//        double price = Double.parseDouble(request.getParameter("price"));
//        int idCategory = Integer.parseInt(request.getParameter("idCategory"));
//        int deleted=0;
//
//        Product newProduct = new Product(id,name,img, quantity, price, idCategory,deleted);
//        iProductDAO.updateProduct(newProduct);
//        request.setAttribute("success", "Update Success!");
//        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/product/edit.jsp");
//        requestDispatcher.forward(request, response);
        Product product = null;
        HashMap<String, List<String>> errors = new HashMap<>();
        try {
            int id=Integer.parseInt(request.getParameter("id"));
            String name = request.getParameter("name");
            int quantity = 0;
            if (!request.getParameter("quantity").equals("")) {
                quantity = Integer.parseInt(request.getParameter("quantity"));
            }
            int idcategory = Integer.parseInt(request.getParameter("idCategory"));
            double price = 0;
            if (!request.getParameter("price").equals("")) {
                price = Double.parseDouble(request.getParameter("price"));
            }
            int deleted =0;
            product = new Product(id,name, quantity, price,idcategory,deleted);
            for (Part part : request.getParts()) {
                System.out.println("Content type of Part" + part.getContentType());
                System.out.println("Name of Part" + part.getName());
                if(part.getName().equals("img")){
                    String fileName = extractFileName(part);
                    if(!fileName.equals(""))
                    {
                        fileName = new File(fileName).getName();
                        part.write("/Users/macbookpro/Documents/module3/module3/Product_manage/src/main/webapp/images/" + fileName);

                        String servletRealPath = this.getServletContext().getRealPath("/") + "images/" + fileName;
                        System.out.println("servletRealPath: " + servletRealPath);
                        part.write(servletRealPath);
                        product.setImg(fileName);
                    }else{
                        product.setImg("tv soni.png");
                    }
                }
            }

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
                iProductDAO.updateProduct(product);
                Product pro = new Product();
                request.setAttribute("product", pro);
                request.setAttribute("success", "Insert Success!");
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/product/create.jsp");
                requestDispatcher.forward(request, response);
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
            ex.printStackTrace();
        }
    }

    private void insertProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        Product product = null;
        HashMap<String, List<String>> errors = new HashMap<>();
        try {

            String name = request.getParameter("name");
            int quantity = 0;
            if (!request.getParameter("quantity").equals("")) {
                quantity = Integer.parseInt(request.getParameter("quantity"));
            }
            int idcategory = Integer.parseInt(request.getParameter("idCategory"));
            double price = 0;
            if (!request.getParameter("price").equals("")) {
                price = Double.parseDouble(request.getParameter("price"));
            }
            int deleted =0;
            product = new Product(name, quantity, price,idcategory,deleted);
            for (Part part : request.getParts()) {
                System.out.println("Content type of Part" + part.getContentType());
                System.out.println("Name of Part" + part.getName());
                if(part.getName().equals("img")){
                        String fileName = extractFileName(part);
                        if(!fileName.equals(""))
                        {
                            fileName = new File(fileName).getName();
                            part.write("/Users/macbookpro/Documents/module3/module3/Product_manage/src/main/webapp/images/" + fileName);

                            String servletRealPath = this.getServletContext().getRealPath("/") + "images/" + fileName;
                            System.out.println("servletRealPath: " + servletRealPath);
                            part.write(servletRealPath);
                            product.setImg(fileName);
                        }else{
                            product.setImg("tv soni.png");
                        }
                }
            }

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
                Product pro = new Product();
                request.setAttribute("product", pro);
                request.setAttribute("success", "Insert Success!");
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/product/create.jsp");
                requestDispatcher.forward(request, response);
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
            ex.printStackTrace();
        }
    }
    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length() - 1);
            }
        }
        return "";
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
