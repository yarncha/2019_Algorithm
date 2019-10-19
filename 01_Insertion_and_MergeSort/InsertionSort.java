import java.io.*;
import java.util.Arrays;
import java.util.stream.Stream;

public class InsertionSort {
    public static void main(String[] args) throws IOException {
        String inputFileName = "./data02.txt";      //입력한 파일 이름
        BufferedReader reader = new BufferedReader(new FileReader(inputFileName));
        String inputString = reader.readLine();     //한 줄을 읽고
        int[] inputArray = Stream.of(inputString.split(",")).mapToInt(Integer::parseInt).toArray();     //,단위로 분할하여 int array에 넣는다.

        for (int i = 0; i < inputArray.length - 1; i++) {       //array에서 한 칸씩 진행하면서 다음 작업을 수행한다.
            if (inputArray[i] > inputArray[i + 1]) {        //현재 값보다 다음 값이 작을 경우
                int numberToCompare = inputArray[i + 1];        //비교할 값을 저장해둔 후
                inputArray[i + 1] = inputArray[i];
                inputArray[i] = numberToCompare;        //위치를 변경한다.
                int j = i - 1;
                while ((j >= 0) && (inputArray[j] > numberToCompare)) {     //위치를 변경한 후 비교하는 값들을 이전에 정렬된 값들과 비교하며
                    inputArray[j + 1] = inputArray[j];
                    inputArray[j] = numberToCompare;        //위치를 정해 나간다.
                    j--;
                }
            }
        }
        OutputStream output = new FileOutputStream("./hw01_05_201701992_insertion.txt");
        String str = arrayToString(inputArray);
        byte[] by = str.getBytes();
        output.write(by);       //정해진 이름의 파일에 해당 배열의 string을 저장한다.
    }

    public static String arrayToString(int[] inputArray) {
        String resultString = Arrays.toString(inputArray);
        resultString = resultString.replace("[", "");
        resultString = resultString.replace(" ", "");
        resultString = resultString.replace("]", "");
        return resultString;
    }
}
