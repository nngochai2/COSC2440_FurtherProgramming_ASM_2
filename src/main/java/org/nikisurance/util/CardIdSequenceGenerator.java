package org.nikisurance.util;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;

/**
 * @author Team 15
 * This class is responsible for generating the card numbers of the insurance cards.
 * These numbers represent the ID of the cards
 */

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
