package com.zhaniya.finalproject.commands;

import com.zhaniya.finalproject.model.pet.*;

import java.util.Random;

public class TalkCommand implements Command {
    private final Pet pet;
    private final Random random = new Random();

    public TalkCommand(Pet pet) {
        this.pet = pet;
    }

    @Override
    public void execute() {
        if (pet == null) {
            System.out.println("Питомец не найден.");
            return;
        }

        String mood = pet.getMood();
        String phrase;

        if (pet.getState() instanceof SleepingState) {
            System.out.println(pet.getName() + " спит... не будем мешать. 😴");
            return;
        }

        if (pet.getState() instanceof SickState) {
            phrase = randomFrom(new String[]{
                "Мне плохо... 😢",
                "Можно обнимашки?..",
                "Я не хочу ничего делать...",
                "Я немного болею..."
            });
        } else if (pet.getState() instanceof AngryState) {
            phrase = randomFrom(new String[]{
                "Я зол! 😠",
                "Оставь меня в покое!",
                "Ты меня обидел...",
                "Я не в настроении говорить."
            });
        } else if (pet.getState() instanceof TiredState) {
            phrase = randomFrom(new String[]{
                "Я устал... 😴",
                "Можно я отдохну?",
                "Ты классный, но я хочу поспать...",
                "Так хочется в кроватку..."
            });
        } else if (pet.getState() instanceof HungryState) {
            phrase = randomFrom(new String[]{
                "Я голоден... 😟",
                "Покорми меня, пожалуйста!",
                "Живот урчит...",
                "Когда обед?.."
            });
        } else {
            // Happy or neutral
            phrase = randomFrom(new String[]{
                "Я рад тебя видеть! 🐾",
                "Ты сегодня прекрасно выглядишь!",
                "Обнимашки? 😽",
                "Можешь погладить меня? 😊",
                "Я тебя очень люблю!",
                "С тобой так уютно!",
                "Я был хорошим питомцем сегодня?",
                "Давай поиграем!",
                "Ты мой лучший друг!"
            });
        }

        System.out.println(pet.getName() + " говорит: \"" + phrase + "\"");

        // 🎨 можно отобразить фразу в UI
        // GameUI.showSpeechBubble(pet.getName(), phrase);
    }

    private String randomFrom(String[] options) {
        return options[random.nextInt(options.length)];
    }
}
