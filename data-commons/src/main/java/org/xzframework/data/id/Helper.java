package org.xzframework.data.id;

import java.net.InetAddress;

public final class Helper {
    private Helper() {
    }

    // IP ADDRESS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    private static final byte[] ADDRESS_BYTES;

    static {
        byte[] address;
        try {
            address = InetAddress.getLocalHost().getAddress();
        }
        catch ( Exception e ) {
            address = new byte[4];
        }
        ADDRESS_BYTES = address;
    }

    public static byte[] getAddressBytes() {
        return ADDRESS_BYTES;
    }


    // JVM identifier ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    private static final byte[] JVM_IDENTIFIER_BYTES;
    private static final int JVM_IDENTIFIER_INT;

    static {
        JVM_IDENTIFIER_INT = (int) ( System.currentTimeMillis() >>> 8 );
        JVM_IDENTIFIER_BYTES = BytesHelper.fromInt( JVM_IDENTIFIER_INT );
        format(JVM_IDENTIFIER_INT);
    }

    public static byte[] getJvmIdentifierBytes() {
        return JVM_IDENTIFIER_BYTES;
    }


    // counter ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    private static short counter = (short) 0;

    /**
     * Unique in a millisecond for this JVM instance
     * (unless there are more than {@value Short#MAX_VALUE}
     * instances created in a millisecond)
     */
    public static short getCountShort() {
        synchronized ( Helper.class ) {
            if ( counter < 0 ) {
                counter = 0;
            }
            return counter++;
        }
    }

    public static byte[] getCountBytes() {
        return BytesHelper.fromShort( getCountShort() );
    }


    // Helper methods ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    public static String format(int value) {
        final String formatted = Integer.toHexString( value );
        StringBuilder buf = new StringBuilder( "00000000" );
        buf.replace( 8 - formatted.length(), 8, formatted );
        return buf.toString();
    }

    public static String format(short value) {
        String formatted = Integer.toHexString( value );
        StringBuilder buf = new StringBuilder( "0000" );
        buf.replace( 4 - formatted.length(), 4, formatted );
        return buf.toString();
    }

}
