package com.gj.miclrn.identifies;

import java.net.NetworkInterface;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.Enumeration;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class sequenceGenerator {

    private static final Queue<Long> IG_GEN_QUEUE = new ConcurrentLinkedQueue<Long>();

    private static final ScheduledExecutorService idQueueFiller = Executors.newSingleThreadScheduledExecutor();

    private static final int UNUSED_BITS = 1; // Sign bit, Unused (always set to 0)
    private static final int EPOCH_BITS = 31;// 41;
    private static final int NODE_ID_BITS = 6;// 10;
    private static final int SEQUENCE_BITS = 8;// 12;

    private static final int maxNodeId = (int) (Math.pow(2, NODE_ID_BITS) - 1);
    private static final int maxSequence = (int) (Math.pow(2, SEQUENCE_BITS) - 1);

    // Custom Epoch (January 1, 2015 Midnight UTC = 2015-01-01T00:00:00Z)
    private static final long CUSTOM_EPOCH = 1420070400000L;

    private final int nodeId;

    private volatile long lastTimestamp = -1L;
    private volatile long sequence = 0L;

    // Create SequenceGenerator with a nodeId
    private sequenceGenerator(int nodeId) {
        if (nodeId < 0 || nodeId > maxNodeId) {
            throw new IllegalArgumentException(String.format("NodeId must be between %d and %d", 0, maxNodeId));
        }
        this.nodeId = nodeId;

        idQueueFiller.scheduleWithFixedDelay(new QueueFiller(), 0, 100, TimeUnit.MILLISECONDS);
    }

    // Let SequenceGenerator generate a nodeId
    private sequenceGenerator() {
        this.nodeId = createNodeId();
        idQueueFiller.submit(new QueueFiller());
        idQueueFiller.scheduleWithFixedDelay(new QueueFiller(), 0, 100, TimeUnit.MILLISECONDS);
    }

    private static class SequenceGeneratorHelper {
        private static final sequenceGenerator INSTANCE = new sequenceGenerator();
    }

    public static sequenceGenerator getInstance() {
        return SequenceGeneratorHelper.INSTANCE;
    }

    public long nextId() {
        if (!IG_GEN_QUEUE.isEmpty()) {
            return IG_GEN_QUEUE.poll();
        }
        return nextIdInt();
    }

    private synchronized long nextIdInt() {
        long currentTimestamp = timestamp();

        if (currentTimestamp < lastTimestamp) {
            throw new IllegalStateException("Invalid System Clock!");
        }

        if (currentTimestamp == lastTimestamp) {
            sequence = (sequence + 1) & maxSequence;
            if (sequence == 0) {
                // Sequence Exhausted, wait till next millisecond.
                currentTimestamp = waitNextMillis(currentTimestamp);
            }
        } else {
            // reset sequence to start with zero for the next millisecond
            sequence = 0;
        }

        lastTimestamp = currentTimestamp;

        long id = currentTimestamp << (NODE_ID_BITS + SEQUENCE_BITS);
        id |= (nodeId << SEQUENCE_BITS);
        id |= sequence;
        return id;
    }

    // Get current timestamp in milliseconds, adjust for the custom epoch.
    private static long timestamp() {
        return Instant.now().toEpochMilli() - CUSTOM_EPOCH;
    }

    // Block and wait till next millisecond
    private long waitNextMillis(long currentTimestamp) {
        while (currentTimestamp == lastTimestamp) {
            currentTimestamp = timestamp();
        }
        return currentTimestamp;
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

    private class QueueFiller implements Runnable {

        @Override
        public void run() {
            if (IG_GEN_QUEUE.size() < 1000) {
                for (int i = 0; i < 5000; i++) {
                    IG_GEN_QUEUE.offer(nextIdInt());
                }
            }

        }

    }

}