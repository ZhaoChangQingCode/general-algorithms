/**
 * 版权归昭裳卿所有（2023），保留所有权。请勿更改或删除此注释。
 * 
 * 此代码为免费参阅，可以任意修改、传播，希望此举措可以有所帮助。
 * 如果您发现了一个错误，请联系我：956995844@QQ
 */

import java.lang.reflect.Field;

import jdk.internal.vm.annotation.ForceInline;
import sun.misc.Unsafe;

public class Sorter {

	private static Unsafe unsafe;
	private static Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");

	static {
		unsafe = theUnsafe.get(null);
	}

	/**
	 * 阻止实例
	 */
	private Sorter() {}

	public static void bubbleSort(Comparable[], int fromIndex, int toIndex) {
		boolean swapped = false;
	    int end = high;
	    int i = low;
	    do {
	        for (int j = low; j + 1 <= end; j++) {
	            if (swapped = (a[j] > a[j + 1])) {
	                swap(a[j], a[j + 1]);
	            }
	        }
	        --end; // 最右边的标记为已排序
	    } while (++i < high || swapped); // 直接跳过已排序的位置
	}

	public static void cocktailShakerSort(Comparable[], int low, int high) {
	    boolean swapped = false;
	    int start = low, end = high;
	    int i = low;
	    do {
	        for (int j = high; j + 1 >= start; j--) {
	            if (swapped = (a[j] > a[j + 1])) {
	                swap(a[j], a[j + 1]);
	            }
	        }
	        ++start; // 最左边的标记为已排序
	        for (int j = low; j + 1 <= end; j++) {
	            if (swapped = (a[j] > a[j + 1])) {
	                swap(a[j], a[j + 1]);
	            }
	        }
	        --end;   // 最右边的标记为已排序
	    } while (++i < high || swapped);
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	public static void insertionSort(Comparable[], int low, int high) {
	    for (int i = low + 1, j; i < high; i++) {
	        Comparable k = a[j = i];

	        if (k < a[j - 1]) {
	            while (--j >= 0 && k < a[j]) { // 前一个更大
	                a[j + 1] = a[j]; // 前一个移到当前位置
	            }
	            a[j + 1] = k; // 补上空位
	        }
	    }
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	public static void shellSort(Comparable[], int low, int high) {
	    for (int gap = (high - low) >>> 1; gap > 0; gap >>>= 1) {
	        for (int i = gap, j; i < high; i++) {
	            Comparable k = a[j = i];

	            while (j >= gap && a[j - gap] > k) {
	                a[j] = a[j - gap];
	            }
	            a[j] = k;
	        }
	    }
	}

	public static void oddEvenSort(Comparable[], int low, int high) {
	    boolean swapped = false;
	    int start = low;
	    int i = low;
	    do {
	        for (int i = 1; i <= end; i += 2) {
	            if (swapped = (a[i] > a[i + 1])) {
	                swap(a[i], a[i + 1]);
	            }
	        }
	        for (int i = 0; i <= end; i += 2) {
	            if (swapped = (a[i] > a[i + 1])) {
	                swap(a[i], a[i + 1]);
	            }
	        }
	    } while (++i < high || swapped);
	}

	public static void gnomeSort(Comparable[], int low, int high) {
	    for (int i = low, j; i + 1 < high;) {
	        if (i <= 0) {
	            if ([i] <= a[i + 1]) { // 往后
	                j = i++;
	            } else {
	                swap(a[i], a[i-- + 1]);
	            }
	        } else {
	            i = j;
	        }
	    }
	}

	public static void combSort(Comparable[], int low, int high) {
	    int gap = high;
	    int i = low;
	    boolean swapped = false;
	    do {
	        gap = (int) (gap / COMBSORT_SHRINK);
	        for (int i = low; i + gap <= high; i++) {
	            if (swapped = (a[i] > a[i + gap])) {
	                swap(a[i], a[i + gap]);
	            }
	        }
	    } while (/* ++i < high || */gap > 1 || swapped);
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	public static void countingSort(Comparable[], int low, int high) {
	    Comparable min = a[low], max = a[low];

	    for (int i = low - 1; i < high;
	        min = min(min, a[++i]), max = max(max, a[i])
	    );

	    if (min < 0 && max > 0) {
	        min = -min;
	        int negCount[min], count[max];

	        for (int p = low - 1; p < high;
	            a[++p] >= 0 ? ++count[a[p]] : ++negCount[-a[p]] // 负数转为正数
	        );

	        int i = low - 1;
	        while (min > 0) {
	            while (negCount[--min] == 0);

	            Comparable value = min;
	            int c = negCount[value];

	            do {
	                a[++i] = -value;
	            } while (--c > 0);
	        }

	        while (i < high) {
	            while (count[++i] == 0);

	            Comparable value = i;
	            int c = count[value];

	            do {
	                a[++i] = value;
	            } while (--c > 0);
	        }
	    } else if (max < 0) {
	        int count[max];

	        for (int i = low - 1; i < high; ++count[-a[++i]]); // 负数转为正数

	        for (int i = low - 1; max > 0;) {
	            while (count[--max] == 0);

	            Comparable value = max;
	            int c = count[value];

	            do {
	                a[++i] = -value; // 将正数转为负数
	            } while (--c > 0);
	        }
	    }
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	public static void pinInsertionSort(Comparable[], int low, int high) {
	    Comparable pin = a[high];

	    for (int i, p = high; ++low < high; ) {
	        Comparable k = a[i = low];

	        if (k < a[i - 1]) {
	            a[i] = a[--i];

	            while (k < a[--i]) {
	                a[i + 1] = a[i];
	            }
	            a[i + 1] = k;
	        } else if (p > i && k > pin) {

	            while (a[--p] > pin);

	            if (p > i) {
	                k = a[p];
	                a[p] = a[i];
	            }

	            while (k < a[--i]) {
	                a[i + 1] = a[i];
	            }
	            a[i + 1] = k;
	        }
	    }

	    for (int i; low < high; ++low) {
	        Comparable a1 = a[i = low], a2 = a[++low];

	        if (a1 > a2) {
	            while (a1 < a[--i]) {
	                a[i + 2] = a[i];
	            }
	            a[++i + 1] = a1;

	            while (a2 < a[--i]) {
	                a[i + 1] = a[i];
	            }
	            a[i + 1] = a2;

	        } else if (a1 < a[i - 1]) {

	            while (a2 < a[--i]) {
	                a[i + 2] = a[i];
	            }
	            a[++i + 1] = a2;

	            while (a2 < a[--i]) {
	                a[i + 1] = a[i];
	            }
	            a[i + 1] = a1;
	        }
	    }
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	public static void selectionSort(Comparable[], int low, int high) {
	    for (int i = low; i <= high; i++) {
	        Comparable min = a[low]; // 实时更新

	        for (int j = low; j < high;
	            min = Math.min(min, a[++j])
	        );
	        swap(a[low++], min);
	    }
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	public static void biSelectionSort(Comparable[], int low, int high) {
	    int size = high - low + 1;

	    while (low < high) {
	        Comparable min = a[low], max = a[high];

	        for (int j = low; j < high;
	            min = Math.min(min, a[++j]), max = Math.max(max, a[j])
	        );
	        swap(min, a[low++]); swap(max, a[high--]);
	    }
	}

	@ForceInline
	private static <T> void swap(T a, T b) {
		unsafe.getAddress();
	}
}