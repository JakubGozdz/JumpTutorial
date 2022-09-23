package pl.redbyte.jumptutorial;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.Input.Keys;


public class JumpTutorial extends ApplicationAdapter {

	private Assets assets;
	private Music music;
	private Texture playerTexture, platformTexture;
	private JumpPlayer player;
	private Array<Platform> platforms;
	private OrthographicCamera camera;
	private float gravity = -20;
	private SpriteBatch batch;


	@Override
	public void create () {
		assets = new Assets();
		assets.load();
		assets.manager.finishLoading();

		if(assets.manager.update()){
			loadData();
			init();
		}
	}


	private void loadData() {
		playerTexture = assets.manager.get("player.png", Texture.class);
		platformTexture = assets.manager.get("platform.png", Texture.class);
		music = assets.manager.get("music.ogg", Music.class);
	}


	private void init() {
		batch = new SpriteBatch();
		music.play();
		camera = new OrthographicCamera(480, 600);
		player = new JumpPlayer(playerTexture, assets.manager.get("jump.ogg", Sound.class));
		platforms = new Array<>();
		for(int i = 1; i < 30; i++){
			Platform platform = new Platform((platformTexture));
			platform.x = MathUtils.random(480);
			platform.y = 200 * i;
			platforms.add(platform);
		}
	}


	@Override
	public void render () {
		update();
		ScreenUtils.clear(1, 1, 1, 1);
		batch.begin();
		batch.setProjectionMatrix(camera.combined);
		for(Platform platform : platforms){
			platform.draw(batch);
		}
		player.draw(batch);
		batch.end();
	}

	private void update() {
		handleInput();
		camera.position.set(player.x + player.getWidth()/2, player.y + 200, 0);
		camera.update();
		player.y += player.jumpVelocity * Gdx.graphics.getDeltaTime();
		if(player.y > 0) {
			player.jumpVelocity += gravity;
		} else {
			player.y = 0;
			player.canJump = true;
			player.jumpVelocity = 0;
		}
		for(Platform platform : platforms){
			if(isPlayerOnPlatform(platform)){
				player.canJump = true;
				player.jumpVelocity = 0;
				player.y = platform.y + platform.height;
			}
		}
	}


	private boolean isPlayerOnPlatform(Platform platform) {
		return player.jumpVelocity <= 0 && player.overlaps(platform) && !(player.y <= platform.y);
	}


	private void handleInput() {
		if(Gdx.input.isKeyPressed(Keys.A)){
			player.x -= 500 * Gdx.graphics.getDeltaTime();
		}
		if(Gdx.input.isKeyPressed(Keys.D)){
			player.x += 500 * Gdx.graphics.getDeltaTime();
		}
		if(Gdx.input.justTouched() || Gdx.input.isKeyPressed(Keys.SPACE)){
			player.jump();
		}
	}


	@Override
	public void dispose () {
		assets.dispose();
	}
}