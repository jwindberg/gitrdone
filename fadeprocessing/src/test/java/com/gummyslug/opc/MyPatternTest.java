package com.gummyslug.opc;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.junit.Test;

public class MyPatternTest {

	public static final Random RANDOM = new Random();

	PatternTimerTask patternTimerTask;

	@Test
	public void testUpdatePattern() {

		OpenPixelControl opc = new OpenPixelControl("192.168.1.35", 7890, 32, 16);

		List<Pattern> patterns = new LinkedList<Pattern>();
		patterns.add(new ConwayLife(opc));
		patterns.add(new SlidePattern(opc));
		patterns.add(new SineWaveChase(opc));
		patterns.add(new Rainbow(opc));
		patterns.add(new WavingFlag(opc));

		patternTimerTask = new PatternTimerTask(patterns.get(0));

		patternTimerTask.start();

		int index = 0;

		try {
			while (true) {
				Thread.sleep(1000 * (10 + RANDOM.nextInt(20)));
				index++;
				if (index >= patterns.size()) {
					index = 0;
				}
				patternTimerTask.setPattern(patterns.get(index));

			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	protected void finalize() throws Throwable {
		System.out.println("finalizing");
		if (patternTimerTask != null)
			System.out.println("canceling");
			patternTimerTask.cancel();
	}

}
