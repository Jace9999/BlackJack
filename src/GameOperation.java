import java.util.List;

/**
 * @Author: Jun Zhu
 * @Date: 2022/10/10
 * @Description: Because every game has a game processor class, this class is the parent class of the game processor class in every game
 */
public class GameOperation {
    protected int playerNumber;

    public GameOperation(){
    }

    public GameOperation(int playerNumber){
        this.playerNumber = playerNumber;
    }
}
