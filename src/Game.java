import java.util.Scanner;

/*
 * @Author Jun Zhu
 * @Description 
 * This class contains main method which is the entry of entire black jack game.
 * @Date  2022/10/10
 **/
public class Game {

    /*
     * @Author Jun Zhu
     * @Description
     * This main class initiate a new GameProcessor, and defined the player number by user.
     * @Date  2022/10/10
     * @Param [java.lang.String[]]
     * @return void
     **/
    public static void main(String[] args) {
        boolean playGame = true;
        Scanner scanner = new Scanner(System.in);
        while(playGame){
            boolean loop = true;
            while(loop){
                loop = false;
                System.out.println("Please enter player number, banker is included.");
                System.out.println("Maximum number is 7, Minimum number is 3");
                String number = scanner.nextLine();
                for(char ch : number.toCharArray()){
                    if(!Character.isDigit(ch)){
                        loop = true;
                        break;
                    }
                }
                if(!loop ){
                    int num = Integer.parseInt(number);
                    if(num <= 7 && num >= 3){
                        TriantaEnaGameOperation deck = new TriantaEnaGameOperation(num);
                    }else{
                        loop = true;
                    }
                }

            }
            boolean correctInput = false;
            while(!correctInput){
                System.out.println();
                System.out.println("Enter N to Exit game, Enter Y to continue play.");
                String s = scanner.nextLine();
                if(s.equals("N")){
                    correctInput = true;
                    playGame = false;
                }else if(s.equals("Y")){
                    correctInput = true;
                    playGame = true;
                }
            }
        }
    }
}
