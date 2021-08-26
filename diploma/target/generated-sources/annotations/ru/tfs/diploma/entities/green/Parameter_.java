package ru.tfs.diploma.entities.green;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import ru.tfs.diploma.entities.red.ParameterType;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Parameter.class)
public abstract class Parameter_ extends ru.tfs.diploma.entities.BaseRecord_ {

	public static volatile SingularAttribute<Parameter, ParameterType> parameterType;
	public static volatile SingularAttribute<Parameter, Product> product;
	public static volatile SingularAttribute<Parameter, Long> id;
	public static volatile SingularAttribute<Parameter, String> value;

	public static final String PARAMETER_TYPE = "parameterType";
	public static final String PRODUCT = "product";
	public static final String ID = "id";
	public static final String VALUE = "value";

}

