import org.newdawn.slick.Input;

/**
 * Represents an abstract enemy.
 */
public abstract class Enemy extends Sprite {
	public static final float POWERUP_PROBABILITY = 0.05f;

	private int t = 0;
	private int delay;

	public int getDelay() { return delay; }
	public int getTime() { return t; }

	/**
	 * Construct the enemy object.
	 * @param src	the image file to use for the sprite
	 * @param x		the x position to create the enemy at
	 * @param y		the y position to create the enemy at
	 * @param delay	the time to wait before entering the screen
	 */
	public Enemy(String src, float x, float y, int delay) {
		super(src, x, y);
		this.delay = delay;
	}

	/**
	 * Called when the enemy is shot by another sprite.
	 * @param other	the sprite the enemy was shot by
	 */
	public void onEnemyShot(Sprite other) {
		deactivate();
		other.deactivate();
	}

	@Override
	public void deactivate() {
		super.deactivate();
		if (Math.random() <= POWERUP_PROBABILITY) {
			// coin toss for one or the other
			Powerup powerup = null;
			if (Math.random() > 0.5f) {
				powerup = new ShieldPowerup(getX(), getY());
			} else {
				powerup = new ShotSpeedPowerup(getX(), getY());
			}
			World.getInstance().addSprite(powerup);
		}
	}

	@Override
	public final void update(Input input, int delta) {
		t += delta;
		if (t > delay) {
			enemyBehaviour(delta);
		}
	}

	/**
	 * Detailed behaviour once the enemy is active. Details vary depending on the enemy type.
	 * @param delta	the number of milliseconds that have passed since the last frame
	 */
	public void enemyBehaviour(int delta) {
	}

	@Override
	public void contactSprite(Sprite other) {
		if (onScreen() && other instanceof PlayerLaserShot) {
			onEnemyShot(other);
			other.deactivate();
		}
	}
}
