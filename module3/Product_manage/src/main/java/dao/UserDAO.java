package dao;

import model.Product;
import model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements IUserDAO {
    private String jdbcURL = "jdbc:mysql://localhost:3306/product_manage?useSSL=false";
    private String jdbcUsername = "root";
    private String jdbcPassword = "12345678";

    private static final String INSERT_USERS_SQL = "INSERT INTO users" + "  (name, email, idCountry,password) VALUES " +
            " (?, ?, ?,?);";

    private static final String SELECT_USER_BY_ID = "select id,name,email,idCountry,password from users where id =?";
    private static final String SELECT_ALL_USERS = "select * from users";
    private static final String DELETE_USERS_SQL = "delete from users where id = ?;";
    private static final String CHECK_USER_PASS = "select * from users where user = ? and password = ?";
    private static final String UPDATE_USERS_SQL = "update users set name = ?,email= ?, idCountry =?,password=? where id = ?;";
    private static final String SELECT_USER_BY_EMAIL = "select u.id,u.name,u.email, u.idCountry, u.password\r\n"
            + "    		 from users as u inner join country as c\r\n"
            + "    		where u.email = ? and u.idCountry = c.id;";
    private int noOfRecords;


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
    public void insertUser(User user) throws SQLException {
        System.out.println(INSERT_USERS_SQL);
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL)) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setInt(3, user.getIdCountry());
            preparedStatement.setString(1, user.getPassword());
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
    public User selectUser(int id) {
        User user = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID);) {
            preparedStatement.setInt(1, id);
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String name = rs.getString("name");
                String email = rs.getString("email");
                int idcountry = Integer.parseInt(rs.getString("idCountry"));
                String password = rs.getString("password");
                user = new User(id, name, email, idcountry, password);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return user;
    }

    @Override
    public List<User> selectAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection connection = getConnection();

             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS);) {
            System.out.println(this.getClass() + " selectAllUsers() + statement: " + preparedStatement);

            ResultSet rs = preparedStatement.executeQuery();


            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                int idCountry = Integer.parseInt(rs.getString("idCountry"));
                String password = rs.getString("password");
                users.add(new User(id, name, email, idCountry, password));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return users;
    }

    @Override
    public boolean deleteUser(int id) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(DELETE_USERS_SQL);) {
            statement.setInt(1, id);
            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }

    @Override
    public boolean updateUser(User user) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(UPDATE_USERS_SQL);) {
            statement.setInt(1, user.getId());
            statement.setString(2, user.getName());
            statement.setString(3, user.getEmail());
            statement.setInt(4, user.getIdCountry());
            statement.setString(5, user.getPassword());

            rowUpdated = statement.executeUpdate() > 0;
        }
        return rowUpdated;
    }

    @Override
    public int getNoOfRecords() {
        return noOfRecords;
    }

    @Override
    public List<User> selectUserPaging(int offset, int noOfRecords, String search) {

        String query = "select SQL_CALC_FOUND_ROWS * from users where name like ? limit "
                + offset + ", " + noOfRecords;
        List<User> list = new ArrayList<User>();
        User user = null;

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = getConnection();

            stmt = connection.prepareStatement(query);
            stmt.setString(1, '%' + search + '%');

            System.out.println(stmt);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setEmail(resultSet.getString("email"));
                user.setIdCountry(resultSet.getInt("idCountry"));
                user.setPassword(resultSet.getString("password"));
                list.add(user);
            }
            resultSet.close();
            resultSet = stmt.executeQuery("SELECT FOUND_ROWS()");
            if (resultSet.next())
                this.noOfRecords = resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

}
