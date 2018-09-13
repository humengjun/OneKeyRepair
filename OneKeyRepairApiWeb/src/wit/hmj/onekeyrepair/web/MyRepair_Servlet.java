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

import model.GenerateJsonString;
import model.RepairJson;

import org.json.JSONException;

import utils.DBUtil;
import utils.DataBaseTable;

/**
 * Servlet implementation class Register_1_Servlet
 */
@WebServlet("/MyRepair_Servlet")
public class MyRepair_Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public MyRepair_Servlet() {
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
		String sql = "select * from " + DataBaseTable.Table_Repair
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
			List<RepairJson.DataBean> dataBeans = new ArrayList<RepairJson.DataBean>();
			while (resultSet.next()) {
				String time = resultSet.getString("time");
				String thing = resultSet.getString("thing");
				String city = resultSet.getString("city");
				String address = resultSet.getString("address");
				String phone = resultSet.getString("phone");
				int state = resultSet.getInt("state");
				String workNumber = resultSet.getString("worknumber");
				String workerName = resultSet.getString("workername");
				String workerPhone = resultSet.getString("workerphone");
				String imagelist = resultSet.getString("imagelist");
				String image[] = imagelist.split(",");
				List<String> imageList = new ArrayList<String>();
				for (int i = 0; i < image.length; i++) {
					imageList.add(image[i]);
				}
				// 得到该工单编号的评分和评价
				String sql_evaluation = "select * from "
						+ DataBaseTable.Table_Evaluation
						+ " where workNumber=?";
				Object o_evaluation[] = { workNumber };
				ResultSet resultSet2 = dbUtil.query(sql_evaluation,
						o_evaluation);
				String score = "";
				String evaluation = "";
				if (resultSet2.next()) {
					score = resultSet2.getString("score");
					evaluation = resultSet2.getString("evaluation");
				}
				dataBeans.add(new RepairJson.DataBean(address, workNumber,
						workerPhone, phone, city, "", time, state, workerName,
						thing, imageList, score, evaluation));
			}

			String jsonString = GenerateJsonString
					.getRepairJsonString(new RepairJson("success", 1, dataBeans));
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
