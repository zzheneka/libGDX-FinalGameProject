package com.zhaniya.finalproject.commands;

import com.zhaniya.finalproject.model.pet.Pet;
import com.zhaniya.finalproject.model.items.Item;

public class UseItemCommand implements Command {
    private final Pet pet;
    private final Item item;

    public UseItemCommand(Pet pet, Item item) {
        this.pet = pet;
        this.item = item;
    }

    @Override
    public void execute() {
        if (pet == null || item == null) {
            System.out.println("❌ Нельзя использовать предмет: объект отсутствует.");
            return;
        }

        switch (item.getType()) {
            case "food":
                pet.setEnergy(pet.getEnergy() + 20);
                pet.setMood("Сыт");
                pet.updateLastFedTime();
                System.out.println(pet.getName() + " съел " + item.getName() + " и восстановил энергию!");
                break;
            case "toy":
                pet.setMood("Радуется новой игрушке!");
                pet.setEnergy(pet.getEnergy() + 5);
                System.out.println(pet.getName() + " поиграл с " + item.getName() + " и стал веселее!");
                break;
            case "medicine":
                pet.setHealth(pet.getHealth() + 25);
                pet.setMood("Чувствует себя лучше");
                System.out.println(pet.getName() + " принял " + item.getName() + " и здоровье улучшилось!");
                break;
            case "soap":
                pet.setMood("Чистый и довольный");
                pet.setHealth(pet.getHealth() + 5);
                System.out.println(pet.getName() + " помылся с " + item.getName() + " и стал чище!");
                break;
            default:
                System.out.println("⚠️ Этот предмет (" + item.getName() + ") не оказывает эффекта.");
        }
    }
}
