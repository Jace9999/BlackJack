import java.util.*;

public class Deck {
    private List<Player> playerList;
    private Player banker;
    private List<Card> cardList;

    private boolean noOneBet;

    public Deck() {
        noOneBet = false;
        this.cardList = new ArrayList<>();
        this.playerList = new ArrayList<>();
        initialCards();
        reshuffledCards();
        initialPlayer();
        while(!noOneBet){
            noOneBet = true;
            sendCardForPlayer();
        }
        printCardSumForPlayer();
    }


    public void sendCardForPlayer(){
        for(Player player : this.playerList){
            if(player.isBetInThisRound() && !player.isBanker()){
                Card card = this.cardList.remove(0);
                player.getCardList().add(card);
                if(card.getValue().equals(CardValue.ACE)){
                    player.setContainsAce(true);
                }
                setCurrentScore(player);
                getCurrentScore(player);
                if(isBust(player)){

                }else{
                    System.out.println("Do " + player.getName() +  " wanna take another card ?");
                }
            }
        }
    }

    public boolean isBust(Player player){

    }

    public void getCurrentScore(Player player){
        if(player.isContainsAce()){
            System.out.println("Since " + player.getName() + " contains Ace, so current score could be: " + player.getCardAlternateScore());
        }
        System.out.println(player.getName() + " current score is: " + player.getCardScore());
    }

    public void setCurrentScore(Player player){
            int sum =0;
            int alternateSum = 0;
            for(Card card:player.getCardList()){
                if (player.isContainsAce()){
                    if(card.getValue().equals(CardValue.ACE)){
                        sum += card.getValue().getValue();
                        alternateSum += 11;
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
            }
            player.setCardScore(sum);
    }

    public void printCardSumForPlayer(){
        for(Player player : this.playerList){
            if(!player.isBanker()){
                if(player.isBustForThisRound()){
                    System.out.println("Current score for " + player.getName() + " is " + player.getCardScore() + " which is bust!");
                }else{
                    System.out.println("Current score for " + player.getName() + " is " + player.getCardScore());
                }
                if(player.isContainsAce()){
                    System.out.println("Since " + player.getName() + " has Ace, so the score could be " + player.getCardAlternateScore());
                }
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
        for(CardSuit suit: CardSuit.values()){
            for(CardValue value: CardValue.values()){
                Card card = new Card(suit, value);
                this.cardList.add(card);
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


    
}
