import java.util.*;

public class Deck {
    private List<Player> playerList;
    private Player banker;
    private List<Card> cardList;

    private boolean noOneBet;

    private boolean round1;

    private boolean startBank;

    private int alivePlayer;

    private int roundNumber;

    public Deck() {
        this.cardList = new ArrayList<>();
        this.playerList = new ArrayList<>();
        roundNumber = 1;
        initialCards();
        reshuffledCards();
        initialPlayer();
        this.alivePlayer = this.playerList.size();
        while(alivePlayer > 2){
            System.out.println();
            System.out.println("Start round " + roundNumber);
            roundNumber++;
            noOneBet = false;
            startBank = false;
            round1 = true;
            while (!noOneBet) {
                noOneBet = true;
                sendCardForPlayer();
                round1 = false;
            }

            startBank = true;
            while (this.banker.isContinueBetThisRound()) {
                sendCardForPlayer();
            }
            dealWithContainsAcePlayer();
            beatWithBanker();
            if (banker.isBustThisRound()) {
                System.out.println(banker.getName() + " is the banker, he bust in this round, his current money is " + banker.getMoney());
            } else {
                System.out.println(banker.getName() + " is the banker, his card score is " + banker.getCardScore() + " and his current money is " + banker.getMoney());
            }
            alivePlayer = 0;
            reset();
            pickRichestPlayerAsBanker();
        }
    }

    public void reset(){
        for (Player player : this.playerList){
            if(player.getMoney() > 0){
                alivePlayer++;
                player.setContainsAce(false);
                player.setCardScore(0);
                player.setBustThisRound(false);
                player.setContinueBetThisRound(true);
                player.setCardList(new ArrayList<>());
                player.setCardAlternateScore(0);
            }else{
                player.setContinueBetThisRound(false);
            }
            player.setBanker(false);
            player.setBetForThisRound(0);
        }
        if(this.cardList.size() < 40){
            this.cardList = new ArrayList<>();
            this.initialCards();
            this.reshuffledCards();
        }
    }

    public void pickRichestPlayerAsBanker(){
        Player richest = null;
        int maxMoney = 0;
        for(Player player : playerList){
            if(player.getMoney() > maxMoney){
                richest = player;
                maxMoney = player.getMoney();
            }
        }
        this.banker = richest;
        this.banker.setBanker(true);
    }

    public void dealWithContainsAcePlayer(){
        for(Player player : this.playerList){
            if(player.isContainsAce() && !player.isBustThisRound()){
                if(player.getCardScore() > 31){
                    player.setCardScore(player.getCardAlternateScore());
                }
            }
        }
    }

    public void beatWithBanker(){
        if(this.banker.isBustThisRound()){
            for(Player player : this.playerList){
                if(!player.isBanker() && player.getBetForThisRound() > 0){
                    int betForThisRound = player.getBetForThisRound();
                    if(!player.isBustThisRound()){
                        player.setMoney(player.getMoney() + betForThisRound);
                        banker.setMoney(banker.getMoney() - betForThisRound);
                        System.out.println(player.getName() + " score is " + player.getCardScore() + " win, his current money is " + player.getMoney());
                    }else{
                        player.setMoney(player.getMoney() - betForThisRound);
                        banker.setMoney(banker.getMoney() + betForThisRound);
                        System.out.println(player.getName() + " bust in this round， his score is " + player.getCardScore() + " lose, his current money is " + player.getMoney());
                    }
                }
            }
        }else{
            for(Player player : this.playerList){
                if(!player.isBanker() && player.getBetForThisRound() > 0){
                    int betForThisRound = player.getBetForThisRound();
                    if(player.isBustThisRound()){
                        player.setMoney(player.getMoney() - betForThisRound);
                        banker.setMoney(banker.getMoney() + betForThisRound);
                        System.out.println(player.getName() + " bust in this round， his score is " + player.getCardScore() + " lose, his current money is " + player.getMoney());
                    }else if(banker.getCardScore() >= player.getCardScore()){
                        player.setMoney(player.getMoney() - betForThisRound);
                        banker.setMoney(banker.getMoney() + betForThisRound);
                        System.out.println(player.getName() + " score is " + player.getCardScore() + " lose, his current money is " + player.getMoney());
                    }else if(banker.getCardScore() < player.getCardScore()){
                        player.setMoney(player.getMoney() + betForThisRound);
                        banker.setMoney(banker.getMoney() - betForThisRound);
                        System.out.println(player.getName() + " score is " + player.getCardScore() + " win, his current money is " + player.getMoney());
                    }
                }
            }
        }
    }


