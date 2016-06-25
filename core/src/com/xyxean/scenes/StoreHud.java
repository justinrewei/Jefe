package com.xyxean.scenes;

import com.badlogic.gdx.Gdx;
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
 * Created by justinwei on 4/3/2016.
 */
public class StoreHud implements Disposable {
    public Stage stage;
    private Viewport viewport;
    BitmapFont dfont;

    Skin skin;
    TextureAtlas buttonAtlas, optionAtlas, storeAtlas;

    //sprites
    public static TextButton upgrades;
    public static TextButton design;
    public static TextButton backButton;
    private TextButton.TextButtonStyle upgradesStyle, designStyle;


    public StoreHud(final MainGame game, SpriteBatch sb){
        dfont = new BitmapFont(Gdx.files.internal("font/myfont.fnt"));
        viewport = new FitViewport(MainGame.V_WIDTH, MainGame.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);
        skin = new Skin();
        buttonAtlas = new TextureAtlas("sprites/difficultybutton.pack");
        skin.addRegions(buttonAtlas);

        upgradesStyle = new TextButton.TextButtonStyle();
        upgradesStyle.up = skin.getDrawable("white");
        upgradesStyle.font = dfont;
        upgradesStyle.fontColor = Color.BLACK;

        designStyle = new TextButton.TextButtonStyle();
        designStyle.up = skin.getDrawable("white");
        designStyle.font = dfont;
        designStyle.fontColor = Color.BLACK;

        upgrades = new TextButton("UPGRADES", upgradesStyle);
        design = new TextButton("DESIGN", designStyle);
        backButton = new TextButton("BACK", upgradesStyle);
        stage = new Stage(viewport, sb);
        Label.LabelStyle font = new Label.LabelStyle(new Label.LabelStyle(dfont,  Color.WHITE));
        Table table = new Table();
        table.center();
        table.setFillParent(true);

        Label titleLabel = new Label("STORE", font);

        table.add(backButton).padTop(20).maxSize(40).padRight(5);
        table.add(titleLabel).padTop(20).maxSize(30).padRight(45);
        table.row();
        table.add(upgrades).expandX().minWidth(170).padLeft(120).padTop(-10).maxHeight(50);
        table.row();
        table.add(design).minWidth(170).padLeft(120).maxHeight(50).padTop(-10);

        stage.addActor(table);

        Gdx.input.setInputProcessor(stage);

        backButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                game.changeStateMenu();
                MainGame.manager.get("audio/button.wav", Sound.class).play();
                dispose();
                return true;
            }
        });

    }




    @Override
    public void dispose() {
        stage.dispose();
    }
}


