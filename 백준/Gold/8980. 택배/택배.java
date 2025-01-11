/*
= BOJ 8980. 택배
*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int villageSize, maxCapacity, parcelSize;
    static Parcel[] parcels;
    static int[] capacities;
    static int maxDeliveryCount;

    static class Parcel {
        int start, end, size;

        public Parcel(int start, int end, int size) {
            this.start = start;
            this.end = end;
            this.size = size;
        }
    }

    public static void main(String[] args) throws IOException {
        init();

        startDelivery();

        System.out.println(maxDeliveryCount);
    }

    private static void startDelivery() {
        for (Parcel parcel : parcels) {
            int availableCapacity = findAvailableCapacity(parcel);

            if (availableCapacity == 0)
                continue;

            for (int villageIdx = parcel.start; villageIdx < parcel.end; villageIdx++)
                capacities[villageIdx] -= availableCapacity;

            maxDeliveryCount += availableCapacity;
        }
    }
    private static int findAvailableCapacity(Parcel parcel) {
        int availableCapacity = parcel.size;

        for (int villageIdx = parcel.start; villageIdx < parcel.end; villageIdx++)
            availableCapacity = Math.min(capacities[villageIdx], availableCapacity);

        return availableCapacity;
    }

    private static void init() throws IOException {
        st = new StringTokenizer(input.readLine());
        villageSize = Integer.parseInt(st.nextToken());
        maxCapacity = Integer.parseInt(st.nextToken());

        parcelSize = Integer.parseInt(input.readLine());
        parcels = new Parcel[parcelSize];
        for (int idx = 0; idx < parcelSize; idx++) {
            st = new StringTokenizer(input.readLine());

            int start = Integer.parseInt(st.nextToken());
            int end = Integer.parseInt(st.nextToken());
            int size = Integer.parseInt(st.nextToken());

            parcels[idx] = new Parcel(start, end, size);
        }

        capacities = new int[villageSize + 1];
        Arrays.fill(capacities, maxCapacity);

        Arrays.sort(parcels,
                Comparator.comparingInt((Parcel o) -> o.end)
                        .thenComparingInt(o -> o.start));
    }
}