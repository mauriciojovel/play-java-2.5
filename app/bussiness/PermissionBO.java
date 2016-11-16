package bussiness;

import models.Permission;
import models.Permission_;
import models.Role;
import models.Role_;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created by mjovel on 11-21-16.
 */
public class PermissionBO extends BasicBO<Permission, Long> {

    public List<Permission> findByRole(String roleName) {
        CriteriaBuilder cb = em().getCriteriaBuilder();
        CriteriaQuery<Permission> query = cb.createQuery(Permission.class);
        Root<Permission> from = query.from(Permission.class);
        Join<Permission, Role> roleJoin = from.join(Permission_.roles);
        query.where(cb.equal(roleJoin.get(Role_.name), roleName));
        return em().createQuery(query).getResultList();
    }
}
