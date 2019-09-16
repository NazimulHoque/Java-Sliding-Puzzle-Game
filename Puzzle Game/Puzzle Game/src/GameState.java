
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;



public class GameState{
    String[][] Pets;
    String[][] Numbers;
    String[][] Lego;
    String[][] Scenery;
    String hiddenImage;

    public GameState(){
        //3 string arrays that store the image name in the correct place
        Pets=new String[4][4];
        Numbers=new String[4][4];
        Scenery=new String[4][4];
        Lego=new String[4][4];

        Pets=setString("Pets",Pets);
        Numbers=setString("Numbers",Numbers);
        Scenery=setString("Scenery",Scenery);
        Lego=setString("Lego",Lego);



    }


     private String[][] setString(String name, String[][] type){
        for(int row=0; row<4; row++){
            for(int col=0; col<4; col++) {
                type[col][row]= name+"_"+row+col+".png";
            }
        }
        return type;
    }

    public ImageView getyImageView(Button c){//use this method to retreiever iamge object
        ImageView image= new ImageView();

        return image;
    }



}

