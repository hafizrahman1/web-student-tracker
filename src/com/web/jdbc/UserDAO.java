package com.web.jdbc;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.web.exception.RegistrationException;
import com.web.util.OracleSqlQueries;


public class UserDAO {
	//Create new user and save it to the database
	public int createUser(User user) throws ClassNotFoundException, IOException, RegistrationException, SQLException {
		int userId = 0;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		
		String[] idColumn = {"user_id"};
		OracleConnection oracle = new OracleConnection();
		
		try {
			conn = oracle.getConnection();
			System.out.println("Connection Established!");
			//What's the purpose of idColumn?
			stmt = conn.prepareStatement(OracleSqlQueries.CREATE_USER, idColumn);
			//Fill out the ? in the SQL query string
			stmt.setString(1, user.getFirstName());
			stmt.setString(2, user.getLastName());
			stmt.setString(3, user.getUserName());
			stmt.setString(4, user.getPassword());
			stmt.setString(5, user.getGender());
			stmt.setString(6, user.getAddress());
			stmt.setLong(7, user.getPhoneNumber());
			stmt.setString(8, user.getEmail());
			//For new user registration
			stmt.executeUpdate();
			
			//Retrieve any auto generated keys created as a result of executing this statement object
			result = stmt.getGeneratedKeys();
			if(result.next()) {
				userId = result.getInt(1);
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
			throw new RegistrationException(e.getMessage());
		}finally {
			if(result != null) {
				result.close();
			}
			if (stmt != null) {
				stmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
		
		return userId;
	}
	
	//Get the user object by user_id
	public User getUser(int userId) throws RegistrationException, SQLException, ClassNotFoundException, IOException{
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		User user = null;
		OracleConnection oracle = new OracleConnection();
		try {
			conn = oracle.getConnection();
			System.out.println("Connection Established!");
			stmt = conn.prepareStatement(OracleSqlQueries.GET_USER_BY_ID);
			//Assign the userName to the sql query prepared statement
			stmt.setInt(1, userId);
			//Execute the prepared statement query and store it to resultSet
			resultSet = stmt.executeQuery();
			
			if(resultSet.next()) {
				user = new User();
				user.setUserId(resultSet.getInt(1));
				user.setFirstName(resultSet.getString(2));
				user.setLastName(resultSet.getString(3));
				user.setUserName(resultSet.getString(4));
				user.setPassword(resultSet.getString(5));
				user.setGender(resultSet.getString(6));
				user.setAddress(resultSet.getString(7));
				user.setPhoneNumber(resultSet.getLong(8));
				user.setEmail(resultSet.getString(9));
				user.setAdminRole(resultSet.getInt(10));
			}
			
			
		}catch(SQLException e){
			throw new RegistrationException(e.getMessage());
		}finally {
			try {
				if(resultSet != null) {
					resultSet.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			}catch(SQLException e) {
				System.out.println(e.getStackTrace());
			}
		}
		return user;
	}
	
	//Get the user object by user_name
	public User getUser(String userName) throws RegistrationException, SQLException, ClassNotFoundException, IOException{
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		User user = null;
		OracleConnection oracle = new OracleConnection();
		try {
			conn = oracle.getConnection();
			System.out.println("Connection Established!");
			stmt = conn.prepareStatement(OracleSqlQueries.GET_USER);
			//Assign the userName to the sql query prepared statement
			stmt.setString(1, userName);
			//Execute the prepared statement query and store it to resultSet
			resultSet = stmt.executeQuery();
			
			if(resultSet.next()) {
				user = new User();
				user.setUserId(resultSet.getInt(1));
				user.setFirstName(resultSet.getString(2));
				user.setLastName(resultSet.getString(3));
				user.setUserName(resultSet.getString(4));
				user.setPassword(resultSet.getString(5));
				user.setGender(resultSet.getString(6));
				user.setAddress(resultSet.getString(7));
				user.setPhoneNumber(resultSet.getLong(8));
				user.setEmail(resultSet.getString(9));
				user.setAdminRole(resultSet.getInt(10));
			}
			
			
		}catch(SQLException e){
			throw new RegistrationException(e.getMessage());
		}finally {
			try {
				if(resultSet != null) {
					resultSet.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			}catch(SQLException e) {
				System.out.println(e.getStackTrace());
			}
		}
		return user;
	}
	
	//Get all the users
	public List<User> getAllUsers() throws RegistrationException, SQLException, ClassNotFoundException, IOException{
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		List<User> usersList = null;
		User user = null;
		OracleConnection oracle = new OracleConnection();
		try {
			conn = oracle.getConnection();
			System.out.println("Connection Established!");
			stmt = conn.prepareStatement(OracleSqlQueries.GET_USERS);
			resultSet = stmt.executeQuery();
			usersList = new ArrayList<User>();
			
			while(resultSet.next()) {
				user = new User();
				user.setUserId(resultSet.getInt(1));
				user.setFirstName(resultSet.getString(2));
				user.setLastName(resultSet.getString(3));
				user.setUserName(resultSet.getString(4));
				user.setPassword(resultSet.getString(5));
				user.setGender(resultSet.getString(6));
				user.setAddress(resultSet.getString(7));
				user.setPhoneNumber(resultSet.getLong(8));
				user.setEmail(resultSet.getString(9));
				user.setAdminRole(resultSet.getInt(10));
				//Add to the users list
				usersList.add(user);
			}
		}catch(SQLException e) {
			throw new RegistrationException(e.getMessage());
		}finally {
			if(resultSet != null) {
				resultSet.close();
			}
			if (stmt != null) {
				stmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
		
		return usersList;
	}
	
	//Update the user
	public void updateUser(User user) throws ClassNotFoundException, IOException, RegistrationException, SQLException {
		Connection conn = null;
		PreparedStatement stmt = null;
		OracleConnection oracle = new OracleConnection();
		
		try {
			conn = oracle.getConnection();
			System.out.println("Connection Established!");
			stmt = conn.prepareStatement(OracleSqlQueries.UPDATE_USER);
			stmt.setString(1, user.getFirstName());
			stmt.setString(2, user.getLastName());
			stmt.setString(3, user.getUserName());
			stmt.setString(4, user.getPassword());
			stmt.setString(5, user.getGender());
			stmt.setString(6, user.getAddress());
			stmt.setLong(7, user.getPhoneNumber());
			stmt.setString(8, user.getEmail());
			stmt.setInt(9, user.getUserId());
			
			// execute the update statement
			stmt.executeUpdate();
		
		}catch(SQLException e) {
			throw new RegistrationException(e.getMessage());
		}finally {
			if(stmt != null) {
				stmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
	}
	
	public static void main(String[] args) throws ClassNotFoundException, RegistrationException, SQLException, IOException {
		UserDAO DAO = new UserDAO();
		User user = DAO.getUser("user");
		System.out.println(user.getFirstName() + "|" + user.getLastName() + "|" + user.getGender() + "|" + user.getUserName() + "|" + user.getPassword() + "|" + user.getEmail());
		
		User newUser = new User();
		newUser.setFirstName("Hafizur");
		newUser.setLastName("Rahman");
		newUser.setUserName("hafizur");
		newUser.setPassword("password");
		newUser.setGender("M");
		newUser.setAddress("3506 73rd St Jackson Heights");
		newUser.setPhoneNumber(3475278509L);
		newUser.setEmail("hafizur@restaurant.org");
		
		int userId = DAO.createUser(newUser);
		System.out.println("Last created user id is: " + userId);
//		DAO.updateUser(newUser);
		
		List<User> users = DAO.getAllUsers();
		System.out.println(users);
	}

	public void deleteUser(int userId) throws Exception {
		Connection conn = null;
		PreparedStatement stmt = null;
		OracleConnection oracle = new OracleConnection();
		
		try {
			// get connection to database
			conn = oracle.getConnection();
			
			// prepared statement
			stmt = conn.prepareStatement(OracleSqlQueries.DELETE_USER);
			
			// set params
			stmt.setInt(1, userId);
			
			// execute sql statement
			stmt.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			// clean up JDBC code
			if (stmt != null) {
				stmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
	}
}
