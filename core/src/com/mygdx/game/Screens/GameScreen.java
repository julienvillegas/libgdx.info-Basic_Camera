package com.mygdx.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.time.Instant;

/**
 * Created by julienvillegas on 17/01/2017.
 */
public class GameScreen implements Screen {

    private Stage stage;
    private Game game;
    private OrthographicCamera camera;

    private final int startX = 1100;// -Gdx.graphics.getWidth()/2;
    private final int startY = 1225;

    private final int endX = 2350;// -Gdx.graphics.getWidth()/2;
    private final int endY = 600;

    private float minAltitude = 0.5f;
    private float maxAltitude = 2.5f;

    private float percent;
    private float counter;
    private long startTime;
    private final float animation_duration = 15000;

    public GameScreen(Game aGame) {
        game = aGame;
        stage = new Stage(new ScreenViewport());

        Image map = new Image(new Texture("map.jpg"));
        stage.addActor(map);
        camera = (OrthographicCamera) stage.getViewport().getCamera();
        camera.translate(startX,startY);
        counter = 0;
        startTime = Instant.now().toEpochMilli();

    }

    @Override
    public void show() {
        Gdx.app.log("MainScreen","show");
        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void render(float delta) {
        //jave 8
        long secondFromStart = Instant.now().toEpochMilli()-startTime;
        percent = (secondFromStart%animation_duration)/animation_duration;
        percent = (float)Math.cos(percent*Math.PI*2)/2+0.5f;
        Gdx.app.log("render","secondFromStart:"+ secondFromStart+", %:"+percent);
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        moveCamera();
        stage.act();
        stage.draw();
    }

    private void moveCamera(){
        float currentX = startX + (endX-startX)*percent;
        float currentY = startY + (endY-startY)*percent;
        float percentZ = Math.abs(percent - 0.5f)*2;
        float currentZ = maxAltitude - (maxAltitude-minAltitude)*percentZ  ;

        camera.position.x = currentX;
        camera.position.y = currentY;
        camera.zoom = currentZ;
        camera.update();
    }


    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }


}
