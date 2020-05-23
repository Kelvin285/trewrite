package kmerrill285.modelloader.animation;

import java.util.HashMap;

public class AnimationController {
	public HashMap<String, Animation> animations;
	public Animation nextAnimation = null;
	public Animation currentAnimation = null;
	public float nextSpeed = 0.0f;
	public float time = 1.0f;
	public AnimationController() {
		animations = new HashMap<String, Animation>();
	}
	
	public void update(float animationSpeed) {
		nextAnimation = currentAnimation;
		if (time < 1.0) {
			time += nextSpeed;
		} else {
			currentAnimation = nextAnimation;
		}
		if (currentAnimation != null) {
			currentAnimation.update(animationSpeed);
		}
		if (nextAnimation != null) {
			nextAnimation.update(animationSpeed);
		}
	}
	
	public void setNextAnimation(Animation animation, float nextSpeed) {
		this.time = 0;
		this.nextAnimation = animation;
		this.nextSpeed = nextSpeed;
	}
	
	public float getAnimationLerp() {
		return time / 1.0f;
	}
}
