import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;

import java.util.Map;

public class CardElement {
    private final Pane cardElementPane;
    private final ImageView cardElementImage;
    private Pane cardPane;
    private Map<String,Card> cards;
    public void setCardsInfo(Pane p, Map<String,Card> m){
        cardPane = p;
        cards = m;
    }
    public Pane getCardPane(){
        return cardPane;
    }
    CardElement(){
        cardElementPane = new Pane();
        cardElementImage = new ImageView(new Image("background.png"));
        cardElementImage.fitHeightProperty().bind(cardElementPane.heightProperty());
        cardElementPane.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if(db.hasString()){
                String nodeId = db.getString();
                ImageView card = (ImageView) cardPane.lookup("#" + nodeId);
                if (card!=null){
                    cardPane.getChildren().remove(card);
                    cardElementPane.getChildren().add(card);
                    // move from hand to filed
                    card.relocate(0,0);
                    card.fitHeightProperty().bind(cardElementPane.heightProperty());
                    card.setPreserveRatio(true);
                    success = true;
                }
                //remove from deck
                cards.get(nodeId).deactivate();
            }
            event.setDropCompleted(success);
            event.consume();
        });
        cardElementPane.setOnDragOver(event -> {
            if(event.getGestureSource() != cardElementImage && event.getDragboard().hasString()){
                event.acceptTransferModes(TransferMode.MOVE);
            }
            event.consume();
        });
        cardElementPane.getChildren().add(cardElementImage);
      //  cardElementPane.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
      //  cardElementPane.setPadding(new Insets(10.0));
    }
    public Node getNode(){
        return cardElementPane;
    }
}
