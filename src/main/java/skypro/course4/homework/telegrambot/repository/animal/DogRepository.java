package skypro.course4.homework.telegrambot.repository.animal;

import skypro.course4.homework.telegrambot.entity.animal.Dog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface DogRepository extends JpaRepository<Dog, Integer> {


    @Transactional
    @Modifying
    @Query(value = "UPDATE Dog SET name = :name, age = :age, isHealthy = :isHealthy, isVaccinated = :isVaccinated WHERE id = :id")
    Integer updateById(@Param("id") Integer id,
                       @Param("name") String name,
                       @Param("age") Integer age,
                       @Param("isHealthy") Boolean isHealthy,
                       @Param("isVaccinated") Boolean isVaccinated);


    @Query(value = "SELECT d FROM Dog d WHERE d.isHealthy = :isHealthy")
    List<Dog> findAllByHealth(@Param("isHealthy") Boolean isHealthy);


    @Query(value = "SELECT d FROM Dog d WHERE d.isVaccinated = :isVaccinated")
    List<Dog> findAllByVaccination(@Param("isVaccinated") Boolean isVaccinated);


    @Query(value = "SELECT d FROM Dog d WHERE d.isHealthy = :isHealthy AND d.isVaccinated = :isVaccinated")
    List<Dog> findAllByHealthAndVaccination(@Param("isHealthy") Boolean isHealthy, @Param("isVaccinated") Boolean isVaccinated);

    List<Dog> findByProbationIn(List<Integer> probationList);
}