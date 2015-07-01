package com.gummyslug.processing.opc;

import processing.core.PApplet;
import processing.core.PConstants;

import com.gummyslug.processing.opc.OPC.Layout;

public abstract class AnimationApplet extends PApplet {

	protected OPC opc;

	private static final long serialVersionUID = 1L;

	static public final int BLOCK_SIZE = 256;

	 static public final String HOST_NAME = "192.168.254.6";
	static public final int HOST_PORT = 7890;
	static public final Layout LAYOUT = Layout.FOUR;

	@Override
	public void setup() {
		super.setup();
		opc = new OPC(this);
		setSize(getLedLayout());
		setupInternal();
	}

	public Layout getLedLayout() {
		return LAYOUT;
	}

	public int getBlockSize() {
		return BLOCK_SIZE;
	}

	public void setSize(OPC.Layout layout) {
		switch (layout) {
		case ONE:
			size(getBlockSize(), getBlockSize(), getRenderer());
			break;
		case TWO:
			size(getBlockSize() * 2, getBlockSize(), getRenderer());
			break;
		case FOUR:
			size(getBlockSize(), getBlockSize(), getRenderer());
			break;
		case TOWER:
			size(getBlockSize(), getBlockSize() * 2, getRenderer());
			break;
		case TWO_DIAMOND:
			size(getBlockSize() * 2, getBlockSize(), getRenderer());
			break;
		default:
			throw new RuntimeException("unknown layout");
		}
		opc.setLayout(layout);
	}

	protected String getRenderer() {
		return PConstants.JAVA2D;
	}

	protected void setupInternal() {
		// do what you will here.
	}

	@Override
	public void dispose() {
		super.dispose();
		opc.clear();
	}

}
