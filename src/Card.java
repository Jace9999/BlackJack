import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/*
 * @Author Jun Zhu
 * @Description
 * Card class represent a card entity in the game
 * Each card contains two attributes which are card suit and card value
 * @Date  2022/10/10
 **/
public class Card {
    private CardSuit suit;
    private CardValue value;

    public Card(CardSuit suit, CardValue value) {
        this.suit = suit;
        this.value = value;
    }

    public Card(){

    }

    /*
     * @Author Jun Zhu
     * @Description
     * initiate a new deck for the game
     * @Date  2022/10/10
     * @Param []
     * @return java.util.List<Card>
     **/
    public List<Card> initialCards(){
        List<Card> deck = new ArrayList<>();
        for(int i =0;i<2;i++){
            for(CardSuit suit: CardSuit.values()){
                for(CardValue value: CardValue.values()){
                    Card card = new Card(suit, value);
                    deck.add(card);
                }
            }
        }
        return deck;
    }

    /*
     * @Author Jun Zhu
     * @Description
     * Use Random to reshuffle the deck
     * @Date  2022/10/10
     * @Param [java.util.List<Card>]
     * @return void
     **/
    public void reshuffledCards(List<Card> deck){
        Random random = new Random();
        for(int i = 0; i<deck.size(); i++){
            int toExchange = random.nextInt(deck.size());
            Card tempCard = deck.get(i);
            deck.set(i, deck.get(toExchange));
            deck.set(toExchange, tempCard);
        }
    }

    @Override
    public String toString() {
        return "Card{" +
                "suit=" + suit +
                ", value=" + value +
                '}';
    }

    public CardSuit getSuit() {
        return suit;
    }

    public void setSuit(CardSuit suit) {
        this.suit = suit;
    }

    public CardValue getValue() {
        return value;
    }

    public void setValue(CardValue value) {
        this.value = value;
    }
}
