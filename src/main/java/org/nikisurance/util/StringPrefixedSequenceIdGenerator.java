//package org.nikisurance.util;
//
//import org.hibernate.HibernateException;
//import org.hibernate.MappingException;
//import org.hibernate.boot.model.relational.Sequence;
//import org.hibernate.engine.spi.SharedSessionContractImplementor;
//import org.hibernate.id.IdentifierGenerator;
//import org.hibernate.id.enhanced.SequenceStyleGenerator;
//import org.hibernate.jpa.internal.util.ConfigurationHelper;
//import org.hibernate.service.ServiceRegistry;
//import org.hibernate.type.Type;
//import org.hibernate.type.spi.TypeConfiguration;
//
//import java.io.Serializable;
//import java.util.Properties;
//
//import static javax.sound.midi.MidiSystem.getSequence;
//import static org.hibernate.jpa.internal.util.ConfigurationHelper.*;
//
//public class StringPrefixedSequenceIdGenerator implements IdentifierGenerator {
//    public static final String DEFAULT_VALUE_PREFIX = "c-";
//    private String valuePrefix = DEFAULT_VALUE_PREFIX;
//
//    public static final String NUMBER_FORMAT_PARAMETER = "number_format";
//    public static final String NUMBER_FORMAT_DEFAULT = "%07d";
//    private String numberFormat;
//
//    @Override
//    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
//        Sequence sequence = getSequence(session);
//        String sequenceId = sequence.getNextVal(session); // Assuming getNextVal exists
//        return valuePrefix + sequenceId;
//    }
//
//    @Override
//    public void configure(Type type, Properties params, ServiceRegistry serviceRegistry) throws MappingException {
//        super.configure(new TypeConfiguration().getBasicTypeRegistry().getRegisteredType(Long.class), params, serviceRegistry);
//    }
//
//    @Override
//    public String get
//}
