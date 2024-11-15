package org.firstinspires.ftc.teamcode.common;

import static java.lang.Double.max;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@Config
@TeleOp(name="PIDTunner")
public class PIDTunner extends LinearOpMode {

    private PIDController controller;

    public static double kp=0;
    public static double ki=0;
    public static double kd=0;

    public static double position=0;

    public FtcDashboard dashboard;


    @Override
    public void runOpMode() throws InterruptedException {

        dashboard = FtcDashboard.getInstance();

        controller=new PIDController(kp,ki,kd,0,"lift",hardwareMap,telemetry);
        controller.reverse();

        waitForStart();

        while(!isStopRequested()){
            position=Math.min(1500,position);

            controller.setKp(kp);
            controller.setKi(ki);
            controller.setKd(kd);
            controller.setPosition(position);
            controller.update();

            double currentPos=controller.getCurrentPositon();

            TelemetryPacket packet=new TelemetryPacket();
            packet.put("-200",-200);
            packet.put("currentPos",currentPos);
            packet.put("targetPos",position);
            packet.put("2000",2000);
            dashboard.sendTelemetryPacket(packet);
            dashboard.updateConfig();

        }

    }
}
