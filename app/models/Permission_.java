package models;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Permission.class)
public abstract class Permission_ {

	public static volatile ListAttribute<Permission, Role> roles;
	public static volatile SingularAttribute<Permission, String> name;
	public static volatile SingularAttribute<Permission, String> description;
	public static volatile SingularAttribute<Permission, Long> id;
	public static volatile ListAttribute<Permission, User> users;

}

