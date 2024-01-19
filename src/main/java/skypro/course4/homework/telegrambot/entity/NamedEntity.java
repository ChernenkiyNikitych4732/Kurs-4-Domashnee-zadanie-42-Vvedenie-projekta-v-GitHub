package skypro.course4.homework.telegrambot.entity;

import skypro.course4.homework.telegrambot.exception.ValidationException;
import skypro.course4.homework.telegrambot.service.ValidationRegularService;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public class NamedEntity extends BaseEntity {

    @Column(name = "name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (!ValidationRegularService.validateBaseStr(name)) {
            throw new ValidationException(name);
        }
        this.name = name;
    }

    @Override
    public String toString() {
        return getName();
    }
}