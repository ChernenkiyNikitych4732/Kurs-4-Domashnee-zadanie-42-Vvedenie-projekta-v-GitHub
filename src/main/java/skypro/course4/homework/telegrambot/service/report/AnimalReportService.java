package skypro.course4.homework.telegrambot.service.report;

import skypro.course4.homework.telegrambot.entity.animal.Animal;
import skypro.course4.homework.telegrambot.entity.animal.person.Customer;
import skypro.course4.homework.telegrambot.entity.animal.person.report.AnimalReport;

import java.time.LocalDateTime;
import java.util.List;


public interface AnimalReportService {


    void uploadAnimalReport(
            String photo
            , String diet
            , String wellBeing
            , String behavior
            , LocalDateTime dateCreate
            , Animal animal
            , Customer customer);



    AnimalReport findById(Integer id);


    AnimalReport save(AnimalReport report);


    void remove(Integer id);


    List<AnimalReport> getAll();
}