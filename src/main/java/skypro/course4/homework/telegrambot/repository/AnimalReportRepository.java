package skypro.course4.homework.telegrambot.repository;

import skypro.course4.homework.telegrambot.entity.animal.Animal;
import skypro.course4.homework.telegrambot.entity.animal.person.report.AnimalReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface AnimalReportRepository extends JpaRepository<AnimalReport, Integer> {


    @Transactional
    AnimalReport findFirstByAnimalOrderByDateCreateDesc(Animal animal);
}