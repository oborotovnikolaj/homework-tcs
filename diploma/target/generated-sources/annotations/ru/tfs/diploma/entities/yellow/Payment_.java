package ru.tfs.diploma.entities.yellow;

import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Payment.class)
public abstract class Payment_ extends ru.tfs.diploma.entities.BaseRecord_ {

	public static volatile SingularAttribute<Payment, String> cardPan;
	public static volatile SingularAttribute<Payment, Long> id;
	public static volatile SingularAttribute<Payment, Integer> version;
	public static volatile SingularAttribute<Payment, BigDecimal> value;
	public static volatile SingularAttribute<Payment, Order> order;

	public static final String CARD_PAN = "cardPan";
	public static final String ID = "id";
	public static final String VERSION = "version";
	public static final String VALUE = "value";
	public static final String ORDER = "order";

}

