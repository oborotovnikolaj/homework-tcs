package ru.tfs.diploma.entities.yellow;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Shipment.class)
public abstract class Shipment_ extends ru.tfs.diploma.entities.BaseRecord_ {

	public static volatile SingularAttribute<Shipment, String> address;
	public static volatile SingularAttribute<Shipment, Long> id;
	public static volatile SingularAttribute<Shipment, Integer> version;
	public static volatile SingularAttribute<Shipment, Order> order;

	public static final String ADDRESS = "address";
	public static final String ID = "id";
	public static final String VERSION = "version";
	public static final String ORDER = "order";

}

