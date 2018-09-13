package OneKeyRepairServer;

import utils.DBUtil;

public class PutWorker {
	public static void main(String[] args) {
		DBUtil dbUtil = DBUtil.getInstance();
		try {
			String sql = "insert worker values(id,?,?)";
			Object o1[] = { "张三", "13567899876" };
			dbUtil.update(sql, o1);
			Object o2[] = { "李四", "13566667777" };
			dbUtil.update(sql, o2);
			Object o3[] = { "王五", "15085855858" };
			dbUtil.update(sql, o3);
			Object o4[] = { "赵六", "18080800808" };
			dbUtil.update(sql, o4);
			Object o5[] = { "田七", "13317143496" };
			dbUtil.update(sql, o5);

		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
}
