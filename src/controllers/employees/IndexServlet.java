package controllers.employees;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import util.DBUtil;

/**
 * Servlet implementation class IndexServlet
 */
@WebServlet(name = "employees/index", urlPatterns = { "/employees/index" })
public class IndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public IndexServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(Ht
	 * tpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		EntityManager em = DBUtil.createEntityManager();
		Employee login_employee = (Employee)request.getSession().getAttribute("login_employee");

        int page = 1;
        try{
            page = Integer.parseInt(request.getParameter("page"));
        } catch(NumberFormatException e) { }
        List<Employee> employees = em.createNamedQuery("getAllEmployees", Employee.class)
                                     .setFirstResult(15 * (page - 1))
                                     .setMaxResults(15)
                                     .getResultList();

        long employees_count = (long)em.createNamedQuery("getEmployeesCount", Long.class)
                                       .getSingleResult();

        for(Employee e : employees) {
	        //フォローしているかどうか
	        long followed_count = (long)em.createNamedQuery("followCheck")
	        		.setParameter("followed_id", e.getId())
	        		.setParameter("follower_id", login_employee.getId())
	        		.getSingleResult();

	        System.out.println("followed_count " + followed_count);
	        if(followed_count == 0) {
	        	request.setAttribute("relationships" + e.getId(), "following");
	        }else {
	        	request.setAttribute("relationships" + e.getId(), "deleteFollowing");
	        }
        }

//        List<Relationship> relationships = em.createNamedQuery("followCheck", Relationship.class)
//        		.setParameter("follower_id", login_employee.getId())
//        		.getResultList();
//        for(Relationship i : relationships) {
//        	System.out.println("フォローされてる人のID: " + i.getFollowed_id());
//        	System.out.println("フォローしてる人のID: " + i.getFollower_id());
//        }


        em.close();

       // request.setAttribute("relationships", relationships);
        request.setAttribute("employees", employees);
        request.setAttribute("employees_count", employees_count);
        request.setAttribute("page", page);
        if(request.getSession().getAttribute("flush") != null) {
            request.setAttribute("flush", request.getSession().getAttribute("flush"));
            request.getSession().removeAttribute("flush");
        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/employees/index.jsp");
        rd.forward(request, response);
    }
}
