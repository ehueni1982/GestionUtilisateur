package dao;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.User;



public class UserDao {
	//Connexion à la BDD
	
	private String jdbc = "jdbc:mysql://localhost:3306/demo?useSSL=FALSE&serverTimezone=UTC&useLegacyDatetimeCode=false";
	private String jdbcUserName = "root";
	private String jdbcPassword = "";

	//Création de requêtes SQL
	private static final String INSERT_USERS_SQL = "INSERT INTO users" + "(name, email, country)VALUES" + "(?, ?, ?)";
	private static final String SELECT_USER_BY_ID = "select id, name, email, country from users where id = ?";
	private static final String SELECT_ALL_USERS = "select * from users";
	private static final String DELETE_USERS_SQL = "delete from users where id= ?";
	private static final String UPDATE_USERS_SQL= "update users set name = ?, email = ?, country = ? where id = ?";

	//Création de la fonction de connexion
	
	
	  private Connection getConnection() throws SQLException,
	  ClassNotFoundException {
	  
	  Class.forName("com.mysql.cj.jdbc.Driver"); Connection connection =
	  DriverManager.getConnection(jdbc,jdbcUserName,jdbcPassword);
	  System.out.println("Connected to Database.");
	  
	  return connection;
	  
	  
	  }
	 
	
	
	
	/*
	 * protected Connection getConnection() {
	 * 
	 * Connection connection = null; try { Class.forName("com.mysql.jdbc.Drive");
	 * connection = DriverManager.getConnection(jdbc,jdbcUserName,jdbcPassword);
	 * 
	 * }catch(SQLException e){ e.printStackTrace(); } catch (ClassNotFoundException
	 * e) { e.printStackTrace(); } return connection; }
	 */
	//Création d'une méthode insert
	public void insertUser(User user) throws SQLException{
		try(Connection connection = getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL)){
		preparedStatement.setString(1, user.getName());
		preparedStatement.setString(2, user.getEmail());
		preparedStatement.setString(3, user.getCountry());

		preparedStatement.executeUpdate();

		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	//Création de la méthode update
	public boolean updateUser(User user) throws SQLException, ClassNotFoundException {
		boolean rowUpdated;
		try(Connection connection = getConnection();
		PreparedStatement statement =  connection.prepareStatement(UPDATE_USERS_SQL);){

		statement.setString(1, user.getName());
		statement.setString(2, user.getEmail());
		statement.setString(3, user.getCountry());
		statement.setInt(4, user.getId());
		
		rowUpdated = statement.executeUpdate()>0;

		}
		return rowUpdated;
	}

	//Sélection d'un utilisateur par id
	public User selectUser(int id) throws SQLException{
		User user = null;
		try(Connection connection = getConnection();
		PreparedStatement statement =  connection.prepareStatement(SELECT_USER_BY_ID);){
			statement.setInt(1, id);
			ResultSet rs = statement.executeQuery();

			while(rs.next()) {
				String name = rs.getString("name");
				String email = rs.getString("email");
				String country = rs.getString("country");
				user = new User(id, name, email, country);

			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	//Sélection de tous les utilisateurs
	public List<User> selectAllUser() throws SQLException{
		List<User> users = new ArrayList<>();
		try (Connection connection = getConnection();
				PreparedStatement statement =  connection.prepareStatement(SELECT_ALL_USERS);){
			ResultSet rs = statement.executeQuery();

			while(rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String email = rs.getString("email");
				String country = rs.getString("country");
				users.add(new User(id, name, email, country));
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return users;
	}

	//Suppression d'utilisateur
	public boolean deleteUser(int id) throws SQLException, ClassNotFoundException{
		boolean rowDeleted;
		try(Connection connection = getConnection();
				PreparedStatement statement =  connection.prepareStatement(DELETE_USERS_SQL);){
			statement.setInt(1, id);
			rowDeleted = statement.executeLargeUpdate() > 0;

		}
		return rowDeleted;

	}

}