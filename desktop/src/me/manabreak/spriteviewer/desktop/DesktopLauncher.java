package me.manabreak.spriteviewer.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import me.manabreak.spriteviewer.SpriteAnimator;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        SpriteAnimator a = new SpriteAnimator(arg);
        new LwjglApplication(a, config);
    }
}
