package ru.tfs.diploma.entities.grey;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Client.class)
public abstract class Client_ extends ru.tfs.diploma.entities.BaseRecord_ {

	public static volatile SingularAttribute<Client, String> phone;
	public static volatile SingularAttribute<Client, Long> id;
	public static volatile SingularAttribute<Client, String> region;

	public static final String PHONE = "phone";
	public static final String ID = "id";
	public static final String REGION = "region";

}

