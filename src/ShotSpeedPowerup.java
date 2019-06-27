/**
 * A powerup object that increases the player's shooting speed.
 */
public class ShotSpeedPowerup extends Powerup {
	public static final String PATH = "res/shotspeed-powerup.png";

	/**
	 * Constructs the powerup at a given position.
	 * @param x	the x coordinate to create the powerup at
	 * @param y the y coordinate to create the powerup at
	 */
	public ShotSpeedPowerup(float x, float y) {
		super(PATH, x, y);
	}
}
