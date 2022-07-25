package bai1;

public class BinarySearch {
    public static void main(String[] args) {
        int[] list = {1,5,7,8,12,19,25};
        int key = 7;
        System.out.println(binarySearch(list, key));
    }
    public static int binarySearch(int[] list, int key) {
        int low = 0;
        int high = list.length - 1;
        while (high >= low) {
            int mid = (low + high) / 2;
            if (key == list[mid])
                return mid;
            else if (list[mid] > key)
                return high = mid - 1;
            else
                return low = mid + 1;
        }
        return -1;
    }
}
