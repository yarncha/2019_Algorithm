import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClosestPair {
    public static void main(String[] args) throws IOException {
        String fileName = "data03_closest.txt";
        //읽을 파일 이름

        BufferedReader reader = new BufferedReader(new FileReader(new File(fileName)));
        List<Point> inputPoints = new ArrayList<Point>();
        String s;
        while ((s = reader.readLine()) != null) {
            String[] array = s.split(",");
            Point toInput = new Point(Double.parseDouble(array[0]), Double.parseDouble(array[1]));
            inputPoints.add(toInput);
        }
        //파일을 읽고 inputPoints에 점들을 List형식으로 담는다.

        inputPoints = mergeSort(inputPoints, 0, inputPoints.size() - 1, "x");
        //x좌표 기준으로 오름차순 정렬하고

        System.out.println("Closest distance of these points : " + findClosestPair(inputPoints));
        //x좌표 기준으로 정렬한 리스트에서 가까운 점들을 찾는 알고리즘을 수행한다.
    }

    //-----------------------
    //자료형
    public static class Point {
        private double x;
        private double y;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public double getX() {
            return this.x;
        }

        public double getY() {
            return this.y;
        }
    }

    //List에서 가장 가까운 점들을 찾아 그 거리를 리턴하는 메소드
    public static double findClosestPair(List<Point> inputPoints) {
        double minimum;
        if (inputPoints.size() <= 3) {
            switch (inputPoints.size()) {
                case 1:
                    return 0;
                case 2:
                    return distance(inputPoints.get(0), inputPoints.get(1));
                case 3:
                    double min = distance(inputPoints.get(0), inputPoints.get(1));
                    if (distance(inputPoints.get(0), inputPoints.get(2)) < min) {
                        min = distance(inputPoints.get(0), inputPoints.get(2));
                    }
                    if (distance(inputPoints.get(1), inputPoints.get(2)) < min) {
                        min = distance(inputPoints.get(1), inputPoints.get(2));
                    }
                    return min;
            }
        }
        //3이하일 경우에는 그냥 일일이 계산하고 값 리턴

        double middle = (inputPoints.get(inputPoints.size() / 2 - 1).getX() + inputPoints.get(inputPoints.size() / 2).getX()) / 2;
        //수가 가장 반반으로 나누어지는 x축을 찾아 반으로 나눈후
        double minOfLeft = findClosestPair(inputPoints.subList(0, inputPoints.size() / 2));
        double minOfRight = findClosestPair(inputPoints.subList(inputPoints.size() / 2, inputPoints.size()));
        //재귀적으로 그 반반에 대해 최소를 각각 구한다.
        if (minOfLeft < minOfRight) {
            minimum = minOfLeft;
        } else {
            minimum = minOfRight;
        }
        //양쪽의 최소중 더 최소인 것을 구한다.

        List<Point> nearPoints = new ArrayList<Point>();
        for (int i = inputPoints.size() / 2 - 1; i >= 0; i--) {
            if (inputPoints.get(i).getX() > middle - minimum) {
                nearPoints.add(inputPoints.get(i));
            } else {
                break;
            }
            //반으로 나누었던 x축에서 -최소 범위만큼 안에 있는 원소를 새로운 point리스트에 넣고
            //범위를 벗어나는 순간 break;하면서 리스트를 만든다.
        }
        for (int i = inputPoints.size() / 2; i < inputPoints.size(); i++) {
            if (inputPoints.get(i).getX() < middle + minimum) {
                nearPoints.add(inputPoints.get(i));
            } else {
                break;
            }
            //위와 같은 작업을 +최소 범위만큼 수행한다.
        }
        //n/2에서 최소값만큼 양 옆에 있는 좌표를 찾아서 nearPoints에 저장하고
        nearPoints = mergeSort(nearPoints, 0, nearPoints.size() - 1, "y");
        //이 리스트들을 y값 기준으로 sort한 후

        for (int i = 0; i < nearPoints.size() - 1; i++) {
            if (nearPoints.get(i + 1).getY() - nearPoints.get(i).getY() < minimum) {
                //현재 nearPoints는 y값 기준으로 정렬되어 있으므로 앞 인덱스부터 두개씩 비교한다.
                double temp = distance(nearPoints.get(i), nearPoints.get(i + 1));
                //이 때 y값을 먼저 비교하며 이것이 minimum보다 클 경우는 어차피 거리가 minimum보다 크게 나오므로 계산하지 않는다.
                if (temp < minimum) {
                    minimum = temp;
                }
            }
        }
        return minimum;
    }

    //거리를 계산하는 메소드
    public static double distance(Point first, Point second) {
        return Math.sqrt((second.getX() - first.getX()) * (second.getX() - first.getX()) + (second.getY() - first.getY()) * (second.getY() - first.getY()));
    }

    //정렬을 위한 머지소트
    public static List<Point> mergeSort(List<Point> inputList, int startIndex, int endIndex, String xOrY) {
        if (startIndex < endIndex) {
            int middleIndex = (endIndex + startIndex) / 2;
            inputList = mergeSort(inputList, startIndex, middleIndex, xOrY);
            inputList = mergeSort(inputList, middleIndex + 1, endIndex, xOrY);
            return merge(inputList, startIndex, middleIndex, endIndex, xOrY);
        }
        return inputList;
    }

    public static List<Point> merge(List<Point> inputList, int startIndex, int middleIndex, int endIndex, String xOrY) {
        List<Point> resultList = new ArrayList<Point>();
        resultList.addAll(inputList);

        int resultPosition = startIndex;
        int array1Position = startIndex;
        int array2Position = middleIndex + 1;

        while ((array1Position <= middleIndex) && (array2Position <= endIndex)) {
            if (xOrY.equals("x")) {
                if (inputList.get(array1Position).getX() <= inputList.get(array2Position).getX()) {
                    resultList.set(resultPosition, inputList.get(array1Position));
                    array1Position++;
                } else {
                    resultList.set(resultPosition, inputList.get(array2Position));
                    array2Position++;
                }
            } else {
                if (inputList.get(array1Position).getY() <= inputList.get(array2Position).getY()) {
                    resultList.set(resultPosition, inputList.get(array1Position));
                    array1Position++;
                } else {
                    resultList.set(resultPosition, inputList.get(array2Position));
                    array2Position++;
                }
            }
            resultPosition++;
        }

        while (array1Position <= middleIndex) {
            resultList.set(resultPosition, inputList.get(array1Position));
            array1Position++;
            resultPosition++;
        }       //앞 배열이 남아 있으면 그 배열을 전부 결과 배열에 옮겨 담는다

        while (array2Position <= endIndex) {
            resultList.set(resultPosition, inputList.get(array2Position));
            array2Position++;
            resultPosition++;
        }       //뒤 배열도 마찬가지

        return resultList;
    }
}
