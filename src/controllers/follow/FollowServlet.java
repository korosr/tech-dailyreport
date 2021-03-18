package controllers.follow;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.Relationship;
import util.DBUtil;

/**
 * Servlet implementation class FollowServlet
 */
@WebServlet(name = "employees/follow", urlPatterns = { "/employees/follow" })
public class FollowServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public FollowServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		EntityManager em = DBUtil.createEntityManager();

		//フォローorフォロー解除かのパラメータ取得
		String whichFollow = request.getParameter("follow");
		System.out.println("フォローorフォロー解除: " + whichFollow);

		//フォローしたい人のID
        int followedId = Integer.parseInt(request.getParameter("empId"));
        System.out.println("フォローしたい人のID: " + followedId);

		Employee e = (Employee) request.getSession().getAttribute("login_employee");
		//ログインユーザのID
		int loginUserId = e.getId();
		System.out.println("フォローする人のID: " + loginUserId);

		//フォローチェック
		long existRelation = (long)em.createNamedQuery("followCheck", Long.class)
				.setParameter("follower_id", loginUserId)
				.setParameter("followed_id", followedId)
                .getSingleResult();
		System.out.println("フォローチェック: " + existRelation);

		//相手の名前
		String name = em.find(Employee.class, followedId).getName();

		if("following".equals(whichFollow)) {
			if(existRelation != 0) {
				request.getSession().setAttribute("flush", name + "さんは既にフォロー済みです。");
			}else {
				System.out.println("フォローします！");
		        Relationship r = new Relationship();
		        r.setFollowed_id(followedId);
		        r.setFollower_id(loginUserId);

		        em.getTransaction().begin();
		        em.persist(r);
		        em.getTransaction().commit();
		        e = em.find(Employee.class, followedId);
		        System.out.println("フォローした人の名前:" + e.getName());
		        request.getSession().setAttribute("flush", e.getName() + "さんをフォローしました。");
			}
		} else {
			if(existRelation == 0) {
				request.getSession().setAttribute("flush", name + "さんをフォローしていません。");
			}else {
				em.getTransaction().begin();
				em.createNamedQuery("deleteRelationship")
				.setParameter("followed_id", followedId)
				.setParameter("follower_id", loginUserId)
				.executeUpdate();
				em.getTransaction().commit();
				request.getSession().setAttribute("flush", name + "さんのフォローを解除しました。");
			}
		}

        em.close();
        response.sendRedirect(request.getContextPath() + "/employees/index");
	}
}