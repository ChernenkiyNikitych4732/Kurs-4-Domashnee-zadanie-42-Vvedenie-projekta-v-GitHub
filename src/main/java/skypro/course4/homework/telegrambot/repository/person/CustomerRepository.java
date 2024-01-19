package skypro.course4.homework.telegrambot.repository.person;

import skypro.course4.homework.telegrambot.entity.animal.person.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface CustomerRepository extends JpaRepository<Customer, Integer> {


    Boolean existsByChatId(Long chatId);


    Optional<Customer> findByChatId(Long chatId);
}