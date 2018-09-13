package wit.hmj.onekeyrepair.web;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.GenerateJsonString;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.JSONException;

import utils.DBUtil;
import utils.DataBaseTable;

/**
 * Servlet implementation class Register_1_Servlet
 */
@WebServlet("/Repair_Servlet")
public class Repair_Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public Repair_Servlet() {
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
		// 手机号码+内容+城市+详细地址+图片+（语音）括号可有可无
		uploadFile(request, response);
	}

	private void uploadFile(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// TODO 自动生成的方法存根
		Map<String, String> map = new HashMap<String, String>();
		List<String> imageList = new ArrayList<String>();
		String simpleFileName = "";
		// F:\tomcat\apache-tomcat-8.0.47\webapps\img
		String fileDir = "F:\\tomcat\\apache-tomcat-8.0.47\\webapps\\img";// 修改目录
		String message = "";
		if (ServletFileUpload.isMultipartContent(request)) {
			DiskFileItemFactory factory = new DiskFileItemFactory();
			factory.setSizeThreshold(20 * 1024); // 20KB
			factory.setRepository(factory.getRepository());
			ServletFileUpload upload = new ServletFileUpload(factory);
			int maxSize = 4 * 1024 * 1024; // 4MB
			List formlists = null;
			try {
				formlists = upload.parseRequest(request);
			} catch (FileUploadException e) {
				e.printStackTrace();
			}
			Iterator iterator = formlists.iterator();
			while (iterator.hasNext()) {
				FileItem formitem = (FileItem) iterator.next();
				if (formitem.isFormField()) {
					String fieldname = formitem.getFieldName();
					// 这里是非上传文件的表单域，可以通过formitem.getString("UTF-8")来获取相应表单字段的值
					String value = formitem.getString("UTF-8");
					map.put(fieldname, value);
					System.out.println(fieldname + ":" + value);
				} else {
					// 这里是上传文件的表单域
					String name = formitem.getName();
					if (formitem.getSize() > maxSize) {
						message = "您上传的文件太大，请重新选择不超过4M的文件";
						break;
					}
					String fileSize = new Long(formitem.getSize()).toString();
					if (name == null || "".equals(name) && "0".equals(fileSize))
						continue;
					int delimiter = name.lastIndexOf("\\");
					simpleFileName = delimiter == -1 ? name : name
							.substring(delimiter + 1);
					File saveFile = new File(fileDir, simpleFileName);
					try {
						formitem.write(saveFile);
						imageList.add(simpleFileName);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		// 根据map和imageList操作数据库
		String phone = map.get("phone");
		String thing = map.get("thing");
		String useid = map.get("useid");
		String address = map.get("address");
		String city = map.get("city");
		String imageListString = "";
		for (int i = 0; i < imageList.size(); i++) {
			if (i != imageList.size() - 1) {
				imageListString += imageList.get(i) + ",";
			} else {
				imageListString += imageList.get(i);
			}
		}

		String sql = "insert " + DataBaseTable.Table_Repair
				+ " values(id,?,?,?,?,?,?,?,?,?,?,?)";
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Object[] o = { useid, format.format(new Date()), thing, city, address,
				phone, imageListString, 1, "", "", "" };
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
			int i = dbUtil.update(sql, o);
			if (i == 1) {
				String jsonString = GenerateJsonString.getSuccessJsonString();
				System.out.println(jsonString);
				out.write(jsonString);
			} else {
				String jsonString = GenerateJsonString
						.getErrorJsonString(message);
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
		System.out.println(phone);
		System.out.println(thing);
		System.out.println(address);
		System.out.println(city);
		System.out.println(imageListString);
	}

}
