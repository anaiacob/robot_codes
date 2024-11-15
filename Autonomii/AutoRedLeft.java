package org.firstinspires.ftc.teamcode.Autonomii;


import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.WebCam;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;
@Disabled
@Autonomous (name="AutoRedLeft")
public class AutoRedLeft extends LinearOpMode {
    private WebCam webcam;
    private String square = "";
    public Servo handLeftServo = null;
    public Servo handRightServo = null;
    @Override
    public void runOpMode() throws InterruptedException {
        webcam = new WebCam();

        webcam.init(hardwareMap);
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        handLeftServo = hardwareMap.servo.get("handLeftServo");
        handRightServo = hardwareMap.servo.get("handRightServo");
        while (!isStarted()) {
            // Update and display the color discovered by the webcam during the waiting period
            square = webcam.findSquareWithRedAverageFunc();
            telemetry.addData("Detected square", square);
            telemetry.update();
            idle();
        }


        ///TRAIECTORII

        TrajectorySequence lasa_pixel_mov_center = drive.trajectorySequenceBuilder(new Pose2d())
                .lineToLinearHeading(new Pose2d(31,5 , Math.toRadians(0)))
                .build();
        TrajectorySequence ia_pixel_alb =drive.trajectorySequenceBuilder(new Pose2d())
                .lineToLinearHeading(new Pose2d(-10,0,Math.toRadians(90)))
                .build();
        TrajectorySequence mers_catre_pixel_alb = drive.trajectorySequenceBuilder(new Pose2d())
                .lineToLinearHeading(new Pose2d(-30,0,Math.toRadians(0)))
                .build();
        TrajectorySequence mers_intors_fata = drive.trajectorySequenceBuilder(new Pose2d())
                .lineToLinearHeading(new Pose2d(10,30,Math.toRadians(-90)))
                .build();
        TrajectorySequence mers_fata_backdrop = drive.trajectorySequenceBuilder(new Pose2d())
                .lineToLinearHeading(new Pose2d(0,100,Math.toRadians(0)))
                .build();

        handLeftServo.setPosition(0.65);
        handRightServo.setPosition(0.32);
        waitForStart();

        square = webcam.findSquareWithRedAverageFunc();
        telemetry.addData("Square", square);
        telemetry.update();

        while (opModeIsActive()) {

            idle();
            if (square == "left"){

            }
            else if(square == "center")
            {
                drive.followTrajectorySequence(lasa_pixel_mov_center);
                sleep(100);


            }
            else if(square == "right")
            {

            }
            else if(square == "unknown")
            {

            }

        }

    }
}
