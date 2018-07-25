package org.systemx.populationGenerator.supportClasses;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class ProgressBar {
	private int oldDoneTotal;
	private int doneTotal;
	private int total;
	private boolean inverse = true;
	
	private static JProgressBar jProgressBar;
	private static boolean pbSet = false;
	private static JLabel jLabel;
	private static boolean lblSet = false;
	private static JPanel jPanel;

	public ProgressBar(int total, String name) {
		System.out.print("Progress: [");
		this.total = total;
		if(pbSet){
			jProgressBar.setMaximum(total);
			jProgressBar.setMinimum(0);
			jPanel.repaint();
		}
		if(lblSet){
			jLabel.setText(name);
			jPanel.repaint();
		}
	}

	public void update(int done) {
		
		if(inverse){
			doneTotal = 50 * (done-total) / total;
		}else{
			doneTotal = 50 * done / total;
		}
		
		if (doneTotal != oldDoneTotal) {
			oldDoneTotal = doneTotal;
			if(done==total || oldDoneTotal >=49){
				System.out.print("=]");
			}else{
				System.out.print("=");
			}
		}
		if(inverse){
			jProgressBar.setValue(total - done);
		}else{
			jProgressBar.setValue(done);
		}

		jPanel.repaint();
	}

	public boolean isInverse() {
		return inverse;
	}

	public void setInverse(boolean inverse) {
		this.inverse = inverse;
	}

	public static void setProgressBar(JProgressBar jProgressBar) {
		ProgressBar.jProgressBar = jProgressBar;
		pbSet = true;
	}

	public static void setjLabel(JLabel jLabel) {
		ProgressBar.jLabel = jLabel;
		lblSet = true;
	}

	public static void setjPanel(JPanel jPanel) {
		ProgressBar.jPanel = jPanel;
	}
	
	
}
