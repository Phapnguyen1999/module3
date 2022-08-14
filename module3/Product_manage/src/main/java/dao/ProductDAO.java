package dao;

import model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO implements IProductDAO {
    private String jdbcURL = "jdbc:mysql://localhost:3306/product_manage?useSSL=false";
    private String jdbcUsername = "root";
    private String jdbcPassword = "12345678";

    private static final String INSERT_PRODUCT_SQL = "INSERT INTO product (name,img,quantity,price,idCategory,deleted) VALUES (?,?, ?, ?, ?,?);";
    private static final String SELECT_PRODUCT_BY_ID = "select id,name,img,quantity,price,idCategory,deleted from product where id =?";
    private static final String SELECT_ALL_PRODUCTS = "select * from product where deleted=0";
    private static final String DELETE_PRODUCT_SQL = "update product set deleted = 1 where id = ?;";
    private static final String UPDATE_PRODUCT_SQL = "update product set name = ?,img = ?,quantity = ?, price =?,idCategory=?,deleted=? where id = ?;";
    private int noOfRecords;

    public ProductDAO() {

    }

    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return connection;
    }

    @Override
    public void insertProduct(Product product) {
        System.out.println(INSERT_PRODUCT_SQL);
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PRODUCT_SQL)) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getImg());
            preparedStatement.setInt(3, product.getQuantity());
            preparedStatement.setDouble(4, product.getPrice());
            preparedStatement.setInt(5, product.getIdCategory());
            preparedStatement.setInt(6,product.getDeleted());
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    private void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }


    @Override
    public Product selectProduct(int id) {
        Product product = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PRODUCT_BY_ID);) {
            preparedStatement.setInt(1, id);
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String name = rs.getString("name");
                String img = rs.getString("img");
                int quantity = rs.getInt("quantity");
                double price = rs.getDouble("price");
                int idcategory = rs.getInt("idCategory");
                int deleted= rs.getInt("deleted");
                product = new Product(name,img, quantity, price, idcategory,deleted);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return product;
    }

    @Override
    public List<Product> selectAllProduct() {
        List<Product> products = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_PRODUCTS);) {
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String img = rs.getString("img");
                int quantity = rs.getInt("quantity");
                double price = rs.getDouble("price");
                int idCategory = rs.getInt("idCategory");
                int deleted=rs.getInt("deleted");
                products.add(new Product(id, name,img, quantity, price, idCategory,deleted));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return products;
    }

    @Override
    public List<Product> selectProductPaging(int offset, int noOfRecords) {
        String query = "select SQL_CALC_FOUND_ROWS * from product limit " + offset + ", " + noOfRecords;
        List<Product> list= new ArrayList<Product>();
        Product product=null;
        Statement statement=null;
        Connection connection=null;
        try{
            connection =getConnection();
            statement=connection.createStatement();
            ResultSet resultSet=statement.executeQuery(query);
            while (resultSet.next()){
                product=new Product();
                product.setId(resultSet.getInt("id"));
                product.setName(resultSet.getString("name"));
                product.setImg(resultSet.getString("img"));
                product.setQuantity(resultSet.getInt("quantity"));
                product.setPrice(resultSet.getDouble("price"));
                product.setIdCategory(resultSet.getInt("idCategory"));
                product.setDeleted(resultSet.getInt("deleted"));
                list.add(product);
            }
            resultSet.close();
            resultSet=statement.executeQuery("SELECT FOUND_ROWS()");
            if (resultSet.next())
                this.noOfRecords=resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null)
                    statement.close();
                if (connection!= null)
                    connection.close();
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
        return list;
    }
    public List<Product> selectProductPaging(int offset, int noOfRecords,String search) {
        String query = "select SQL_CALC_FOUND_ROWS * from product where name like ?  AND deleted = 0 limit "
                + offset + ", " + noOfRecords+"";
        List<Product> list= new ArrayList<Product>();
        Product product=null;

        Connection connection=null;
        PreparedStatement stmt=null;
        try{
            connection =getConnection();

            stmt = connection.prepareStatement(query);
            stmt.setString(1,'%' + search + '%');
            System.out.println(stmt);
            ResultSet resultSet=stmt.executeQuery();
            while (resultSet.next()){
                product=new Product();
                product.setId(resultSet.getInt("id"));
                product.setName(resultSet.getString("name"));
                product.setImg(resultSet.getString("img"));
                product.setQuantity(resultSet.getInt("quantity"));
                product.setPrice(resultSet.getDouble("price"));
                product.setIdCategory(resultSet.getInt("idCategory"));
                product.setDeleted(resultSet.getInt("deleted"));
                list.add(product);
            }
            resultSet.close();
            resultSet=stmt.executeQuery("SELECT FOUND_ROWS()");
            if (resultSet.next())
                this.noOfRecords=resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
                if (connection!= null)
                    connection.close();
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
        return list;
    }
    public int getNoOfRecords(){
        return noOfRecords;
    }

    @Override
    public boolean deleteProduct(int id) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_PRODUCT_SQL);) {
            statement.setInt(1, id);
            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }

    @Override
    public boolean updateProduct(Product product) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_PRODUCT_SQL);) {
            statement.setString(1, product.getName());
            statement.setString(2, product.getImg());
            statement.setInt(3, product.getQuantity());
            statement.setDouble(4, product.getPrice());
            statement.setInt(5, product.getIdCategory());
            statement.setInt(6,product.getDeleted());
            statement.setInt(7, product.getId());
            rowUpdated = statement.executeUpdate() > 0;
        }
        return rowUpdated;
    }
}
