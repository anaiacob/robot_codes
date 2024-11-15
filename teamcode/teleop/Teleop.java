package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.JavaUtil;
import org.firstinspires.ftc.teamcode.common.PIDController;
import org.firstinspires.ftc.teamcode.common.controller.ButtonType;
import org.firstinspires.ftc.teamcode.common.controller.ControllerInterface;
import org.firstinspires.ftc.teamcode.common.Hardware;

@TeleOp(name = "Teleop")

public class Teleop extends LinearOpMode {

    private Hardware hardware;

    private ControllerInterface controller1=new ControllerInterface();

    private ControllerInterface controller2=new ControllerInterface();

    private PIDController liftPID;

    private int liftPos=0;
    private int liftMaxPos=1550;

    private Teleop teleop;

    private Thread updateStateThread=new Thread(()->updateState());

    @Override
    public void runOpMode() throws InterruptedException {
        hardware = new Hardware(hardwareMap);
        teleop=new Teleop();

        initBot();

        initButtons();

        waitForStart();

        updateStateThread.start();

        while (!isStopRequested()) {
            powerDrivetrain();

            displayData();

        }
    }

    private void initBot(){
        hardware.setMotorDirection(hardware.leftFrontMotor, DcMotorEx.Direction.REVERSE);
        hardware.setMotorDirection(hardware.leftRearMotor, DcMotorEx.Direction.REVERSE);
        hardware.setMotorDirection(hardware.liftMotor, DcMotorSimple.Direction.REVERSE);

        liftPID=new PIDController(0.03,0.0001,0.00001,0,"lift",hardwareMap,telemetry);
        liftPID.reverse();

        hardware.leftFrontMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        hardware.leftRearMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        hardware.rightFrontMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        hardware.rightRearMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

    }

    public void powerDrivetrain() {
        float forward = gamepad1.left_stick_y,
                strafe = -gamepad1.left_stick_x,
                turn = gamepad1.right_stick_x;
        double denominator = JavaUtil.maxOfList(JavaUtil.createListWith(1, Math.abs(forward) + Math.abs(strafe) + Math.abs(turn)));
        hardware.leftFrontMotor.setPower((forward + strafe + turn) / denominator * 2);
        hardware.rightFrontMotor.setPower((forward - (strafe + turn)) / denominator * 2);
        hardware.leftRearMotor.setPower((forward - (strafe - turn)) / denominator * 2);
        hardware.rightRearMotor.setPower((forward + (strafe - turn)) / denominator * 2);
    }


    private void displayData(){
        telemetry.addData("Lift target pos: ",liftPos);
        telemetry.addData("Lift encoder pos: ",hardware.liftMotor.getCurrentPosition());
        telemetry.addData("Vacuum encoder pos: ",hardware.vacuumMotor.getCurrentPosition());
        telemetry.addData("square ",gamepad1.square);
        telemetry.addData("triangle ",gamepad1.triangle);
        telemetry.addData("circle ",gamepad1.circle);
        telemetry.addData("cross ",gamepad1.cross);
        telemetry.update();
    }

    private void initButtons(){
        controller1.leftBumper.setType(ButtonType.Hold);
        controller1.leftBumper.onHold=a-> {
                hardware.setPower(hardware.vacuumMotor,1);
        };
        controller1.leftBumper.onRelease= a-> {
                hardware.setPower(hardware.vacuumMotor,0);
        };

        controller1.rightBumper.setType(ButtonType.Hold);
        controller1.rightBumper.onHold= a-> {
                hardware.setPower(hardware.vacuumMotor,-1);
        };
        controller1.rightBumper.onRelease= a-> {
                hardware.setPower(hardware.vacuumMotor,0);
        };

        controller1.dpadUp.setType(ButtonType.Hold);
        controller1.dpadUp.onHold= a-> {
            liftPos+=10;
            if(liftPos>liftMaxPos){
                liftPos=liftMaxPos;
            }
            liftPID.setPosition(liftPos);
        };

        controller1.dpadDown.setType(ButtonType.Hold);
        controller1.dpadDown.onHold= a-> {
            liftPos-=10;
            if(liftPos<0){
                liftPos=0;
            }
            liftPID.setPosition(liftPos);
        };

        controller1.square.setType(ButtonType.Press);
        controller1.square.onPress = a->{
            hardware.openOnePixel();
        };

        controller1.triangle.setType(ButtonType.Press);
        controller1.triangle.onPress = a->{
            hardware.openTwoPixels();
        };

        controller1.circle.setType(ButtonType.Press);
        controller1.circle.onPress = a->{
            hardware.closeBox();
        };

        controller2.dpadUp.setType(ButtonType.Hold);
        controller2.dpadUp.onHold=a->{
          hardware.setPower(hardware.hook,0.5);
        };
        controller2.dpadUp.onRelease=a->{
          hardware.setPower(hardware.hook,0);
        };

    }

    private void updateState(){
        while (!isStopRequested()) {
            controller1.update(gamepad1);
            controller2.update(gamepad2);
            liftPID.update();
        }
    }

}
