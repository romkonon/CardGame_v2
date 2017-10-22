import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;

public class Card {
    private final Image cardImage;
    private final Image highlightImage;
    private final ImageView currentCardImage;
    public String getImageViewId(){
        return currentCardImage.getId();
    }
    private void activate(){
        currentCardImage.setImage(highlightImage);
    }
    public void deactivate(){
        currentCardImage.setImage(cardImage);
    }
    public Node getNode(){
        return currentCardImage;
    }
    Card(Image[] image){
        this.cardImage = image[0];
        this.highlightImage = image[1];
        currentCardImage = new ImageView();
        currentCardImage.setImage(this.cardImage);
        currentCardImage.setId(this.getClass().getSimpleName()+System.currentTimeMillis());

        currentCardImage.setOnDragDetected(event -> {
            activate();
            Dragboard db = currentCardImage.startDragAndDrop(TransferMode.MOVE);
            ClipboardContent content = new ClipboardContent();
            content.putString(currentCardImage.getId());
            db.setContent(content);
            event.consume();
        });
    }
}
