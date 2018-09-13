package wit.hmj.onekeyrepair.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;

import model.GenerateJsonString;
import model.RegisterJson;
import utils.DBUtil;
import utils.DataBaseTable;
import utils.PhoneMap;

/**
 * Servlet implementation class Register_1_Servlet
 */
@WebServlet("/Register_1_Servlet")
public class Register_1_Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public Register_1_Servlet() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String receive = "" + ((int) ((Math.random() * 9 + 1) * 100000));
		// 返回验证码
		response.setHeader("content-type", "text/html;charset=UTF-8");
		request.setCharacterEncoding("utf-8");// 将解码方式设为来源网页的编码方式即
		response.setCharacterEncoding("UTF-8");// 设置将字符以"UTF-8"编码输出到客户端浏览器
		/**
		 * PrintWriter out =
		 * response.getWriter();这句代码必须放在response.setCharacterEncoding
		 * ("UTF-8");之后
		 * 否则response.setCharacterEncoding("UTF-8")这行代码的设置将无效，浏览器显示的时候还是乱码
		 */
		PrintWriter out = response.getWriter();// 获取PrintWriter输出流
		String jsonString = "{\"status\":0,\"msg\":\"验证码错误\",\"data\":null}";
		System.out.println(jsonString);
		out.write(jsonString);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setHeader("content-type", "text/html;charset=UTF-8");
		request.setCharacterEncoding("utf-8");// 将解码方式设为来源网页的编码方式即
		String phone = request.getParameter("phone");
		System.out.println("手机号码：" + phone);
		// 判断手机号是否被注册，再进行下一步操作
		String sql = "select * from " + DataBaseTable.Table_RegisterLogin
				+ " where phone=?";
		Object[] o = { phone };
		DBUtil dbUtil = DBUtil.getInstance();
		ResultSet resultSet = null;

		response.setCharacterEncoding("UTF-8");// 设置将字符以"UTF-8"编码输出到客户端浏览器
		/**
		 * PrintWriter out =
		 * response.getWriter();这句代码必须放在response.setCharacterEncoding
		 * ("UTF-8");之后
		 * 否则response.setCharacterEncoding("UTF-8")这行代码的设置将无效，浏览器显示的时候还是乱码
		 */
		PrintWriter out = response.getWriter();// 获取PrintWriter输出流
		try {
			resultSet = dbUtil.query(sql, o);
			if (!resultSet.next()) {
				// 随机生成验证码
				String receive = ""
						+ ((int) ((Math.random() * 9 + 1) * 100000));
				// 保存当前手机号验证码
				PhoneMap.put(phone, receive);
				// 返回验证码
				RegisterJson register1Json = new RegisterJson("success", 1, new RegisterJson.DataBean(receive));
				String jsonString = GenerateJsonString.getRegisterJsonString(register1Json);
				System.out.println(jsonString);
				out.write(jsonString);
			} else {
				String jsonString = GenerateJsonString.getErrorJsonString("手机号已被注册");
				System.out.println(jsonString);
				out.write(jsonString);
			}

		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			try {
				String jsonString = GenerateJsonString.getErrorJsonString(e.getMessage());
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
