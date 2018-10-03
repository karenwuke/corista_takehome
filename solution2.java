package corista;
// this is my thinking routine of the solution to this number guessing question, I have updated my code several times
// to optimize the algorithm.
// ----------------------routine:--------------------------------
// iterate through the digits and at each digit we observe the changes on number of goat and chicken to see
// if this digit fit the position (which in the worst case o(5*10)) rather than generating number
// from 00000 to 99999. (which is o(10^5))
// ----------------------optimizations:--------------------------
// update 0. use random guess number rather than fixed number, like 12345, as initial guess
// update 1. use the chicken -> to reduce the guess number library (if the number doesn't match any digit,
// we can drop that number from upcoming guesses)
// update 2. update the initial guess to fit the update 1. (remove the repeat number to solve the problem
// that update 2 may encounter.)
// ----------------------minor modifications:--------------------
// 0. rather than directly generate the numbers from random library (like this: Math.random()*100000)
// we can randomly choose 5 digits from a 0-9 list and by this way, it's easier to control the permutations.
// 1. rather than use the random combination of 5 digis from 0-9, we can choose the random non-repeated digits
// by doing this, we can leverage the service of chicken condition and terminate the code earlier.
// 2. do not allow repeat digits at the initial guess number, to solve the ambiguity of which element credit
// for chicken number change.

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class solution2 {
    private boolean mute_stdout;

    public solution2(){
        //legacy constructor
         mute_stdout = false;
    }
    public solution2(boolean mute_stdout){
        this.mute_stdout = mute_stdout;
    }
    // digit library used to generate initial numbers.
    public static void main(String[] args){
        // get random number from library:

        //??not real 5 digits number
        //int secretint = (int) (Math.random()*100000+1);

        //int secretint = 31015;
        //String secret = String.valueOf(secretint);
        solution2 s2 = new solution2();
        String secret = s2.num_init(true);
        // initial guess

        //int gint = (int) (Math.random()*100000+1);
        //String guess = String.valueOf(gint);
        String guess = s2.num_init(false);
        s2.getAns("67890","67898"); //debug on the special case of loop times to double check the algorithm works
        //s2.getAns(guess,secret);
    }

    //get closer to the secret
    public int getAns(String guess,String secret){
        // build the guess digit library: initially from 0 to 9
        // but after the first iteration, the library may get rid of some digits which may never be used.
        ArrayList<Integer> guess_library = new ArrayList<Integer>();
        for(int i=0; i < 10; i++){
            guess_library.add(i);
        }
        int times =1;
        //change i-th digit, and see the change of the goat number. if goat number changed, the i-th number
        // is the right number in the right position.
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
                int oldC = feedback(guess,secret)[1];
                //for(int j =0; j< 10;j++){
                for(int j=0; j<guess_library.size();j++){
                    if(guess_library.get(j)+'0' == guess.charAt(4-i))
                        continue;
                    //change the guess number
                    //int gg = (g/10**(i+1))*10**(i+1)+j;
                    times++;
                    //int gg = (int) ((g/Math.pow(10,i+1))*(Math.pow(10,i+1))+j*Math.pow(10,i));
                    //start iteration from right to left <---
                    ga[4-i] = (char)(guess_library.get(j).intValue()+'0'); // int to char use ascii code to make the offset
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
                    //check the chic number
                    if(newC == 0 || newC < oldC){
                        //if new is 0 means no digit was even in the secret num
                        //also if the chic num decreased, the new digit shouldn't even be guessed anymore.
                        guess_library.remove(guess_library.get(j)); //need to be an Integer object, or will be regard as index
                        j--; //after remove the element, I need to maintain the index again.
                    }
                    //check the goat number
                    if(newG > oldG){
                        guess = gg;
                        //break innerloop;
                        j = guess_library.size();
                    }else if(newG <oldG){
                        ga=guess.toCharArray();
                        //break innerloop;
                        j = guess_library.size();
                    }
                }
            }else{
                if( ! mute_stdout){
                    System.out.println("The guess number is "+guess);
                }
                i = 5;
            }
        }
        if(! mute_stdout){
            System.out.println("After "+times +" days (guess), the secret number is found");
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

