package org.nikisurance.util;

import java.util.concurrent.atomic.AtomicLong;

public class IDGenerator {
    private static final String CLAIM_PREFIX = "f-";
    private static final String CUSTOMER_PREFIX = "c-";
    private static final int CLAIM_ID_LENGTH = 10;
    private static final int CUSTOMER_ID_LENGTH = 7;

    private static final AtomicLong claimSequence = new AtomicLong();
    private static final AtomicLong customerSequence = new AtomicLong();

    /**
     * This method is used to ensure that the generated number is of the desired length.
     * @param number: the sequence number
     * @param length: length of the spared spaces that need to be padded
     * @return padded number
     */
    private static String padNumber(long number, int length) {
        String numberStr = String.valueOf(number);
        return "0".repeat(Math.max(0, length - numberStr.length())) +
                numberStr;
    }

    public static String generateClaimID() {
        long sequenceNumber = claimSequence.incrementAndGet();
        return CLAIM_PREFIX + padNumber(sequenceNumber, CLAIM_ID_LENGTH - CLAIM_PREFIX.length());
    }

    public static String generateCustomerID() {
        long sequenceNumber = customerSequence.incrementAndGet();
        return CUSTOMER_PREFIX + padNumber(sequenceNumber, CUSTOMER_ID_LENGTH - CUSTOMER_PREFIX.length());
    }
}
