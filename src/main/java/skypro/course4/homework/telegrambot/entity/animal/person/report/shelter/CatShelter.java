package skypro.course4.homework.telegrambot.entity.animal.person.report.shelter;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import skypro.course4.homework.telegrambot.entity.animal.Cat;

import java.util.List;

@Entity

public class CatShelter extends AnimalShelter {

    @OneToMany(mappedBy = "catShelter")
    private List<Cat> cats;

    public List<Cat> getCats() {
        return cats;
    }

    public void setCats(List<Cat> cats) {
        this.cats = cats;
    }
}