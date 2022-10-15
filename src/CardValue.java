/*
 * @Author Jun Zhu
 * @Description
 * Card value enum type
 * I set the default value of Card Ace is 11, but there is an alternate value for Ace if player contains Ace in the card.
 * @Date  2022/10/10
 **/
public enum CardValue {
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9),
    TEN(10),
    JACK(10),
    QUEEN(10),
    KING(10),
    ACE(11);

    private int value;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    CardValue(int value) {
        this.value = value;
    }
    CardValue() {}
}
