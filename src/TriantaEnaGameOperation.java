import java.util.*;


/*
 * @Author Jun Zhu
 * @Date  2022/10/10
 * @Description
 * This class is a core game processing class, including methods that control the whole game process.
 * In my design, I set this game entire auto-control by program.
 * The user input only define the player number, then the program would like to initiate a new deck and players.
 * After that, program chooses a random banker to start the first round game.
 * if players first card is less than 6, they would like to fold in this round.
 * For players who do not fold, they get card and bet for the current round.
 * In my design, if player choose to bet for current round,
 * both of Players and dealers only give up taking cards if their cards exceeds or equals 27 points ,or bust.
 * So there are only two result for the banker and player who does not fold, the first one is cards over or equals to 27 or bust!
 * Then the program calculate the result for banker and player, adding money or removing money.
 * After that, the program chooses the richest player as next round banker.
 * Keep looping this way every round until there are only two players left (have money) in the game.
 **/
public class TriantaEnaGameOperation extends GameOperation{
    private TriantaEnaPlayer banker;

    private List<Card> deck;

    private Card card;

    private int betPlayerNumber;

    private boolean isRound1;

    private int alivePlayer;

    private int roundNumber;

    private List<TriantaEnaPlayer> playerList;


    /*
     * @Author Jun Zhu
     * @Description
     * initiate required attributes, deck and player
     * @Date  2022/10/10
     * @Param [int]
     * @return
     **/
    public TriantaEnaGameOperation(int playerNumber) {
        super(playerNumber);
        playerList = new ArrayList<>();
        card = new Card();
        this.deck = card.initialCards();
        card.reshuffledCards(this.deck);
        this.alivePlayer = playerNumber;
        roundNumber = 1;
        initialPlayer();
        initialFirstRandomBanker();
        startGame();
    }

    /*
     * @Author Jun Zhu
     * @Description
     * control every round game process, if alive players less than 3 then game over
     * @Date  2022/10/10
     * @Param []
     * @return void
     **/
    public void startGame(){
        while(alivePlayer > 2) {
            System.out.println();
            System.out.println("Start round " + roundNumber);
            roundNumber++;
            isRound1 = true;
            sendCard(banker);
            betPlayerNumber = alivePlayer;
            while (betPlayerNumber > 0) {
                betPlayerNumber = 0;
                for (TriantaEnaPlayer triantaEnaPlayer : this.playerList) {
                    if (triantaEnaPlayer.isContinueBetThisRound() && !triantaEnaPlayer.isBanker()) {
                        sendCard(triantaEnaPlayer);
                        triantaEnaPlayer.setCurrentCardScore();
                        int playerBet = triantaEnaPlayer.checkIfContinueBetForPlayer(isRound1, this.banker, this.alivePlayer);
                        betPlayerNumber += playerBet;
                    }
                }
                isRound1 = false;
            }
            while (banker.isContinueBetThisRound()) {
                sendCard(banker);
                banker.setCurrentCardScore();
                banker.checkIfContinueBetForBanker();
            }
            beatWithBanker();
            printBankerScore();
            reset();
            pickRichestPlayerAsBanker();
        }
    }

    public void printBankerScore(){
        if (banker.isBustThisRound()) {
            System.out.println(banker.getName() + " is the banker, he bust in this round, his current money is " + banker.getMoney());
        } else {
            System.out.println(banker.getName() + " is the banker, his card score is " + banker.getCardScore() + " and his current money is " + banker.getMoney());
        }
        this.banker.printPlayerCard();
    }

    
    /*
     * @Author Jun Zhu
     * @Description
     * reset the data for each player after each round
     * @Date  2022/10/10
     * @Param []
     * @return void
     **/
    public void reset(){
        alivePlayer = 0;
        for (TriantaEnaPlayer triantaEnaPlayer : this.playerList){
            if(triantaEnaPlayer.getMoney() > 0){
                alivePlayer++;
                triantaEnaPlayer.setContainsAce(false);
                triantaEnaPlayer.setCardScore(0);
                triantaEnaPlayer.setBustThisRound(false);
                triantaEnaPlayer.setContinueBetThisRound(true);
                triantaEnaPlayer.setCardList(new ArrayList<>());
                triantaEnaPlayer.setCardAlternateScore(0);
            }else{
                triantaEnaPlayer.setContinueBetThisRound(false);
            }
            triantaEnaPlayer.setBanker(false);
            triantaEnaPlayer.setBetForThisRound(0);
        }
        if(this.deck.size() < 40){
            this.deck = card.initialCards();
            card.reshuffledCards(this.deck);
        }
    }

