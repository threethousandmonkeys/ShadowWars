/**
 * An enemy sprite that moves in a sine pattern.
 */
public class SineEnemy extends Enemy {
	public final static String ENEMY_SPRITE_PATH = "res/sine-enemy.png";
	public final static float AMPLITUDE = 96f;
	public final static float PERIOD = 1500f;
	public final static float SPEED = 0.15f;

	private float initialX;
	@Override
	public int getScore() {
		return 100;
	}

	/**
	 * Constructs the object. See parent for arguments.
	 */
	public SineEnemy(float x, float y, int delay) {
		super(ENEMY_SPRITE_PATH, x, y, delay);
		initialX = x;
	}

	@Override
	public void enemyBehaviour(int delta) {
		setX(initialX + AMPLITUDE * Math.sin(Math.PI * 2 / PERIOD * (getTime() - getDelay())));
		move(0, SPEED * delta);
	}
}
