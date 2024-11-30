import java.io.*;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

	public class Login extends HttpServlet {
	    /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        // Get the username and password from the form
	        String username = request.getParameter("username");
	        String password = request.getParameter("password");

	        // Here we use a simple hardcoded check for demo purposes
	        // In a real application, you would check these values against a database
	        String correctUsername = "admin";
	        String correctPassword = "password123";

	        // Check if the credentials are correct
	        if (username.equals(correctUsername) && password.equals(correctPassword)) {
	            // If correct, redirect to the success page
	            response.sendRedirect("success.jsp");
	        } else {
	            // If incorrect, send an error message and forward to the login page
	            request.setAttribute("errorMessage", "Invalid username or password.");
	            RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
	            dispatcher.forward(request, response);
	        }
	    }
}
