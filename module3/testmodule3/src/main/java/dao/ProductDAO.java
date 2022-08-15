package dao;

import model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO implements IProductDAO{
    private String jdbcURL = "jdbc:mysql://localhost:3306/productmanage?useSSL=false";
    private String jdbcUsername = "root";
    private String jdbcPassword = "12345678";

    private static final String INSERT_PRODUCT_SQL = "INSERT INTO products (name,price,quantity,color,description,idCategory) VALUES (?, ?, ?, ?, ?, ?);";
    private static final String SELECT_PRODUCT_BY_ID = "select id,name,price,quantity,color,description,idCategory from products where id =?";
    private static final String SELECT_ALL_PRODUCTS = "select * from products";
    private static final String DELETE_PRODUCT_SQL = "delete from products where id = ?;";
    private static final String UPDATE_PRODUCT_SQL = "update products set name = ?,price = ?,quantity = ?, color =?,description=?,idCategory=? where id = ?;";

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
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.setInt(3, product.getQuantity());
            preparedStatement.setString(4, product.getColor());
            preparedStatement.setString(5, product.getDescription());
            preparedStatement.setInt(6,product.getIdCategory());
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
                double price = rs.getDouble("price");
                int quantity = rs.getInt("quantity");
                String color = rs.getString("color");
                String description=rs.getString("description");
                int idCategory = rs.getInt("idCategory");
                product = new Product(name,price, quantity, color,description, idCategory);
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
                int id =rs.getInt("id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                int quantity = rs.getInt("quantity");
                String color = rs.getString("color");
                String description=rs.getString("description");
                int idCategory = rs.getInt("idCategory");
                products.add(new Product(id,name,price, quantity, color,description, idCategory));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return products;
    }

    @Override
    public List<Product> selectAllProductSearch(String search) {
        String query = "select * from products where name like ? ";
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
                product.setPrice(resultSet.getDouble("price"));
                product.setQuantity(resultSet.getInt("quantity"));
                product.setColor(resultSet.getString("color"));
                product.setDescription(resultSet.getString("description"));
                product.setIdCategory(resultSet.getInt("idCategory"));
                list.add(product);
            }
            resultSet.close();
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
            statement.setDouble(2, product.getPrice());
            statement.setInt(3, product.getQuantity());
            statement.setString(4, product.getColor());
            statement.setString(5, product.getDescription());
            statement.setInt(6,product.getIdCategory());
            statement.setInt(7,product.getId());
            rowUpdated = statement.executeUpdate() > 0;
        }
        return rowUpdated;
    }
}
