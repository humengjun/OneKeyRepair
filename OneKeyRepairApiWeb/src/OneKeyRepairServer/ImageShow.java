package OneKeyRepairServer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ImageShow extends JFrame {
	private JLabel jLabel;
	private String path = "F:/tomcat/apache-tomcat-8.0.47/webapps/img/";

	public void setImage(String image) throws IOException {
		File file = new File(path + image);
		if (!file.exists()) {
			return;
		}
		BufferedImage imageBuffer = ImageIO.read(file);

		int width = 300;
		int height = 300;

		double scaleWidth = (double) width / (double) imageBuffer.getWidth();
		double scaleHeight = (double) height / (double) imageBuffer.getHeight();

		BufferedImage dstImage = new BufferedImage(width, height,
				imageBuffer.getType());

		AffineTransform affineTransform = new AffineTransform();
		affineTransform.scale(scaleWidth, scaleHeight);
		AffineTransformOp affineTransformOp = new AffineTransformOp(
				affineTransform, 2);//
		affineTransformOp.filter(imageBuffer, dstImage);

		jLabel.setIcon(new ImageIcon(dstImage));
	}

	public ImageShow() {
		this.setTitle("图片展示");
		this.setAlwaysOnTop(true);
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		this.setBounds(d.width / 2 - 250, d.height / 2 - 250, 400, 400);

		JPanel jPanel = new JPanel();
		jLabel = new JLabel();
		// 加载图片文件目录
		this.add(jPanel, BorderLayout.CENTER);
		jPanel.add(jLabel);
	}
}
