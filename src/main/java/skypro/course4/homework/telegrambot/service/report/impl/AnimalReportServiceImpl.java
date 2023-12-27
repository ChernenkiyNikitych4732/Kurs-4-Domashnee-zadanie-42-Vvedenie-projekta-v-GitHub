package skypro.course4.homework.telegrambot.service.report.impl;

import skypro.course4.homework.telegrambot.entity.animal.Animal;
import skypro.course4.homework.telegrambot.entity.animal.person.Customer;
import skypro.course4.homework.telegrambot.entity.animal.person.report.AnimalReport;
import skypro.course4.homework.telegrambot.repository.AnimalReportRepository;
import skypro.course4.homework.telegrambot.service.report.AnimalReportService;
import org.springframework.stereotype.Service;
import skypro.course4.homework.telegrambot.exception.ReportException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
public class AnimalReportServiceImpl implements AnimalReportService {

    private final AnimalReportRepository animalReportRepository;

    public AnimalReportServiceImpl(AnimalReportRepository animalReportRepository) {
        this.animalReportRepository = animalReportRepository;
    }

    @Override
    public void uploadAnimalReport(
            String photo
            , String diet
            , String wellBeing
            , String behavior
            , LocalDateTime dateCreate
            , Animal animal
            , Customer customer) {

        AnimalReport animalReport = new AnimalReport();
        animalReport.setPhoto(photo);
        animalReport.setDiet(diet);
        animalReport.setWellBeing(wellBeing);
        animalReport.setBehavior(behavior);
        animalReport.setDateCreate(dateCreate);
        animalReport.setAnimal(animal);
        animalReport.setCustomer(customer);
        this.animalReportRepository.save(animalReport);
    }

    @Override
    public AnimalReport findById(Integer id) {
        return this.animalReportRepository
                .findById(id).orElseThrow(ReportException::new);
    }

    @Override
    public AnimalReport save(AnimalReport report) {
        if (report != null) {
            this.animalReportRepository.save(report);
        }
        return report;
    }

    @Override
    public void remove(Integer id) {
        Optional<AnimalReport> byId = animalReportRepository.findById(id);
        if (byId.isPresent()) {
            this.animalReportRepository.deleteById(id);
        }
    }

    @Override
    public List<AnimalReport> getAll() {
        return this.animalReportRepository.findAll();
    }
}