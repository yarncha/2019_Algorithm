import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class MaxPriorityQueue {
    public static void main(String[] args) throws IOException {
        String inputFileName = "data04.txt";
        //입력 파일의 이름
        BufferedReader reader = new BufferedReader(new FileReader(inputFileName));
        ArrayList<Data> priorityQueue = new ArrayList<>();
        String readLine;
        while ((readLine = reader.readLine()) != null) {
            String[] temp = readLine.split(", ");
            Data newData = new Data(Integer.parseInt(temp[0]), temp[1]);
            priorityQueue.add(newData);
        }
        //파일을 읽어 배열에 저장한다.

        bulid_max_heap(priorityQueue);
        printArray(priorityQueue);
        //저장된 배열을 최대 힙으로 만든다.

        //Test---------------------------------
        Scanner scan = new Scanner(System.in);
        System.out.println("\n1. 추가 / 2. 최대값 / 3. 최대 우선순위 원소 제거 / 4. 특정 원소 우선순위 값 증가 / 5. 제거 / 6. 종료");
        System.out.print(">>이용하려는 기능의 번호를 선택해 주세요 : ");
        int command = scan.nextInt();

        while (command != 6) {
            switch (command) {
                case 1:
                    System.out.println(">>추가하고자 하는 데이터의 우선순위, 과목 이름을 입력하세요.");
                    Data testData = new Data(scan.nextInt(), scan.nextLine());
                    priorityQueue = insert(priorityQueue, testData);
                    break;
                case 2:
                    System.out.println("<<우선순위값이 최대인 원소 : " + max(priorityQueue));
                    break;
                case 3:
                    priorityQueue = extract_max(priorityQueue);
                    break;
                case 4:
                    System.out.println(">>우선순위 값을 증가시킬 원소의 인덱스와 그 증가량을 입력하세요.");
                    priorityQueue = increase_key(priorityQueue, priorityQueue.get(scan.nextInt()), scan.nextInt());
                    break;
                case 5:
                    System.out.println(">>삭제할 원소의 인덱스를 입력하세요.");
                    priorityQueue = delete(priorityQueue, priorityQueue.get(scan.nextInt()));
                    break;
            }
            System.out.println("\n1. 추가 / 2. 최대값 / 3. 최대 우선순위 원소 제거 / 4. 특정 원소 우선순위 값 증가 / 5. 제거 / 6. 종료");
            System.out.print(">>이용하려는 기능의 번호를 선택해 주세요 : ");
            command = scan.nextInt();
        }//각각의 제시된 메소드들을 사용하고 각각의 결과를 나타낸다.
    }

    //배열을 입력 받아 이 배열을 최대 힙으로 만든다.
    private static void bulid_max_heap(ArrayList<Data> arrayList) {
        //(자식을 가지는)마지막 노드부터 max_heapify수행, 하나씩 위로 올라가서 루트까지 수행
        for (int i = arrayList.size() / 2 - 1; i >= 0; i--) {
            arrayList = max_heapify(arrayList, i);
        }
    }

    //배열과 인덱스를 입력 받아 해당 인덱스를 루트 노드로 보고 자식들과 비교해 위치를 변경한다. 이때 자식 노드들은 모두 정렬되어 있다고 생각한다.
    private static ArrayList<Data> max_heapify(ArrayList<Data> arrayList, int rootIndex) {
        int largest;
        int left = rootIndex * 2 + 1;
        int right = rootIndex * 2 + 2;

        if ((arrayList.size() > left) && (arrayList.get(rootIndex).getPriority() < arrayList.get(left).getPriority())) {
            largest = left;
        } else {
            largest = rootIndex;
        }

        if ((arrayList.size() > right) && (arrayList.get(largest).getPriority() < arrayList.get(right).getPriority())) {
            largest = right;
        }
        //왼쪽과 오른쪽 모두를 비교해서 큰 우선순위를 갖는 원소를 찾는다.

        if (largest != rootIndex) {
            Data newRoot = arrayList.get(largest);
            arrayList.set(largest, arrayList.get(rootIndex));
            arrayList.set(rootIndex, newRoot);

            arrayList = max_heapify(arrayList, largest);
            //자리가 변경되었을 때 그 변경된 자식만 다시 정렬을 수행해준다.
        }
        return arrayList;
    }

    //배열에 toAdd라는 원소를 하나 넣는다.
    private static ArrayList<Data> insert(ArrayList<Data> arrayList, Data toAdd) {
        arrayList.add(toAdd);
        //arrayList에 넣으려는 원소를 삽입한 후

        int currentIndex = arrayList.size() - 1;
        int parentIndex;
        while (currentIndex > 0) {
            if (currentIndex % 2 == 0) {
                parentIndex = (currentIndex - 2) / 2;
            } else {
                parentIndex = (currentIndex - 1) / 2;
            }
            //부모의 인덱스를 구한 뒤에

            if (arrayList.get(parentIndex).getPriority() < arrayList.get(currentIndex).getPriority()) {
                Data swapData = arrayList.get(parentIndex);
                arrayList.set(parentIndex, arrayList.get(currentIndex));
                arrayList.set(currentIndex, swapData);
                currentIndex = parentIndex;
            } else {
                break;
            }
        }
        printArray(arrayList);
        System.out.println("<<과목이 추가되었습니다.");
        return arrayList;
        //계속해서 부모 원소와 비교해가며 위치를 바꾼다. 이때 부모가 더 우선순위가 크면 종료하고 리턴한다.
    }

    //우선순위값이 최대인 원소를 리턴한다.
    private static String max(ArrayList<Data> arrayList) {
        return arrayList.get(0).getPriority() + ", " + arrayList.get(0).getSubjectName();
        //우선순위값이 최대인 원소는 index가 0인 곳에 있다
    }

    //우선순위값이 최대인 원소를 삭제한다.
    private static ArrayList<Data> extract_max(ArrayList<Data> arrayList) {
        arrayList.set(0, arrayList.get(arrayList.size() - 1));
        arrayList.remove(arrayList.size() - 1);
        //0번째 원소가 우선순위값이 최대이므로 삭제하고 가장 마지막에 위치한 원소를 0번째로 이동시킨 뒤

        max_heapify(arrayList, 0);
        printArray(arrayList);
        System.out.println("<<우선순위값이 최대인 과목이 제거되었습니다.");
        return arrayList;
        //이미 정렬되어 있던 배열에 0번째 원소만 정렬하면 되는 것이므로 max_heapify로 정렬하고 리턴한다.
    }

    //특정 원소의 우선순위 값을 특정 값만큼 증가시킨다.
    private static ArrayList<Data> increase_key(ArrayList<Data> arrayList, Data toEdit, int increaseNum) {
        int currentIndex = findIndex(arrayList, toEdit, 0);

        if (currentIndex > -1) {        //-1일 경우는 해당 인덱스를 찾지 못한 것
            arrayList.get(currentIndex).setPriority(increaseNum);
            //우선 우선순위 값을 변경시킬 원소를 찾은 뒤 우선순위 값을 변경시키고,

            int parentIndex;
            while (currentIndex > 0) {
                if (currentIndex % 2 == 0) {
                    parentIndex = (currentIndex - 2) / 2;
                } else {
                    parentIndex = (currentIndex - 1) / 2;
                }
                //부모의 인덱스를 구한 뒤에

                if (arrayList.get(parentIndex).getPriority() < arrayList.get(currentIndex).getPriority()) {
                    Data swapData = arrayList.get(parentIndex);
                    arrayList.set(parentIndex, arrayList.get(currentIndex));
                    arrayList.set(currentIndex, swapData);
                    currentIndex = parentIndex;
                } else {
                    break;
                }
            }
            //계속해서 부모 원소와 비교해가며 위치를 바꾼다. 이때 부모가 더 우선순위가 크면 종료하고 리턴한다.
            printArray(arrayList);
            System.out.println("<<값 수정이 완료되었습니다.");
        } else {
            System.out.println("<<해당 원소가 존재하지 않습니다. 값 수정이 취소됩니다.");
        }

        return arrayList;
    }

    //특정 원소를 삭제한다.
    private static ArrayList<Data> delete(ArrayList<Data> arrayList, Data toDelete) {
        int currentIndex = findIndex(arrayList, toDelete, 0);

        if (currentIndex > -1) {
            arrayList.set(currentIndex, arrayList.get(arrayList.size() - 1));
            arrayList.remove(arrayList.size() - 1);
            //우선 삭제할 값을 찾고 그 위치에 마지막 원소를 넣은 뒤 사이즈를 줄이고

            max_heapify(arrayList, currentIndex);
            //이미 정렬되어 있던 배열에 해당 원소만 정렬하면 되는 것이므로 max_heapify로 정렬하고 리턴한다.

            printArray(arrayList);
            System.out.println("<<삭제가 완료되었습니다.");
        } else {
            System.out.println("<<해당 원소가 존재하지 않습니다. 값 삭제가 취소됩니다.");
        }

        return arrayList;
    }

    private static void printArray(ArrayList<Data> arrayList) {
        System.out.println("---결과--------------------------------------");
        for (Data data : arrayList) {
            System.out.println(data.getPriority() + ", " + data.getSubjectName());
        }

//        int line=0;
//        int last=0;
//        for (int i=0;i<arrayList.size();i++){
//            System.out.print(arrayList.get(i).getPriority()+"|"+arrayList.get(i).getSubjectName()+"   ");
//            if(i==0 || i-last==Math.pow(2,line)){
//                System.out.println("");
//                last=i;
//                line++;
//            }
//        }     //테스트해볼 때 트리를 시각적으로 보여 주기 위한 출력

        System.out.println("\n---------------------------------------------");
    }

    //배열에서 해당 원소를 찾아서 인덱스를 리턴하는 재귀함수
    private static int findIndex(ArrayList<Data> arrayList, Data toFind, int compareIndex) {
        if (arrayList.get(compareIndex) == toFind) {
            return compareIndex;
            //비교하려는 인덱스의 원소랑 비교했을 때 같으면 그 인덱스의 원소라는 것이므로 리턴
        } else if (arrayList.get(compareIndex).getPriority() > toFind.getPriority()) {      //찾으려는 원소의 우선순위가 비교하려는 원소보다 작을 경우는 그 자식중에 있을 수도 있으므로
            int newCompare = -1;

            if (arrayList.size()>(compareIndex * 2 + 1) && arrayList.get(compareIndex * 2 + 1).getPriority() >= toFind.getPriority()) {
                newCompare = findIndex(arrayList, toFind, compareIndex * 2 + 1);
            }       //왼쪽보다 작거나 같을 경우 왼쪽에서도 찾아보고

            if (arrayList.size()>(compareIndex * 2 + 2) && newCompare == -1 && arrayList.get(compareIndex * 2 + 2).getPriority() >= toFind.getPriority()) {
                newCompare = findIndex(arrayList, toFind, compareIndex * 2 + 2);
            }       //오른쪽보다 작거나 같을 경우 오른쪽에서도 찾아 본 후
            return newCompare;      //그 결과를 리턴한다.
        } else {
            return -1;      //찾지 못할 경우 -1리턴
        }
    }

    static class Data {
        private int priority;
        private String subjectName;

        Data(int priority, String subjectName) {
            this.priority = priority;
            this.subjectName = subjectName;
        }

        int getPriority() {
            return priority;
        }

        String getSubjectName() {
            return subjectName;
        }

        void setPriority(int newPriority) {
            this.priority = newPriority;
        }
    }
}
