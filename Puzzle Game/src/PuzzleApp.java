import javafx.collections.FXCollections;
import javafx.animation.*;
import javafx.util.Duration;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.application.Application;
import javafx.event.*;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.Insets;
import java.util.Random;



public class PuzzleApp extends Application{
    ButtonTile[][] tiles;
    GameState currentGame = new GameState();
    public void start(Stage primaryStage) {
        TimeField timer = new TimeField();
        Pane mainPane = new Pane();
        Pane pallet = new Pane();

        Pane thumbtray = new Pane();
        thumbtray.setPrefSize(187,751);
        thumbtray.relocate(771,10);

        Label thumbnail = new Label();
        thumbnail.setPrefSize(187,187);
        thumbnail.relocate(0,0);
        thumbnail.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("BLANK.png"))));

        Button startstop = new Button("start");//initial state of button//should be changed
        startstop.setDisable(true);
        startstop.relocate(0,357);
        startstop.setPrefSize(187,30);
        startstop.setStyle("-fx-font: 12 arial; -fx-base: DARKGREEN; -fx-text-fill: WHITE;");

        ListView<String> imagelist = new ListView<>();
        String[] images = {"Pets","Scenery","Lego","Numbers"};
        imagelist.setItems(FXCollections.observableArrayList(images));
        imagelist.relocate(0,197);
        imagelist.setPrefSize(187,150);
        imagelist.setOnMouseClicked(new EventHandler<MouseEvent>() {                //handles item lsit selection  may have future funcitonality
            public void handle(MouseEvent mouseEvent) {
                thumbnail.setGraphic(new ImageView(new Image(getClass().getResourceAsStream(imagelist.getSelectionModel().getSelectedItem()+"_Thumbnail"+".png"))));
                startstop.setDisable(false);

            }});


        Timeline updateTimer = new Timeline(new KeyFrame(Duration.millis(1000),                                     //insert code
                new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                        timer.addsecond();
                    }
                }));
        updateTimer.setCycleCount(Timeline.INDEFINITE);




        startstop.setOnAction(new EventHandler<ActionEvent>() {                                                 // add timer functionality
            public void handle(ActionEvent actionEvent) {
                if(imagelist.getSelectionModel().isEmpty()){

                }else if(startstop.getText()=="start"){//
                    startstop.setText("stop");
                    startstop.setStyle("-fx-font: 12 arial; -fx-base: RED; -fx-text-fill: WHITE;");
                    imagelist.setDisable(true);
                    setPuzzImage(imagelist.getSelectionModel().getSelectedItem());
                    thumbnail.setOpacity(0.3);
                    updateTimer.play();
                    startGame();
                    for(int i =1; i<=400; i++){
                        Random rand = new Random();
                        int randomX = rand.nextInt((3) + 1);
                        int randomY = rand.nextInt((3) + 1);
                        swap(randomY,randomX);
                    }

                }else{
                    startstop.setText("start");
                    startstop.setStyle("-fx-font: 12 arial; -fx-base: DARKGREEN; -fx-text-fill: WHITE;");
                    imagelist.setDisable(false);
                    thumbnail.setOpacity(1.00);
                    updateTimer.stop();
                    timer.reset();

                }
            }});





        Label time = new Label("Time");
        time.relocate(0,397);
        time.setPrefSize(60,20);
        thumbtray.getChildren().addAll(thumbnail,imagelist,startstop,time,timer);





        tiles = new ButtonTile[4][4];
        for(int row=0; row<4; row++){
            for(int col=0; col<4; col++){
                tiles[row][col]=new ButtonTile();
                tiles[row][col].setPrefSize(187,187);
                tiles[row][col].setPadding(new Insets(0,0,0,0));
                tiles[row][col].setImage("BLANK.png");
                tiles[row][col].relocate(row*187+row*1,col*187+col*1);
                tiles[row][col].setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent actionEvent) {
                        /////////////////////////////////////////////////////////////////////////////////////////////////////
                        for(int row=0; row<4; row++) {
                            for (int col = 0; col < 4; col++) {
                                if (actionEvent.getSource() == tiles[row][col]) {
                                    //System.out.println("button "+col+""+row+" was clicked");//r//c//debugging purposes
                                    //System.out.println(tiles[row][col].isBlack());
                                    swap(row,col);

                                }

                            }
                        }
                        if(checkGame()){
                            System.out.println("YOU  WIN");
                            startstop.setText("start");
                            startstop.setStyle("-fx-font: 12 arial; -fx-base: DARKGREEN; -fx-text-fill: WHITE;");
                            imagelist.setDisable(false);
                            thumbnail.setOpacity(1.00);
                            updateTimer.stop();

                        }else{
                            System.out.println("set");
                        }
                        //slide chap 5 pg 160
                    }});

                pallet.getChildren().add(tiles[row][col]);

            }
        }
        pallet.relocate(10,10);
        mainPane.getChildren().add(pallet);//store tiles buttons
        mainPane.getChildren().add(thumbtray);//store thumbnail tray



        //place elements on to main pane
        //make button array


        primaryStage.setTitle("Puzzle Game");
        primaryStage.setScene(new Scene(mainPane,968,771));
        primaryStage.setResizable(false);
        primaryStage.show();


    }

    //create button setting method that pciks and choooses based on what is passed in
    public void setPuzzImage(String name){
        for(int row=0; row<4; row++){
            for(int col=0; col<4; col++) {
                tiles[col][row].setImage(name +"_"+row+col+ ".png");
                tiles[col][row].setOriginal(name +"_"+row+col+ ".png");
            }
        }
    }




    public void swap(int y,int x){//row//col//original already set, just set black
        String imageString = tiles[y][x].imagename;
        if(x==0){//cannot check left//must always check right

            if(y==0){//check  down
                if(tiles[y+1][x].isBlack()){
                    tiles[y][x].setImage(tiles[y+1][x].imagename);
                    tiles[y+1][x].setImage(imageString);

                }else if(tiles[y][x+1].isBlack()){
                    tiles[y][x].setImage(tiles[y][x+1].imagename);
                    tiles[y][x+1].setImage(imageString);
                }

            }else if(y==3){//check up
                if(tiles[y-1][x].isBlack()){
                    tiles[y][x].setImage(tiles[y-1][x].imagename);
                    tiles[y-1][x].setImage(imageString);

                }else if(tiles[y][x+1].isBlack()){
                    tiles[y][x].setImage(tiles[y][x+1].imagename);
                    tiles[y][x+1].setImage(imageString);
                }
            }else{//edge case
                if(tiles[y+1][x].isBlack()){
                    tiles[y][x].setImage(tiles[y+1][x].imagename);
                    tiles[y+1][x].setImage(imageString);
                }else if(tiles[y-1][x].isBlack()){
                    tiles[y][x].setImage(tiles[y-1][x].imagename);
                    tiles[y-1][x].setImage(imageString);
                }else if(tiles[y][x+1].isBlack()){
                    tiles[y][x].setImage(tiles[y][x+1].imagename);
                    tiles[y][x+1].setImage(imageString);
                }

            }

        }else if(x==3){//cannot check right must always check left

            if(y==0){//check  down
                if(tiles[y+1][x].isBlack()){
                    tiles[y][x].setImage(tiles[y+1][x].imagename);
                    tiles[y+1][x].setImage(imageString);

                }else if(tiles[y][x-1].isBlack()){
                    tiles[y][x].setImage(tiles[y][x-1].imagename);
                    tiles[y][x-1].setImage(imageString);
                }
            }else if(y==3){//check up
                if(tiles[y-1][x].isBlack()){
                    tiles[y][x].setImage(tiles[y-1][x].imagename);
                    tiles[y-1][x].setImage(imageString);
                }else if(tiles[y][x-1].isBlack()){
                    tiles[y][x].setImage(tiles[y][x-1].imagename);
                    tiles[y][x-1].setImage(imageString);
                }
            }else{//edge case
                if(tiles[y+1][x].isBlack()){
                    tiles[y][x].setImage(tiles[y+1][x].imagename);
                    tiles[y+1][x].setImage(imageString);
                }else if(tiles[y-1][x].isBlack()){
                    tiles[y][x].setImage(tiles[y-1][x].imagename);
                    tiles[y-1][x].setImage(imageString);
                }else if(tiles[y][x-1].isBlack()){
                    tiles[y][x].setImage(tiles[y][x-1].imagename);
                    tiles[y][x-1].setImage(imageString);
                }
            }
        }else if(y==0){//check below
            if(tiles[y][x-1].isBlack()){//left
                tiles[y][x].setImage(tiles[y][x-1].imagename);
                tiles[y][x-1].setImage(imageString);
            }else if(tiles[y+1][x].isBlack()){//below
                tiles[y][x].setImage(tiles[y+1][x].imagename);
                tiles[y+1][x].setImage(imageString);
            }else if(tiles[y][x+1].isBlack()){//right
                tiles[y][x].setImage(tiles[y][x+1].imagename);
                tiles[y][x+1].setImage(imageString);
            }

        }else if(y==3){//check above
            if(tiles[y][x-1].isBlack()){//left
                tiles[y][x].setImage(tiles[y][x-1].imagename);
                tiles[y][x-1].setImage(imageString);
            }else if(tiles[y-1][x].isBlack()){//above
                tiles[y][x].setImage(tiles[y-1][x].imagename);
                tiles[y-1][x].setImage(imageString);
            }else if(tiles[y][x+1].isBlack()){//right
                tiles[y][x].setImage(tiles[y][x+1].imagename);
                tiles[y][x+1].setImage(imageString);
            }
        }else{
            if(tiles[y][x-1].isBlack()){//left
                tiles[y][x].setImage(tiles[y][x-1].imagename);
                tiles[y][x-1].setImage(imageString);

            }else if(tiles[y-1][x].isBlack()){//above
                tiles[y][x].setImage(tiles[y-1][x].imagename);
                tiles[y-1][x].setImage(imageString);
            }else if(tiles[y][x+1].isBlack()){//right
                tiles[y][x].setImage(tiles[y][x+1].imagename);
                tiles[y][x+1].setImage(imageString);
            }else if(tiles[y+1][x].isBlack()){//below
                tiles[y][x].setImage(tiles[y+1][x].imagename);
                tiles[y+1][x].setImage(imageString);
            }
        }


    }

    public boolean checkGame(){
        int unmatched=0;
        for(int row=0;row<=3;row++){
            for(int col=0;col<=3;col++){

                if(tiles[row][col].imagename.equals(tiles[row][col].original)){

                }else{
                    unmatched++;
                }
            }
        }
        if(unmatched==1){
            return true;
        }else{
            return false;
        }
    }

    public void startGame(){//will randomeely choose which tile to  make black
        Random rand = new Random();
        int randomX = rand.nextInt((3) + 1);
        int randomY = rand.nextInt((3) + 1);
        tiles[randomX][randomY].setImage("BLANK.png");


    }



    static void main(String[] args){

        launch(args);

    }

}
