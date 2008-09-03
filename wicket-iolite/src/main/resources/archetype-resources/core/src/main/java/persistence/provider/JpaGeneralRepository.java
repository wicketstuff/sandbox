package ${package}.persistence.provider;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Query;

import org.domdrides.entity.Entity;
import org.hibernate.type.EntityType;
import org.springframework.orm.jpa.support.JpaDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class JpaGeneralRepository extends JpaDaoSupport implements GeneralRepository {

	public JpaGeneralRepository() {
	}

	/* (non-Javadoc)
	 * @see ${package}.persistence.provider.GeneralRepository#getAllAsList(java.lang.Class)
	 */
	public <T> List<T> getAllAsList(Class<? extends Entity<String>> entityClass) {

		final String messageJpaql = "select x from " + entityClass.getName()
				+ " x";
		return (List<T>) getJpaTemplate().find(messageJpaql);
	}

	/* (non-Javadoc)
	 * @see ${package}.persistence.provider.GeneralRepository#add(org.domdrides.entity.Entity)
	 */
	public Entity add(Entity entity) {
		getJpaTemplate().persist(entity);
		return entity;
	}

	/* (non-Javadoc)
	 * @see ${package}.persistence.provider.GeneralRepository#contains(org.domdrides.entity.Entity)
	 */
	public boolean contains(
			Entity entity) {

		entity = getById(entity.getId(), (Class<? extends Entity<? extends Serializable>>) entity.getClass());
		return entity != null;
	}

	/* (non-Javadoc)
	 * @see ${package}.persistence.provider.GeneralRepository#getAll(java.lang.Class)
	 */
	public Set<EntityType> getAll(Class<? extends Entity<String>> entityClass) {
		final String jpaql = "select x from " + entityClass.getName() + " x";
		return queryForSet(jpaql);
	}

	@SuppressWarnings("unchecked")
	private HashSet<EntityType> queryForSet(String jpaql) {
		return new HashSet<EntityType>(getJpaTemplate().find(jpaql));
	}

	/* (non-Javadoc)
	 * @see ${package}.persistence.provider.GeneralRepository#getById(java.lang.Object, java.lang.Class)
	 */
	public <T> Entity getById(Object id, Class<? extends Entity<? extends Serializable>> clazz) {
		return getJpaTemplate().find(clazz, id);
	}

	/* (non-Javadoc)
	 * @see ${package}.persistence.provider.GeneralRepository#remove(org.hibernate.type.EntityType)
	 */
	public void remove(EntityType entity) {
		getJpaTemplate().remove(entity);
	}

	/* (non-Javadoc)
	 * @see ${package}.persistence.provider.GeneralRepository#update(org.hibernate.type.EntityType)
	 */
	public EntityType update(EntityType entity) {
		return getJpaTemplate().merge(entity);
	}

	/* (non-Javadoc)
	 * @see ${package}.persistence.provider.GeneralRepository#size(java.lang.Class)
	 */
	public int size(Class<? extends Entity<String>> entityClass) {
		List results = getJpaTemplate().find(
				"select count(*) from " + entityClass.getName());
		return ((Number) results.get(0)).intValue();
	}

	private <T> List<? extends Entity<? extends Serializable>> findEntity(int first, int count, String property,
			boolean ascending, String table) {

		String q = "select p from " + table + " p order by p.:property :sort";

		q = q.replace(":property", property);
		if (ascending) {
			q = q.replace(":sort", "asc");
		} else {
			q = q.replace(":sort", "desc");
		}
		Query query = getJpaTemplate().getEntityManager() .createQuery(q);
		query.setFirstResult(first);
		query.setMaxResults(count);
		
		return (List<? extends Entity<? extends Serializable>>) query.getResultList();
	}

}
