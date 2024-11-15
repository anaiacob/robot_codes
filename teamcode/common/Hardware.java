package org.firstinspires.ftc.teamcode.common;

import static android.os.SystemClock.sleep;

import android.graphics.Path;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Hardware {

    private HardwareMap hardwareMap;

    public DcMotorEx leftRearMotor;
    public DcMotorEx leftFrontMotor;
    public DcMotorEx rightRearMotor;
    public DcMotorEx rightFrontMotor;

    public DcMotorEx vacuumMotor;

    public DcMotorEx liftMotor;

    public DcMotorEx hook;

    public Servo box;

    public Servo servo;

    public Servo fly;


    public Hardware(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;

        this.leftRearMotor = hardwareMap.get(DcMotorEx.class, "leftRear");
        this.leftFrontMotor = hardwareMap.get(DcMotorEx.class, "leftFront");
        this.rightRearMotor = hardwareMap.get(DcMotorEx.class, "rightRear");
        this.rightFrontMotor = hardwareMap.get(DcMotorEx.class, "rightFront");

        this.vacuumMotor = hardwareMap.get(DcMotorEx.class, "vacuum");
        this.liftMotor = hardwareMap.get(DcMotorEx.class, "lift");

        this.hook=hardwareMap.get(DcMotorEx.class, "hook");

        this.box=hardwareMap.get(Servo.class,"box");

        this.servo=hardwareMap.get(Servo.class,"drop");

        this.fly=hardwareMap.get(Servo.class, "avion");

        setMotorDirection(liftMotor, DcMotorEx.Direction.REVERSE);
    }


    public void gotoPosition(DcMotorEx motor, int pos,double power) {
        motor.setTargetPosition(pos);
        motor.setPower(power);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void setPower(DcMotorEx motor, double power) {
        motor.setPower(power);
    }

    public void setMotorDirection(DcMotorEx motor, DcMotorSimple.Direction dir) {
        motor.setDirection(dir);
    }


    public void openOnePixel(){
        box.setPosition(0.33);
    }

    public void openTwoPixels(){
        box.setPosition(0);
    }

    public void closeBox(){
        box.setPosition(0.51);
    }

    public void standbyFly() { fly.setPosition(0);}

    public void startFlight() { fly.setPosition(0.7);}

    public void dropNow() {
        sleep(200);
        servo.setPosition(0.5);}

    public void standbyDrop() { servo.setPosition(0);}

    public void dropOnAuton () {
        gotoPosition(liftMotor, -400, 1);
        sleep(100);
        box.setPosition(0);
        sleep(1500);
        gotoPosition(liftMotor, 0, 1);
        sleep(1000);
        box.setPosition(0.51);
        sleep(500);
    }

    public void dropOnAuton2 () {
        gotoPosition(liftMotor, -400, 1);
        sleep(100);
        box.setPosition(0);
        sleep(1500);
        gotoPosition(liftMotor, 110, 1);
        box.setPosition(0.51);
    }

}
