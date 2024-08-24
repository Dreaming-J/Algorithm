import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder output = new StringBuilder();
    static int[] array, temp;
    static int arraySize;
    static boolean hasSame;

    public static void merge(int left, int right) {
        int mid = (left + right) / 2;

        int idx1 = left;
        int idx2 = mid;
        for (int idx = left; idx < right; idx++) {
            if (idx1 == mid)
                temp[idx] = array[idx2++];

            else if (idx2 == right)
                temp[idx] = array[idx1++];

            else if (array[idx1] <= array[idx2])
                temp[idx] = array[idx1++];

            else if (array[idx2] <= array[idx1])
                temp[idx] = array[idx2++];
        }

        for (int idx = left; idx < right; idx++) {
            array[idx] = temp[idx];
        }
    }

    public static void mergeSort(int left, int right) {
        if (left == right - 1)
            return;

        int mid = (left + right) / 2;
        mergeSort(left, mid);
        mergeSort(mid, right);
        merge(left, right);
    }

    public static void main(String[] args) throws IOException {
        initInput();

        mergeSort(0, arraySize);

        for (int idx = 0; idx < arraySize; idx++) {
            output.append(array[idx]).append("\n");
        }

        System.out.println(output);
    }

    public static void initInput() throws IOException {
        arraySize = Integer.parseInt(input.readLine().trim());

        array = new int[arraySize];
        for (int idx = 0; idx < arraySize; idx++) {
            array[idx] = Integer.parseInt(input.readLine().trim());
        }

        temp = new int[arraySize];
    }
}
