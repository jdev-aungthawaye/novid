package jdev.novid.component.ddd;

import java.net.NetworkInterface;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.Enumeration;

/**
 * Distributed Sequence Generator. Inspired by Twitter snowflake:
 * 
 * https://github.com/twitter/snowflake/tree/snowflake-2010
 */
public final class Snowflake {

    private static final int TOTAL_BITS = 64;

    private static final int EPOCH_BITS = 42;

    private static final int NODE_ID_BITS = 10;

    private static final int SEQUENCE_BITS = 12;

    private static final int maxNodeId = (int) (Math.pow(2, NODE_ID_BITS) - 1);

    private static final int maxSequence = (int) (Math.pow(2, SEQUENCE_BITS) - 1);

    private static final long CUSTOM_EPOCH = 1577813400000L;

    private static long timestamp() {

        return Instant.now().toEpochMilli() - CUSTOM_EPOCH;

    }

    private final int nodeId;

    private long lastTimestamp = -1L;

    private long sequence = 0L;

    private final static Snowflake INSTANCE = new Snowflake();

    private Snowflake() {

        this.nodeId = createNodeId();

    }

    public final static Snowflake get() {

        return INSTANCE;

    }

    public long nextId() {

        long currentTimestamp = timestamp();

        synchronized (this) {

            if (currentTimestamp < lastTimestamp) {

                throw new IllegalStateException("Invalid System Clock!");

            }

            if (currentTimestamp == lastTimestamp) {

                sequence = (sequence + 1) & maxSequence;

                if (sequence == 0) {

                    currentTimestamp = waitNextMillis(currentTimestamp);

                }

            } else {

                sequence = 0;

            }

            lastTimestamp = currentTimestamp;

        }

        long id = currentTimestamp << (TOTAL_BITS - EPOCH_BITS);
        id |= (nodeId << (TOTAL_BITS - EPOCH_BITS - NODE_ID_BITS));
        id |= sequence;
        return id;

    }

    private int createNodeId() {

        int nodeId;

        try {

            StringBuilder sb = new StringBuilder();
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();

            while (networkInterfaces.hasMoreElements()) {

                NetworkInterface networkInterface = networkInterfaces.nextElement();
                byte[] mac = networkInterface.getHardwareAddress();

                if (mac != null) {

                    for (int i = 0; i < mac.length; i++) {

                        sb.append(String.format("%02X", mac[i]));

                    }

                }

            }

            nodeId = sb.toString().hashCode();

        } catch (Exception ex) {

            nodeId = (new SecureRandom().nextInt());

        }

        nodeId = nodeId & maxNodeId;
        return nodeId;

    }

    private long waitNextMillis(long currentTimestamp) {

        while (currentTimestamp == lastTimestamp) {

            currentTimestamp = timestamp();

        }

        return currentTimestamp;

    }

}
