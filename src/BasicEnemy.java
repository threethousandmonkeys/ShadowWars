/**
 * A simple enemy that moves down the screen.
 *
 */
public class BasicEnemy extends Enemy {
	public final static String ENEMY_SPRITE_PATH = "res/basic-enemy.png";
	public final static float SPEED = 0.2f;

	@Override
	public int getScore() {
		return 50;
	}

	/**
	 * Constructs the basic enemy. See parent for argument details.
	 */
	public BasicEnemy(float x, float y, int delay) {
		super(ENEMY_SPRITE_PATH, x, y, delay);
	}

	@Override
	public void enemyBehaviour(int delta) {
		move(0, SPEED * delta);
	}
}
