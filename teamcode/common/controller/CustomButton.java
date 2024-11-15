package org.firstinspires.ftc.teamcode.common.controller;

import java.util.function.Consumer;

public class CustomButton {

    private ButtonType buttonType;

    private boolean prevState=false;

    public Consumer onPress;

    public Consumer onHold;

    public Consumer onRelease;


    public CustomButton(ButtonType buttonType){
        this.buttonType=buttonType;
    }

    public void update(boolean state){
        switch (buttonType){
            case Hold:
                if(prevState&&state){
                    if(onHold!=null)
                        onHold.accept(1);
                }
                if(prevState&&!state){
                    if(onRelease!=null)
                        onRelease.accept(1);
                }
                break;

            case Press:
                if(!prevState&&state){
                    if(onPress!=null)
                        onPress.accept(1);
                }
                break;
        }

        prevState=state;
    }

    public void setType(ButtonType type){
        buttonType=type;
    }

}
