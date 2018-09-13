package wit.hmj.onekeyrepair.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.GenerateJsonString;

import org.json.JSONException;

import utils.DBUtil;
import utils.DataBaseTable;

/**
 * Servlet implementation class Register_1_Servlet
 */
@WebServlet("/FeedBack_Servlet")
public class FeedBack_Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public FeedBack_Servlet() {
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
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");// 将解码方式设为来源网页的编码方式即

		String useid = request.getParameter("useid");
		String content = request.getParameter("content");

		System.out.println("useid：" + useid);
		System.out.println("反馈信息：" + content);
		// 根据useid和反馈信息添加数据库
		String sql = "insert " + DataBaseTable.Table_FeedBack
				+ " values(id,?,?,?)";
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Object[] o = { useid, format.format(new Date()), content };
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
			int i = dbUtil.update(sql, o);
			if (i == 1) {
				String jsonString = GenerateJsonString.getSuccessJsonString();
				System.out.println(jsonString);
				out.write(jsonString);
			} else {
				String jsonString = GenerateJsonString
						.getErrorJsonString("反馈失败");
				System.out.println(jsonString);
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
		}

	}

}
