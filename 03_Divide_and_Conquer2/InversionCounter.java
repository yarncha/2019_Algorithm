import java.io.*;
import java.util.Arrays;
import java.util.stream.Stream;

public class InversionCounter {
    static int inversionCounter = 0;        //sorting을 진행하며 inversion이 몇 개 있는지 세는 카운터

    public static void main(String[] args) throws IOException {
        String inputFileName = "./data03_inversion.txt";        //읽을 파일의 이름
        BufferedReader reader = new BufferedReader(new FileReader(inputFileName));
        String inputString = reader.readLine();
        int[] inputArray = Stream.of(inputString.split(",")).mapToInt(Integer::parseInt).toArray();     //파일에서 값을 읽어 주어진 배열을 만든다
        System.out.println("Input Data : " + Arrays.toString(inputArray));

        inputArray = mergeSort(inputArray, 0, inputArray.length - 1);       //mergeSort를 수행한다. 이때 재귀적으로 정렬이 일어난다.

        System.out.println("Output Data : " + Arrays.toString(inputArray));     //결과값 출력. 제대로 정렬되었는지 확인함
        System.out.println("Number of Inversions : " + inversionCounter);       //inversion의 개수
    }

    public static int[] mergeSort(int[] inputArray, int startIndex, int endIndex) {     //처음 값의 인덱스와 끝 값의 인덱스를 정해주면 재귀적으로 반으로 나누어 정렬하는 함수
        if (startIndex < endIndex) {
            int middleIndex = (endIndex + startIndex) / 2;
            inputArray = mergeSort(inputArray, startIndex, middleIndex);        //앞의 반을 정렬하고
            inputArray = mergeSort(inputArray, middleIndex + 1, endIndex);      //뒤의 반을 정렬한다.
            return merge(inputArray, startIndex, middleIndex, endIndex);        //그 후 앞과 뒤를 merge한다.
        }
        return inputArray;
    }

    public static int[] merge(int[] inputArray, int startIndex, int middleIndex, int endIndex) {
        int[] resultArray = new int[inputArray.length];     //결과값을 저장할 배열
        System.arraycopy(inputArray, 0, resultArray, 0, inputArray.length);

        int resultPosition = startIndex;
        int array1Position = startIndex;
        int array2Position = middleIndex + 1;

        while ((array1Position <= middleIndex) && (array2Position <= endIndex)) {       //반으로 나눠진 배열에 대해서 어느 한쪽이 없어질 때까지 다음을 반복
            if (inputArray[array1Position] <= inputArray[array2Position]) {       //반으로 나눠진 배열에서 하나씩 골라 둘 중 작은 값을 결과 배열에 넣는다.
                resultArray[resultPosition] = inputArray[array1Position];
                array1Position++;
            } else {
                resultArray[resultPosition] = inputArray[array2Position];
                array2Position++;
                inversionCounter += middleIndex - array1Position + 1;
                //머지소트와 대부분이 동일하지만 오른쪽 배열이 왼쪽 배열보다 작아서 결과 배열에 넣는 경우에 inversion의 개수를 왼쪽 배열에 남아있는 원소의 수만큼 카운트해준다는 점에서 다르다.
            }
            resultPosition++;
        }

        while (array1Position <= middleIndex) {
            resultArray[resultPosition] = inputArray[array1Position];
            array1Position++;
            resultPosition++;
        }       //앞 배열이 남아 있으면 그 배열을 전부 결과 배열에 옮겨 담는다

        while (array2Position <= endIndex) {
            resultArray[resultPosition] = inputArray[array2Position];
            array2Position++;
            resultPosition++;
        }       //뒤 배열도 마찬가지

        return resultArray;
    }
}
