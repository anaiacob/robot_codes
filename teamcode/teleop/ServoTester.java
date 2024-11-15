package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.common.controller.ButtonType;
import org.firstinspires.ftc.teamcode.common.controller.ControllerInterface;
@Disabled
@TeleOp(name="ServoTester")
public class ServoTester extends LinearOpMode {

    private Servo servo;

    private ControllerInterface controller1=new ControllerInterface();

    private double pos=0;

    @Override
    public void runOpMode() throws InterruptedException {

        servo=hardwareMap.get(Servo.class,"drop");

        waitForStart();

        setControls();

        servo.setPosition(pos);

        while (!isStopRequested()){
            controller1.update(gamepad1);
            telemetry.addData("Position",pos);
            telemetry.addData("State",gamepad1.dpad_up);
            telemetry.update();
        }

    }

    void setControls(){
        controller1.dpadLeft.setType(ButtonType.Press);
        controller1.dpadLeft.onPress= a ->{
            pos-=0.05;
            pos=Math.max(-1,pos);
        };
        controller1.dpadRight.setType(ButtonType.Press);
        controller1.dpadRight.onPress= a ->{
            pos+=0.05;
            pos=Math.min(1,pos);
        };
        controller1.dpadUp.setType(ButtonType.Press);
        controller1.dpadUp.onPress=a->{
          servo.setPosition(pos);
        };
    }
}
