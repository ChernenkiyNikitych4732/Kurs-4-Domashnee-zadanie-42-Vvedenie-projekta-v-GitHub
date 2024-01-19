package skypro.course4.homework.telegrambot.repository;

import skypro.course4.homework.telegrambot.entity.animal.person.report.shelter.DogShelter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DogShelterRepository extends JpaRepository<DogShelter, Integer> {
}