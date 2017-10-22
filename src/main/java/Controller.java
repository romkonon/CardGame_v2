import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.util.HashMap;
import java.util.List;

public class Controller {
    @FXML
    FlowPane hand;
    @FXML
    FlowPane controls;
    @FXML
    GridPane field;

    @FXML
    Button redraw;

    public void initialize(){
        field.add(CardGrid.getCardGrid().getNode(),0,0);
        hand.getChildren().add(createHandPane(CardGrid.getCardGrid().getCardPane()));
        cardHashMap = new HashMap<>();
        CardGrid.getCardGrid().setCardsInfo(cardPane,cardHashMap);
        fillHand();
    }
    private FlowPane cardPane = null;
    private HashMap<String,Card> cardHashMap;

    private FlowPane createHandPane(final Pane cardElementPane){
        if (!(cardPane == null))
            return cardPane;
        cardPane = new FlowPane();
        cardPane.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if(db.hasString()){
                String nodeId = db.getString();
                ImageView card = (ImageView) cardElementPane.lookup("#" + nodeId);
                if (card!=null){
                    cardElementPane.getChildren().remove(card);
                    cardPane.getChildren().add(card);
                    success=true;
                }
                cardHashMap.get(nodeId).deactivate();
            }
            event.setDropCompleted(success);
            event.consume();
        });
        cardPane.setOnDragOver(event -> {
            if (event.getGestureSource()!=cardPane && event.getDragboard().hasString()){
                event.acceptTransferModes(TransferMode.MOVE);
            }
            event.consume();
        });
        return cardPane;
    }
    private  void fillHand(){
        CardListBuilder cardListBuilder = new CardListBuilder();
        if(cardPane == null)
            throw new IllegalStateException("Should call getCards() before populating!");
        List<Card> cards = cardListBuilder.getCardList();
        cards.stream().map(card -> {
            cardPane.getChildren().add(card.getNode());
            return card;
        }).forEach(card -> {cardHashMap.put(card.getImageViewId(),card);
        });
    }
}
