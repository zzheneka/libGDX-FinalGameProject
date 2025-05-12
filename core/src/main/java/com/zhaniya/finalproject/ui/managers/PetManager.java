package com.zhaniya.finalproject.ui.managers;

import com.zhaniya.finalproject.model.pet.Pet;
import com.zhaniya.finalproject.model.pet.HappyState;
import com.zhaniya.finalproject.model.pet.TiredState;
import com.zhaniya.finalproject.model.pet.SleepingState;
import com.zhaniya.finalproject.model.pet.HungryState;
import com.zhaniya.finalproject.model.pet.SickState;

public class PetManager {
    private final Pet pet;

    public PetManager(Pet pet) {
        this.pet = pet;
    }

    // Метод для кормления питомца
    public void feedPet(int amount) {
        if (pet.getEnergy() < 100) {
            pet.feed(amount);
            pet.setMood("Счастлив");
            pet.setState(new HappyState(pet));  // Передаем объект питомца
            System.out.println(pet.getName() + " поел! Энергия: " + pet.getEnergy());
        } else {
            System.out.println(pet.getName() + " не хочет есть, он полон энергии!");
        }
    }

    // Метод для игры с питомцем
    public void playWithPet() {
        if (pet.getEnergy() > 20) {
            pet.play();
            pet.setMood("Веселый");
            pet.setState(new HappyState(pet));  // Передаем объект питомца
            System.out.println(pet.getName() + " поиграл! Настроение: " + pet.getMood());
        } else {
            System.out.println(pet.getName() + " устал и не хочет играть!");
            pet.setState(new TiredState(pet));  // Передаем объект питомца
        }
    }

    // Метод для сна питомца
    public void putPetToSleep() {
        if (pet.getEnergy() < 50) {
            pet.sleep();
            pet.setMood("Отдыхающий");
            pet.setState(new SleepingState(pet));  // Передаем объект питомца
            System.out.println(pet.getName() + " уснул! Настроение: " + pet.getMood());
        } else {
            System.out.println(pet.getName() + " не хочет спать!");
        }
    }

    // Метод для обновления состояния питомца
    public void update(float delta) {
        pet.handleState();
        if (pet.getEnergy() < 20) {
            pet.setState(new TiredState(pet));  // Уставший
        } else if (pet.getEnergy() < 50) {
            pet.setState(new HungryState(pet));  // Голодный
        } else if (pet.getEnergy() < 80) {
            pet.setState(new SickState(pet));    // Болен
        } else {
            pet.setState(new HappyState(pet));   // Счастлив
        }
    }

    // Получение статуса питомца
    public String getStatus() {
        return "Питомец: " + pet.getName() + "\nЭнергия: " + pet.getEnergy() + "\nНастроение: " + pet.getMood();
    }
}
