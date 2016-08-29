package me.manabreak.spriteviewer;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class SpriteAnimator extends ApplicationAdapter {
    SpriteBatch batch;
    Texture img;
    int zoomLevel = 1;
    float timer = 0f;
    int frames = 8;
    boolean checkingFile = true;
    private String[] args;
    private Gui gui;
    private float frameSpeed = 0.17f;
    private TextureRegion[][] regions;
    private int currentFrame = 0;
    private FileHandle fileHandle;
    private long lastModified;
    private boolean shouldReload = false;
    private float reloadTimer = 0f;
    private int tries = 0;
    private int row = 0;
    private boolean whiteBG = true;

    public SpriteAnimator(String[] args) {
        this.args = args;
        for (String s : args) {
            System.out.println("Arg: " + s);
        }
    }

    @Override
    public void create() {
        gui = new Gui(this);
        batch = new SpriteBatch();
        if (args.length > 2) {
            fileHandle = Gdx.files.internal(args[0]);
            if (fileHandle.exists()) {
                try {
                    lastModified = fileHandle.lastModified();
                    img = new Texture(args[0]);
                    regions = TextureRegion.split(img, Integer.parseInt(args[1]), Integer.parseInt(args[2]));
                    frames = regions[0].length;
                } catch (Exception e) {
                    Gdx.app.exit();
                }
            } else Gdx.app.exit();
        } else Gdx.app.exit();
    }

    @Override
    public void resize(int width, int height) {
        batch = new SpriteBatch();
        gui.resize(width, height);
    }

    TextureRegion getCurrentRegion() {
        return regions[row][currentFrame];
    }

    @Override
    public void render() {
        if (whiteBG) {
            Gdx.gl.glClearColor(1, 1, 1, 1);
        } else {
            Gdx.gl.glClearColor(0, 0, 0, 1);
        }
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        checkFileChanges();

        float dt = Gdx.graphics.getDeltaTime();

        if (shouldReload) {
            reloadTimer += dt;
            if (reloadTimer > 0.1f) {
                try {
                    System.out.println("Reloading!");
                    FileHandle handle = Gdx.files.internal(args[0]);
                    lastModified = handle.lastModified();
                    shouldReload = false;
                    img = new Texture(args[0]);
                    regions = TextureRegion.split(img, 16, 24);
                } catch (Exception e) {
                    tries++;
                    reloadTimer = 0f;
                    if (tries == 5) {
                        shouldReload = false;
                        System.err.println("Failure to reload image");
                    }
                }
            }
        }

        if (img != null) {

            timer += dt;
            if (timer >= frameSpeed) {
                timer -= frameSpeed;
                currentFrame++;
                currentFrame %= frames;
            }

            int w = Gdx.graphics.getWidth();
            int h = Gdx.graphics.getHeight();

            TextureRegion r = getCurrentRegion();
            int rw = r.getRegionWidth();
            int rh = r.getRegionHeight();

            batch.begin();
            if (zoomLevel == 1) {
                batch.draw(r, w / 2 - rw / 2, h / 2 - rh / 2);
            } else {
                rw *= zoomLevel;
                rh *= zoomLevel;
                batch.draw(r, w / 2 - rw / 2, h / 2 - rh / 2, rw, rh);
            }
            batch.end();

            if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
                zoomLevel = 1;
                gui.updateZoom(zoomLevel);
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
                zoomLevel = 2;
                gui.updateZoom(zoomLevel);
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)) {
                zoomLevel = 3;
                gui.updateZoom(zoomLevel);
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_4)) {
                zoomLevel = 4;
                gui.updateZoom(zoomLevel);
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_5)) {
                zoomLevel = 5;
                gui.updateZoom(zoomLevel);
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_6)) {
                zoomLevel = 6;
                gui.updateZoom(zoomLevel);
            }

            if (Gdx.input.isKeyJustPressed(Input.Keys.X)) {
                whiteBG = !whiteBG;
                gui.useWhiteText(!whiteBG);
            }

            if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
                frames--;
                if (frames < 1) frames = 1;
                gui.updateFrames(frames);
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
                frames++;
                if (frames > regions[row].length) frames = regions[row].length;
                gui.updateFrames(frames);
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
                row++;
                if (row > regions.length - 1) row = regions.length - 1;
                gui.updateRow(row);
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
                row--;
                if (row < 0) row = 0;
                gui.updateRow(row);
            }

            if (Gdx.input.isKeyJustPressed(Input.Keys.PLUS)) {
                frameSpeed += 0.01f;
                gui.updateFrameSpeed(frameSpeed);
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.MINUS)) {
                frameSpeed -= 0.01f;
                gui.updateFrameSpeed(frameSpeed);
            }
        }

        gui.act(dt);
        gui.draw();
    }

    private void checkFileChanges() {
        if (shouldReload) return;

        FileHandle handle = Gdx.files.internal(args[0]);
        if (img != null && handle.lastModified() > lastModified) {
            System.out.println("Initiating reload...");
            tries = 0;
            shouldReload = true;
            reloadTimer = 0f;
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        if (img != null) img.dispose();
    }

    public float getFrameSpeed() {
        return frameSpeed;
    }

    public int getFrames() {
        return frames;
    }

    public int getRow() {
        return row;
    }

    public int getZoom() {
        return zoomLevel;
    }
}
