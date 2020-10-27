package com.simple.todo.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Java-doc и у полей тоже
 */
@Entity
// не зватает указания в какой таблице лежит
public class Deal {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // это не нужно идентификатор в контрукторе выдавать
    private long id; // TODO: тип должен быть UUID

    private String name;

    private String description;

    private short priority; //from 1 to 5  // TODO: приоритер сделать перечислением

    private boolean isDone;

    private Date dateCreation;

    private Date dateEdition;

    // TODO: у всех поелй должно быть принудительно указано в какой колонке лежат данные

    protected Deal() {
    }

    public Deal(String name, String description, short priority) {
        this.name = name;
        this.description = description;
        this.priority = priority;
        this.isDone = false;
        Date date = new Date();
        this.dateCreation = date;
        this.dateEdition = date;
    }

    @Override
    public String toString() {
        return String.format(
                "Deal[id=%d, name='%s', description='%s']",
                id, name, description);
    }

    // TODO: все геттеры / сеттеры легко заменяются на аннотации @Getter / @Setter
    public Date getDateCreation() {
        return dateCreation;
    }

    public Date getDateEdition() {
        return dateEdition;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isDone() {
        return isDone;
    }

    public short getPriority() {
        return priority;
    }

    public long getId() {
        return id;
    }

    //Нужен ли в сущности сеттер? В примере не было сеттеров https://spring.io/guides/gs/accessing-data-jpa/
    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDone() {
        isDone = true;
    }

    public void setUnDone() {
        isDone = false;
    }

    public void setPriority(short priority) {
        this.priority = priority;
    }
}
