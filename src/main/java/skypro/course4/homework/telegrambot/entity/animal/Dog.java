package skypro.course4.homework.telegrambot.entity.animal;

import jakarta.persistence.*;

import skypro.course4.homework.telegrambot.entity.animal.person.report.shelter.DogShelter;
import lombok.NoArgsConstructor;
@Entity
@NoArgsConstructor
@DiscriminatorValue("DOG")
public class Dog extends Animal {

    @JoinColumn(name = "DOG_SHELTER_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private DogShelter dogShelter;

    public Dog(String name, Integer age, Boolean isHealthy, Boolean isVaccinated) {
        setName(name);
        setAge(age);
        setHealthy(isHealthy);
        setVaccinated(isVaccinated);
    }

    public DogShelter getDogShelter() {
        return dogShelter;
    }

    public void setDogShelter(DogShelter dogShelter) {
        this.dogShelter = dogShelter;
    }
}