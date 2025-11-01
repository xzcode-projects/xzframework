//package org.xzframework.data.id;
//
//import java.util.UUID;
//
//public class SimpleUuidGenerator implements UuidGenerator {
//
//    private static final SimpleUuidGenerator INSTANCE = new SimpleUuidGenerator();
//    private final long mostSignificantBits;
//
//    private SimpleUuidGenerator() {
//        // generate the "most significant bits" ~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//        byte[] hiBits = new byte[8];
//        // use address as first 32 bits (8 * 4 bytes)
//        System.arraycopy(Helper.getAddressBytes(), 0, hiBits, 0, 4);
//        // use the "jvm identifier" as the next 32 bits
//        System.arraycopy(Helper.getJvmIdentifierBytes(), 0, hiBits, 4, 4);
//        // set the version (rfc term) appropriately
//        hiBits[6] &= 0x0f;
//        hiBits[6] |= 0x10;
//        mostSignificantBits = BytesHelper.asLong(hiBits);
//    }
//
//    public static long generateLeastSignificantBits(long seed) {
//        byte[] loBits = new byte[8];
//        short hiTime = (short) (seed >>> 32);
//        int loTime = (int) seed;
//        System.arraycopy(BytesHelper.fromShort(hiTime), 0, loBits, 0, 2);
//        System.arraycopy(BytesHelper.fromInt(loTime), 0, loBits, 2, 4);
//        System.arraycopy(Helper.getCountBytes(), 0, loBits, 6, 2);
//        loBits[0] &= 0x3f;
//        loBits[0] |= (byte) ((byte) 2 << (byte) 6);
//
//        return BytesHelper.asLong(loBits);
//    }
//
//    public static UUID randomUuid() {
//        return INSTANCE.generateUuid();
//    }
//
//    public UUID generateUuid() {
//        long leastSignificantBits = generateLeastSignificantBits(System.currentTimeMillis());
//        return new UUID(mostSignificantBits, leastSignificantBits);
//    }
//
//    @Override
//    public UUID nextUuid() {
//        return null;
//    }
//
//}
