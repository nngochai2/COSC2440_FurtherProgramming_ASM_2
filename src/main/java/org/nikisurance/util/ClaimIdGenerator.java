package org.nikisurance.util;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.enhanced.SequenceStyleGenerator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;
import org.hibernate.type.spi.TypeConfiguration;

import java.io.Serializable;
import java.util.Properties;

public class ClaimIdGenerator extends SequenceStyleGenerator {
    public static final String VALUE_PREFIX_PARAMETER = "valuePrefix";
    public static final String VALUE_PREFIX_DEFAULT = "f-";
    private String valuePrefix;

    public static final String NUMBER_FORMAT_PARAMETER = "numberFormat";
    public static final String NUMBER_FORMAT_DEFAULT = "%010d";
    private String numberFormat;

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        return valuePrefix
                + String.format(numberFormat, super.generate(session, object));
    }

    @Override
    public void configure(Type type, Properties params, ServiceRegistry serviceRegistry) throws MappingException {
        super.configure(new TypeConfiguration().getBasicTypeRegistry().getRegisteredType(Long.class), params, serviceRegistry);
        valuePrefix = params.getProperty(VALUE_PREFIX_PARAMETER, VALUE_PREFIX_DEFAULT);
        numberFormat = params.getProperty(NUMBER_FORMAT_PARAMETER, NUMBER_FORMAT_DEFAULT);
    }
}
