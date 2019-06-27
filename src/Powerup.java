import org.newdawn.slick.Input;

/**
 * A generic powerup object.
 */
public abstract class Powerup extends Sprite {
	public static final float SPEED = 0.1f;

	/**
	 * Constructs the powerup at a given position.
	 * @param imageSrc	the image file to use for the sprite
	 * @param x			the x coordinate to create the powerup at
	 * @param y 		the y coordinate to create the powerup at
	 */
	public Powerup(String imageSrc, float x, float y) {
		super(imageSrc, x, y);
	}

	@Override
	public void update(Input input, int delta) {
		move(0, SPEED * delta);
	}
}
