package models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * Created by mjovel on 11-21-16.
 */
@Entity
@Table(name = "ROLE", uniqueConstraints = {
        @UniqueConstraint(name = "unq_role_name", columnNames = "name")
})
public class Role implements be.objectify.deadbolt.java.models.Role, Serializable {

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public Long id;

    @Column(name = "NAME", nullable = false, length = 30)
    public String name;

    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    public List<User> users;

    @ManyToMany
    @JoinTable(
            name="ROLE_PERMISSION",
            joinColumns=@JoinColumn(name="ROLE_ID", referencedColumnName="ID"),
            inverseJoinColumns=@JoinColumn(name="PERMISSION_ID", referencedColumnName="ID"))
    @JsonIgnore
    // This column is a set because we need get the permission from the user.
    // another solution is use order column but is a many to many relationship
    // See http://stackoverflow.com/a/17567590 to more information.
    public Set<Permission> permissions;

    @Override
    public String getName() {
        return name;
    }
}
