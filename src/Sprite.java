import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import utilities.BoundingBox;

/**
 * Represents a sprite object that can be rendered and updated.
 */
public abstract class Sprite {
	public final static float MARGIN = 5f;

	private Image image = null;
	private float x;
	private float y;
	private BoundingBox bb;
	private boolean active = true;

	/**
	 * Constructs the sprite object.
	 * @param imageSrc	the file to load the image from
	 * @param x			the x coordinate to create the sprite at
	 * @param y			the y coordinate to create the sprite at
	 */
	public Sprite(String imageSrc, float x, float y) {
		try {
			image = new Image(imageSrc);
		} catch (SlickException e) {
			e.printStackTrace();
		}

		this.x = x;
		this.y = y;

		bb = new BoundingBox(image, x, y);
	}

	/*
	 * Returns true whenever the sprite is on screen.
	 */
	public boolean onScreen() {
		return x >= 0 && x <= App.SCREEN_WIDTH + bb.getWidth()
			&& y >= 0 && y <= App.SCREEN_HEIGHT + bb.getHeight();
	}

	/*
	 *  Forces the sprite to remain on the screen
	 */
	public void clampToScreen() {
		x = Math.max(x, 0);
		x = Math.min(x, App.SCREEN_WIDTH);
		y = Math.max(y, 0);
		y = Math.min(y, App.SCREEN_HEIGHT);
	}

	/**
	 * Updates the sprite's state. Left empty for a default implementation.
	 * @param input	the Slick input object
	 * @param delta	the number of milliseconds since the last frame
	 */
	public void update(Input input, int delta) {

	}

	/**
	 * Renders the sprite to the screen.
	 */
	public void render() {
		image.drawCentered(x, y);
	}

	/*
	 * Called whenever this Sprite makes contact with another.
	 */
	public void contactSprite(Sprite other) {

	}

	/**
	 * Accesses the amount of score the sprite is worth. Default to zero.
	 * @return	the points the sprite is worth
	 */
	public int getScore() {
		return 0;
	}

	/**
	 * Sets the x position of the sprite.
	 * @param x	 the target x position
	 */
	public void setX(double x) { this.x = (float)x; bb.setX((float)x); }
	/**
	 * Sets the y position of the sprite.
	 * @param y	 the target y position
	 */
	public void setY(double y) { this.y = (float)y; bb.setY((float)y); }
	/**
	 * Accesses the x position of the sprite.
	 * @return	the x position of the sprite
	 */
	public float getX() { return x; }
	/**
	 * Accesses the y position of the sprite.
	 * @return	the y position of the sprite
	 */
	public float getY() { return y; }
	/**
	 * Returns true if the sprite is active.
	 * @return	whether the sprite is active
	 */
	public boolean getActive() { return active; }
	/**
	 * Deactivates this sprite.
	 */
	public void deactivate() { active = false; }

	/**
	 * Returns the sprite's bounding box (by copy)
	 * @return	the bounding box
	 */
	public BoundingBox getBoundingBox() {
		return new BoundingBox(bb);
	}
	/**
	 * Moves the sprite by a given amount.
	 * @param dx	the x amount to move
	 * @param dy	the y amount to move
	 */
	public void move(float dx, float dy) {
		x += dx;
		y += dy;
		bb.setX(x);
		bb.setY(y);
	}
}
