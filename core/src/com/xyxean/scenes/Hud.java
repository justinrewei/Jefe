package com.xyxean.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
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
import com.sun.javafx.css.converters.SizeConverter;
import com.xyxean.MainGame;
import com.xyxean.screens.PlayScreen;
import com.xyxean.sprites.Player;

/**
 * Created by justinwei on 2/26/2016.
 */
public class Hud implements Disposable {
    //a stage is an empty box. a table can be laid out inside the stage to organize the labels.
    public static Stage stage;
    private Viewport viewport;
    private PlayScreen screen;
    private BitmapFont font;

    //time tracking
    private float timeCount, multiplierCount;

    private static Integer score;
    private Integer multiplier;
    private Integer amultiplier;
    private TextButton.TextButtonStyle buttonStyle;

    Skin skin;

    static Label scoreLabel;
    Label multiplierLabel;
    Label scoreText;

    public Hud(PlayScreen screen, SpriteBatch sb){
        this.screen = screen;
        font = new BitmapFont(Gdx.files.internal("font/myfont.fnt"));
        score = 0;
        multiplier = 0;
        if (isEasyDifficulty())
            amultiplier = 1;
        else if (isMediumDifficulty())
            amultiplier = 2;
        else if (isHardDifficulty())
            amultiplier = 3;
        else if (isReallyHardDifficulty())
            amultiplier = 4;


        skin = new Skin();

        buttonStyle = new TextButton.TextButtonStyle();

        buttonStyle.font = font;
        buttonStyle.fontColor = Color.WHITE;


        viewport = new FitViewport(MainGame.V_WIDTH, MainGame.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);
        //create table
        Table table = new Table();
        table.top();
        table.setFillParent(true); //table is now size of stage

        scoreText  = new Label("SCORE", new Label.LabelStyle(font, Color.BLACK));
        scoreLabel = new Label(String.format("%08d", score), new Label.LabelStyle(font, Color.BLACK));
        if (isEasyDifficulty())
            multiplierLabel = new Label(String.format(amultiplier + "." + multiplier + "x MULTIPLIER", multiplier), new Label.LabelStyle(font, Color.BLACK));
        else if (isMediumDifficulty())
            multiplierLabel = new Label(String.format(amultiplier + "." + multiplier + "x MULTIPLIER", multiplier), new Label.LabelStyle(font, Color.BLACK));
        else if (isHardDifficulty())
            multiplierLabel = new Label(String.format(amultiplier + "." + multiplier + "x MULTIPLIER", multiplier), new Label.LabelStyle(font, Color.BLACK));
        else if (isReallyHardDifficulty())
            multiplierLabel = new Label(String.format(amultiplier + "." + multiplier + "x MULTIPLIER", multiplier), new Label.LabelStyle(font, Color.BLACK));

        //add labels to table
        table.add(scoreText).expandX().padTop(3);
        table.row();
        table.add(scoreLabel).expandX();
        table.row();
        table.add(multiplierLabel).expandX().padTop(3);
        stage.addActor(table);

        Gdx.input.setInputProcessor(stage);


    }


    public void update(float dt){
        if (screen.getPlayer().currentState != Player.State.DEAD) {
            timeCount += dt;



            if (timeCount >= 0.01) {
                 if (isEasyDifficulty())
                score += (int) (1 * (amultiplier + (multiplier * 0.1)) * 1);
                else if (isMediumDifficulty())
                     score += (int) (1 * (amultiplier + (multiplier * 0.1)) * 1);
                else if (isHardDifficulty())
                     score += (int) (1 * (amultiplier + (multiplier * 0.1)) * 1);
                else if (isReallyHardDifficulty())
                     score += (int) (1 * (amultiplier + (multiplier * 0.1)) * 1);
                scoreLabel.setText(String.format("%08d", score));
                timeCount = 0;
            }

            multiplierCount += dt;
            if (multiplierCount >= 2) {
                multiplier += 1;

                if (multiplier >= 10){
                    amultiplier += 1;
                    multiplier = 0;
                }

                multiplierLabel.setText(String.format(amultiplier + "." + multiplier + "x MULTIPLIER", multiplier));
                multiplierCount = 0;
            }
        }
    }


    public static Integer getScore() {
        return score;
    }

    public static void addScore(int value){
        score += value;
        scoreLabel.setText(String.format("%06d", score));
    }

    public boolean isEasyDifficulty() {
        return DifficultyHud.easyDifficulty;
    }
    public boolean isMediumDifficulty() {
        return DifficultyHud.mediumDifficulty;
    }
    public boolean isHardDifficulty() {
        return DifficultyHud.hardDifficulty;
    }    public boolean isReallyHardDifficulty() {
        return DifficultyHud.reallyHardDifficulty;
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
