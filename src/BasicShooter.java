/**
 * A simple enemy that fires continuously.
 */
public class BasicShooter extends Enemy {
	public final static String ENEMY_SPRITE_PATH = "res/basic-shooter.png";
	public final static float SPEED = 0.2f;
	public final static float MARGIN = 48;
	public final static float SHOOT_DELAY = 3500;
	public final static float BOTTOM_MARGIN = 256;

	private float targetY;
	private float shootTime = 0;

	@Override
	public int getScore() {
		return 200;
	}

	/**
	 * Constructs the basic shooter enemy. See parent for argument details.
	 */
	public BasicShooter(float x, float y, int delay) {
		super(ENEMY_SPRITE_PATH, x, y, delay);
		targetY = (int)(Math.random() * (App.SCREEN_HEIGHT - MARGIN * 2 - BOTTOM_MARGIN) + MARGIN);
	}

	@Override
	public void enemyBehaviour(int delta) {
		if (getY() < targetY) {
			move(0, SPEED * delta);
		} else {
			shootTime += delta;
			if (shootTime >= SHOOT_DELAY) {
				shootTime -= SHOOT_DELAY;
				World.getInstance().addSprite(new EnemyLaserShot(getX(), getY()));
			}
		}
	}
}
