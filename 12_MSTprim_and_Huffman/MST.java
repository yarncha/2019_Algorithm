import java.util.Hashtable;
import java.util.PriorityQueue;

public class MST {

    public static void main(String[] args) {
        Hashtable<String, Boolean> isConnected = new Hashtable<String, Boolean>();
        PriorityQueue<Edge> edges = new PriorityQueue<Edge>();
        //정점들의 연결 여부를 가지고 있을 points테이블과, 간선과 간선 사이의 가중치를 저장하고 있을 edges 정의

        isConnected = initPoints(isConnected);
        edges = initEdges(edges);
        //초기화 및 값 넣기

        prim(isConnected, edges);
        //프림 알고리즘 수행 및 프린트
    }

    //간선의 정보를 담을 edge클래스. String으로 각 정점의 이름과, int로 정점 사이의 거리를 가지고 있다.
    static class Edge implements Comparable<Edge> {
        String startPoint;      //시작점
        String endPoint;        //끝점
        int val;                //가중치

        //생성자
        Edge(String startPoint, String endPoint, int val) {
            this.startPoint = startPoint;
            this.endPoint = endPoint;
            this.val = val;
        }

        @Override
        public int compareTo(Edge other) {
            return val <= other.val ? -1 : 1;
        }
        //edge들의 PriorityQueue에서 원소를 꺼낼 때 val, 즉 정점 사이의 거리가 가장 작은 객체를 꺼낼 수 있게 한다.
    }

    //값을 넣어주는 메소드
    private static Hashtable<String, Boolean> initPoints(Hashtable<String, Boolean> isConnected) {
        isConnected.put("a", false);
        isConnected.put("b", false);
        isConnected.put("c", false);
        isConnected.put("d", false);
        isConnected.put("e", false);
        isConnected.put("f", false);
        isConnected.put("g", false);
        isConnected.put("h", false);
        isConnected.put("i", false);
        return isConnected;
    }

    private static PriorityQueue<Edge> initEdges(PriorityQueue<Edge> edges) {
        edges.add(new Edge("a", "b", 4));
        edges.add(new Edge("b", "c", 8));
        edges.add(new Edge("c", "d", 7));
        edges.add(new Edge("d", "e", 9));
        edges.add(new Edge("e", "f", 10));
        edges.add(new Edge("d", "f", 14));
        edges.add(new Edge("c", "f", 4));
        edges.add(new Edge("a", "h", 8));
        edges.add(new Edge("i", "c", 2));
        edges.add(new Edge("i", "g", 6));
        edges.add(new Edge("h", "g", 1));
        edges.add(new Edge("g", "f", 2));
        edges.add(new Edge("h", "i", 7));
        edges.add(new Edge("b", "h", 11));
        return edges;
    }

    //prim 알고리즘을 통해 MST를 구하도록 해 주는 메소드
    public static void prim(Hashtable<String, Boolean> isConnected, PriorityQueue<Edge> edges) {
        if (edges.size() > 0) {     //원소가 한개 이상일때만 수행가능
            int sum = 0;
            //총 가중치 합을 저장할 sum
            Edge start = edges.poll();
            //처음에 한 원소를 꺼냄
            isConnected.replace(start.startPoint, true);
            isConnected.replace(start.endPoint, true);
            //해당 원소에 해당하는 정점들을 연결되었다고 표시해주고
            sum += start.val;
            //합도 더해주고
            System.out.println(start.startPoint + "에서 시작합니다.");
            System.out.println("> " + start.startPoint + "와 " + start.endPoint + " 연결 -> 두 정점의 가중치 " + start.val);
            //해당 정점으로부터 계속 연결된 선을 늘려나감

            while (edges.size() > 0) {      //edges에 저장된 것이 없을 때까지 반복
                PriorityQueue<Edge> temp = new PriorityQueue<Edge>();
                while (edges.size() > 0) {
                    Edge current = edges.poll();
                    temp.add(current);
                    //하나를 꺼내고 임시 보관함에도 저장
                    if ((!(isConnected.get(current.startPoint) && isConnected.get(current.endPoint))) && (isConnected.get(current.startPoint) || isConnected.get(current.endPoint))) {
                        //만약 이 꺼낸 원소가 cycle을 형성하지 않고,
                        //두 정점 중 하나는 연결되어 있을 경우
                        if (isConnected.get(current.startPoint)) {
                            isConnected.replace(current.endPoint, true);
                        } else {
                            isConnected.replace(current.startPoint, true);
                        }
                        //둘중 연결되어 있지 않던 정점을 연결된 상태로 바꿔주고

                        sum += current.val;
                        //간선 가중치만큼 더해줌

                        edges.addAll(temp);
                        temp.clear();
                        //temp에 잠깐 저장되어 있던 간선들을 다시 edges로 옮김

                        System.out.println("> " + current.startPoint + "와 " + current.endPoint + " 연결 -> 두 정점의 가중치 " + current.val);
                    }
                }
            }

            System.out.println("MST의 총 가중치 : " + sum);
        }
    }
}

