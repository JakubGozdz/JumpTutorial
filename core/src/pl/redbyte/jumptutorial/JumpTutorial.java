package pl.redbyte.jumptutorial;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

public class JumpTutorial extends ApplicationAdapter {


	private Music music;
	private Texture playerTexture, platformTexture;
	private JumpPlayer player;
	private Array<Platform> platforms;
	private OrthographicCamera camera;
	private float gravity = -20;
	private SpriteBatch batch;


	@Override
	public void create () {
		loadData();
		init();
	}


	private void init() {
		batch = new SpriteBatch();
		music.play();
		camera = new OrthographicCamera(480, 600);

		player = new JumpPlayer(playerTexture);
		platforms = new Array<>();

		for(int i = 1; i < 3; i++){
			Platform platform = new Platform((platformTexture));
			platform.x = MathUtils.random(480);
			platform.y = 200 * i;
			platforms.add(platform);
		}
	}


	private void loadData() {
		playerTexture = new Texture("player.png");
		platformTexture = new Texture("platform.png");
		music = Gdx.audio.newMusic(Gdx.files.internal("music.ogg"));
	}


	@Override
	public void render () {
		update();
		ScreenUtils.clear(1, 1, 1, 1);
		batch.begin();
		for(Platform platform : platforms){
			platform.draw(batch);
		}
		player.draw(batch);
		batch.end();
	}

	private void update() {

	}


	@Override
	public void dispose () {
		batch.dispose();
		playerTexture.dispose();
		platformTexture.dispose();
		music.dispose();
		player.dispose();
	}
}