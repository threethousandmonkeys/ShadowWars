import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 * Class to manage all the various objects in the game.
 */
public class World {
	public static final String BKG_PATH = "res/space.png";
	public static final String LIVES_PATH = "res/lives.png";

	private static final float ENEMY_Y_OFFSET = -64;
	private static final float BACKGROUND_SCROLL_SPEED = 0.2f;
	private static final int INITIAL_LIVES = 3;
	private static final int SPEEDUP_RATIO = 5;

	private float backgroundOffset = 0;
	private Image background;
	private Image livesImage;
	private int score = 0;
	private int lives = INITIAL_LIVES;

	private static World world;
	/**
	 * Accesses the singleton instance.
	 * @return	the singleton instance
	 */
	public static World getInstance() {
		if (world == null) {
			world = new World();
		}
		return world;
	}

	private ArrayList<Sprite> sprites = new ArrayList<>();

	/**
	 * Adds a sprite to the internal list.
	 * @param sprite	the sprite object to add to the list
	 */
	public void addSprite(Sprite sprite) {
		sprites.add(sprite);
	}

	private World() {
		try {
			background = new Image(BKG_PATH);
			livesImage = new Image(LIVES_PATH);
		} catch (SlickException e) {
			e.printStackTrace();
		}

		// Create sprites
		sprites.add(new Player());
		spawnEnemies();

		world = this;
	}

	/**
	 * Updates the world's representation of objects.
	 * @param input	the Slick input object
	 * @param delta	the number of milliseconds since the last frame
	 */
	public void update(Input input, int delta) {
		int effectiveDelta = delta * (input.isKeyDown(Input.KEY_S) ? SPEEDUP_RATIO : 1);
		// Update all sprites
		for (int i = 0; i < sprites.size(); ++i) {
			sprites.get(i).update(input, effectiveDelta);
		}
		// Handle collisions
		for (int i = 0; i < sprites.size(); ++i) {
			Sprite sprite = sprites.get(i);
			for (int j = 0; j < sprites.size(); ++j) {
				Sprite other = sprites.get(j);
				if (sprite != other && other.getActive() && sprite.getBoundingBox().intersects(other.getBoundingBox())) {
					sprite.contactSprite(other);
				}
			}
		}
		// Clean up inactive sprites
		for (int i = 0; i < sprites.size(); ++i) {
			if (sprites.get(i).getActive() == false) {
				score += sprites.get(i).getScore();
				sprites.remove(i);
				// decrement counter to make sure we don't miss any
				--i;
			}
		}

		backgroundOffset += BACKGROUND_SCROLL_SPEED * effectiveDelta;
		backgroundOffset = backgroundOffset % background.getHeight();
	}

	/**
	 * Renders all game objects to the screen.
	 * @param g	the graphics object to use
	 */
	public void render(Graphics g) {
		// Tile the background image
		for (int i = 0; i < App.SCREEN_WIDTH; i += background.getWidth()) {
			for (int j = -background.getHeight() + (int)backgroundOffset; j < App.SCREEN_HEIGHT; j += background.getHeight()) {
				background.draw(i, j);
			}
		}
		// Draw all sprites
		for (Sprite sprite : sprites) {
			sprite.render();
		}
		g.drawString("Score: " + score, 20, App.SCREEN_HEIGHT - 30);
		for (int i = 1; i <= lives; ++i) {
			livesImage.draw(20 + 40 * (i - 1), App.SCREEN_HEIGHT - 72);
		}
	}

	/**
	 * Called whenever the player loses a life.
	 */
	public void lifeLost() {
		--lives;
		if (lives == 0) {
			System.exit(0);
		}
	}

	private void spawnEnemies() {
		try (BufferedReader br = new BufferedReader(new FileReader("res/waves.txt"))) {
			String line = null;
			while ((line = br.readLine()) != null) {
				if (line.charAt(0) != '#') {
					// read the data from the line
					String[] data = line.split(",");

					// This next section uses a technique called "reflection", where
					// classes are looked up by their (string) names. In this way,
					// we can avoid having to use the Factory pattern without
					// sacrificing extensibility. Is there a downside to this approach?
					String className = data[0];
					int x = Integer.parseInt(data[1]);
					int delay = Integer.parseInt(data[2]);

					Class<?> c = Class.forName(className);
					addSprite((Sprite)c.getConstructor(new Class[] { float.class, float.class, int.class })
								       .newInstance(new Object[] { x, ENEMY_Y_OFFSET, delay }));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
