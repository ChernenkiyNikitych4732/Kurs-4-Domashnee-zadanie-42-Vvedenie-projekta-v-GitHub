package skypro.course4.homework.telegrambot.entity.animal.person.report.shelter;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import skypro.course4.homework.telegrambot.entity.animal.Dog;


import java.util.List;
@Entity
@DiscriminatorValue("DSH")
public class DogShelter extends AnimalShelter {

    @OneToMany(mappedBy = "dogShelter")
    private List<Dog> dogs;

    public List<Dog> getDogs() {
        return dogs;
    }

    public void setDogs(List<Dog> dogs) {
        this.dogs = dogs;
    }
}