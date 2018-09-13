package OneKeyRepairServer;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Login extends JFrame{
	JLabel jl_bt;
	JLabel jl_count;
	JLabel jl_pwd;
	JTextField jf;
	JPasswordField jp;
	//初始化窗口
	public Login(){
		this.setTitle("登录界面");
		this.setSize(300,320);
		this.setLocation(200,200);
		this.setLayout(null);
		
		//设置标签
		jl_bt=new JLabel("用户登录");
		jl_bt.setFont(new Font("黑体", Font.BOLD, 20));
		jl_bt.setSize(100,30);
		jl_bt.setLocation(100,30);
		this.add(jl_bt);
		jl_count=new JLabel("账号：");
		jl_count.setSize(50,30);
		jl_count.setLocation(50,80);
		this.add(jl_count);
		jl_pwd=new JLabel("密码：");
		jl_pwd.setSize(50,30);
		jl_pwd.setLocation(50,130);
		this.add(jl_pwd);
		
		//设置文本框
		jf=new JTextField("manager");
		jf.setSize(150,30);
		jf.setLocation(100, 80);
		this.add(jf);
		
		//设置密码框
		jp=new JPasswordField("123456");
		jp.setSize(150,30);
		jp.setLocation(100, 130);
		this.add(jp);
		//按钮---添加事件
		JButton jbt_zc=new JButton("注册");
		jbt_zc.setSize(80, 30);
		jbt_zc.setLocation(50, 180);
//		this.add(jbt_zc);
		JButton jbt_dl=new JButton("登录");
		jbt_dl.setSize(200, 30);
		jbt_dl.setLocation(50, 180);
		this.add(jbt_dl);
		jbt_dl.addActionListener(new LoginAction());
	}
	public class LoginAction implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			String name = jf.getText();
			String password = jp.getText();
			if(name.equals("manager")&&password.equals("123456")){
				new Server();
				Login.this.setVisible(false);
			}
		}
		
	}
	public static void main(String[] args) {
		new Login().setVisible(true);
	}
}
