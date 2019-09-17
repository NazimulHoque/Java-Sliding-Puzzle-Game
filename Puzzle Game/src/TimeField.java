
import javafx.scene.control.TextField;

public class TimeField extends TextField {
    int secondholder;
    int minuteholder;
    public TimeField(){
        this.setEditable(false);
        this.setPrefSize(125,20);
        this.relocate(63,397);

        this.setText("00:00");
    }

    public void reset(){
        secondholder=0;
        minuteholder=0;
        this.setText("00:00");
    }

    public void addsecond(){
        if(secondholder==59){
            addminute();
            secondholder=0;
        }
        secondholder++;
        this.UpdateField();
    }
    private void addminute(){
        minuteholder++;
        this.UpdateField();
    }

    private void UpdateField(){
        String forminute = String.format("%02d",minuteholder);
        String formsecond = String.format("%02d",secondholder);
       this.setText(forminute+":"+formsecond);
    }


}
