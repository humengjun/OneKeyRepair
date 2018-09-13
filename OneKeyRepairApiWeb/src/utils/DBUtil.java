package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DBUtil {
	//"jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&characterEncoding=UTF-8","root","123456"
	private String url = "jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&characterEncoding=UTF-8";
	private String user = "root";
	private String pwd = "123456";
	
	private static DBUtil dbUtil = new DBUtil();
	
	Connection conn;
	PreparedStatement ps;
	ResultSet rs;
	
	private DBUtil(){
		
	}
	
	public static DBUtil getInstance(){
		return dbUtil;
	}
	private void conn() throws ClassNotFoundException, SQLException{
		Class.forName("com.mysql.jdbc.Driver");
		conn=DriverManager.getConnection(url,user,pwd);
	}
	public void close(){
		try {
			if(rs!=null){
				rs.close();
			}
			if(ps!=null){
				ps.close();
			}
			if(conn!=null){
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public int update(String sql,Object o[]) throws Exception{
		conn();
		int ii=0;
		try {
			ps=conn.prepareStatement(sql);
			for(int i=0;i<o.length;i++){
				ps.setObject(i+1, o[i]);
			}
			ii=ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		close();
		return ii;
	}
	public ResultSet query(String sql,Object o[]) throws Exception{
		conn();
		try {
			ps=conn.prepareStatement(sql);
			for(int i=0;i<o.length;i++){
				ps.setObject(i+1, o[i]);
			}
			rs=ps.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
}
