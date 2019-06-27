/**
 * The big bad boss sprite.
 */
public class Boss extends Enemy {
	public final static String ENEMY_SPRITE_PATH = "res/boss.png";
	public final static float INITIAL_SPEED = 0.05f;
	public final static float SPEED = 0.2f;
	public final static float SHOOTING_SPEED = 0.1f;
	public final static int VALUE = 5000;
	public final static int TARGET_Y = 72;
	public final static int MAX_HEALTH = 60;
	public final static int MOVE_DELAY = 5000;
	public final static int SHOOTING_DELAY = 2000;
	public final static int SHOOTING_TIME = 5000;
	public final static int SHOT_DELAY = 200;
	public final static int MARGIN = 128;
	public final static int SHOT1_OFFSET = -97;
	public final static int SHOT2_OFFSET = -74;
	public final static int SHOT3_OFFSET = 74;
	public final static int SHOT4_OFFSET = 97;

	private int health = MAX_HEALTH;
	private boolean shooting = false;
	private boolean moving = false;
	private int moveTimer = 0;
	private int shootingTimer = 0;
	private int firingTimer = 0;
	private int destX;

	@Override
	public int getScore() {
		return VALUE;
	}

	@Override
	public void onEnemyShot(Sprite other) {
		--health;
		other.deactivate();
		if (health == 0) {
			deactivate();
		}
	}

	/**
	 * Construct the boss. See parent for argument details.
	 */
	public Boss(float x, float y, int delay) {
		super(ENEMY_SPRITE_PATH, x, y, delay);
	}

	@Override
	public void enemyBehaviour(int delta) {
		// move onto the screen
		if (getY() < TARGET_Y) {
			move(0, INITIAL_SPEED * delta);
		} else {
			// wait until we should move
			if (!moving) {
				moveTimer += delta;
				if (moveTimer >= MOVE_DELAY) {
					moveTimer = 0;
					moving = true;
					// choose a position
					destX = (int)(Math.random() * (App.SCREEN_WIDTH - MARGIN * 2) + MARGIN);
				}
			}
			if (moving) {
				if (!shooting) {
					// move to the chosen position
					if ((int)Math.abs(destX - getX()) > 0) {
						move(Math.signum(destX - getX()) * SPEED * delta, 0);
					}
					else {
						shooting = true;
					}
				}
				else {
					shootingTimer += delta;
					if (shootingTimer >= SHOOTING_DELAY + SHOOTING_TIME) {
						moving = false;
						shootingTimer = 0;
						shooting = false;
					}
					else if (shootingTimer >= SHOOTING_DELAY) {
						// don't throw away our (rest) shot
						firingTimer += delta;
						if (firingTimer > SHOT_DELAY) {
							firingTimer = 0;
							World.getInstance().addSprite(new EnemyLaserShot(getX() + SHOT1_OFFSET, getY()));
							World.getInstance().addSprite(new EnemyLaserShot(getX() + SHOT2_OFFSET, getY()));
							World.getInstance().addSprite(new EnemyLaserShot(getX() + SHOT3_OFFSET, getY()));
							World.getInstance().addSprite(new EnemyLaserShot(getX() + SHOT4_OFFSET, getY()));
						}
						// choose a new position if we don't have one set
						if (destX == 0) {
							destX = (int)(Math.random() * (App.SCREEN_WIDTH - MARGIN * 2) + MARGIN);
						}
						move(Math.signum(destX - getX()) * SHOOTING_SPEED * delta, 0);
					} else {
						destX = 0;
					}
				}
			}
		}
	}
}
