package corista;

import java.util.HashMap;
import java.util.Random;


public class solution {
    private boolean mute_stdout = true;
    public static void main(String[] args){
        solution s1 = new solution();
        //----------legacy bad initialize----------------
        /*
        //??not real 5 digits number
        int secretint = (int) (Math.random()*100000+1);
        //int secretint = 31015;
        String secret = String.valueOf(secretint);
        int gint = (int) (Math.random()*100000+1);
        String guess = String.valueOf(gint);
        */
        // new initialize:
        String secret = s1.num_init(true);
        String guess = s1.num_init(true);
        new solution().getAns(guess,secret);
    }

    //get closer to the secret
    public int getAns(String guess,String secret){
        int times =1;
        //change i-th digit, and see the change of the goat number. if goat number is reduced, the i-th number is the right number in the right position
        //if the goat number
        char[] ga = guess.toCharArray();
        if(!mute_stdout){
            System.out.println("The secret number is "+secret);
            System.out.println("It's "+times+" times guess");
            System.out.println("The guess number is "+guess);
            System.out.println("The number of goat is "+feedback(guess,secret)[0]);
            System.out.println("The number of chicken is "+feedback(guess,secret)[1]);
        }
        for(int i=0; i < guess.length();i++){
            if(!guess.equals(secret)){
                int oldG = feedback(guess,secret)[0];
                for(int j =0; j< 10;j++){
                    //change the guess number
                    //int gg = (g/10**(i+1))*10**(i+1)+j;
                    times++;
                    //???
                    //int gg = (int) ((g/Math.pow(10,i+1))*(Math.pow(10,i+1))+j*Math.pow(10,i));
                    ga[4-i] = (char)(j+'0');
                    String gg = String.valueOf(ga);
                    int[] newfd = feedback(gg,secret);
                    int newG = newfd[0];
                    int newC = newfd[1];
                    if(!mute_stdout){
                        System.out.println("It's "+times+" times guess");
                        System.out.println("The guess number is "+gg);
                        System.out.println("The number of goat is "+newG);
                        System.out.println("The number of chicken is "+newC);
                    }
                    if(newG > oldG){
                        guess = gg;
                        j=10;
                    }else if(newG <oldG){
                        ga=guess.toCharArray();
                        j =10;
                    }
                }
            }else{
                if(!mute_stdout){
                    System.out.println("The guess number is "+guess);
                    System.out.println("After "+times +" days (guess), the secret number is find");
                }
                i = 5;
            }
        }
        if(!mute_stdout){
            System.out.println("After "+times +" days (guess), the secret number is find");
        }
        return times;
    }

    //the farmer's feedback(reward)
    private int[] feedback(String guess, String secret){
        int[] fd = new int[2];//the first int is the number of goat, the second number is the number of chicken
        int goat = 0;
        int chic = 0;
        int common =0;
        HashMap<Character,Integer> hms = new HashMap<>();
        for(int i = 0; i < guess.length(); i++){
            //right number in the right position get 1 goat
            if(guess.charAt(i)==secret.charAt(i)){
                goat++;
            }
            if(hms.containsKey(secret.charAt(i))){
                hms.put(secret.charAt(i),hms.get(secret.charAt(i))+1);
            }else {
                hms.put(secret.charAt(i),1);
            }
        }
        for(int i =0; i < guess.length(); i++){
            if(hms.containsKey(guess.charAt(i)) && hms.get(guess.charAt(i))>0){
                common++;
                hms.put(guess.charAt(i),hms.get(guess.charAt(i))-1);

            }
        }
        chic =common-goat;
        fd[0] = goat;
        fd[1] = chic;
//        System.out.println("The secret number is "+secret);
//        System.out.println("The guess number is "+guess);
//        System.out.println("The number of goat is "+goat);
//        System.out.println("The number of chicken is "+chic);
        return fd;
    }

    public String num_init(boolean allowRepeat){
        Random rand = new Random();
        String num = "";
        while(num.length() < 5){
            int tmpDigit = rand.nextInt((9 - 0) + 1) + 0;
            String strDigit = String.valueOf(tmpDigit);
            if(!allowRepeat && num.contains(strDigit)){
                continue;
            }
            num = num+strDigit;
        }
        return num;
    }

    private void swap(char[] ga, int i, int j){
        char temp = ga[i];
        ga[i] = ga[j];
        ga[j] = temp;
    }
}
