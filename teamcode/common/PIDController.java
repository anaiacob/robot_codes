package org.firstinspires.ftc.teamcode.common;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class PIDController {

    private double kp ,ki,kd;
    private double position;
    private double integralSum = 0;
    private double lastError = 0;
    private double a=0.8;
    private double currentFilterEstimate=0,previousFilterEstimate;
    private double derivative,maxIntegralSum=200;
    private double lastPosition=0;
    private double error=0;

    private ElapsedTime timer = new ElapsedTime();

    private DcMotorEx motor;
    private HardwareMap hardwareMap;
    private String motorName;
    private Telemetry telemetry;


    public PIDController(double kp, double ki, double kd, double position, String motorName, HardwareMap hardwareMap, Telemetry telemetry){
        this.kp=kp;
        this.ki=ki;
        this.kd=kd;

        this.position=position;
        this.hardwareMap=hardwareMap;

        this.motorName=motorName;
        this.motor=hardwareMap.get(DcMotorEx.class,motorName);
        this.motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        this.telemetry=telemetry;
    }


    public void update(){
        // obtain the encoder position
        double encoderPosition = motor.getCurrentPosition();
        // calculate the error
        error = position - encoderPosition;

        double errorChange = (error - lastError);

        // filter out hight frequency noise to increase derivative performance
        currentFilterEstimate = (a * previousFilterEstimate) + (1-a) * errorChange;
        previousFilterEstimate = currentFilterEstimate;

        // rate of change of the error
        derivative = currentFilterEstimate / timer.seconds();

        // sum of all error over time
        integralSum = integralSum + (error * timer.seconds());


        // max out integral sum
        if (integralSum > maxIntegralSum) {
            integralSum = maxIntegralSum;
        }

        if (integralSum < -maxIntegralSum) {
            integralSum = -maxIntegralSum;
        }

        // reset integral sum upon setpoint changes
        if (position != lastPosition) {
            integralSum = 0;
        }

        double power = (kp * error) + (ki * integralSum) + (kd * derivative);

        motor.setPower(power);

        lastError = error;

        lastPosition = position;

        // reset the timer for next time
        timer.reset();

    }

    public void setPosition(double position){
        this.position=position;
    }

    public void setKp(double kp){
        this.kp=kp;
    }
    public void setKi(double ki){
        this.ki=ki;
    }

    public void setKd(double kd){
        this.kd=kd;
    }

    public double getCurrentPositon(){
        return motor.getCurrentPosition();
    }

    public double getTargetPositon(){
        return this.position;
    }

    public void reverse(){
        motor.setDirection(DcMotorEx.Direction.REVERSE);
    }

    public double getError(){
        return error;
    }

    public double getIntegralSum(){
        return integralSum;
    }

    public void stop(){
        motor.setPower(0);
    }

}