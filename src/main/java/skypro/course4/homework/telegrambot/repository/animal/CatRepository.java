package skypro.course4.homework.telegrambot.repository.animal;

import skypro.course4.homework.telegrambot.entity.animal.Cat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
public interface CatRepository extends JpaRepository<Cat, Integer> {


    @Transactional
    @Modifying
    @Query(value = "UPDATE Cat SET name = :name, age = :age, isHealthy = :isHealthy, isVaccinated = :isVaccinated WHERE id = :id")
    Integer updateById(@Param("id") Integer id,
                       @Param("name") String name,
                       @Param("age") Integer age,
                       @Param("isHealthy") Boolean isHealthy,
                       @Param("isVaccinated") Boolean isVaccinated);


    @Query(value = "SELECT c FROM Cat c WHERE c.isHealthy = :isHealthy")
    List<Cat> findAllByHealth(@Param("isHealthy") Boolean isHealthy);


    @Query(value = "SELECT c FROM Cat c WHERE c.isVaccinated = :isVaccinated")
    List<Cat> findAllByVaccination(@Param("isVaccinated") Boolean isVaccinated);


    @Query(value = "SELECT c FROM Cat c WHERE c.isHealthy = :isHealthy AND c.isVaccinated = :isVaccinated")
    List<Cat> findAllByHealthAndVaccination(@Param("isHealthy") Boolean isHealthy, @Param("isVaccinated") Boolean isVaccinated);


    List<Cat> findByProbationIn(List<Integer> probationList);
}