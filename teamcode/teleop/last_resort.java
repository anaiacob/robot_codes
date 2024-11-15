package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.robotcore.external.JavaUtil;
import org.firstinspires.ftc.teamcode.common.Hardware;
import org.firstinspires.ftc.teamcode.common.PIDController;
import org.firstinspires.ftc.teamcode.common.controller.ButtonType;
import org.firstinspires.ftc.teamcode.common.controller.ControllerInterface;

import java.util.ArrayList;
import java.util.List;
@Disabled
@TeleOp(name = "last_resort")

public class last_resort extends LinearOpMode {

    private Hardware hardware;

    private ControllerInterface controller1 = new ControllerInterface();

    private ControllerInterface controller2 = new ControllerInterface();

    private PIDController liftPID;

    private int liftPos = 0;
    private int liftMaxPos = 1250;

    private double reduction = 3;

    private Thread updateStateThread = new Thread(() -> updateState());

    private List<Double> liftPositions = new ArrayList<Double>();

    @Override
    public void runOpMode() throws InterruptedException {
        hardware = new Hardware(hardwareMap);

        liftPositions.add(500.0);
        liftPositions.add(870.0);
        liftPositions.add(1260.0);
        liftPositions.add(0.0);


        initBot();

        initButtons();
        //hardware.drop.setPosition(0);

        waitForStart();

        updateStateThread.start();

        while (opModeIsActive()) {

            powerDrivetrain();

            displayData();

            initButtons();

        }
    }


    private void initBot() {
        hardware.setMotorDirection(hardware.leftFrontMotor, DcMotorEx.Direction.REVERSE);
        hardware.setMotorDirection(hardware.leftRearMotor, DcMotorEx.Direction.REVERSE);
        //hardware.setMotorDirection(hardware.liftMotor, DcMotorSimple.Direction.REVERSE);

        liftPID = new PIDController(0.004, 0.0001, 0.00001, 0, "lift", hardwareMap, telemetry);
        //liftPID.reverse();

        hardware.leftFrontMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        hardware.leftRearMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        hardware.rightFrontMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        hardware.rightRearMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        hardware.hook.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        hardware.liftMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        hardware.standbyFly();

    }

    public void powerDrivetrain() {
        float forward = gamepad1.left_stick_y,
                strafe = -gamepad1.left_stick_x,
                turn = gamepad1.right_stick_x;
        double denominator = JavaUtil.maxOfList(JavaUtil.createListWith(1, Math.abs(forward) + Math.abs(strafe) + Math.abs(turn)));
        hardware.leftFrontMotor.setPower((forward + strafe + turn) / denominator * reduction);
        hardware.rightFrontMotor.setPower((forward - (strafe + turn)) / denominator * reduction);
        hardware.leftRearMotor.setPower((forward - (strafe - turn)) / denominator * reduction);
        hardware.rightRearMotor.setPower((forward + (strafe - turn)) / denominator * reduction);
    }


    private void displayData() {
        telemetry.addData("Lift target pos: ", liftPos);
        telemetry.addData("Lift encoder pos: ", hardware.liftMotor.getCurrentPosition());
        telemetry.addData("Vacuum encoder pos: ", hardware.vacuumMotor.getCurrentPosition());
        telemetry.addData("square ", gamepad1.square);
        telemetry.addData("triangle ", gamepad1.triangle);
        telemetry.addData("circle ", gamepad1.circle);
        telemetry.addData("cross ", gamepad1.cross);
        telemetry.update();
    }

