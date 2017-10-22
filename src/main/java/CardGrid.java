public class CardGrid {
    private static CardElement cardGrid;
    public static CardElement getCardGrid(){
        if(cardGrid == null)
            cardGrid = new CardElement();
        return cardGrid;
    }
}
