package org.firstinspires.ftc.teamcode.autonomy;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.common.Hardware;
import org.firstinspires.ftc.teamcode.common.PIDController;
import org.firstinspires.ftc.teamcode.common.drivetrain.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.common.drivetrain.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.util.WebcamCodeplm;

@Disabled
@Deprecated
@Autonomous(name = "BlueRightClose")
public class BlueRightClose extends LinearOpMode {

    private Hardware hardware;
    private PIDController liftPID;
    private WebcamCodeplm webcam;
    private String square = "";


    private boolean isRunning = true;


    private Thread updateStateThread = new Thread(() -> updateState());


    private SampleMecanumDrive drive;

    //int first, second, ahead1;
    @Override
    public void runOpMode() throws InterruptedException {
        hardware = new Hardware(hardwareMap);
        drive = new SampleMecanumDrive(hardwareMap);
        webcam = new WebcamCodeplm();

        webcam.init(hardwareMap);


        while (!isStarted()) {
            // Update and display the color discovered by the webcam during the waiting period
            square=webcam.findSquareWithRedAverageFunc();
            telemetry.addData("Detected square", square);
            telemetry.update();
            idle();
        }

        TrajectorySequence firstPixelTraj = drive.trajectorySequenceBuilder(new Pose2d())
                .lineToLinearHeading(new Pose2d(28, 7, Math.toRadians(0)))
                .build();

        TrajectorySequence backdropPixelTraject = drive.trajectorySequenceBuilder(firstPixelTraj.end())
                .lineToLinearHeading(new Pose2d(20, -7, Math.toRadians(0)))
                .build();

        TrajectorySequence backdropPixelTraj = drive.trajectorySequenceBuilder(backdropPixelTraject.end())
                .lineToLinearHeading(new Pose2d(5, -5, Math.toRadians(92)))
                .build();

        TrajectorySequence toStackTraj1 = drive.trajectorySequenceBuilder(backdropPixelTraj.end())
                .lineToLinearHeading(new Pose2d(5, 40, Math.toRadians(92)))
                .build();

        TrajectorySequence toStackTraj2 = drive.trajectorySequenceBuilder(toStackTraj1.end())
                .lineToLinearHeading(new Pose2d(20, 88, Math.toRadians(92)))
                .build();

        TrajectorySequence toStackTraject3 = drive.trajectorySequenceBuilder(toStackTraj2.end())
                .lineToLinearHeading(new Pose2d(0, 70, Math.toRadians(92)))
                .build();

        TrajectorySequence toStackTrajectory3 = drive.trajectorySequenceBuilder(toStackTraject3.end())
                .lineToLinearHeading(new Pose2d(0, 5, Math.toRadians(92)))
                .build();

        TrajectorySequence toStackTraj3 = drive.trajectorySequenceBuilder(toStackTrajectory3.end())
                .lineToLinearHeading(new Pose2d(25, -4, Math.toRadians(92)))
                .build();

        TrajectorySequence toStackTraj4 = drive.trajectorySequenceBuilder(toStackTraj3.end())
                .lineToLinearHeading(new Pose2d(25, -4, Math.toRadians(92)))
                .build();

        TrajectorySequence toStackTrajectory5 = drive.trajectorySequenceBuilder(toStackTraj4.end())
                .lineToLinearHeading(new Pose2d(3, 2, Math.toRadians(92)))
                .build();

        TrajectorySequence toStackTraject5 = drive.trajectorySequenceBuilder(toStackTrajectory5.end())
                .lineToLinearHeading(new Pose2d(3, 50, Math.toRadians(92)))
                .build();


        TrajectorySequence toStackTraj5 = drive.trajectorySequenceBuilder(toStackTraject5.end())
                .lineToLinearHeading(new Pose2d(23, 86, Math.toRadians(92)))
                .build();


        waitForStart();

        square = webcam.findSquareWithRedAverageFunc();
        telemetry.addData("Square", square);
        telemetry.update();

        while (opModeIsActive()) {

            idle();
            square = webcam.findSquareWithRedAverageFunc();
            telemetry.addData("Square", square);
            telemetry.update();

            if (square == "left") {
//            first = 7;
//            second = -7;
//            ahead1 = 28;
//
//        } else if (square == "RIGHT") {
//            first = -8;
//            second = -10;
//            ahead1 = 28;
//
//        } else if (square == "CENTER") {
//            first = -4;
//            second = -10;
//            ahead1 = 35;
//        }


                drive.followTrajectorySequence(firstPixelTraj);

                sleep(100);
                hardware.dropNow();
                sleep(300);

                drive.followTrajectorySequence(backdropPixelTraject);

                drive.followTrajectorySequence(backdropPixelTraj);

                drive.followTrajectorySequence(toStackTraj1);

                drive.followTrajectorySequence(toStackTraj2);

                hardware.dropOnAuton();
                sleep(500);

                drive.followTrajectorySequence(toStackTraject3);

                drive.followTrajectorySequence(toStackTrajectory3);

                drive.followTrajectorySequence(toStackTraj3);

                drive.followTrajectorySequence(toStackTraj4);

                hardware.setPower(hardware.vacuumMotor, 1);
                sleep(1000);

                drive.followTrajectorySequence(toStackTrajectory5);

                drive.followTrajectorySequence(toStackTraject5);

                drive.followTrajectorySequence(toStackTraj5);

                hardware.dropOnAuton();

            }
        }
    }

    private void updateState() {
        while (!isStopRequested()) {
            liftPID.update();
        }
    }


}

