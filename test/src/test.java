import java.io.*;
import java.util.*;

public class test {
    static class jew implements Comparable<jew>{
        int w;
        int v;
        public jew(int w, int v){
            this.w = w;
            this.v = v;
        }

        @Override
        public int compareTo(jew o){return o.v - this.v;}
    }
    public static void main(String[] args)throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine() , " ");

        int bagW = Integer.parseInt(st.nextToken());
        int N = Integer.parseInt(st.nextToken());

        // List<jew> bag = new ArrayList<>();
        PriorityQueue<jew> bag = new PriorityQueue<>();
        for(int i = 0; i < N; i++){
            st = new StringTokenizer(br.readLine() , " ");
            bag.add(new jew(Integer.parseInt(st.nextToken()),Integer.parseInt(st.nextToken())));
        }

        // bag.sort((o1, o2) -> o2.v - o1.v);
        int res = 0;
        while(bagW > 0){
            jew tem = bag.poll();
            if(tem.w <= bagW){
                bagW -= tem.w;
                res += tem.w*tem.v;
            }else{
                res += bagW * tem.v;
                bagW = 0;
            }
        }
        System.out.println(res);
    }
}
