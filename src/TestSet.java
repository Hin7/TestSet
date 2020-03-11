/**
 * Task 2057 SGU (set)
 *
 * @author Hin7
 * @version 1.0 11.11.2019
 * @version 1.1 10.03.2020
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.*;

public class TestSet {
    public static void main(String[] args) {
        try {
            Scanner in = new Scanner(Paths.get("input.txt"), "UTF-8");
            PrintWriter out = new PrintWriter("output.txt", "UTF-8");
            //ArrayList<Integer> digits = new ArrayList<>();
            //LinkedList<Integer> digits = new LinkedList<>();
            //SortedIArrayList digits = new SortedIArrayList();
            //SortedIArrayOnMap digits = new SortedIArrayOnMap();
            //NotSortedIArrayList digits = new NotSortedIArrayList();
            //SortedIArray digits = new SortedIArray();
            NotSortedIArray digits = new NotSortedIArray();
            int nOper = in.nextInt();
            if(nOper > 0)
                digits.setCapacity(nOper);
            while (nOper-- > 0) {
                //System.out.println("nOper = " + nOper);
                int tOper = in.nextInt();
                //System.out.println("tOper = " + tOper);
                if (tOper == 1) {
                    digits.add(in.nextInt());
                } else if (tOper == 2) {
                    out.println(digits.removeMin());
                }
                //System.out.println(digits);
            }
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class SortedIArrayList {
        ArrayList<Integer> data = new ArrayList<>();
        int searchLevel = 20;


        Integer removeMin() {
            return data.remove(0);
        }

        void add(Integer element) {
            /*int idx = Arrays.binarySearch(data.toArray(), element);
            if ( idx < 0 ) idx = -idx -1;
            data.add(idx, element);*/

            if (data.isEmpty()) {
                data.add(element);
                return;
            }
            data.add(searchIndex(0, data.size(), element), element);
        }

        int lineSearchIndex(int fromI, int toI, int key) {
            int idx = fromI;
            for (Integer v : data.subList(fromI, toI)) {
                if (key < v)
                    break;
                idx++;
            }
            return idx;
        }

        int searchIndex(int fromI, int toI, int key) {
            if ((toI - fromI) <= searchLevel)
                return lineSearchIndex(fromI, toI, key);
            int middleI = (fromI - toI) / 2 + toI;
            if (key < data.get(middleI))
                return searchIndex(fromI, middleI, key);
            else
                return searchIndex(middleI, toI, key);
        }

        void setCapacity(int capacity) {
            data.ensureCapacity(capacity);
        }

        @Override
        public String toString() {
            return data.toString();
        }
    }

    public static class SortedIArrayOnMap {
        Map<Integer, Integer> data = new TreeMap<>();

        Integer removeMin() {
            for (Integer k : data.keySet()) {
                Integer val = data.get(k);
                if (val.compareTo(0) > 0) {
                    data.put(k, val - 1);
                    return k;
                } /*else data.remove(k);*/
            }
            return 0;
        }

        void add(Integer element) {
            data.merge(element, 1, Integer::sum);
        }

        void setCapacity(int capacity) {
            return;
        }

        @Override
        public String toString() {
            return data.toString();
        }
    }

    public static class NotSortedIArrayList {
        ArrayList<Integer> data = new ArrayList<>();
        //LinkedList<Integer> data = new LinkedList<>();
        boolean sorted = false;

        Integer removeMin() {
            if (sorted)
                return data.remove(0);
            else {
                data.sort((a1, a2) -> a1.compareTo(a2));
                sorted = true;
                return data.remove(0);
            }
        }

        void add(Integer element) {
            data.add(element);
            sorted = false;
        }

        void setCapacity(int capacity) {
            data.ensureCapacity(capacity);
        }

        @Override
        public String toString() {
            return data.toString();
        }
    }

    public static class SortedIArray {
        int[] data = new int[100];
        int size = 0;

        int removeMin(){
            return -data[--size];
        }

        void add(int element) {
            int idx = Arrays.binarySearch(data, 0, size, -element);
            if (idx < 0)
                idx = -idx-1;
            if (idx != size)
                System.arraycopy(data, idx, data, idx + 1, size - idx);
            data[idx] = - element;
            size++;
        }

        void setCapacity(int capacity) {
            data = new int[capacity];
        }

        @Override
        public String toString() {
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < size; i++) {
                result.append(data[i]);
                result.append(" ");
            }
            return result.toString();
        }
    }

    public static class NotSortedIArray{
        int[] data = new int[10];
        int size =0;
        boolean sorted = false;

        int removeMin(){
            if(!sorted){
                Arrays.sort(data, 0, size);
                sorted = true;
            }
            return -data[--size];
        }

        void add(int element){
            data[size++] = -element;
            sorted = false;
        }

        void setCapacity(int capacity) {
            data = new int[capacity];
        }

        @Override
        public String toString() {
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < size; i++) {
                result.append(data[i]);
                result.append(" ");
            }
            return result.toString();
        }
    }
}
