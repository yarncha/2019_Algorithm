import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class QuickSort {
    public static void main(String[] args) throws IOException {
        String inputFileName = "data05.txt";
        //입력 파일의 이름
        BufferedReader reader = new BufferedReader(new FileReader(inputFileName));
        ArrayList<Integer> array = new ArrayList<>();
        String readLine = reader.readLine();
        String[] temp = readLine.split(",");
        for (int i = 0; i < temp.length; i++) {
            array.add(Integer.parseInt(temp[i]));
        }
        //파일을 읽어 배열에 저장한다.

        ArrayList<Integer> arrayForRandom = (ArrayList<Integer>) array.clone();
        FileWriter fw = new FileWriter("hw05_05_201701992_data05_quick.txt");
        quickSort(array, 0, array.size() - 1);
        fw.write(trimmingArray(array));
        fw.close();
        //QuickSort

        FileWriter fw2 = new FileWriter("hw05_05_201701992_data05_quickRandom.txt");
        quickSort_withRandom(arrayForRandom, 0, arrayForRandom.size() - 1);
        fw2.write(trimmingArray(arrayForRandom));
        fw2.close();
        //QuickSort_withRandom
    }

    //---출력값을 다듬어주는 메소드---
    public static String trimmingArray(ArrayList<Integer> inputArray) {
        String result = inputArray.toString();
        result = result.replace(" ", "");
        result = result.replace("[", "");
        result = result.replace("]", "");
        //공백 없애주고 앞뒤의 []없애줌.
        return result;
    }

    //---배열에서 두 원소의 위치를 바꾸는 메소드---
    private static void swap(ArrayList<Integer> inputArray, int target01, int target02) {
        if (target01 != target02) {
            int temp = inputArray.get(target01);
            inputArray.set(target01, inputArray.get(target02));
            inputArray.set(target02, temp);
        }
    }

    //---기본적인 Partition 알고리즘을 사용한 QuickSort---
    public static void quickSort(ArrayList<Integer> inputArray, int startIndex, int endIndex) {
        if (startIndex < endIndex) {
            //재귀의 종료 조건은 startIndex와 endIndex가 같은 경우이다.(즉 정렬하고자 하는 배열에 원소가 없을 경우)

            int midIndex = partition(inputArray, startIndex, endIndex);
            //전체 array에서 endIndex값 기준으로 작은 원소, 큰 원소로 나눈다. 그리고 그 값을 나눠진 가운데로 옮긴 다음 그 인덱스를 midIndex에 넣는다.

            quickSort(inputArray, startIndex, midIndex - 1);
            //작은 쪽(왼쪽)을 같은 방식으로 정렬한다.
            quickSort(inputArray, midIndex + 1, endIndex);
            //큰 쪽(오른쪽)을 같은 방식으로 정렬한다.
        }
    }

    //---기본적인 partition 알고리즘. endIndex에 있는 값을 기준으로 작은 값과 큰 값을 구분하고 그 경계선에 endIndex값을 둔 뒤 그 위치를 리턴한다.---
    private static int partition(ArrayList<Integer> inputArray, int startIndex, int endIndex) {
        int i = startIndex - 1;
        int j = startIndex;
        for (; j < endIndex; j++) {
            //j는 한 칸씩 진행한다.
            if (inputArray.get(j) < inputArray.get(endIndex)) {
                //endIndex에 위치한 값을 기준으로 정하고 이 값보다 j가 작을 경우
                i++;
                swap(inputArray, i, j);
                //앞 부분부터 그 작은 값들을 채운다. i는 이 앞부분을 세는 인덱스
            }
        }
        //그렇게 반복이 끝나고 나면 중간값을 기준으로 반반씩 작은 원소와 큰 원소로 정렬되어 있고
        i++;
        swap(inputArray, i, endIndex);
        //작은 값들 끝에 그 중간값을 넣어주면 i를 기준으로 정렬이 완료된다. 이 i를 리턴한다.
        return i;
    }

    //---partition부분이 random값을 3개 뽑아 정렬하는 quickSort---
    public static void quickSort_withRandom(ArrayList<Integer> inputArray, int startIndex, int endIndex) {
        if (startIndex < endIndex) {
            //재귀의 종료 조건은 startIndex와 endIndex가 같은 경우이다.(즉 정렬하고자 하는 배열에 원소가 없을 경우)

            int midIndex = randomizedPartition(inputArray, startIndex, endIndex);
            //전체 array에서 endIndex값 기준으로 작은 원소, 큰 원소로 나눈다. 그리고 그 값을 나눠진 가운데로 옮긴 다음 그 인덱스를 midIndex에 넣는다.

            quickSort(inputArray, startIndex, midIndex - 1);
            //작은 쪽(왼쪽)을 같은 방식으로 정렬한다.
            quickSort(inputArray, midIndex + 1, endIndex);
            //큰 쪽(오른쪽)을 같은 방식으로 정렬한다.
        }
    }

    //---랜덤 값을 뽑아 partition을 수행하는 메소드---
    private static int randomizedPartition(ArrayList<Integer> inputArray, int startIndex, int endIndex) {
        if ((endIndex - startIndex) > 1) {
            //원소가 3개 이상일 때만 수행한다.
            int randomMid = randomIndex(inputArray, startIndex, endIndex);
            swap(inputArray, randomMid, endIndex);
        }
        //해당 범위에서 임의의 인덱스 3개를 뽑고 그 중 중간값을 구해서 그 중간값을 기준으로 나눌 수 있도록 endIndex에 둔다.
        return partition(inputArray, startIndex, endIndex);
    }

    //---해당 범위 안에서 임의의 원소 3개를 뽑고 중간값을 구해 리턴하는 메소드---
    private static int randomIndex(ArrayList<Integer> inputArray, int startIndex, int endIndex) {
        Random random = new Random();
        int value1 = random.nextInt(endIndex - startIndex + 1) + startIndex;
        int value2 = random.nextInt(endIndex - startIndex + 1) + startIndex;
        while (value1 == value2) {
            value2 = random.nextInt(endIndex - startIndex + 1) + startIndex;
        }
        int value3 = random.nextInt(endIndex - startIndex + 1) + startIndex;
        while (value1 == value3) {
            value3 = random.nextInt(endIndex - startIndex + 1) + startIndex;
        }
        while (value2 == value3) {
            value3 = random.nextInt(endIndex - startIndex + 1) + startIndex;
        }
        //랜덤으로 중복을 제외하고 3개의 인덱스를 뽑고

        if (inputArray.get(value1) < inputArray.get(value2)) {
            if (inputArray.get(value1) > inputArray.get(value3)) {
                return value1;
            } else {
                if (inputArray.get(value2) < inputArray.get(value3)) {
                    return value2;
                } else {
                    return value3;
                }
            }
        } else {
            if (inputArray.get(value1) < inputArray.get(value3)) {
                return value1;
            } else {
                if (inputArray.get(value2) > inputArray.get(value3)) {
                    return value2;
                } else {
                    return value3;
                }
            }
        }
        //그 중에서 가운데 값의 인덱스를 찾아 리턴한다.
    }
}
