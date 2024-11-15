package org.firstinspires.ftc.teamcode.autonomy;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.common.Hardware;
import org.firstinspires.ftc.teamcode.common.PIDController;
import org.firstinspires.ftc.teamcode.common.drivetrain.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.common.drivetrain.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.util.WebcamCodeplm;


@Autonomous(name = "blueleft1")
public class blueleft1 extends LinearOpMode {

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
            square = webcam.findSquareWithBlueAverageFunc();
            telemetry.addData("Detected square", square);
            telemetry.update();
            idle();
        }


        //left

        TrajectorySequence firstPixelTraj = drive.trajectorySequenceBuilder(new Pose2d())
                .lineToLinearHeading(new Pose2d(28, 7, Math.toRadians(0)))
                .build();

        TrajectorySequence backdropPixelTraject = drive.trajectorySequenceBuilder(firstPixelTraj.end())
                .lineToLinearHeading(new Pose2d(20, 0, Math.toRadians(0)))
                .build();

        TrajectorySequence backdropPixelTraj = drive.trajectorySequenceBuilder(backdropPixelTraject.end())
                .lineToLinearHeading(new Pose2d(4, 7, Math.toRadians(90)))
                .build();

        TrajectorySequence toStackTraj1 = drive.trajectorySequenceBuilder(backdropPixelTraj.end())
                .lineToLinearHeading(new Pose2d(6, 25, Math.toRadians(90)))
                .build();

        TrajectorySequence toStackTraj2 = drive.trajectorySequenceBuilder(toStackTraj1.end())
                .lineToLinearHeading(new Pose2d(6, 40, Math.toRadians(90)))
                .build();

        TrajectorySequence toStackTrajec2 = drive.trajectorySequenceBuilder(toStackTraj2.end())
                .lineToLinearHeading(new Pose2d(20, 40, Math.toRadians(90)))
                .build();


        TrajectorySequence toStackTraject2 = drive.trajectorySequenceBuilder(toStackTrajec2.end())
                .lineToLinearHeading(new Pose2d(20, 42, Math.toRadians(90)))
                .build();
//
//        //center
        TrajectorySequence firstPixelTrajC = drive.trajectorySequenceBuilder(new Pose2d())
                .lineToLinearHeading(new Pose2d(35, 10, Math.toRadians(0)))
                .build();

        TrajectorySequence backdropPixelTrajectC = drive.trajectorySequenceBuilder(firstPixelTrajC.end())
                .lineToLinearHeading(new Pose2d(33, 0, Math.toRadians(180)))
                .build();

//        TrajectorySequence backdropPixelTrajorC = drive.trajectorySequenceBuilder(backdropPixelTrajectC.end())
//                .lineToLinearHeading(new Pose2d(34, 20, Math.toRadians(179)))
//                .build();

        TrajectorySequence backdropPixelTrajC = drive.trajectorySequenceBuilder(backdropPixelTrajectC.end())
                .lineToLinearHeading(new Pose2d(24.5, 20, Math.toRadians(179)))
                .build();

        TrajectorySequence toStackTraj1C = drive.trajectorySequenceBuilder(backdropPixelTrajC.end())
                .lineToLinearHeading(new Pose2d(24.5, 40, Math.toRadians(92)))
                .build();

        TrajectorySequence toStackTraj2C = drive.trajectorySequenceBuilder(toStackTraj1C.end())
                .lineToLinearHeading(new Pose2d(24.5, 44, Math.toRadians(89)))
                .build();
