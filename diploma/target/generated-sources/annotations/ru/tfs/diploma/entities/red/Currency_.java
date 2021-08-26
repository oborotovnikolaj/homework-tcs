package ru.tfs.diploma.entities.red;

import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Currency.class)
public abstract class Currency_ extends ru.tfs.diploma.entities.BaseRecord_ {

	public static volatile SingularAttribute<Currency, BigDecimal> multiplier;
	public static volatile SingularAttribute<Currency, String> name;
	public static volatile SingularAttribute<Currency, Long> id;

	public static final String MULTIPLIER = "multiplier";
	public static final String NAME = "name";
	public static final String ID = "id";

}

