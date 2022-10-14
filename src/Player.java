import java.util.List;

public class Player {

    private String name;
    private int money;
    private int betForThisRound;

    private List<Card> cardList;

    private int cardScore;

    private int cardAlternateScore;
    private boolean isBanker;

    private boolean containsAce;

    private boolean isBustThisRound;

    private boolean continueBetThisRound;


    public Player(String name, int money, int betForThisRound, List<Card> cardList, int cardScore, int cardAlternateScore,
                  boolean isBanker, boolean continueBetThisRound, boolean containsAce, boolean isBustThisRound) {
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
