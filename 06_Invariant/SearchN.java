import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class SearchN {
    public static void main(String[] args) throws IOException {
        String inputFileNameA = "data06_a.txt";
        String inputFileNameB = "data06_b.txt";
        //입력 파일의 이름
        BufferedReader readerA = new BufferedReader(new FileReader(inputFileNameA));
        BufferedReader readerB = new BufferedReader(new FileReader(inputFileNameB));
        ArrayList<Integer> arrayA = new ArrayList<>();
        ArrayList<Integer> arrayB = new ArrayList<>();
        String readLineA = readerA.readLine();
        String readLineB = readerB.readLine();
        String[] tempA = readLineA.split(", ");
        String[] tempB = readLineB.split(", ");
        for (int i = 0; i < tempA.length; i++) {
            arrayA.add(Integer.parseInt(tempA[i]));
            arrayB.add(Integer.parseInt(tempB[i]));
        }
        //A와 B를 읽어 배열에 저장한다.
        System.out.println("두 배열 읽기를 완료했습니다.");

        System.out.println("두 배열에서 가운데 원소는 "+findIndexN(arrayA, 0, arrayA.size() - 1, arrayB, 0, arrayB.size() - 1)+"입니다.");
    }

    public static int findIndexN(ArrayList<Integer> arrayA, int startA, int endA, ArrayList<Integer> arrayB, int startB, int endB) {
        //배열의 범위를 줄여 나가면서 원소가 2개만 남을 때까지 반복한다.
        while(endA-startA!=1){
            int midA = startA + ((endA - startA) / 2);
            int midB = startB + ((endB - startB) / 2);
            //가운데 원소의 index를 구한다.

            if (arrayA.get(midA) < arrayB.get(midB)) {      //A가 B보다 가운데 원소가 작을 경우는
                startA = midA;
                endB = startB+(endA-midA);
                //A의 범위를 mid보다 큰 범위, B의 범위를 mid보다 작은 범위로 줄인 후 다시 반복을 수행하도록 한다.
            } else {      //B가 더 작을 경우는
                endA = startA+(endB-midB);
                startB = midB;
                //B의 범위를 mid보다 큰 범위, A의 범위를 mid보다 작은 범위로 줄인 후 다시 반복을 수행하도록 한다.
            }
        }

        //각 배열 당 원소가 2개씩만 남아서 반복을 탈출했을 경우
        if (arrayA.get(startA) < arrayB.get(startB)) {      //첫 번째 원소들을 비교하여 큰 원소를 찾고
            if (arrayA.get(endA) < arrayB.get(startB)) {
                return arrayA.get(endA);
            }
            return arrayB.get(startB);
            //그 원소와 반대쪽의 다음 원소를 비교하여 두 번째로 큰 원소를 찾아낸다.
        } else {
            if (arrayA.get(startA) < arrayB.get(endB)) {
                return arrayA.get(startA);
            }
            return arrayB.get(endB);
        }
    }
}
