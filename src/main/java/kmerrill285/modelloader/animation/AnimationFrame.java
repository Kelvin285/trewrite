package kmerrill285.modelloader.animation;

import java.util.HashMap;

public class AnimationFrame {
	public HashMap<String, AnimationFrameData> frameData;
	public int time;
	public AnimationFrame(int time) {
		this.time = time;
		this.frameData = new HashMap<String, AnimationFrameData>();
	}
}
