package wit.hmj.onekeyrepair.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.GenerateJsonString;
import model.LoginJson;

import org.json.JSONException;

import utils.DBUtil;
import utils.DataBaseTable;

/**
 * Servlet implementation class Register_1_Servlet
 */
@WebServlet("/Login_Servlet")
public class Login_Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public Login_Servlet() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().println(
				"<html>" + "<head>" + "<meta charset=\"utf-8\">"
						+ "<title>菜鸟教程(runoob.com)</title>" + "</head>"
						+ "<body>");
		response.getWriter().println("<h1>hello world</h1>");
		response.getWriter().println("</body></html>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("content-type", "text/html;charset=UTF-8");
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");// 将解码方式设为来源网页的编码方式即
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		System.out.println("昵称：" + name);
		System.out.println("密码：" + password);
		// 根据昵称和密码检索数据库，返回useid
		String sql = "select * from " + DataBaseTable.Table_RegisterLogin
				+ " where name=? and password=?";
		Object o[] = { name, password };
		DBUtil dbUtil = DBUtil.getInstance();

		response.setCharacterEncoding("UTF-8");// 设置将字符以"UTF-8"编码输出到客户端浏览器
		/**
		 * PrintWriter out =
		 * response.getWriter();这句代码必须放在response.setCharacterEncoding
		 * ("UTF-8");之后
		 * 否则response.setCharacterEncoding("UTF-8")这行代码的设置将无效，浏览器显示的时候还是乱码
		 */
		PrintWriter out = response.getWriter();// 获取PrintWriter输出流
		try {
			ResultSet resultSet = dbUtil.query(sql, o);
			if (resultSet.next()) {
				String useid = resultSet.getString("useid");
				String jsonString = GenerateJsonString
						.getLoginJsonString(new LoginJson("success",
								new LoginJson.DataBean(useid), 1));
				System.out.println(jsonString);
				out.write(jsonString);
			} else {
				String jsonString = GenerateJsonString
						.getErrorJsonString("用户不存在或密码错误");
				out.write(jsonString);
			}
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			try {
				String jsonString = GenerateJsonString.getErrorJsonString(e
						.getMessage());
				System.out.println(jsonString);
				out.write(jsonString);
			} catch (JSONException e1) {
				// TODO 自动生成的 catch 块
				e1.printStackTrace();
			}
		} finally {
			dbUtil.close();
		}
	}

}
