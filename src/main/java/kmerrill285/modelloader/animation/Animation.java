package kmerrill285.modelloader.animation;

import java.util.HashMap;

public class Animation {
	public HashMap<Integer, AnimationFrame> frames;
	public float duration;
	public String title;
	public float currentFrame;
	public Animation(String title, float duration) {
		this.title = title;
		this.duration = duration;
		this.frames = new HashMap<Integer, AnimationFrame>();
		this.currentFrame = 0;
		
	}
	
	public void update(float speed) {
		currentFrame += speed;
		if (currentFrame > duration) currentFrame -= duration;
		if (currentFrame < 0) currentFrame += duration;
	}
	
	public void setFrame(int frame) {
		this.currentFrame = frame;
	}
	
	public float getFrameLerp(float timeBetweenFrames, float timeUntilNextFrame) {
		return 1.0f - (timeUntilNextFrame / timeBetweenFrames);
	}
	
	public AnimationFrame getFrameFor(float time) {
		for (int i = (int)time; i >= 0; i--) {
			if (frames.get(i) != null) {
				return frames.get(i);
			}
		}
		return frames.get(0);
	}
	
	public AnimationFrame getNextFrameAfter(float time) {
		for (int i = (int)time + 1; i < time + duration; i++) {
			if (time >= duration) time = 0;
			if (frames.get(i) != null) {
				return frames.get(i);
			}
		}
		return getFrameFor(time);
	}

	public AnimationFrame getCurrentFrame() {
		return getFrameFor(this.currentFrame);
	}
	
	public AnimationFrame getNextFrame() {
		return getNextFrameAfter(this.currentFrame);
	}
}
