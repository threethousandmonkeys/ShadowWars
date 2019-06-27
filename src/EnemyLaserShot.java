import org.newdawn.slick.Input;

/**
 * A laser shot as fired by the player.
 */
public class EnemyLaserShot extends Sprite {
	public final static String SHOT_SPRITE_PATH = "res/enemy-shot.png";
	public final float SHOT_SPEED = 0.7f;

	/**
	 * Constructs the laser at a given position.
	 * @param x	the x coordinate to create the laser at
	 * @param y the y coordinate to create the laser at
	 */
	public EnemyLaserShot(float x, float y) {
		super(SHOT_SPRITE_PATH, x, y);
	}

	@Override
	public void update(Input input, int delta) {
		move(0, SHOT_SPEED);
		if (!onScreen()) {
			deactivate();
		}
	}
}
