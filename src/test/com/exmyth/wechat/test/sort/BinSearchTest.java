package com.exmyth.wechat.test.sort;

public class BinSearchTest {
	public static void main(String[] args) {
	}

	static int binSearch(int array[], int key) {
		if (array.length == 0) {
			return -1;
		}
		int low = 0, high = array.length - 1;
		int mid;
		int index = high;
		int diff = Math.abs(array[high] - key);
		while (low <= high) {
			if (low == index && (high - low == 1)) {
				mid = high;
			} else {
				mid = (low + high) / 2;
			}
			if (key == array[mid]) {
				return mid;
			}

			else if (key < array[mid]) {
				high = mid - 1;
			}

			else if (key > array[mid]) {
				int d = key - array[mid];
				if (d < diff) {
					index = mid;
					diff = d;
					low = mid;
				} else {
					return index;
				}
			}

		}
		return -1;
	}

}
