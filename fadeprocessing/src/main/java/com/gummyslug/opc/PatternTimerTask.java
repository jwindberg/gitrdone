package com.gummyslug.opc;

import java.util.Timer;
import java.util.TimerTask;

public class PatternTimerTask extends TimerTask {
	private Pattern pattern;
	private Timer timer = new Timer(true);

	public PatternTimerTask(Pattern pattern) {
		this.pattern = pattern;
	}

	public void start() {
		timer.schedule(this, 0, 1000 / 60);
	}

	public void stop() {
		timer.purge();
	}
	

	@Override
	public void run() {
		pattern.updatePattern();
		pattern.writePixels();
	}

	public void setPattern(Pattern pattern) {
		this.pattern = pattern;
		System.out.println(this.pattern.getClass().getSimpleName());
		this.pattern.init();
	}
}
