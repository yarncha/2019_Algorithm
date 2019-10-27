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
        int result = 0;
        if (endA - startA == 1) {
            if (endB - startB == 1) {
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
        //두 배열 모두가 길이가 2일 때는 길이 순으로 나열했을 때 2번째 원소를 리턴하면 이 원소가 가운데 원소이다.
        //따라서 원소 몇 개를 비교해 두번째 원소를 알아내고 리턴한다.

        //배열 길이가 둘 다 2보다 길 경우 재귀를 이용하여 범위를 점점 줄여나가야 한다.
        int midA = startA + ((endA - startA) / 2);
        int midB = startB + ((endB - startB) / 2);

        if (arrayA.get(midA) < arrayB.get(midB)) {      //A가 B보다 가운데 원소가 작을 경우는
            result = findIndexN(arrayA, midA, endA, arrayB, startB, startB + (endA - midA));
            //A에서 가운데보다 큰 원소, B에서 가운데보다 작은 원소의 범위를 추출하여 다시 findIndexN()메소드를 수행한다.
        } else {      //B가 더 작을 경우는
            result = findIndexN(arrayA, startA, startA + (endB - midB), arrayB, midB, endB);
            //A에서 가운데보다 작은 원소, B에서 가운데보다 큰 원소의 범위를 추출하여 다시 findIndexN()메소드를 수행한다.
        }
        return result;      //결과를 리턴한다.
    }
}
