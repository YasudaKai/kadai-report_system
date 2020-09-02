package Follow;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.Follow;
import utils.DBUtil;

/**
 * Servlet implementation class EmployeeFollowServlet
 */
//ログインフィルターにかかっていた。。
@WebServlet("/follow")
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
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

            //データベースへの接続の準備
            EntityManager em = DBUtil.createEntityManager();

            //インスタンスを作成し、フォロー型の変数ｆに代入
            Follow f   = new Follow();


            Employee followed = em.find(Employee.class, Integer.parseInt(request.getParameter("report_employee_id")));

            //インスタンスfollowedをそのまま、Followプロパティのfollowed_idにセットする。
            //今、followed_idプロパティの中身はレコード情報を持ったインスタンスが入っている。
            f.setFollowed(followed);



            //セッションスコープにある、ログインした状態のインスタンス情報を取得。変数followに代入。
            //今、Followクラスのプロパティfollowed_idの中身はログインしている人のインスタンスが入っている。
            Employee login_employee = (Employee)request.getSession().getAttribute("login_employee");
            f.setFollow(login_employee);


              long follow_count = (long)em.createNamedQuery("checkFollow", Long.class)
                      .setParameter("follow", login_employee)
                      .setParameter("followed", followed)
                      .getSingleResult();

              if(follow_count > 0){

              request.setAttribute("follow_count", follow_count);

              }


            em.getTransaction().begin();
            em.persist(f);
            em.getTransaction().commit();
            em.close();

            //topPageIndexServletにリダイレクト
            response.sendRedirect(request.getContextPath() + "/");





        }
    }


