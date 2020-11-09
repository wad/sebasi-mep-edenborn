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

        boolean isFirstDataPoint = true;

        for (Map.Entry<Integer, Integer> entry : countsByX.entrySet()) {

            int x = entry.getKey();

            if (isFirstDataPoint) {
                isFirstDataPoint = false;
                minX = x;
                maxX = x;
            } else {
                if (x < minX) {
                    minX = x;
                }
                if (x > maxX) {
                    maxX = x;
                }
            }
            int count = entry.getValue();
            if (count > maxCount) {
                maxCount = count;
            }
        }

        if (isFirstDataPoint) {
            builder.append(linePrefix).append("[no data for histogram]").append("\n");
            return;
        }

        int bucketRange = maxX - minX;
        int numBuckets = bucketRange + 1;
        if (numBuckets > DESIRED_NUM_BUCKETS) {
            numBuckets = DESIRED_NUM_BUCKETS;
        }

        int[] lowestValuesForBuckets = new int[numBuckets];
        int[] highestValuesForBuckets = new int[numBuckets];
        setBucketRanges(
                lowestValuesForBuckets,
                highestValuesForBuckets,
                minX,
                maxX);

        int[] countsByBucket = new int[numBuckets];
        for (int i = 0; i < numBuckets; i++) {
            countsByBucket[i] = 0;
        }

        for (Map.Entry<Integer, Integer> entry : countsByX.entrySet()) {
            int x = entry.getKey();
            int count = entry.getValue();
            int bucketIndex = determineWhichBucket(x, lowestValuesForBuckets, highestValuesForBuckets);
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
            builder.append(getBucketLinePrefix(
                    lowestValuesForBuckets[i],
                    highestValuesForBuckets[i],
                    maxX,
                    maxCountInBucket,
                    count));
            int numCharsInBar = determineNumCharsInBar(count, maxCountInBucket);
            for (int j = 0; j < numCharsInBar; j++) {
                builder.append("#");
            }
            builder.append("\n");
        }
    }

    static void setBucketRanges(
            int[] lowestValueInBucket,
            int[] highestValueInBucket,
            int minX,
            int maxX) {
        int numBuckets = lowestValueInBucket.length;
        double bucketSize = (maxX - minX) / (double) numBuckets;

        lowestValueInBucket[0] = minX;
        highestValueInBucket[numBuckets - 1] = maxX;

        for (int bucketIndex = 0; bucketIndex < numBuckets; bucketIndex++) {

            if (bucketIndex > 0) {
                lowestValueInBucket[bucketIndex] = highestValueInBucket[bucketIndex - 1] + 1;
            }

            double largestValueForBucketDouble = minX + ((bucketIndex + 1) * bucketSize);
            highestValueInBucket[bucketIndex] = (int) largestValueForBucketDouble;
        }
    }

    static int determineWhichBucket(
            int x,
            int[] lowestValuesForBuckets,
            int[] highestValuesForBuckets) {
        for (int i = 0; i < lowestValuesForBuckets.length; i++) {
            if (x >= lowestValuesForBuckets[i] && x <= highestValuesForBuckets[i]) {
                return i;
            }
        }
        throw new IllegalStateException("Value " + x + " doesn't fit into any bucket. "
                + showBucketRanges(lowestValuesForBuckets, highestValuesForBuckets));
    }

    static String showBucketRanges(
            int[] lowestValuesForBuckets,
            int[] highestValuesForBuckets) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < lowestValuesForBuckets.length; i++) {
            builder.append(lowestValuesForBuckets[i]).append('-').append(highestValuesForBuckets[i]).append(",");
        }
        return builder.toString();
    }

    static String getBucketLinePrefix(
            int lowerBound,
            int upperBound,
            int maxX,
            int maxCount,
            int count) {

        // Line prefix looks like: "0-210 (2345)    | "
        int maxDigitsOfRangePortion = (String.valueOf(maxX).length()) * 2;
        maxDigitsOfRangePortion += 2; // the dash character, and the trailing space.
        int maxDigitsOfCountPortion = String.valueOf(maxCount).length();
        maxDigitsOfCountPortion += 3; // the two parenthesis, and the trailing space.

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