    private void initButtons() {
        //      controller1

        controller1.leftBumper.setType(ButtonType.Hold);
        controller1.leftBumper.onHold = a -> {
            hardware.setPower(hardware.vacuumMotor, 1);
        };
        controller1.leftBumper.onRelease = a -> {
            hardware.setPower(hardware.vacuumMotor, 0);
        };

        controller1.rightBumper.setType(ButtonType.Hold);
        controller1.rightBumper.onHold = a -> {
            hardware.setPower(hardware.vacuumMotor, -1);
        };
        controller1.rightBumper.onRelease = a -> {
            hardware.setPower(hardware.vacuumMotor, 0);
        };

        controller1.square.setType(ButtonType.Press);
        controller1.square.onPress = a -> {
            liftPID.setPosition(liftPositions.get(3));
        };

        controller1.triangle.setType(ButtonType.Press);
        controller1.triangle.onPress = a -> {
            liftPID.setPosition(liftPositions.get(2));
        };

        controller1.circle.setType(ButtonType.Press);
        controller1.circle.onPress = a -> {
            liftPID.setPosition(liftPositions.get(0));
            hardware.closeBox();
            reduction = 3f;
        };

        controller1.cross.setType(ButtonType.Press);
        controller1.cross.onPress = a -> {
            liftPID.setPosition(liftPositions.get(1));
        };

        controller1.options.setType(ButtonType.Press);
        controller1.options.onPress = a -> {
                reduction = 0.5;
//            else if(j == true) {
//                p = 2f;
//                j = false;
//            }
        };

        controller1.dpadLeft.setType(ButtonType.Press);
        controller1.dpadLeft.onPress = a -> {
            hardware.openOnePixel();
        };

        controller1.dpadRight.setType(ButtonType.Press);
        controller1.dpadRight.onPress = a -> {
            hardware.openTwoPixels();
        };

        controller1.share.setType(ButtonType.Press);
        controller1.share.onPress = a -> {
            hardware.closeBox();
        };

//        controller1.dpadDown.setType(ButtonType.Press);
//        controller1.dpadDown.onRelease = a -> {
//            // hardware.setPower(hardware.hook, 0);
//            hardware.openOnePixel();
//            sleep(1000);
//            hardware.closeBox();
//            liftPID.setPosition(-500);
//            sleep(1400);
//            hardware.openTwoPixels();
//        };

//        controller1.dpadUp.setType(ButtonType.Press);
//        controller1.dpadUp.onPress = a -> {
//            hardware.openOnePixel();
//            sleep(1000);
//            hardware.closeBox();
//            liftPos = 900;
//            liftPID.setPosition(-liftPos);
//            sleep(1400);
//            hardware.openTwoPixels();
//        };
//
//        controller1.dpadDown.setType(ButtonType.Press);
//        controller1.dpadDown.onPress = a -> {
//            hardware.openOnePixel();
//            sleep(1000);
//            hardware.closeBox();
//            liftPos = 500;
//            liftPID.setPosition(-liftPos);
//            sleep(1400);
//            hardware.openTwoPixels();
//        };


        //      controller2


//        controller2.leftBumper.setType(ButtonType.Hold);
//        controller2.leftBumper.onHold = a -> {
//            liftPos += 10;
//            liftPID.setPosition(-liftPos);
//        };
//
//        controller2.rightBumper.setType(ButtonType.Hold);
//        controller2.rightBumper.onHold = a -> {
//            liftPos -= 10;
//            liftPID.setPosition(-liftPos);
//        };

//
//        controller2.rightBumper.setType(ButtonType.Hold);
//        controller2.rightBumper.onHold = a -> {
//            hardware.setPower(hardware.vacuumMotor, -1);
//        };
//        controller1.rightBumper.onRelease = a -> {
//            hardware.setPower(hardware.vacuumMotor, 0);
//        };

        controller2.dpadUp.setType(ButtonType.Hold);
        controller2.dpadUp.onHold = a -> {
            hardware.setPower(hardware.hook, -1);
        };
        controller2.dpadUp.onRelease = a -> {
            hardware.setPower(hardware.hook, 0);
        };

        controller2.dpadDown.setType(ButtonType.Hold);
        controller2.dpadDown.onHold = a -> {
            hardware.setPower(hardware.hook, 1);
        };
        controller2.dpadDown.onRelease = a -> {
            hardware.setPower(hardware.hook, 0);
        };

        controller2.share.setType(ButtonType.Press);
        controller2.share.onPress=a->{
            hardware.dropNow();
        };

        controller2.options.setType(ButtonType.Press);
        controller2.options.onPress=a->{
            hardware.standbyDrop();
        };

        controller2.triangle.setType(ButtonType.Press);
        controller2.triangle.onPress = a -> {
            hardware.startFlight();
        };


        controller2.cross.setType(ButtonType.Press);
        controller2.cross.onPress = a -> {
            hardware.closeBox();
        };

        controller2.circle.setType(ButtonType.Press);
        controller2.circle.onPress = a -> {
            hardware.standbyFly();
        };

//        controller2.rightBumper.setType(ButtonType.Press);
//        controller2.rightBumper.onPress = a -> {
//            hardware.liftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        };

        controller2.square.setType(ButtonType.Press);
        controller2.square.onPress = a -> {
            reduction = 3;
        };

    }

    private void updateState() {
        controller1.update(gamepad1);
        controller2.update(gamepad2);
        liftPID.update();
        initButtons();
    }
}
