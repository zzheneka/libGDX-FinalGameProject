package com.zhaniya.finalproject.model.pet;

public class PetBuilder {
    private String name;
    private PetType type = PetType.DOG;
    private int energy = 100;
    private int health = 100;
    private Class<? extends PetState> stateClass;

    public PetBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public PetBuilder setType(PetType type) {
        this.type = type;
        return this;
    }

    public PetBuilder setEnergy(int energy) {
        this.energy = energy;
        return this;
    }

    public PetBuilder setHealth(int health) {
        this.health = health;
        return this;
    }

    public PetBuilder setState(Class<? extends PetState> stateClass) {
        this.stateClass = stateClass;
        return this;
    }

    public Pet build() {
        Pet pet = new Pet(name, type, null);
        pet.setEnergy(energy);
        pet.setHealth(health);

        try {
            if (stateClass != null) {
                PetState state = stateClass.getConstructor(Pet.class).newInstance(pet);
                pet.setState(state);
            } else {
                pet.setState(new HappyState(pet));
            }
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при создании состояния для питомца: " + e.getMessage());
        }

        return pet;
    }
}
