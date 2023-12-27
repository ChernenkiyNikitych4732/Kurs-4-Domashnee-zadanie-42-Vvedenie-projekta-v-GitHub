package skypro.course4.homework.telegrambot.entity.animal.person.report.shelter;

import jakarta.persistence.*;

import skypro.course4.homework.telegrambot.entity.animal.person.Person;
import skypro.course4.homework.telegrambot.entity.animal.person.report.shelter.AnimalShelter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@DiscriminatorValue("EMPLOYEE")
public class Employee extends Person {

    @JoinTable(name = "ANIMAL_SHELTER_EMPLOYEE_LINK",
            joinColumns = @JoinColumn(name = "EMPLOYEE_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "ANIMAL_SHELTER_ID", referencedColumnName = "ID"))
    @ManyToMany
    private List<AnimalShelter> animalShelters;

    public Employee(String firstName, String lastName) {
        setFirstName(firstName);
        setLastName(lastName);
    }

    public List<AnimalShelter> getAnimalShelters() {
        return animalShelters;
    }

    public void setAnimalShelters(List<AnimalShelter> animalShelters) {
        this.animalShelters = animalShelters;
    }
}