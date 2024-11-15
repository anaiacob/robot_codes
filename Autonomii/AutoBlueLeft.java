package org.firstinspires.ftc.teamcode.Autonomii;


import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.WebCam;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;
@Disabled
@Autonomous (name="AutoBlueLeft")
public class AutoBlueLeft extends LinearOpMode {
    private WebCam webcam;
    private String square = "";
    @Override
    public void runOpMode() throws InterruptedException {
        webcam = new WebCam();

        webcam.init(hardwareMap);
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        while (!isStarted()) {
            // Update and display the color discovered by the webcam during the waiting period
            square = webcam.findSquareWithBlueAverageFunc();
            telemetry.addData("Detected square", square);
            telemetry.update();
            idle();
        }


        ///TRAIECTORII

        TrajectorySequence lasa_pixel_mov_center = drive.trajectorySequenceBuilder(new Pose2d())
                .lineToLinearHeading(new Pose2d(31,0 , Math.toRadians(0)))
                .build();
        TrajectorySequence lasa_pixel_mov_right = drive.trajectorySequenceBuilder(new Pose2d())
                .lineToLinearHeading(new Pose2d(31,-3 , Math.toRadians(0)))
                .build();
        TrajectorySequence lasa_pixel_mov_left = drive.trajectorySequenceBuilder(new Pose2d())
                .lineToLinearHeading(new Pose2d(31,3 , Math.toRadians(0)))
                .build();
        waitForStart();

        square = webcam.findSquareWithRedAverageFunc();
        telemetry.addData("Square", square);
        telemetry.update();

        while (opModeIsActive()) {

            idle();
            if (square == "left"){
                drive.followTrajectorySequence(lasa_pixel_mov_left);
            }
            else if(square == "center")
            {
                drive.followTrajectorySequence(lasa_pixel_mov_center);
            }
            else if(square == "right")
            {
                drive.followTrajectorySequence(lasa_pixel_mov_right);
            }
            else if(square == "unknown")
            {
                drive.followTrajectorySequence(lasa_pixel_mov_center);
            }

        }

    }
}
