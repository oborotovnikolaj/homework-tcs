package ru.tfs.diploma.entities.green;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import ru.tfs.diploma.entities.red.Language;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Info.class)
public abstract class Info_ extends ru.tfs.diploma.entities.BaseRecord_ {

	public static volatile SingularAttribute<Info, Product> product;
	public static volatile SingularAttribute<Info, String> description;
	public static volatile SingularAttribute<Info, Language> language;
	public static volatile SingularAttribute<Info, Long> id;
	public static volatile SingularAttribute<Info, String> tittle;

	public static final String PRODUCT = "product";
	public static final String DESCRIPTION = "description";
	public static final String LANGUAGE = "language";
	public static final String ID = "id";
	public static final String TITTLE = "tittle";

}