    public void sendCardForPlayer(){
        for(Player player : this.playerList){
            if(player.isContinueBetThisRound()){
                if(round1 || (!player.isBanker() && player.getBetForThisRound() > 0)){
                    Card card = this.cardList.remove(0);
                    player.getCardList().add(card);
                    if(card.getValue().equals(CardValue.ACE)){
                        player.setContainsAce(true);
                    }
                    setCurrentScore(player);
                    if(round1 && player.getCardScore() >= 6 && !player.isBanker()){
                        int bankerMoney = this.banker.getMoney();
                        int playerMoney = player.getMoney();
                        if(bankerMoney >= (this.alivePlayer-1) * 50 && playerMoney >= 50){
                           player.setBetForThisRound(50);
                       }else if(bankerMoney < (this.alivePlayer-1) * 50
                                && playerMoney >= bankerMoney / (this.alivePlayer-1)){
                           player.setBetForThisRound(bankerMoney / (this.alivePlayer-1));
                       }else{
                           player.setBetForThisRound(playerMoney);
                       }
                        System.out.println(player.getName() + " bet " + player.getBetForThisRound() + " for this round");
                    }else if(round1 && !player.isBanker()){
                        System.out.println(player.getName() + " get first card less than 6, so choose to fold in this round");
                    }
                }
            }
        }
        if(startBank ){
            Card card = this.cardList.remove(0);
            this.banker.getCardList().add(card);
            if(card.getValue().equals(CardValue.ACE)){
                this.banker.setContainsAce(true);
            }
            setCurrentScore(this.banker);
        }
    }


    public void setCurrentScore(Player player){
            int sum =0;
            int alternateSum = 0;
            for(Card card:player.getCardList()){
                if (player.isContainsAce()){
                    if(card.getValue().equals(CardValue.ACE)){
                        sum += card.getValue().getValue();
                        alternateSum += 1;
                        player.setContainsAce(false);
                    }else{
                        sum += card.getValue().getValue();
                        alternateSum += card.getValue().getValue();
                    }
                }else{
                    sum += card.getValue().getValue();
                    alternateSum += card.getValue().getValue();
                }
            }
            if(alternateSum != sum){
                player.setContainsAce(true);
                player.setCardAlternateScore(alternateSum);
                if(alternateSum > 31 && sum > 31){
                    player.setBustThisRound(true);
                }
            }else{
                if(sum > 31){
                    player.setBustThisRound(true);
                }
            }
            player.setCardScore(sum);
            noOneBet = false;
            if(player.isBustThisRound()){
                player.setContinueBetThisRound(false);
            }else{
                if(player.getCardScore() >= 27){
                    player.setContinueBetThisRound(false);
                }
            }
            if(player.isContainsAce()){
                if(player.getCardAlternateScore() >= 27){
                    player.setContinueBetThisRound(false);
                }
            }
    }



    public void initialPlayer(){
        System.out.println("Please enter the number of players in this game, the banker for first round is random.");
        System.out.println("Player number should not greater than 7.");
        Scanner scanner = new Scanner(System.in);
        int playerNum = 7;
        for(int i=0;i<playerNum;i++){
            Player player = new Player("player" + i,100,0,new ArrayList<>(),
                    0,0,false, true, false, false);
            this.playerList.add(player);
        }
        int randomBanker = new Random().nextInt(this.playerList.size());
        this.banker = this.playerList.get(randomBanker);
        this.banker.setBanker(true);
        this.banker.setMoney(300);
    }

    public void initialCards(){
        for(int i =0;i<2;i++){
            for(CardSuit suit: CardSuit.values()){
                for(CardValue value: CardValue.values()){
                    Card card = new Card(suit, value);
                    this.cardList.add(card);
                }
            }
        }
    }

    public void reshuffledCards(){
        Random random = new Random();
        for(int i=0;i<this.cardList.size();i++){
            int toExchange = random.nextInt(cardList.size());
            Card tempCard = cardList.get(i);
            cardList.set(i, cardList.get(toExchange));
            cardList.set(toExchange, tempCard);
        }
    }

    public void getCurrentScore(Player player){
        noOneBet = false;
        if(player.isBustThisRound()){
            System.out.println("Current score for " + player.getName() + " is " + player.getCardScore() + " which is bust!");
            player.setContinueBetThisRound(false);
        }else{
            System.out.println("Current score for " + player.getName() + " is " + player.getCardScore());
            if(player.getCardScore() >= 27){
                player.setContinueBetThisRound(false);
            }
        }
        if(player.isContainsAce()){
            System.out.println("Since " + player.getName() + " has Ace, so the score could be " + player.getCardAlternateScore());
            if(player.getCardAlternateScore() >= 27){
                player.setContinueBetThisRound(false);
            }
        }

    }

    public void printCardSumForPlayer(){
        for(Player player : this.playerList){
            if(!player.isBanker()){
                if(player.isBustThisRound()){
                    System.out.println("Current score for " + player.getName() + " is " + player.getCardScore() + " which is bust!");
                }else{
                    System.out.println("Current score for " + player.getName() + " is " + player.getCardScore());
                    if(player.isContainsAce()){
                        System.out.println("Since " + player.getName() + " has Ace, so the score could be " + player.getCardAlternateScore());
                    }
                }
            }
        }
    }

    public void printBankerCardSum(){
        System.out.println("The banker of this round is " + banker.getName());
        if(this.banker.isBustThisRound()){
            System.out.println("Banker is bust in this round game");
        }else{
            System.out.println("Current score for " + banker.getName() + " is " + banker.getCardScore());
            if(banker.isContainsAce()){
                System.out.println("Since " + banker.getName() + " has Ace, so the score could be " + banker.getCardAlternateScore());
            }
        }
    }


    
}
