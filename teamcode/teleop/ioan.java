package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.JavaUtil;
import org.firstinspires.ftc.teamcode.common.PIDController;
import org.firstinspires.ftc.teamcode.common.controller.ButtonType;
import org.firstinspires.ftc.teamcode.common.controller.ControllerInterface;
import org.firstinspires.ftc.teamcode.common.Hardware;

import java.util.ArrayList;
import java.util.List;
@Disabled
@TeleOp(name = "ioan")

public class ioan extends LinearOpMode {

    private Hardware hardware;

    private ControllerInterface controller1 = new ControllerInterface();

    private ControllerInterface controller2 = new ControllerInterface();

    private PIDController liftPID;

    private int liftPos = 0;
    private int liftMaxPos = 1550;

    private double reduction = 1.5;

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

        waitForStart();

        updateStateThread.start();

        while (!isStopRequested()) {
            powerDrivetrain();

            displayData();

        }
    }

    private void initBot() {
        hardware.setMotorDirection(hardware.leftFrontMotor, DcMotorEx.Direction.REVERSE);
        hardware.setMotorDirection(hardware.leftRearMotor, DcMotorEx.Direction.REVERSE);

        liftPID = new PIDController(0.004, 0.0001, 0.00001, 0, "lift", hardwareMap, telemetry);
        //liftPID.reverse();
        hardware.leftFrontMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        hardware.leftRearMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        hardware.rightFrontMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        hardware.rightRearMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        hardware.standbyFly();
        hardware.closeBox();
        hardware.dropNow();

    }

    public void powerDrivetrain() {
        double forward = gamepad1.left_stick_y * 2.5,
                strafe = -gamepad1.left_stick_x,
                turn = gamepad1.right_stick_x;
        double denominator = JavaUtil.maxOfList(JavaUtil.createListWith(1, Math.abs(forward) + Math.abs(strafe) + Math.abs(turn)));
        hardware.leftFrontMotor.setPower((forward + strafe + turn) / denominator * reduction);
        hardware.rightFrontMotor.setPower((forward - (strafe + turn)) / denominator * reduction);
        hardware.leftRearMotor.setPower((forward - (strafe - turn)) / denominator * reduction);
        hardware.rightRearMotor.setPower((forward + (strafe - turn)) / denominator * reduction);
    }


    private void displayData() {
        telemetry.addData("Lift encoder pos: ", liftPID.getCurrentPositon());
        telemetry.addData("Vacuum encoder pos: ", hardware.vacuumMotor.getCurrentPosition());
        telemetry.addData("square ", gamepad1.square);
        telemetry.addData("triangle ", gamepad1.triangle);
        telemetry.addData("circle ", gamepad1.circle);
        telemetry.addData("cross ", gamepad1.cross);
        telemetry.update();
    }



        private void initButtons() {

//            controller1.dpadUp.setType(ButtonType.Press);
//            controller1.dpadUp.onPress = a -> {
//            };
//
//            controller1.dpadDown.setType(ButtonType.Hold);
//            controller1.dpadDown.onHold = a -> {
//            };

            controller1.dpadRight.setType(ButtonType.Press);
            controller1.dpadRight.onPress = a -> {
                hardware.openTwoPixels();
            };

            controller1.dpadLeft.setType(ButtonType.Press);
            controller1.dpadLeft.onPress = a -> {
                hardware.openOnePixel();
            };

            controller1.square.setType(ButtonType.Press);
            controller1.square.onPress = a -> {
                liftPID.setPosition(liftPositions.get(3));
            };

            controller1.triangle.setType(ButtonType.Press);
            controller1.triangle.onPress = a -> {
                liftPID.setPosition(liftPositions.get(1));
            };

            controller1.circle.setType(ButtonType.Press);
            controller1.circle.onPress = a -> {
                liftPID.setPosition(liftPositions.get(0));
                hardware.closeBox();
                reduction = 3;
            };

            controller1.cross.setType(ButtonType.Press);
            controller1.cross.onPress = a -> {
                liftPID.setPosition(liftPositions.get(1));
            };

            controller1.options.setType(ButtonType.Press);
            controller1.options.onHold = a -> {
                reduction = 0.5;
            };

            controller1.share.setType(ButtonType.Press);
            controller1.share.onPress = a -> {
                hardware.closeBox();
            };


            ////////////////

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

            controller2.dpadUp.setType(ButtonType.Hold);
            controller2.dpadUp.onHold = a -> {
                double pos = liftPID.getTargetPositon();
                if(pos==liftPositions.get(3)){
                    liftPositions.set(3,pos+7);
                }
                pos += 7;
                liftPID.setPosition(pos);

            };

            controller2.dpadDown.setType(ButtonType.Hold);
            controller2.dpadDown.onHold = a -> {
                double pos = liftPID.getTargetPositon();
                if(pos==liftPositions.get(3)){
                    liftPositions.set(3,pos-7);
                }
                pos -= 7;
                liftPID.setPosition(pos);
            };

//            controller2.dpadRight.setType(ButtonType.Press);
//            controller2.dpadRight.onPress = a -> {
//                hardware.startFlight();
//            };
//
//            controller2.dpadLeft.setType(ButtonType.Press);
//            controller2.dpadLeft.onPress = a -> {
//                hardware.standbyFly();
//            };

//            controller2.share.setType(ButtonType.Press);
//            controller2.share.onPress = a -> {
//
//            };
//
//            controller1.options.setType(ButtonType.Press);
//            controller1.options.onPress = a -> {
//                hardware.standbyDrop();
//            };
//
//            controller1.cross.setType(ButtonType.Press);
//            controller1.cross.onPress = a -> {
//                liftPID.setPosition(liftPositions.get(0));
//            };
//
//            controller1.square.setType(ButtonType.Press);
//            controller1.square.onPress = a -> {
//                liftPID.setPosition(liftPositions.get(1));
//            };
//
//            controller1.triangle.setType(ButtonType.Press);
//            controller1.triangle.onPress = a -> {
//                liftPID.setPosition(liftPositions.get(2));
//            };
//
//            controller1.circle.setType(ButtonType.Press);
//            controller1.circle.onPress = a -> {
//                liftPID.setPosition(liftPositions.get(3));
//                hardware.closeBox();
//            };
//
//            controller2.leftTrigger.setType(ButtonType.Hold);
//            controller2.leftTrigger.onHold = a -> {
//                hardware.hook.setPower(-1);
//            };
//            controller2.leftTrigger.onRelease = a -> {
//                hardware.hook.setPower(0);
//            };
//
//            controller2.rightTrigger.setType(ButtonType.Hold);
//            controller2.rightTrigger.onHold = a -> {
//                hardware.hook.setPower(1);
//            };
//            controller2.rightTrigger.onRelease = a -> {
//                hardware.hook.setPower(0);
//            };

        }

    private void updateState() {
        controller1.update(gamepad1);
        controller2.update(gamepad2);
        liftPID.update();
        initButtons();
    }
}
