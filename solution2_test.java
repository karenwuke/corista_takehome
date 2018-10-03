package corista;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class solution2_test {
    private ArrayList<Integer> s2days_list;
    private ArrayList<Integer> s1days_list;
    public solution2_test(){
        s2days_list = new ArrayList<Integer>();
        s1days_list = new ArrayList<Integer>();
    }
    public void run_solution2(){
        solution2 s2 = new solution2(true);
        String secret = s2.num_init(true);
        String guess = s2.num_init(false);
        int s2_times_res = s2.getAns(guess,secret);
        s2days_list.add(s2_times_res);
    }
    public void run_solution1(){
        solution s1 = new solution();
        String secret = s1.num_init(true);
        String guess = s1.num_init(false);
        int s1_times_res = s1.getAns(guess,secret);
        s1days_list.add(s1_times_res);
    }
    public static void main(String[] args){
        solution2_test s2t = new solution2_test();
        int iter_num = 1000000;
        String out_filename = "./test_res.txt";
        if(args.length > 1){
            // customize the test number and the test result dump file name
            iter_num = Integer.parseInt(args[0]);
            out_filename = args[1];
            System.out.println(String.format("iter_num: %d", iter_num));
            System.out.println(String.format("out_filename: %s", out_filename));
        }
        for(int i=0; i<iter_num; i++){
            s2t.run_solution2();
            s2t.run_solution1();
        }
        //System.out.println(s2t.s2days_list.size());
        try{
            File file = new File(out_filename);
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("s1,s2");
            bw.newLine();
            for(int i=0;i<iter_num;i++){
                bw.write(s2t.s1days_list.get(i)+","+s2t.s2days_list.get(i));
                bw.newLine();
            }
            bw.close();
        }catch (IOException e){
            //handle file exception
        }

    }
}
