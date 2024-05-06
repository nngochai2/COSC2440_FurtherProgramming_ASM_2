package org.nikisurance.util;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;

public class CardIdSequenceGenerator implements IdentifierGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        try {
            // Generate the next value from the sequence
            Long id = (Long) session.getSession().createNativeQuery("SELECT nextval ('card_id_sequence')").uniqueResult();
            // Make the sequence start from 1000000000
            return id + 1000000000;
        } catch (Exception e) {
            throw new HibernateException(e);
        }
    }
}
