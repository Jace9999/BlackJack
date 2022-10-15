import java.util.List;

/*
 * @Author Jun Zhu
 * @Description
 * Player class represent a player entity in the game
 * <String name> attribute represents the name of player
 * <int money> attribute represents current money of player
 * <int betForThisRound> represents how much money this player bet for this round
 * <List<Card> cardList> represents cards in the player's hand, it refreshes every round
 * <int cardScore> represents the total score of cards in player's hand
 * <int cardAlternateScore> represents a different score of player's card if player has Ace in the card
 * <boolean isBanker> represents if current player is the banker in current round
 * @Date  2022/10/10
 **/
public class TriantaEnaPlayer extends Player{


    private int money;
    private int betForThisRound;

    private List<Card> cardList;

    private int cardScore;

    private int cardAlternateScore;
    private boolean isBanker;

    private boolean containsAce;

    private boolean isBustThisRound;

    private boolean continueBetThisRound;
    public TriantaEnaPlayer(){}

    public TriantaEnaPlayer(String name, int money, int betForThisRound, List<Card> cardList, int cardScore, int cardAlternateScore,
                            boolean isBanker, boolean continueBetThisRound, boolean containsAce, boolean isBustThisRound) {
        super(name);
        this.money = money;
        this.betForThisRound = betForThisRound;
        this.cardList = cardList;
        this.cardScore = cardScore;
        this.cardAlternateScore = cardAlternateScore;
        this.isBanker = isBanker;
        this.containsAce = containsAce;
        this.continueBetThisRound = continueBetThisRound;
        this.isBustThisRound = isBustThisRound;
    }

    /*
     * @Author Jun Zhu
     * @Description
     * Decide if current player is bust, and set card score for player
     * @Date  2022/10/10
     * @Param []
     * @return void
     **/
    public void setCurrentCardScore(){
        int sum =0;
        int alternateSum = 0;
        for(Card card :getCardList()){
            if (isContainsAce() && card.getValue().equals(CardValue.ACE)){
                sum += card.getValue().getValue();
                alternateSum += 1;
                setContainsAce(false);
            }else{
                sum += card.getValue().getValue();
                alternateSum += card.getValue().getValue();
            }
        }
        if(alternateSum != sum){
            setContainsAce(true);
            setCardAlternateScore(alternateSum);
            if(alternateSum > 31 && sum > 31){
                setBustThisRound(true);
            }
        }else{
            if(sum > 31){
                setBustThisRound(true);
            }
        }
        setCardScore(sum);
        if(isContainsAce() && !isBustThisRound() && getCardScore() > 31){
            setCardScore(getCardAlternateScore());
        }
    }

    /*
     * @Author Jun Zhu
     * @Description
     * Since in round 1 all players get a card including the banker
     * And player is able to fold, but banker can not.
     * That is why I have different methods to check whether continue bet.
     * @Date  2022/10/15
     * @Param [boolean, Player, int]
     * @return int
     **/
    public int checkIfContinueBetForPlayer(boolean isRound1, TriantaEnaPlayer banker, int alivePlayerNumber){
        int betForCurrentRound = 1;
        if(isRound1){
            if(getCardScore() >= 6){
                int bankerMoney = banker.getMoney();
                int playerMoney = getMoney();
                if(bankerMoney >= (alivePlayerNumber-1) * 50 && playerMoney >= 50){
                    setBetForThisRound(50);
                }else if(bankerMoney < (alivePlayerNumber-1) * 50
                        && playerMoney >= bankerMoney / (alivePlayerNumber-1)){
                    setBetForThisRound(bankerMoney / (alivePlayerNumber-1));
                }else{
                    setBetForThisRound(playerMoney);
                }
                System.out.println(getName() + " bet " + getBetForThisRound() + " for this round");
            }else{
                printPlayerCard();
                setContinueBetThisRound(false);
                System.out.println(getName() + " get first card less than 6, so choose to fold in this round");
                betForCurrentRound = 0;
            }
        }

        if(isBustThisRound()){
            setContinueBetThisRound(false);
            betForCurrentRound = 0;
        }else{
            if(getCardScore() >= 27 ){
                setContinueBetThisRound(false);
                betForCurrentRound = 0;
            }
        }
        return betForCurrentRound;
    }


    public void checkIfContinueBetForBanker(){
        if(isBustThisRound()){
            setContinueBetThisRound(false);
        }else{
            if(getCardScore() >= 27 ){
                setContinueBetThisRound(false);
            }
        }
    }

    public void printPlayerCard(){
        System.out.print(getName() + " has card ");
        for(Card card : getCardList()){
            System.out.print( card + " ");
        }
        System.out.println();
    }

    public boolean isBustThisRound() {
        return isBustThisRound;
    }

    public void setBustThisRound(boolean bustThisRound) {
        isBustThisRound = bustThisRound;
    }

    public int getCardAlternateScore() {
        return cardAlternateScore;
    }

    public void setCardAlternateScore(int cardAlternateScore) {
        this.cardAlternateScore = cardAlternateScore;
    }

    public boolean isContainsAce() {
        return containsAce;
    }

    public void setContainsAce(boolean containsAce) {
        this.containsAce = containsAce;
    }

    public boolean isContinueBetThisRound() {
        return continueBetThisRound;
    }

    public void setContinueBetThisRound(boolean continueBetThisRound) {
        this.continueBetThisRound = continueBetThisRound;
    }

    public List<Card> getCardList() {
        return cardList;
    }

    public void setCardList(List<Card> cardList) {
        this.cardList = cardList;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getBetForThisRound() {
        return betForThisRound;
    }

    public void setBetForThisRound(int betForThisRound) {
        this.betForThisRound = betForThisRound;
    }

    public int getCardScore() {
        return cardScore;
    }

    public void setCardScore(int cardScore) {
        this.cardScore = cardScore;
    }

    public boolean isBanker() {
        return isBanker;
    }

    public void setBanker(boolean banker) {
        isBanker = banker;
    }
}
