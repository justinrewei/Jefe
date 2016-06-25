package com.xyxean.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.xyxean.MainGame;
import com.xyxean.scenes.DifficultyHud;
import com.xyxean.scenes.Hud;
import com.xyxean.scenes.MenuHud;

/**
 * Created by justinwei on 3/28/2016.
 */
public class GameOverScreen implements Screen {
    private Viewport viewport;
    private Stage stage;
    private Game game;
    private TextButton.TextButtonStyle buttonStyle;
    Skin skin;
    TextureAtlas buttonAtlas;
    BitmapFont dfont;
    private Texture background;
    Label moneyLabel;


    public static TextButton menuButton, retryButton;

    public GameOverScreen(final Game game) {
        background = new Texture("background/newbg.png");
        this.game = game;
        viewport = new FitViewport(MainGame.V_WIDTH, MainGame.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, ((MainGame) game).batch);
        skin = new Skin();
        dfont = new BitmapFont(Gdx.files.internal("font/font.fnt"));
        Label.LabelStyle font = new Label.LabelStyle(new Label.LabelStyle(dfont, Color.BLACK));
        buttonAtlas = new TextureAtlas("sprites/button.pack");
        skin.addRegions(buttonAtlas);

        buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.up = skin.getDrawable("buttonup");
        buttonStyle.font = dfont;
        buttonStyle.fontColor = Color.WHITE;

        menuButton = new TextButton("RETURN TO\nMENU", buttonStyle);
        retryButton = new TextButton("PLAY AGAIN", buttonStyle);

        Table table = new Table();
        table.center();
        table.setFillParent(true);
        if (DifficultyHud.easyDifficulty) {
            if (Hud.getScore() > MenuHud.easyScore) {
                MenuHud.easyScore = Hud.getScore();
            }
            MenuHud.prefs.putInteger("money1", MenuHud.easyScore);
            MenuHud.prefs.flush();
        } else if (DifficultyHud.mediumDifficulty) {
            if (Hud.getScore() > MenuHud.mediumScore) {
                MenuHud.mediumScore = Hud.getScore();
            }
            MenuHud.prefs.putInteger("money2", MenuHud.mediumScore);
            MenuHud.prefs.flush();
        } else if (DifficultyHud.hardDifficulty) {
            if (Hud.getScore() > MenuHud.hardScore) {
                MenuHud.hardScore = Hud.getScore();
            }
            MenuHud.prefs.putInteger("money3", MenuHud.hardScore);
            MenuHud.prefs.flush();
        } else if (DifficultyHud.reallyHardDifficulty) {
            if (Hud.getScore() > MenuHud.reallyHardScore) {
                MenuHud.reallyHardScore = Hud.getScore();
            }
            MenuHud.prefs.putInteger("money4", MenuHud.reallyHardScore);
            MenuHud.prefs.flush();
        }

            Label gameOverLabel = new Label("GAME OVER", font);
        if (DifficultyHud.easyDifficulty)
        moneyLabel = new Label("EASY MODE\nHIGH SCORE: " + MenuHud.easyScore, font);
        else if (DifficultyHud.mediumDifficulty)
            moneyLabel = new Label("MEDIUM MODE\nHIGH SCORE: " + MenuHud.mediumScore, font);
        else if (DifficultyHud.hardDifficulty)
            moneyLabel = new Label("HARD MODE\nHIGH SCORE: " + MenuHud.hardScore, font);
        else if (DifficultyHud.reallyHardDifficulty)
            moneyLabel = new Label("APOCALYPSE MODE\nHIGH SCORE: " + MenuHud.reallyHardScore, font);
        Label scoreLabel = new Label("SCORE: " + Hud.getScore(), font);
            table.add(gameOverLabel).expandX();
            table.row();
            table.add(scoreLabel).expandX().padTop(10f);
            table.row();
            table.add(menuButton);
            table.row();
            table.add(retryButton);
            table.row();
            table.add(moneyLabel).expandX().padTop(5f);

            stage.addActor(table);
            Gdx.input.setInputProcessor(stage);

            menuButton.addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    ((MainGame) game).changeStateMenu();
                    MainGame.manager.get("audio/button.wav", Sound.class).play();
                    dispose();

                    return true;
                }
            });

            retryButton.addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    ((MainGame) game).changeStatePlay();
                    MainGame.manager.get("audio/button.wav", Sound.class).play();
                    dispose();
                    return true;
                }
            });
        }
        @Override
        public void show () {

        }

        @Override
        public void render ( float delta){
            Gdx.gl.glClearColor(0.686f, 0.933f, 0.933f, 0);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            stage.draw();
        }

        @Override
        public void resize ( int width, int height){

        }

        @Override
        public void pause () {

        }

        @Override
        public void resume () {

        }

        @Override
        public void hide () {

        }

        @Override
        public void dispose () {
            stage.dispose();

        }
    }
