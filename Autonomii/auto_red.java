package org.firstinspires.ftc.teamcode.Autonomii;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name="auto_red")
public class auto_red extends LinearOpMode {
    public DcMotor rightFront = null;
    public DcMotor leftRear = null;
    public DcMotor rightRear = null;
    public DcMotor leftFront = null;
    public Servo handLeftServo = null;
    public Servo handRightServo = null;
    public Servo wristServo = null;
    @Override
    public void runOpMode() throws InterruptedException {
        leftFront = hardwareMap.dcMotor.get("leftFront");
        leftRear = hardwareMap.dcMotor.get("leftRear");
        rightRear = hardwareMap.dcMotor.get("rightRear");
        rightFront = hardwareMap.dcMotor.get("rightFront");
        handLeftServo = hardwareMap.servo.get("handLeftServo");
        handRightServo = hardwareMap.servo.get("handRightServo");
        wristServo = hardwareMap.servo.get("wristServo");

        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftRear.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        rightRear.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        handLeftServo.setPosition(0.65);
        handRightServo.setPosition(0.32);
        sleep(500);
        wristServo.setPosition(0.48);
        waitForStart();
        while(opModeIsActive())
        {
            rightFront.setDirection(DcMotorSimple.Direction.REVERSE);
            rightFront.setTargetPosition(3000);
            rightFront.setMode((DcMotor.RunMode.RUN_TO_POSITION));
            rightFront.setPower(1);

            leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
            leftFront.setTargetPosition(3000);
            leftFront.setMode((DcMotor.RunMode.RUN_TO_POSITION));
            leftFront.setPower(1);

            leftRear.setDirection(DcMotorSimple.Direction.REVERSE);
            leftRear.setTargetPosition(3000);
            leftRear.setMode((DcMotor.RunMode.RUN_TO_POSITION));
            leftRear.setPower(1);

            rightRear.setDirection(DcMotorSimple.Direction.REVERSE);
            rightRear.setTargetPosition(3000);
            rightRear.setMode((DcMotor.RunMode.RUN_TO_POSITION));
            rightRear.setPower(1);
            break;
        }
    }
}
