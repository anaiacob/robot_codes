package org.firstinspires.ftc.teamcode.Autonomii;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous
public class RedClose_OpPark extends LinearOpMode {
    public Servo handLeftServo = null;
    public Servo handRightServo = null;
    public Servo wristServo = null;
    @Override
    public void runOpMode() throws InterruptedException {

        ElapsedTime timer=new ElapsedTime();

        DcMotor leftFront = hardwareMap.dcMotor.get("leftFront");
        DcMotor leftRear = hardwareMap.dcMotor.get("leftRear");
        DcMotor rightRear = hardwareMap.dcMotor.get("rightRear");
        DcMotor rightFront = hardwareMap.dcMotor.get("rightFront");

        handLeftServo = hardwareMap.servo.get("handLeftServo");
        handRightServo = hardwareMap.servo.get("handRightServo");
        wristServo = hardwareMap.servo.get("wristServo");

        handLeftServo.setPosition(0.65);
        handRightServo.setPosition(0.32);
//        sleep(500);
//        wristServo.setPosition(0.48);
        waitForStart();
        timer.reset();
        while(opModeIsActive())
        {
            if(timer.seconds()<1.5)
            {
                leftRear.setPower(-0.5);
                rightFront.setPower(-0.5);
                leftFront.setPower(-0.5);
                rightRear.setPower(-0.5);
            }

            else {
                leftRear.setPower(0);
                rightFront.setPower(0);
                leftFront.setPower(0);
                rightRear.setPower(0);
            }
        }
    }
}
