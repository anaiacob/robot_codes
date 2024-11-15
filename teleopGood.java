package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.external.JavaUtil;
@Disabled
@TeleOp(name="teleopGood")
public class teleopGood extends LinearOpMode{

    public DcMotor rightFront = null;
    public DcMotor leftRear = null;
    public DcMotor rightRear = null;
    public DcMotor leftFront = null;
    //negru down
    public Servo armLeftServo = null;
    //rosu
    public Servo armRightServo = null;
    //verde
    public Servo handLeftServo = null;
    //albastru
    public Servo handRightServo = null;
    //galben
    public Servo wristServo = null;
    public DcMotor elevatorMotorLeft=null;
    public DcMotor elevatorMotorRight=null;
    public double poz1=0.02,poz2;
    @Override
    public void runOpMode() throws InterruptedException {
        leftFront = hardwareMap.dcMotor.get("leftFront");
        leftRear = hardwareMap.dcMotor.get("leftRear");
        rightRear = hardwareMap.dcMotor.get("rightRear");
        rightFront = hardwareMap.dcMotor.get("rightFront");

        armLeftServo = hardwareMap.servo.get("armLeftServo");
        armRightServo = hardwareMap.servo.get("armRightServo");
        handLeftServo = hardwareMap.servo.get("handLeftServo");
        handRightServo = hardwareMap.servo.get("handRightServo");
        wristServo = hardwareMap.servo.get("wristServo");
        elevatorMotorRight = hardwareMap.dcMotor.get("elevatorMotorRight");
        elevatorMotorLeft = hardwareMap.dcMotor.get("elevatorMotorLeft");
        elevatorMotorLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        elevatorMotorRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        elevatorMotorRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        elevatorMotorLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        elevatorMotorRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        elevatorMotorLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //elevatorMotorRight.setDirection(DcMotorSimple.Direction.REVERSE);
        waitForStart();
        while(opModeIsActive()) {
            double forward = -gamepad1.left_stick_y;
            double strafe = gamepad1.left_stick_x;
            double turn = -gamepad1.right_stick_x;

            double denominator = JavaUtil.maxOfList(JavaUtil.createListWith(1, Math.abs(forward) + Math.abs(strafe) + Math.abs(turn)));
            leftFront.setPower((forward + strafe + turn) / denominator);
            leftRear.setPower((forward - (strafe - turn)) / denominator);
            rightFront.setPower((forward - (strafe + turn)) / denominator);
            rightRear.setPower((forward + (strafe - turn)) / denominator);

//            if(gamepad1.dpad_up)
//            {
//                poz1+=0.05;
//                armRightServo.setPosition(poz1);
//                armLeftServo.setPosition(poz1);
//            }
//            if(gamepad1.dpad_down)
//            {
//                poz1-=0.05;
//                armRightServo.setPosition(poz1);
//                armLeftServo.setPosition(poz1);
//            }
            if(gamepad1.triangle)
            {
                wristServo.setPosition(0.48);
            }
            if(gamepad2.dpad_up)
            {
                wristServo.setPosition(0.48);
            }
            if(gamepad1.cross)
            {
                wristServo.setPosition(0.03);
            }
            if(gamepad1.left_bumper){
                handLeftServo.setPosition(0.5); //0.65
            }
            if (gamepad1.right_bumper){
                handRightServo.setPosition(0.48); //0.32
            }
            if(gamepad1.left_trigger > 0.30){
                handLeftServo.setPosition(0.65); //0.50
            }
            if(gamepad1.right_trigger > 0.30)
            {
                handRightServo.setPosition(0.32); //0.48
            }
            if(gamepad2.cross){
                elevatorMotorRight.setTargetPosition(1300);
                elevatorMotorRight.setMode((DcMotor.RunMode.RUN_TO_POSITION));
                elevatorMotorRight.setPower(1);
                elevatorMotorLeft.setTargetPosition(1300);
                elevatorMotorLeft.setMode((DcMotor.RunMode.RUN_TO_POSITION));
                elevatorMotorLeft.setPower(1);
                telemetry.addData("Merge la 1300", "bine");
                telemetry.update();
            }
            if(gamepad2.square)
            {
                elevatorMotorRight.setTargetPosition(1800);
                elevatorMotorRight.setMode((DcMotor.RunMode.RUN_TO_POSITION));
                elevatorMotorRight.setPower(1);
                elevatorMotorLeft.setTargetPosition(1800);
                elevatorMotorLeft.setMode((DcMotor.RunMode.RUN_TO_POSITION));
                elevatorMotorLeft.setPower(1);
                telemetry.addData("Merge la 1800", "bine");
                telemetry.update();
            }

            if(gamepad2.triangle)
            {
                elevatorMotorRight.setTargetPosition(2500);
                elevatorMotorRight.setMode((DcMotor.RunMode.RUN_TO_POSITION));
                elevatorMotorRight.setPower(1);
                elevatorMotorLeft.setTargetPosition(2500);
                elevatorMotorLeft.setMode((DcMotor.RunMode.RUN_TO_POSITION));
                elevatorMotorLeft.setPower(1);
                telemetry.addData("Merge la 2500", "bine");
                telemetry.update();
            }
            if(gamepad2.circle)
            {
                elevatorMotorRight.setTargetPosition(0);
                elevatorMotorRight.setMode((DcMotor.RunMode.RUN_TO_POSITION));
                elevatorMotorRight.setPower(0.8);
                elevatorMotorLeft.setTargetPosition(0);
                elevatorMotorLeft.setMode((DcMotor.RunMode.RUN_TO_POSITION));
                elevatorMotorLeft.setPower(0.8);
                telemetry.addData("Merge la 0", "bine");
                telemetry.update();
            }
            if(gamepad2.right_bumper)
            {
                armRightServo.setPosition(0.710);
                armLeftServo.setPosition(0.710);
            }
            if(gamepad2.left_bumper)
            {
                armRightServo.setPosition(0.02);
                armLeftServo.setPosition(0.02);
            }
        }
        }
}
