package models;

import be.objectify.deadbolt.java.models.Permission;
import be.objectify.deadbolt.java.models.Role;
import be.objectify.deadbolt.java.models.Subject;
import com.fasterxml.jackson.annotation.JsonIgnore;
import constraints.ValidationGroups;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by mjovel on 11-21-16.
 */
@Entity
@Table(name = "USER", uniqueConstraints = {
        @UniqueConstraint(name = "unq_user_email", columnNames = "EMAIL")
})
public class User implements Subject, Serializable {

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public Long id;

    @Column(name = "EMAIL", nullable = false)
    @Constraints.Required(groups = ValidationGroups.UserLogin.class)
    public String email;

    @Column(name = "PASSWORD", nullable = false)
    @Constraints.Required(groups = ValidationGroups.UserLogin.class)
    @JsonIgnore
    public String password;

    @Column(name = "TOKEN")
    @JsonIgnore
    public String token;

    @OneToOne(mappedBy = "user")
    public Person person;

    @ManyToMany
    @JoinTable(
            name="USER_ROLE",
            joinColumns=@JoinColumn(name="USER_ID", referencedColumnName="ID"),
            inverseJoinColumns=@JoinColumn(name="ROLE_ID", referencedColumnName="ID"))
    @JsonIgnore
    public List<models.Role> roles;

    @ManyToMany
    @JoinTable(
            name="USER_PERMISSION",
            joinColumns=@JoinColumn(name="USER_ID", referencedColumnName="ID"),
            inverseJoinColumns=@JoinColumn(name="PERMISSION_ID", referencedColumnName="ID"))
    @JsonIgnore
    public Set<models.Permission> customPermissions;

    @Override
    public List<? extends Role> getRoles() {
        return roles;
    }

    @Override
    @Transient
    public List<? extends Permission> getPermissions() {
        HashSet<models.Permission> permissions = new HashSet<>();
        if(roles != null) {
            roles.forEach(r -> {
                permissions.addAll(r.permissions);
            });
        }
        if(customPermissions != null) {
            permissions.addAll(customPermissions);
        }
        return new ArrayList<>(permissions);
    }

    @Override
    @Transient
    public String getIdentifier() {
        return email;
    }
}
