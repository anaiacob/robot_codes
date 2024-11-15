package org.firstinspires.ftc.teamcode.common.controller;

import com.qualcomm.robotcore.hardware.Gamepad;

public class ControllerInterface {

    public CustomButton leftBumper= new CustomButton(ButtonType.NotAssigned);

    public CustomButton rightBumper= new CustomButton(ButtonType.NotAssigned);

    public CustomButton dpadUp= new CustomButton(ButtonType.NotAssigned);

    public CustomButton dpadDown= new CustomButton(ButtonType.NotAssigned);

    public CustomButton dpadLeft= new CustomButton(ButtonType.NotAssigned);

    public CustomButton dpadRight= new CustomButton(ButtonType.NotAssigned);

    public CustomButton square= new CustomButton(ButtonType.NotAssigned);

    public CustomButton triangle= new CustomButton(ButtonType.NotAssigned);

    public CustomButton circle= new CustomButton(ButtonType.NotAssigned);

    public CustomButton cross= new CustomButton(ButtonType.NotAssigned);

    public CustomButton share = new CustomButton(ButtonType.NotAssigned);

    public CustomButton options = new CustomButton(ButtonType.NotAssigned);

    public CustomButton leftTrigger = new CustomButton(ButtonType.NotAssigned);

    public CustomButton rightTrigger = new CustomButton(ButtonType.NotAssigned);

    public void update(Gamepad gamepad){
        leftBumper.update(gamepad.left_bumper);
        rightBumper.update(gamepad.right_bumper);
        dpadUp.update(gamepad.dpad_up);
        dpadDown.update(gamepad.dpad_down);
        dpadLeft.update(gamepad.dpad_left);
        dpadRight.update(gamepad.dpad_right);
        square.update(gamepad.square);
        triangle.update(gamepad.triangle);
        circle.update(gamepad.circle);
        cross.update(gamepad.cross);
        share.update(gamepad.share);
        options.update((gamepad.options));
        leftTrigger.update((gamepad.left_trigger>0.5));
        rightTrigger.update((gamepad.right_trigger>0.5));

    }

}
