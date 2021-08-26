package ru.tfs.diploma.entities.yellow;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import ru.tfs.diploma.entities.grey.Client;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Order.class)
public abstract class Order_ extends ru.tfs.diploma.entities.BaseRecord_ {

	public static volatile SingularAttribute<Order, Client> client;
	public static volatile SingularAttribute<Order, Long> id;
	public static volatile SingularAttribute<Order, Integer> version;

	public static final String CLIENT = "client";
	public static final String ID = "id";
	public static final String VERSION = "version";

}

