package ru.tfs.diploma.entities;

import java.time.Instant;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(BaseRecord.class)
public abstract class BaseRecord_ {

	public static volatile SingularAttribute<BaseRecord, Instant> createdDate;
	public static volatile SingularAttribute<BaseRecord, Instant> lastModifiedDate;

	public static final String CREATED_DATE = "createdDate";
	public static final String LAST_MODIFIED_DATE = "lastModifiedDate";

}

