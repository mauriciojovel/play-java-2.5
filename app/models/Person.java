package models;

import bussiness.PersonBO;
import play.data.validation.Constraints.Required;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "PERSON")
public class Person extends ValidationModel<Person, PersonBO> implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
	public Long id;

    @Required
    @Column(name = "NAME", nullable = false, length = 30)
    public String name;

    @Column(name = "LAST_NAME", nullable = false, length = 30)
    public String lastName;

    @OneToOne
    @JoinColumn(name = "USER_ID")
    public User user;

}
