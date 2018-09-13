package wit.hmj.onekeyrepair.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.FeedBackJson;
import model.GenerateJsonString;

import org.json.JSONException;

import utils.DBUtil;
import utils.DataBaseTable;

/**
 * Servlet implementation class Register_1_Servlet
 */
@WebServlet("/MyFeedBack_Servlet")
public class MyFeedBack_Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public MyFeedBack_Servlet() {
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
		System.out.println("useid：" + useid);
		// 根据useid检索数据库
		String sql = "select * from " + DataBaseTable.Table_FeedBack
				+ " where useid=?";
		Object o[] = { useid };
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
			List<FeedBackJson.DataBean> dataBeans = new ArrayList<FeedBackJson.DataBean>();
			while (resultSet.next()) {
				String time = resultSet.getString("time");
				String content = resultSet.getString("content");
				dataBeans.add(new FeedBackJson.DataBean("", time, content));
			}
			String jsonString = GenerateJsonString
					.getFeedBackJsonString(new FeedBackJson("success", 1,
							dataBeans));
			out.write(jsonString);
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
