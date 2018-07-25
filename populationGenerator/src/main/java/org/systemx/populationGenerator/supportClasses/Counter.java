package org.systemx.populationGenerator.supportClasses;

import java.util.concurrent.atomic.AtomicLong;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public final class Counter {
	private final String prefix;
	private final String suffix;
	private final AtomicLong counter = new AtomicLong(0);
	private final AtomicLong nextCounter = new AtomicLong(1);
	
	private static JProgressBar jProgressBar;
	private static boolean pbSet = false;
	private static JLabel jLabel;
	private static boolean lblSet = false;
	private static JPanel jPanel;
	

	public Counter(final String prefix, int total) {
		this( prefix , "" , total);
	}


	public Counter(final String prefix, final String suffix, int total) {
		this.prefix = prefix;
		this.suffix = suffix;
		
		if(pbSet){
			jProgressBar.setMaximum(total);
			jProgressBar.setMinimum(0);
			jPanel.repaint();
		}
		if(lblSet){
			jLabel.setText(prefix);
			jPanel.repaint();
		}
	}

	public void incCounter() {
		long i = this.counter.incrementAndGet();
		long n = this.nextCounter.get();
		if (i >= n) {
			if (this.nextCounter.compareAndSet(n, n*2)) {
				System.out.println(this.prefix + n + this.suffix);
			}
		}
		
		printCounterByMinute();
		
		jProgressBar.setValue(Integer.parseInt(this.counter.toString()));
		jPanel.repaint();
	}
	
	
	//joseph
	long time1 = System.currentTimeMillis();
	long time2 = System.currentTimeMillis();
	long counterOld = 0;
	long counterMinutes = 0;
	//joseph
	public void printCounterByMinute() {
		time1 = System.currentTimeMillis();
		if((time1 - time2) >60000){
			counterMinutes++;
			System.out.println(this.prefix + this.counter.get() + this.suffix + " |processed:" + (getCounter()-counterOld) + "| |minute:" +counterMinutes+"|");
			time2 = System.currentTimeMillis();
			counterOld = getCounter();
		}
	}

	public void printCounter() {
		System.out.println(this.prefix + this.counter.get() + this.suffix);
	}
	public long getCounter() {
		return this.counter.get();
	}

	public void reset() {
		this.counter.set(0);
		this.nextCounter.set(1);
	}
	
	public static void setProgressBar(JProgressBar jProgressBar) {
		Counter.jProgressBar = jProgressBar;
		pbSet = true;
	}

	public static void setjLabel(JLabel jLabel) {
		Counter.jLabel = jLabel;
		lblSet = true;
	}

	public static void setjPanel(JPanel jPanel) {
		Counter.jPanel = jPanel;
	}
	
}

