import org.newdawn.slick.Image;
import org.newdawn.slick.Input;

/**
 * Represents the player object, controllable by the user.
 */
public class Player extends Sprite {
	public final static String PLAYER_SPRITE_PATH = "res/spaceship.png";
	public final static String SHIELD_SPRITE_PATH = "res/shield.png";
	public final static int PLAYER_INITIAL_X = 512;
	public final static int PLAYER_INITIAL_Y = 688;
	public final static float SPEED = 0.5f;
	public final static int HIT_DELAY = 3000;
	public final static int SHOT_DELAY = 350;
	public final static int POWERUP_TIME = 5000;
	public final static int POWERUP_SHOT_DELAY = 150;

	private Image shieldImg;
	private int hitTimer = 0;
	private int shotTimer = SHOT_DELAY;
	private int shotDelay = SHOT_DELAY;
	private int powerupTimer = 0;

	/**
	 * Constructs the player object.
	 */
	public Player() {
		super(PLAYER_SPRITE_PATH, PLAYER_INITIAL_X, PLAYER_INITIAL_Y);

		try {
			shieldImg = new Image(SHIELD_SPRITE_PATH);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(Input input, int delta) {
		hitTimer += delta;
		shotTimer += delta;

		powerupTimer += delta;
		if (powerupTimer > POWERUP_TIME) {
			// deactivate faster shooting
			shotDelay = SHOT_DELAY;
		}

		doMovement(input, delta);
		// do shooting
		if (shotTimer >= shotDelay && input.isKeyDown(Input.KEY_SPACE)) {
			shotTimer = 0;
			World.getInstance().addSprite(new PlayerLaserShot(getX(), getY()));
		}
	}

	private void doMovement(Input input, int delta) {
		// handle horizontal movement
		float dx = 0;
		if (input.isKeyDown(Input.KEY_LEFT)) {
			dx -= SPEED;
		}
		if (input.isKeyDown(Input.KEY_RIGHT)) {
			dx += SPEED;
		}

		// handle vertical movement
		float dy = 0;
		if (input.isKeyDown(Input.KEY_UP)) {
			dy -= SPEED;
		}
		if (input.isKeyDown(Input.KEY_DOWN)) {
			dy += SPEED;
		}

		move(dx * delta, dy * delta);
		clampToScreen();
	}

	@Override
	public void contactSprite(Sprite other) {
		if (hitTimer > HIT_DELAY && (other instanceof EnemyLaserShot || other instanceof Enemy)) {
			World.getInstance().lifeLost();
			hitTimer = 0;
		} else if (other instanceof Powerup) {
			if (other instanceof ShieldPowerup) {
				hitTimer = HIT_DELAY - POWERUP_TIME;
			} else if (other instanceof ShotSpeedPowerup) {
				shotDelay = POWERUP_SHOT_DELAY;
				powerupTimer = 0;
			}
			other.deactivate();
		}
	}

	@Override
	public void render() {
		super.render();
		if (hitTimer <= HIT_DELAY) {
			shieldImg.drawCentered(getX(), getY());
		}
	}
}
