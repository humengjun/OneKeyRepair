package wit.hmj.onekeyrepair.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.GenerateJsonString;

import org.json.JSONException;

import utils.DBUtil;
import utils.DataBaseTable;
import utils.UseidMap;

/**
 * Servlet implementation class Register_1_Servlet
 */
@WebServlet("/Register_3_Servlet")
public class Register_3_Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public Register_3_Servlet() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("content-type", "text/html;charset=UTF-8");
		request.setCharacterEncoding("utf-8");// 将解码方式设为来源网页的编码方式即
		// TODO Auto-generated method stub
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String useid = request.getParameter("useid");
		System.out.println("useid=" + useid);
		System.out.println("昵称=" + name);
		System.out.println("密码=" + password);

		String sql = "insert " + DataBaseTable.Table_RegisterLogin
				+ " values(id,?,?,?,?)";
		Object[] o = { useid, name, password, UseidMap.get(useid) };
		System.out.println(useid + UseidMap.get(useid) + name + password);
		DBUtil dbUtil = DBUtil.getInstance();

		response.setCharacterEncoding("UTF-8");// 设置将字符以"UTF-8"编码输出到客户端浏览器
		/**
		 * PrintWriter out =
		 * response.getWriter();这句代码必须放在response.setCharacterEncoding
		 * ("UTF-8");之后
		 * 否则response.setCharacterEncoding("UTF-8")这行代码的设置将无效，浏览器显示的时候还是乱码
		 */
		PrintWriter out = response.getWriter();// 获取PrintWriter输出流
		// 将用户信息保存到数据库

		try {
			if (dbUtil.update(sql, o) == 1) {
				try {
					String jsonString = GenerateJsonString
							.getSuccessJsonString();
					System.out.println(jsonString);
					out.write(jsonString);
				} catch (JSONException e1) {
					// TODO 自动生成的 catch 块
					e1.printStackTrace();
				}
			} else {
				try {
					String jsonString = GenerateJsonString
							.getErrorJsonString("注册失败");
					System.out.println(jsonString);
					out.write(jsonString);
				} catch (JSONException e1) {
					// TODO 自动生成的 catch 块
					e1.printStackTrace();
				}
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
		}

	}

}
