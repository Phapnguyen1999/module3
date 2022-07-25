package bai4;

public class BinaryArray {

    private static int[] list = {2,5,7,9,13,16,18,26,29,34,45,56,67,78};
    public static int binarySearch(int[] arr, int left, int right, int value)
    {
        while (left <= right)
        {
            int middle = (left + right) / 2;
            if (arr[middle] == value)
                return middle;
            else if (arr[middle] > value)
                right = middle - 1;
            else
                left = middle + 1;
            return binarySearch(arr,left,right,value);
        }
        return -1;
    }
    public static void main(String[] args) {
        System.out.println(binarySearch(list,0, list.length - 1 , 2));
        System.out.println(binarySearch(list,0, list.length - 1 , 6));
        System.out.println(binarySearch(list,0, list.length - 1 , 14));
        System.out.println(binarySearch(list,0, list.length - 1 , 67));
        System.out.println(binarySearch(list,0, list.length - 1 , 78));
        System.out.println(binarySearch(list,0, list.length - 1 , 56));
    }
}
