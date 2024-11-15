
package org.firstinspires.ftc.teamcode.autonomy;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.util.WebcamCodeplm;
//import org.firstinspires.ftc.teamcode.drive.opmode.Hardware;

@Autonomous(group = "cameraCode")

public class cameraCode extends LinearOpMode {

    private WebcamCodeplm webcam;
    private String square = "";


    private boolean isRunning = true;

    // private static final long BATTERY_UPDATE_INTERVAL_MS = 5000; // Update every 5 seconds
    // private long lastBatteryUpdateTimestamp = 0;


    @Override
    public void runOpMode() throws InterruptedException {


        webcam = new WebcamCodeplm();

        webcam.init(hardwareMap);


        while (!isStarted()) {
            // Update and display the color discovered by the webcam during the waiting period
            square=webcam.findSquareWithRedAverageFunc();
            telemetry.addData("Detected square", square);
            telemetry.update();
            idle();
        }

        waitForStart();


        square = webcam.findSquareWithRedAverageFunc();
        telemetry.addData("Square", square);
        telemetry.update();

        while (opModeIsActive()) {

            idle();
            square = webcam.findSquareWithRedAverageFunc();
            telemetry.addData("Square", square);
            telemetry.update();
        }}
//    private void updateBatteryVoltage() {
//        if (System.currentTimeMillis() - lastBatteryUpdateTimestamp > BATTERY_UPDATE_INTERVAL_MS) {
//            VoltageSensor voltageSensor = hardwareMap.voltageSensor.iterator().next();
//            double voltage = voltageSensor.getVoltage();
//            RobotLog.dd("BatteryVoltage", "Voltage: %.2fV", voltage);
//            // You can perform additional actions with the battery voltage here, such as sending telemetry or making decisions based on the voltage level
//
//            lastBatteryUpdateTimestamp = System.currentTimeMillis();
//        }
//    }
}