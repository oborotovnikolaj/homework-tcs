package ru.tfs.diploma.entities.yellow;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import ru.tfs.diploma.entities.green.Product;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(OrderItem.class)
public abstract class OrderItem_ extends ru.tfs.diploma.entities.BaseRecord_ {

	public static volatile SingularAttribute<OrderItem, Product> product;
	public static volatile SingularAttribute<OrderItem, Integer> quantity;
	public static volatile SingularAttribute<OrderItem, Long> id;
	public static volatile SingularAttribute<OrderItem, Integer> version;
	public static volatile SingularAttribute<OrderItem, Order> order;

	public static final String PRODUCT = "product";
	public static final String QUANTITY = "quantity";
	public static final String ID = "id";
	public static final String VERSION = "version";
	public static final String ORDER = "order";

}

