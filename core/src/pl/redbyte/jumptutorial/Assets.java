package pl.redbyte.jumptutorial;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Disposable;

public class Assets implements Disposable {

    public  final AssetManager manager = new AssetManager();

    public void load(){
        manager.load("player.png", Texture.class);
        manager.load("platform.png", Texture.class);
        manager.load("music.ogg", Music.class);
        manager.load("jump.ogg", Sound.class);
    }

    @Override
    public void dispose() {
        manager.dispose();
    }
}
