package com.xyxean.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.xyxean.MainGame;

/**
 * Created by justinwei on 4/2/2016.
 */
public class DifficultyHud implements Disposable {
    public Stage stage, stageScore;
    private Viewport viewport;
    BitmapFont dfont;
    public static boolean easyDifficulty = false, mediumDifficulty = false, hardDifficulty = false, reallyHardDifficulty = false;

    Skin skin;
    TextureAtlas buttonAtlas, optionAtlas, storeAtlas;

    //sprites
    public static TextButton easyButton;
    public static TextButton mediumButton;
    public static TextButton hardButton;
    public static TextButton reallyHardButton;
    public static TextButton backButton, scoreButtonEasy, scoreButtonMedium, scoreButtonHard, scoreButtonReallyHard;
    public static TextButton scoreEasy, scoreMedium, scoreHard, scoreReallyHard;
    private TextButton.TextButtonStyle easyStyle, mediumStyle, hardStyle, reallyHardStyle;


    public DifficultyHud(final MainGame game, SpriteBatch sb){
        dfont = new BitmapFont(Gdx.files.internal("font/myfont.fnt"));
        viewport = new FitViewport(MainGame.V_WIDTH, MainGame.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);
        stageScore = new Stage(viewport, sb);
        skin = new Skin();
        buttonAtlas = new TextureAtlas("sprites/difficultybutton.pack");
        skin.addRegions(buttonAtlas);

        easyStyle = new TextButton.TextButtonStyle();
        easyStyle.up = skin.getDrawable("white");
        easyStyle.font = dfont;
        easyStyle.fontColor = Color.BLACK;


        mediumStyle = new TextButton.TextButtonStyle();
        mediumStyle.up = skin.getDrawable("gray");
        mediumStyle.font = dfont;
        mediumStyle.fontColor = Color.BLACK;

        hardStyle = new TextButton.TextButtonStyle();
        hardStyle.up = skin.getDrawable("dark");
        hardStyle.font = dfont;
        hardStyle.fontColor = Color.WHITE;

        reallyHardStyle = new TextButton.TextButtonStyle();
        reallyHardStyle.up = skin.getDrawable("black");
        reallyHardStyle.font = dfont;
        reallyHardStyle.fontColor = Color.WHITE;

        scoreButtonEasy = new TextButton(""+MenuHud.easyScore, mediumStyle);
        scoreButtonMedium = new TextButton(""+MenuHud.mediumScore, mediumStyle);
        scoreButtonHard = new TextButton(""+MenuHud.hardScore, mediumStyle);
        scoreButtonReallyHard = new TextButton(""+MenuHud.reallyHardScore, mediumStyle);

        easyButton = new TextButton("EASY 1x", easyStyle);
        mediumButton = new TextButton("MEDIUM 1.5x", mediumStyle);
        hardButton = new TextButton("HARD 2x", hardStyle);
        reallyHardButton = new TextButton("APOCALYPSE 4x", reallyHardStyle);
        backButton = new TextButton("BACK", easyStyle);

        Label.LabelStyle font = new Label.LabelStyle(new Label.LabelStyle(dfont,  Color.WHITE));
        Table table = new Table();
        table.center();
        table.setFillParent(true);

        table.add(backButton).maxSize(43).padTop(-25).padLeft(-120);
        table.row();
        table.add(reallyHardButton).expandX().minWidth(170).padTop(-13).maxHeight(45);
        table.row();
        table.add(hardButton).minWidth(170).maxHeight(45).padTop(-13);
        table.row();
        table.add(mediumButton).minWidth(170).maxHeight(45).padTop(-13);
        table.row();
        table.add(easyButton).minWidth(170).maxHeight(45).padTop(-13);
        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);

        Table table2 = new Table();
        table2.align(10);
        table2.setFillParent(true);
        table2.add(scoreButtonReallyHard).maxWidth(100).maxHeight(17).padTop(48).padLeft(-4);
        table2.row();
        table2.add(scoreButtonHard).maxWidth(100).maxHeight(17).padTop(-8).padLeft(-4);
        table2.row();
        table2.add(scoreButtonMedium).maxWidth(100).maxHeight(17).padTop(-7).padLeft(-4);
        table2.row();
        table2.add(scoreButtonEasy).maxWidth(100).maxHeight(17).padTop(-10).padLeft(-4);
        stageScore.addActor(table2);

        backButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                game.changeStateMenu();
                MainGame.manager.get("audio/button.wav", Sound.class).play();
                dispose();
                return true;
            }
        });
        easyButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                easyDifficulty = true;
                mediumDifficulty = false;
                hardDifficulty = false;
                reallyHardDifficulty = false;
                game.changeStatePlay();
                MainGame.manager.get("audio/button.wav", Sound.class).play();
                return true;
            }
        });
        mediumButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                mediumDifficulty = true;
                easyDifficulty = false;
                hardDifficulty = false;
                reallyHardDifficulty = false;
                game.changeStatePlay();
                MainGame.manager.get("audio/button.wav", Sound.class).play();
                return true;
            }
        });
        hardButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                hardDifficulty = true;
                easyDifficulty = false;
                mediumDifficulty = false;
                reallyHardDifficulty = false;
                game.changeStatePlay();
                MainGame.manager.get("audio/button.wav", Sound.class).play();
                return true;
            }
        });
        reallyHardButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                reallyHardDifficulty = true;
                easyDifficulty = false;
                mediumDifficulty = false;
                hardDifficulty = false;
             game.changeStatePlay();
                MainGame.manager.get("audio/button.wav", Sound.class).play();
                return true;
            }
        });
    }




    @Override
    public void dispose() {
        stage.dispose();
    }
}



