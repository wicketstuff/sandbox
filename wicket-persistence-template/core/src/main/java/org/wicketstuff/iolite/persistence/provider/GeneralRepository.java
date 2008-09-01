package org.wicketstuff.iolite.persistence.provider;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import org.domdrides.entity.Entity;
import org.hibernate.type.EntityType;

public interface GeneralRepository {

	public abstract <T> List<T> getAllAsList(
			Class<? extends Entity<String>> entityClass);

	public abstract Entity<? extends Serializable> add(Entity<? extends Serializable> entity);

	public abstract boolean contains(
			Entity<? extends Entity<? extends Serializable>> entity);

	public abstract Set<EntityType> getAll(
			Class<? extends Entity<String>> entityClass);

	public abstract <T> Entity getById(Object id, Class<? extends Entity> clazz);

	public abstract void remove(EntityType entity);

	public abstract EntityType update(EntityType entity);

	public abstract int size(Class<? extends Entity<String>> entityClass);

}