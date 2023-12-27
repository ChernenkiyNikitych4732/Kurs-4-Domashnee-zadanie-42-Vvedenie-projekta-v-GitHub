package skypro.course4.homework.telegrambot.service.animal;

import skypro.course4.homework.telegrambot.entity.animal.Cat;
import skypro.course4.homework.telegrambot.entity.animal.person.Customer;
import skypro.course4.homework.telegrambot.repository.animal.CatRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CatService {

    private final CatRepository catRepository;

    public CatService(CatRepository catRepository) {
        this.catRepository = catRepository;
    }


    public boolean save(Cat cat) {
        if (!(cat == null
                || (cat.getName() == null || cat.getName().isBlank())
                || (cat.getAge() == null || cat.getAge() < 0)
                || cat.getHealthy() == null
                || cat.getVaccinated() == null)) {
            catRepository.save(cat);
            return true;
        }
        return false;
    }


    public Optional<Cat> findById(int id) {
        return catRepository.findById(id);
    }


    public List<Cat> findAllByHealth(Boolean isHealthy) {
        return catRepository.findAllByHealth(isHealthy);
    }


    public List<Cat> findAllByVaccinate(Boolean isVaccinated) {
        return catRepository.findAllByVaccination(isVaccinated);
    }


    public List<Cat> findAllByHealthAndVaccination(Boolean isHealthy, Boolean isVaccinated) {
        return catRepository.findAllByHealthAndVaccination(isHealthy, isVaccinated);
    }


    public List<Cat> findAll() {
        return catRepository.findAll();
    }


    public Integer updateById(Integer id, String name, Integer age, Boolean isHealthy, Boolean isVaccinated) {
        Optional<Cat> cat = findById(id);
        if (cat.isEmpty()
                || (name == null || name.isBlank())
                || (age == null || age < 0)
                || isHealthy == null
                || isVaccinated == null) {
            return 0;
        } else {
            catRepository.updateById(id, name, age, isHealthy, isVaccinated);
            return 1;
        }
    }


    public Boolean deleteById(int id) {
        Optional<Cat> findCatById = findById(id);
        if (findCatById.isEmpty()) {
            return false;
        }
        catRepository.deleteById(id);
        return true;
    }
}