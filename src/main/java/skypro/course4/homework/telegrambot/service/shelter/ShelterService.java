package skypro.course4.homework.telegrambot.service.shelter;

import skypro.course4.homework.telegrambot.entity.animal.person.report.shelter.AnimalShelter;

public interface ShelterService <T extends AnimalShelter> {



    String updateName(AnimalShelter t, String name);


    String updateAddress(AnimalShelter t, String address);


    String updateContact(AnimalShelter t, String contact);


    String updateDescription(AnimalShelter t, String description);

}