import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ButtonTile extends Button {
    public String imagename;//store newest swaped iamge
    private boolean Black;
    public String original;//store origional iamge


    public ButtonTile(){
        imagename = "";
        Black = false;
        original="";

    }
    //can set its own image
    public void setImage(String name){
        //set the image of the button
        setImagename(name);
        this.setGraphic(new ImageView(new Image(getClass().getResourceAsStream(name))));
        if(name=="BLANK.png"){
            Black=true;
        }else{
            Black=false;
        }
    }
    private void setImagename(String name1){
        this.imagename = name1;
    }
    public void setOriginal(String name2){//use this manually on each set before swap
        this.original = name2;
    }

    //can tell if its black tile//set to black and return initial image



    public boolean isBlack(){
        return Black;
    }

    //reset black to origional iamge
    //use this system instead of game state


}