    public TriantaEnaGameOperation(){}

    public void pickRichestPlayerAsBanker(){
        TriantaEnaPlayer richest = null;
        int maxMoney = 0;
        for(TriantaEnaPlayer triantaEnaPlayer : playerList){
            if(triantaEnaPlayer.getMoney() > maxMoney){
                richest = triantaEnaPlayer;
                maxMoney = triantaEnaPlayer.getMoney();
            }
        }
        this.banker = richest;
        this.banker.setBanker(true);
    }

    /*
     * @Author Jun Zhu
     * @Description
     * deal the results of the current round and add money to the winner.
     * @Date  2022/10/10
     * @Param []
     * @return void
     **/
    public void beatWithBanker(){
        if(this.banker.isBustThisRound()){
            for(TriantaEnaPlayer triantaEnaPlayer : this.playerList){
                if(!triantaEnaPlayer.isBanker() && triantaEnaPlayer.getBetForThisRound() > 0){
                    int betForThisRound = triantaEnaPlayer.getBetForThisRound();
                    if(!triantaEnaPlayer.isBustThisRound()){
                        triantaEnaPlayer.setMoney(triantaEnaPlayer.getMoney() + betForThisRound);
                        banker.setMoney(banker.getMoney() - betForThisRound);
                        System.out.println(triantaEnaPlayer.getName() + " score is " + triantaEnaPlayer.getCardScore() + " win, his current money is " + triantaEnaPlayer.getMoney());
                    }else{
                        triantaEnaPlayer.setMoney(triantaEnaPlayer.getMoney() - betForThisRound);
                        banker.setMoney(banker.getMoney() + betForThisRound);
                        System.out.println(triantaEnaPlayer.getName() + " bust in this round， his score is " + triantaEnaPlayer.getCardScore() + " lose, his current money is " + triantaEnaPlayer.getMoney());
                    }
                    triantaEnaPlayer.printPlayerCard();
                }
            }
        }else{
            for(TriantaEnaPlayer triantaEnaPlayer : this.playerList){
                if(!triantaEnaPlayer.isBanker() && triantaEnaPlayer.getBetForThisRound() > 0){
                    int betForThisRound = triantaEnaPlayer.getBetForThisRound();
                    if(triantaEnaPlayer.isBustThisRound()){
                        triantaEnaPlayer.setMoney(triantaEnaPlayer.getMoney() - betForThisRound);
                        banker.setMoney(banker.getMoney() + betForThisRound);
                        System.out.println(triantaEnaPlayer.getName() + " bust in this round， his score is " + triantaEnaPlayer.getCardScore() + " lose, his current money is " + triantaEnaPlayer.getMoney());
                    }else if(banker.getCardScore() >= triantaEnaPlayer.getCardScore()){
                        triantaEnaPlayer.setMoney(triantaEnaPlayer.getMoney() - betForThisRound);
                        banker.setMoney(banker.getMoney() + betForThisRound);
                        System.out.println(triantaEnaPlayer.getName() + " score is " + triantaEnaPlayer.getCardScore() + " lose, his current money is " + triantaEnaPlayer.getMoney());
                    }else if(banker.getCardScore() < triantaEnaPlayer.getCardScore()){
                        triantaEnaPlayer.setMoney(triantaEnaPlayer.getMoney() + betForThisRound);
                        banker.setMoney(banker.getMoney() - betForThisRound);
                        System.out.println(triantaEnaPlayer.getName() + " score is " + triantaEnaPlayer.getCardScore() + " win, his current money is " + triantaEnaPlayer.getMoney());
                    }
                    triantaEnaPlayer.printPlayerCard();
                }
            }
        }
    }


    public void sendCard(TriantaEnaPlayer triantaEnaPlayer){
        Card sendCard = this.deck.remove(0);
        triantaEnaPlayer.getCardList().add(sendCard);
        if(sendCard.getValue().equals(CardValue.ACE)){
            triantaEnaPlayer.setContainsAce(true);
        }
    }

    public void initialPlayer(){
        for(int i=0;i<this.playerNumber;i++){
            TriantaEnaPlayer triantaEnaPlayer = new TriantaEnaPlayer("player" + i,100,0,new ArrayList<>(),
                    0,0,false, true, false, false);
            this.playerList.add(triantaEnaPlayer);
        }
    }

    public void initialFirstRandomBanker(){
        int randomBanker = new Random().nextInt(this.playerList.size());
        this.banker = this.playerList.get(randomBanker);
        this.banker.setBanker(true);
        this.banker.setMoney(300);
    }
    
}