//
//
//
//        //right
        TrajectorySequence firstPixelTrajR = drive.trajectorySequenceBuilder(new Pose2d())
                .lineToLinearHeading(new Pose2d(28, 0, Math.toRadians(0)))
                .build();

        TrajectorySequence firstPixelTraR = drive.trajectorySequenceBuilder(firstPixelTrajR.end())
                .lineToLinearHeading(new Pose2d(23, -8, Math.toRadians(-179)))
                .build();

        TrajectorySequence backdropPixelTrajectR = drive.trajectorySequenceBuilder(firstPixelTraR.end())
                .lineToLinearHeading(new Pose2d(20, 10, Math.toRadians(-179)))
                .build();


        TrajectorySequence backdropPixelTrajecR = drive.trajectorySequenceBuilder(backdropPixelTrajectR.end())
                .lineToLinearHeading(new Pose2d(20, 20, Math.toRadians(89)))
                .build();
//
//        TrajectorySequence backdropPixelTrajR = drive.trajectorySequenceBuilder(backdropPixelTrajecR.end())
//                .lineToLinearHeading(new Pose2d(5, -5, Math.toRadians(92)))
//                .build();
//
//        TrajectorySequence toStackTraj1R = drive.trajectorySequenceBuilder(backdropPixelTrajR.end())
//                .lineToLinearHeading(new Pose2d(5, 25, Math.toRadians(92)))
//                .build();
//
//        TrajectorySequence toStackTraj2R = drive.trajectorySequenceBuilder(toStackTraj1R.end())
//                .lineToLinearHeading(new Pose2d(20, 45, Math.toRadians(92)))
//                .build();


        waitForStart();

        square = webcam.findSquareWithRedAverageFunc();
        telemetry.addData("Square", square);
        telemetry.update();

        while (opModeIsActive()) {

            idle();
//            square = webcam.findSquareWithRedAverageFunc();
//            telemetry.addData("Square", square);
//            telemetry.update();

            if (square == "left") {


                drive.followTrajectorySequence(firstPixelTraj);

                sleep(800);
                hardware.dropNow();
                sleep(400);

                drive.followTrajectorySequence(backdropPixelTraject);

                drive.followTrajectorySequence(backdropPixelTraj);

                drive.followTrajectorySequence(toStackTraj1);

                drive.followTrajectorySequence(toStackTraj2);

                drive.followTrajectorySequence(toStackTrajec2);

                drive.followTrajectorySequence(toStackTraject2);

                hardware.dropOnAuton();
                sleep(500);
                break;
            } else if (square == "center") {


                drive.followTrajectorySequence(firstPixelTrajC);
                drive.followTrajectorySequence(backdropPixelTrajectC);

                sleep(800);
                hardware.dropNow();
                sleep(400);

                drive.followTrajectorySequence(backdropPixelTrajC);

                drive.followTrajectorySequence(toStackTraj1C);
//
                drive.followTrajectorySequence(toStackTraj2C);

                hardware.dropOnAuton();
                sleep(500);
                break;
            } else if (square == "right") {


                drive.followTrajectorySequence(firstPixelTrajR);

                drive.followTrajectorySequence(firstPixelTraR);

                sleep(800);
                hardware.dropNow();
                sleep(400);

                drive.followTrajectorySequence(backdropPixelTrajectR);

                drive.followTrajectorySequence(backdropPixelTrajecR);
//
//                drive.followTrajectorySequence(backdropPixelTrajR);
//
//                drive.followTrajectorySequence(toStackTraj1R);
//
//                drive.followTrajectorySequence(toStackTraj2R);
//
//                hardware.dropOnAuton();
//                sleep(500);
                break;
            } else if (square == "unknown") {
                drive.followTrajectorySequence(firstPixelTrajC);
                drive.followTrajectorySequence(backdropPixelTrajectC);

                sleep(800);
                hardware.dropNow();
                sleep(400);

                drive.followTrajectorySequence(backdropPixelTrajC);

                drive.followTrajectorySequence(toStackTraj1C);
//
                drive.followTrajectorySequence(toStackTraj2C);

                hardware.dropOnAuton();
                sleep(500);
                break;
            }
        }
    }

    private void updateState() {
        while (!isStopRequested()) {
            liftPID.update();
        }
    }
}