package OneKeyRepairServer;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;

public class ImagePath extends JFrame {

	ImageShow imageShow;

	public ImagePath(String imageList) {
		this.setTitle("图片目录");
		this.setAlwaysOnTop(true);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		this.setBounds(d.width / 2 - 250, d.height / 2 - 250, 200, 300);
		this.setVisible(true);
		this.setLayout(null);
		imageShow = new ImageShow();

		String array[] = imageList.split(",");
		for (int i = 0; i < array.length; i++) {
			final String image = array[i];
			JButton jButton = new JButton("第" + (i + 1) + "图片");
			jButton.setBounds(0, 20 + 50 * i, 200, 30);
			this.add(jButton);
			jButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO 自动生成的方法存根
					try {
						imageShow.setImage(image);
						imageShow.setVisible(true);
					} catch (IOException e1) {
						// TODO 自动生成的 catch 块
						e1.printStackTrace();
					}

				}
			});
		}

	}
}
