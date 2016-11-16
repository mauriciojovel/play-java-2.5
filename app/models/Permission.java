package models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by mjovel on 11-21-16.
 */
@Entity
@Table(name = "PERMISSION", uniqueConstraints = {
        @UniqueConstraint(name = "unq_permission_name", columnNames = "NAME")
})
public class Permission implements be.objectify.deadbolt.java.models.Permission, Serializable {
    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public Long id;

    @Column(name = "NAME", nullable = false, length = 150)
    public String name;

    @Column(name = "DESCRIPTION", nullable = false)
    public String description;

    @ManyToMany(mappedBy = "permissions")
    @JsonIgnore
    public List<Role> roles;

    @ManyToMany(mappedBy = "customPermissions")
    @JsonIgnore
    public List<User> users;

    @Override
    public String getValue() {
        return name;
    }
}
