package skypro.course4.homework.telegrambot.repository;

import skypro.course4.homework.telegrambot.entity.animal.person.report.shelter.CatShelter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatShelterRepository extends JpaRepository<CatShelter, Integer> {
}