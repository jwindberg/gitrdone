package javacv;

import java.awt.Color;
import java.lang.reflect.InvocationTargetException;

import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacv.OpenCVFrameGrabber;

public class CameraApplet extends javax.swing.JApplet {

	private static final long serialVersionUID = 1L;

	private IplImage frame;
	private OpenCVFrameGrabber grabber;

	public void start() {

		Runnable run = new Runnable() {

			public void run() {
				grabber = new OpenCVFrameGrabber(0);
				try {
					grabber.start();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// IplImage grabbedImage = null;
				try {
					frame = grabber.grab();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// CanvasFrame canvasFrame = new CanvasFrame("Cam");
				// canvasFrame.setCanvasSize(grabbedImage.width(),
				// grabbedImage.height());
				grabber.setFrameRate(10);
				System.out.println("fraamerate = " + grabber.getFrameRate());
//				try {
//					while (capturePnl.isVisible() && (frame = grabber.grab()) != null) {
//						capturePnl.getGraphics().drawImage(frame.getBufferedImage(), 0, 0, 320, 240, null);
//						// System.out.print("inside\n");
//					}
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}

				try {
					grabber.stop();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		};
		Thread capture = new Thread(run);
		capture.start();

	}

	@Override
	public void init() {
		/* Set the Nimbus look and feel */
		// <editor-fold defaultstate="collapsed"
		// desc=" Look and feel setting code (optional) ">
		/*
		 * If Nimbus (introduced in Java SE 6) is not available, stay with the
		 * default look and feel. For details see
		 * http://download.oracle.com/javase
		 * /tutorial/uiswing/lookandfeel/plaf.html
		 */

		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(CameraApplet.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(CameraApplet.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(CameraApplet.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(CameraApplet.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		}
		// </editor-fold>

		/* Create and display the applet */
		try {
			java.awt.EventQueue.invokeAndWait(new Runnable() {
				public void run() {
					initComponents();
				}
			});
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * This method is called from within the init() method to initialize the
	 * form. WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
	private void initComponents() {

		capturePnl = new javax.swing.JPanel();
		getContentPane().setBackground(Color.WHITE);
		setMaximumSize(new java.awt.Dimension(360, 560));
		setMinimumSize(new java.awt.Dimension(360, 560));
		setPreferredSize(new java.awt.Dimension(360, 560));

		capturePnl.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
		capturePnl.setMaximumSize(new java.awt.Dimension(320, 240));
		capturePnl.setMinimumSize(new java.awt.Dimension(320, 240));
		capturePnl.setPreferredSize(new java.awt.Dimension(320, 240));

		javax.swing.GroupLayout capturePnlLayout = new javax.swing.GroupLayout(capturePnl);
		capturePnl.setLayout(capturePnlLayout);
		capturePnlLayout.setHorizontalGroup(capturePnlLayout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 318, Short.MAX_VALUE));
		capturePnlLayout.setVerticalGroup(capturePnlLayout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 238, Short.MAX_VALUE));

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup()
						.addGap(20, 20, 20)
						.addGroup(
								layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
										layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
												.addComponent(capturePnl, javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE)))
						.addContainerGap(20, Short.MAX_VALUE)));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup()
						.addContainerGap()
						.addComponent(capturePnl, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addContainerGap()));
	}

	private javax.swing.JPanel capturePnl;

}