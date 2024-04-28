package web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UserDao;
import model.User;

/**
 * Servlet implementation class UserServlet
 */

@WebServlet("/")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//Importation de UserDao
	private UserDao userDao;

	@Override
	public void init() {
		userDao = new UserDao();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Le traitement du formulaire se fera ici entrée et réponses de nos requêtes

		doGet(request, response);
	}

@Override
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String action = request.getServletPath();

		try {

			switch (action) {

			case "/new":
				showNewForm(request, response);
				break;
			case "/insert":
				insertUser(request, response);
				break;
			case "/delete":
				deleteUser(request, response);
				break;
			case "/edit":
				showEditForm(request, response);
				break;
			case "/update":
				updateUser(request, response);
				break;
			default:
				listUser(request, response);
				break;

			}
		}catch (Exception  ex) {
			throw new ServletException(ex);
		 }

	}

	//Création de la méthode ListUser

	private void listUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		List<User> listUser = userDao.selectAllUser();
		request.setAttribute("listUser", listUser);

		RequestDispatcher dispatcher = request.getRequestDispatcher("user-list.jsp");
		dispatcher.forward(request, response);

	}

	//Création de la méthode ShowNewForm
	private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		RequestDispatcher dispatcher = request.getRequestDispatcher("user-form.jsp");
		dispatcher.forward(request, response);

	}
	//Création de la méthode showEditForm
	private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException{

		int id = Integer.parseInt(request.getParameter("id"));
		User existingUser = userDao.selectUser(id);
		RequestDispatcher dispatcher = request.getRequestDispatcher("user-form.jsp");
		request.setAttribute("user", existingUser);
		dispatcher.forward(request, response);
	}

	//Cration de la méthode Insertion
	private void insertUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException{

		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String country = request.getParameter("country");

		User newUser = new User(name, email, country);
		userDao.insertUser(newUser);
		//Redirection
		response.sendRedirect("list");

	}

	private void updateUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ClassNotFoundException{

	int id = Integer.parseInt(request.getParameter("id"));

	String name = request.getParameter("name");
	String email = request.getParameter("email");
	String country = request.getParameter("country");

	User book = new User(id, name, email, country);
	userDao.updateUser(book);
	response.sendRedirect("list");

	}

	//Cration de la méthode delete
	private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException, ClassNotFoundException{

		int id = Integer.parseInt(request.getParameter("id"));
		userDao.deleteUser(id);
		response.sendRedirect("list");

	}
}
