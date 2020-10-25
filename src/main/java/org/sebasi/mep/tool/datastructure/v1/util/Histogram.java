package org.sebasi.mep.tool.datastructure.v1.util;

import java.util.HashMap;
import java.util.Map;

// Just a hacky little histogram viewer.
public class Histogram {

    static final int DESIRED_NUM_BUCKETS = 20;
    static final int DESIRED_BAR_LENGTH = 40;

    private final Map<Integer, Integer> countsByX = new HashMap<>();

    public void addDataPoint(int x) {
        countsByX.merge(x, 1, Integer::sum);
    }

    public String show() {
        StringBuilder builder = new StringBuilder();
        makeReport(builder, "");
        return builder.toString();
    }

    public void makeReport(
            StringBuilder builder,
            String linePrefix) {
        int minX = 0;
        int maxX = 0;
        int maxCount = 0;

        for (Map.Entry<Integer, Integer> entry : countsByX.entrySet()) {

            int x = entry.getKey();

            if (x < minX) {
                minX = x;
            }

            if (x > maxX) {
                maxX = x;
            }

            int count = entry.getValue();
            if (count > maxCount) {
                maxCount = count;
            }
        }

        int range = maxX - minX;
        if (range < DESIRED_NUM_BUCKETS) {
            makeReport(builder, linePrefix, range, minX, maxX);
        } else {
            makeReport(builder, linePrefix, DESIRED_NUM_BUCKETS, minX, maxX);
        }
    }

    void makeReport(
            StringBuilder builder,
            String linePrefix,
            int numBuckets,
            int minX,
            int maxX) {

        if (numBuckets <= 0) {
            builder.append(linePrefix).append("[no data for histogram]").append("\n");
            return;
        }

        int[] countsByBucket = new int[numBuckets];
        for (int i = 0; i < numBuckets; i++) {
            countsByBucket[i] = 0;
        }

        for (Map.Entry<Integer, Integer> entry : countsByX.entrySet()) {
            int x = entry.getKey();
            int count = entry.getValue();
            int bucketIndex = determineWhichBucket(x, minX, maxX, numBuckets);
            countsByBucket[bucketIndex] += count;
        }

        int maxCountInBucket = 0;
        for (int i = 0; i < numBuckets; i++) {
            if (countsByBucket[i] > maxCountInBucket) {
                maxCountInBucket = countsByBucket[i];
            }
        }

        for (int i = 0; i < numBuckets; i++) {
            int count = countsByBucket[i];
            builder.append(linePrefix);
            builder.append(getBucketLinePrefix(i, minX, maxX, numBuckets, maxCountInBucket, count));
            int numCharsInBar = determineNumCharsInBar(count, maxCountInBucket);
            for (int j = 0; j < numCharsInBar; j++) {
                builder.append("#");
            }
            builder.append("\n");
        }
    }

    static int determineWhichBucket(
            int x,
            int minX,
            int maxX,
            int numBuckets) {
        if (numBuckets <= 0) {
            return 0;
        }

        double range = maxX - minX;
        double bucketSize = range / numBuckets;
        double bucketIndexAsDouble = (double) (x - minX) / bucketSize;
        int bucketIndex = (int) bucketIndexAsDouble;
        if (bucketIndex < 0) {
            bucketIndex = 0;
        }
        if (bucketIndex >= numBuckets) {
            bucketIndex = numBuckets - 1;
        }
        return bucketIndex;
    }

    static String getBucketLinePrefix(
            int bucketIndex,
            int minX,
            int maxX,
            int numBuckets,
            int maxCount,
            int count) {

        // Line prefix looks like: "0-210 (2345)    | "
        int maxDigitsOfRangePortion = (String.valueOf(maxX).length()) * 2;
        maxDigitsOfRangePortion += 2; // the dash character, and the trailing space.
        int maxDigitsOfCountPortion = String.valueOf(maxCount).length();
        maxDigitsOfCountPortion += 3; // the two parenthesis, and the trailing space.

        double bucketSize = (double) (maxX - minX) / numBuckets;
        int lowerBound = (int) (bucketSize * bucketIndex) + minX;
        int upperBound = ((int) (bucketSize * (bucketIndex + 1)) + minX);

        StringBuilder prefix = new StringBuilder();
        prefix.append(lowerBound);
        prefix.append("-");
        prefix.append(upperBound);
        prefix.append(" (");
        prefix.append(count);
        prefix.append(") ");

        int remainingSpacesNeeded = 1 + maxDigitsOfRangePortion + maxDigitsOfCountPortion - prefix.length();
        for (int i = 0; i < remainingSpacesNeeded; i++) {
            prefix.append(" ");
        }
        prefix.append("| ");
        return prefix.toString();
    }

    static int determineNumCharsInBar(
            int count,
            int maxCount) {
        if (count == 0 || maxCount == 0) {
            return 0;
        }

        double p = (double) count / (double) maxCount;
        double t = p * DESIRED_BAR_LENGTH;
        return (int) t;
    }
}
