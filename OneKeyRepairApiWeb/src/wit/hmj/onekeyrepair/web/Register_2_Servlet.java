package wit.hmj.onekeyrepair.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.GenerateJsonString;
import model.LoginJson;

import org.json.JSONException;

import utils.PhoneMap;
import utils.UseidMap;

/**
 * Servlet implementation class Register_1_Servlet
 */
@WebServlet("/Register_2_Servlet")
public class Register_2_Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public Register_2_Servlet() {
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
		// TODO Auto-generated method stub
		response.setHeader("content-type", "text/html;charset=UTF-8");
		request.setCharacterEncoding("utf-8");// 将解码方式设为来源网页的编码方式即
		String phone = request.getParameter("phone");
		String receive = request.getParameter("receive");
		System.out.println("手机号码：" + phone);
		System.out.println("验证码：" + receive);
		response.setCharacterEncoding("UTF-8");// 设置将字符以"UTF-8"编码输出到客户端浏览器
		/**
		 * PrintWriter out =
		 * response.getWriter();这句代码必须放在response.setCharacterEncoding
		 * ("UTF-8");之后
		 * 否则response.setCharacterEncoding("UTF-8")这行代码的设置将无效，浏览器显示的时候还是乱码
		 */
		PrintWriter out = response.getWriter();// 获取PrintWriter输出流

		// 根据手机号和验证码注册生成一个随机的useid返回客户端
		if (PhoneMap.get(phone).equals(receive)) {
			String useid = UUID.randomUUID().toString().substring(0, 15);
			UseidMap.put(useid, phone);
			try {
				String jsonString = GenerateJsonString
						.getLoginJsonString(new LoginJson("success",
								new LoginJson.DataBean(useid), 1));
				System.out.println(jsonString);
				out.write(jsonString);
			} catch (JSONException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}

		} else {
			try {
				String jsonString = GenerateJsonString
						.getErrorJsonString("验证码错误");
				System.out.println(jsonString);
				out.write(jsonString);
			} catch (JSONException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}

	}

}
