package me.manabreak.spriteviewer;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.Locale;

public class Gui extends Stage {

    private final Label.LabelStyle style;
    private Label frameSpeed, frames, row, zoomLevel;
    private BitmapFont font = new BitmapFont();

    public Gui(SpriteAnimator spriteAnimator) {
        super(new ScreenViewport());

        style = new Label.LabelStyle(font, Color.BLACK);
        frameSpeed = new Label("", style);
        frameSpeed.setPosition(1f, 10f);
        updateFrameSpeed(spriteAnimator.getFrameSpeed());
        addActor(frameSpeed);

        frames = new Label("", style);
        frames.setPosition(1f, 25f);
        updateFrames(spriteAnimator.getFrames());
        addActor(frames);

        row = new Label("", style);
        row.setPosition(1f, 40f);
        updateRow(spriteAnimator.getRow());
        addActor(row);

        zoomLevel = new Label("", style);
        zoomLevel.setPosition(1f, 55f);
        updateZoom(spriteAnimator.getZoom());
        addActor(zoomLevel);
    }

    public void updateZoom(int zoom) {
        this.zoomLevel.setText(String.format(Locale.ENGLISH, "Zoom: %d", zoom));
    }

    public void updateRow(int row) {
        this.row.setText(String.format(Locale.ENGLISH, "Row: %d", row));
    }

    public void updateFrames(int frames) {
        this.frames.setText(String.format(Locale.ENGLISH, "Frames: %d", frames));
    }

    public void updateFrameSpeed(float speed) {
        frameSpeed.setText(String.format(Locale.ENGLISH, "Frame speed: %.2f", speed));
    }

    public void resize(int width, int height) {
        getViewport().setScreenSize(width, height);
        getCamera().viewportWidth = width;
        getCamera().viewportHeight = height;
        getCamera().position.x = width / 2;
        getCamera().position.y = height / 2;
        getCamera().update();
    }

    public void useWhiteText(boolean b) {
        if (b) {
            style.fontColor = Color.WHITE;
        } else {
            style.fontColor = Color.BLACK;
        }
    }
}
