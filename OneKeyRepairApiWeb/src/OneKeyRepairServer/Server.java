package OneKeyRepairServer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import utils.DBUtil;
import utils.DataBaseTable;

public class Server extends JFrame {

	private List<String> UseidAndTimeList = new ArrayList<String>();
	private List<String> imageListList = new ArrayList<String>();
	private Vector<String> vector_list = new Vector<String>();

	public Server() {
		this.setTitle("服务器");
		this.setAlwaysOnTop(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		this.setBounds(d.width / 2 - 250, d.height / 2 - 250, 500, 430);
		this.setVisible(true);

		// 添加网格布局容器存放顶部控件
		JPanel jp = new JPanel(new GridLayout());
		this.add(jp, BorderLayout.NORTH);
		// 添加（报修状态）标签
		JLabel jl_state = new JLabel("报修状态：");
		jp.add(jl_state);
		// 添加报修状态的下拉菜单
		Vector<String> vector_state = new Vector<String>();
		vector_state.add("待审核");
		vector_state.add("待维修");
		vector_state.add("已完成");
		final JComboBox<String> jc_state = new JComboBox<String>(vector_state);
		jp.add(jc_state);
		// 添加显示信息JList
		// addData();
		JPanel panel = new JPanel();
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setPreferredSize(new Dimension(480, 250));
		final JList<String> jList = new JList<String>(vector_list);
		scrollPane.setViewportView(jList);
		panel.add(scrollPane);
		this.add(panel);
		jList.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO 自动生成的方法存根

			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO 自动生成的方法存根

			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO 自动生成的方法存根

			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO 自动生成的方法存根

			}

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO 自动生成的方法存根
				if (arg0.getClickCount() == 2) {
					if (jList.getSelectedIndex() != -1) {
						new ImagePath(imageListList.get(jList
								.getSelectedIndex())).setVisible(true);
					}
				}
			}
		});
		jList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!jList.getValueIsAdjusting()) { // 设置只有释放鼠标时才触发
				}
			}
		});
		// 添加确定按钮
		JButton btn_true = new JButton("确定");
		btn_true.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				String stateString = (String) jc_state.getSelectedItem();
				int state = 0;
				if (stateString.equals("待审核")) {
					state = 1;
				} else if (stateString.equals("待维修")) {
					state = 2;
				} else if (stateString.equals("已完成")) {
					state = 3;
				}
				// 清空当前useid与时间集合
				UseidAndTimeList.clear();
				// 清空当前imageList集合
				imageListList.clear();
				// 清空所有条目
				vector_list.clear();
				// 检索数据库，添加（useid,time）到UseidAndTimeList，添加imageList到imageListList
				String sql = "select * from " + DataBaseTable.Table_Repair
						+ " where state=?";
				Object o[] = { state };
				DBUtil dbUtil = DBUtil.getInstance();
				try {
					ResultSet resultSet = dbUtil.query(sql, o);
					while (resultSet.next()) {
						String time = resultSet.getString("time");
						String thing = resultSet.getString("thing");
						String useid = resultSet.getString("useid");
						String city = resultSet.getString("city");
						String address = resultSet.getString("address");
						String phone = resultSet.getString("phone");
						String workNumber = resultSet.getString("worknumber");
						String workerName = resultSet.getString("workername");
						String workerPhone = resultSet.getString("workerphone");
						String imagelist = resultSet.getString("imagelist");
						imageListList.add(imagelist);
						UseidAndTimeList.add(useid + "," + time);
						if (state == 1) {
							vector_list.add("useid：" + useid + "，时间：" + time
									+ "，城市：" + city + "，地址：" + address + "，电话："
									+ phone + "，事项：" + thing);
						} else {
							vector_list.add("useid：" + useid + "，时间：" + time
									+ "，城市：" + city + "，地址：" + address + "，电话："
									+ phone + "，事项：" + thing + "，维修人："
									+ workerName + "，维修人电话：" + workerPhone
									+ "，维修单号：" + workNumber);
						}
					}
				} catch (Exception e1) {
					// TODO 自动生成的 catch 块
					e1.printStackTrace();
				} finally {
					dbUtil.close();
					jList.setListData(vector_list);
				}
			}
		});
		jp.add(btn_true);
		BorderLayout borderLayout = new BorderLayout();
		JPanel jp_btn = new JPanel(borderLayout);
		this.add(jp_btn, BorderLayout.SOUTH);
		// 删除按钮
		JButton btn_delete = new JButton("审核不通过，删除条目");
		jp_btn.add(btn_delete, BorderLayout.NORTH);
		btn_delete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO 自动生成的方法存根
				int index = jList.getSelectedIndex();
				String useidAndTime = UseidAndTimeList.get(index);
				String array[] = useidAndTime.split(",");
				String useid = array[0];
				String time = array[1];
				// 根据useid+时间操作数据库
				String sql = "delete from " + DataBaseTable.Table_Repair
						+ " where useid=? and time=?";
				Object o[] = { useid, time };
				DBUtil dbUtil = DBUtil.getInstance();
				try {
					dbUtil.update(sql, o);
				} catch (Exception e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}

				System.out.println(index);
				System.out.println(useid);
				System.out.println(time);
			}
		});
		// 修改按钮
		JButton btn_update = new JButton("审核通过，修改为待维修状态");
		jp_btn.add(btn_update);
		btn_update.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO 自动生成的方法存根
				int index = jList.getSelectedIndex();
				String useidAndTime = UseidAndTimeList.get(index);
				String array[] = useidAndTime.split(",");
				String useid = array[0];
				String time = array[1];
				DBUtil dbUtil = DBUtil.getInstance();
				int id = new Random().nextInt(5) + 1;
				System.out.println(id);
				String sql_worker = "select * from worker where id = ?";
				Object[] worker_o = { id };
				try {
					ResultSet resultSet = dbUtil.query(sql_worker, worker_o);
					String workername = "";
					String workerphone = "";
					String worknumber = "";
					if (resultSet.next()) {
						workername = resultSet.getString("workername");
						workerphone = resultSet.getString("workerphone");
						DateFormat format = new SimpleDateFormat(
								"yyyyMMddHHmmss");
						String chars = "abcdefghijklmnopqrstuvwxyz";
						worknumber = chars.charAt((int) (Math.random() * 26))
								+ format.format(new Date());
					}
					System.out.println(workername);
					System.out.println(workerphone);
					// 根据useid+时间操作数据库
					String sql = "update " + DataBaseTable.Table_Repair
							+ " set state=2 where useid=? and time=?";
					Object o1[] = { useid, time };
					dbUtil.update(sql, o1);
					sql = "update " + DataBaseTable.Table_Repair
							+ " set worknumber = ? where useid=? and time=?";
					Object o2[] = { worknumber, useid, time };
					dbUtil.update(sql, o2);
					sql = "update " + DataBaseTable.Table_Repair
							+ " set workername = ? where useid=? and time=?";
					Object o3[] = { workername, useid, time };
					dbUtil.update(sql, o3);
					sql = "update " + DataBaseTable.Table_Repair
							+ " set workerphone = ? where useid=? and time=?";
					Object o4[] = { workerphone, useid, time };
					dbUtil.update(sql, o4);

					System.out.println(index);
					System.out.println(useid);
					System.out.println(time);
				} catch (Exception e1) {
					// TODO 自动生成的 catch 块
					e1.printStackTrace();
				} finally {
					dbUtil.close();
				}
			}

		});
		// 完成按钮
		JButton btn_finish = new JButton("维修完成，修改为已完成状态");
		jp_btn.add(btn_finish, BorderLayout.SOUTH);
		btn_finish.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO 自动生成的方法存根
				int index = jList.getSelectedIndex();
				String useidAndTime = UseidAndTimeList.get(index);
				String array[] = useidAndTime.split(",");
				String useid = array[0];
				String time = array[1];
				// 根据useid+时间操作数据库
				String sql = "update " + DataBaseTable.Table_Repair
						+ " set state=3 where useid=? and time=?";
				Object o[] = { useid, time };
				DBUtil dbUtil = DBUtil.getInstance();
				try {
					dbUtil.update(sql, o);
				} catch (Exception e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}

				System.out.println(index);
				System.out.println(useid);
				System.out.println(time);
			}
		});

	}

	private void addData() {
		// TODO 自动生成的方法存根
		vector_list
				.add("item1item1item1item1item1item1item1item1item1item1item1item1item1item1item1item1item1item1"
						+ "item1item1item1item1item1");
		vector_list.add("item2");
		vector_list.add("item3");
		vector_list.add("item4");
		vector_list.add("item5");
		vector_list.add("item6");
		vector_list.add("item7");
		vector_list.add("item8");
		vector_list.add("item9");
		vector_list.add("item10");
		UseidAndTimeList.add("useid1,time1");
		UseidAndTimeList.add("useid2,time2");
		UseidAndTimeList.add("useid3,time3");
		UseidAndTimeList.add("useid4,time4");
		UseidAndTimeList.add("useid5,time5");
		UseidAndTimeList.add("useid6,time6");
		UseidAndTimeList.add("useid7,time7");
		UseidAndTimeList.add("useid8,time8");
		UseidAndTimeList.add("useid9,time9");
		UseidAndTimeList.add("useid10,time10");
		imageListList.add("img1,img2,img3,img4,img5");
		imageListList.add("img1,img2,img3,img4,img5");
		imageListList.add("img1,img2,img3,img4,img5");
		imageListList.add("img1,img2,img3,img4,img5");
		imageListList.add("img1,img2,img3,img4,img5");
		imageListList.add("img1,img2,img3,img4,img5");
		imageListList.add("img1,img2,img3,img4,img5");
		imageListList.add("img1,img2,img3,img4,img5");
		imageListList.add("img1,img2,img3,img4,img5");
		imageListList.add("img1,img2,img3,img4,img5");
	}
}
