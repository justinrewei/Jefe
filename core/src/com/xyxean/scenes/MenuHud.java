package com.xyxean.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
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
import com.xyxean.MainGame;

/**
 * Created by justinwei on 2/26/2016.
 */
public class MenuHud implements Disposable {

    public Stage stage, stage2;
    private Viewport viewport;
    BitmapFont dfont;
    public static Preferences prefs;

    public static Integer easyScore = 0, mediumScore = 0, hardScore = 0, reallyHardScore = 0;

    Skin skin;
    TextureAtlas buttonAtlas, optionAtlas, storeAtlas;

    //sprites
    public static TextButton playButton;
    public static TextButton optionsButton;
    public static TextButton storeButton;
    private TextButton.TextButtonStyle buttonStyle, optionStyle, storeStyle;


    public MenuHud(final MainGame game, SpriteBatch sb){
        prefs = Gdx.app.getPreferences("MyPreferences");
        easyScore = prefs.getInteger("money1");
        mediumScore = prefs.getInteger("money2");
        hardScore = prefs.getInteger("money3");
        reallyHardScore = prefs.getInteger("money4");


        dfont = new BitmapFont(Gdx.files.internal("font/8bitfont.fnt"));
        viewport = new FitViewport(MainGame.V_WIDTH, MainGame.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);
        stage2 = new Stage(viewport, sb);
        skin = new Skin();
        buttonAtlas = new TextureAtlas("sprites/button.pack");
        optionAtlas = new TextureAtlas("sprites/options.pack");
        storeAtlas = new TextureAtlas("sprites/store.pack");
        skin.addRegions(buttonAtlas);
        skin.addRegions(optionAtlas);
        skin.addRegions(storeAtlas);

        buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.up = skin.getDrawable("buttonup");
        buttonStyle.font = dfont;
        buttonStyle.fontColor = Color.WHITE;

        optionStyle = new TextButton.TextButtonStyle();
        optionStyle.up = skin.getDrawable("options");
        optionStyle.font = dfont;
        optionStyle.fontColor = Color.WHITE;

        storeStyle = new TextButton.TextButtonStyle();
        storeStyle.up = skin.getDrawable("storebutton");
        storeStyle.font = dfont;
        storeStyle.fontColor = Color.WHITE;

        playButton = new TextButton("PLAY", buttonStyle);
        optionsButton = new TextButton("", optionStyle);
        storeButton = new TextButton("", storeStyle);
        Label.LabelStyle font = new Label.LabelStyle(new Label.LabelStyle(dfont, Color.BLACK));
        Table table = new Table();
        table.center();
        table.setFillParent(true);
        Table table2 = new Table();
        table2.center();
        table2.setFillParent(true);

        Label moneyLabel = new Label(""+prefs.getInteger("money"), font);
        prefs.flush();

        table.add(storeButton).maxSize(84).align(-55).padTop(30);
        table.add(optionsButton).maxSize(84).align(55).padTop(30);
        table.row();
        table.add(playButton).expandX().padTop(70).padLeft(103).minHeight(140).minWidth(170);
        stage.addActor(table);



        Gdx.input.setInputProcessor(stage);

        playButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                game.changeStateDifficulty();
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
