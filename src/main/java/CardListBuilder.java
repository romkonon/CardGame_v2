import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

public class CardListBuilder {
    private List<Card> cardList;

    public List<Card> getCardList() {
        if(cardList == null){
            buildCardList();
        }
        return cardList;
    }
    private Image[] getCardImage(String cardName){
        Image[]cardImages = new Image[2];
        cardImages[0] = new Image("cardImages/preview/" + cardName);
        cardImages[1] = new Image("cardImages/active/" + cardName);
        return cardImages;
    }

    private void buildCardList() {
        cardList = new ArrayList<>();
        for(String cardName : cardNames){
            Card c = new Card(getCardImage(cardName));
            cardList.add(c);
        }
    }
    private final String[]cardNames = {
            "KClubs.png","KDiamonds.png","KHearts.png","KSpades.png"
    };
}
