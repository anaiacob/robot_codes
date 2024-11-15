package org.firstinspires.ftc.teamcode.Autonomii;
//exterior

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.WebCam;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;
@Disabled
@Autonomous (name="AutoBlueRight")
public class AutoBlueRight extends LinearOpMode {
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
        //left
        //lasa pixel-ul mov
//        TrajectorySequence firstPixelTrajLeft = drive.trajectorySequenceBuilder(new Pose2d())
//                .lineToLinearHeading(new Pose2d(, , Math.toRadians(0)))
//                .build();
        //se intoarce sa ia un pixel din stack

        //
        TrajectorySequence lasa_pixel_mov_left = drive.trajectorySequenceBuilder(new Pose2d())
                .lineToLinearHeading(new Pose2d(27,5 , Math.toRadians(0))) //schiba y
                .build();
        //center

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

        //right

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
