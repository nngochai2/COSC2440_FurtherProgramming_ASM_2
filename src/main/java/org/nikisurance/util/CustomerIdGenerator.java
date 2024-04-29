package org.nikisurance.util;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.util.concurrent.atomic.AtomicLong;

public class CustomerIdGenerator implements IdentifierGenerator {

    @Override
    public Object generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        String prefix = "c-";
        int customer_id_length = 7;
        AtomicLong customerSequence = new AtomicLong();
        long sequenceNumber = customerSequence.incrementAndGet();
        return prefix + padNumber(sequenceNumber, customer_id_length - prefix.length());
    }

    /**
     * This method is used to ensure that the generated number is of the desired length.
     * @param number the sequence number
     * @param length length of the spared spaces that need to be padded
     * @return padded number
     */
    private static String padNumber(long number, int length) {
        String numberStr = String.valueOf(number);
        return "0".repeat(Math.max(0, length - numberStr.length())) +
                numberStr;
    }
}
