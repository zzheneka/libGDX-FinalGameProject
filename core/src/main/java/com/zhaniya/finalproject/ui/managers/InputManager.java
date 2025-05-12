package com.zhaniya.finalproject.ui.managers;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.Input;

public class InputManager extends InputAdapter {
    private final PetManager petManager;
    private final Stage stage;

    public InputManager(PetManager petManager, Stage stage) {
        this.petManager = petManager;
        this.stage = stage;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        // Передаем событие нажатия на сцену для обработки кнопками
        if (stage.touchDown(screenX, screenY, pointer, button)) {
            return true;
        }

        // Если нажатие не обработано сценой, выполняем собственную логику
        if (button == Input.Buttons.LEFT) {
            System.out.println("Левый клик вне UI");
            petManager.playWithPet();
        }
        return super.touchDown(screenX, screenY, pointer, button);
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.SPACE) {
            petManager.feedPet(10);  // Кормим питомца при нажатии пробела
            System.out.println("Питомец покормлен!");
            return true;
        }
        return super.keyDown(keycode);
    }
}
