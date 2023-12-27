package skypro.course4.homework.telegrambot.repository.person;

import skypro.course4.homework.telegrambot.entity.animal.person.report.shelter.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
}