package skypro.course4.homework.telegrambot.entity.animal;

import jakarta.persistence.*;

import skypro.course4.homework.telegrambot.entity.animal.person.report.shelter.CatShelter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@DiscriminatorValue("CAT")
public class Cat extends Animal {

    @JoinColumn(name = "CAT_SHELTER_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private CatShelter catShelter;

    public Cat(String name, Integer age, Boolean isHealthy, Boolean isVaccinated) {
        setName(name);
        setAge(age);
        setHealthy(isHealthy);
        setVaccinated(isVaccinated);
    }

    public CatShelter getCatShelter() {
        return catShelter;
    }

    public void setCatShelter(CatShelter catShelter) {
        this.catShelter = catShelter;
    }
}