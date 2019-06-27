/**
 * A powerup object that gives the player a shield.
 */
public class ShieldPowerup extends Powerup {
	public static final String PATH = "res/shield-powerup.png";

	/**
	 * Constructs the powerup at a given position.
	 * @param x	the x coordinate to create the powerup at
	 * @param y the y coordinate to create the powerup at
	 */
	public ShieldPowerup(float x, float y) {
		super(PATH, x, y);
	}
}
