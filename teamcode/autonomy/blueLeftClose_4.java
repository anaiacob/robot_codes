package org.firstinspires.ftc.teamcode.autonomy;


import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.common.Hardware;
import org.firstinspires.ftc.teamcode.common.PIDController;
import org.firstinspires.ftc.teamcode.common.drivetrain.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.common.drivetrain.trajectorysequence.TrajectorySequence;

@Autonomous(name = "blueLeftClose4")
public class blueLeftClose_4 extends LinearOpMode {

    private Hardware hardware;

    private PIDController liftPID;

    private Thread updateStateThread = new Thread(() -> updateState());
    private boolean isRunning = true;


    private SampleMecanumDrive drive;

    @Override
    public void runOpMode() throws InterruptedException {
        hardware = new Hardware(hardwareMap);
        drive = new SampleMecanumDrive(hardwareMap);

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
                .lineToLinearHeading(new Pose2d(25, 90, Math.toRadians(92)))
                .build();

        TrajectorySequence toStackTraject3 = drive.trajectorySequenceBuilder(toStackTraj2.end())
                .lineToLinearHeading(new Pose2d(5, 70, Math.toRadians(92)))
                .build();

        TrajectorySequence toStackTrajectory3 = drive.trajectorySequenceBuilder(toStackTraject3.end())
                .lineToLinearHeading(new Pose2d(5, 5, Math.toRadians(92)))
                .build();

        TrajectorySequence toStackTraj3 = drive.trajectorySequenceBuilder(toStackTrajectory3.end())
                .lineToLinearHeading(new Pose2d(25, -8, Math.toRadians(92)))
                .build();

        TrajectorySequence toStackTraj4 = drive.trajectorySequenceBuilder(toStackTraj3.end())
                .lineToLinearHeading(new Pose2d(25, -9, Math.toRadians(92)))
                .build();

        TrajectorySequence toStackTrajectory5 = drive.trajectorySequenceBuilder(toStackTraj4.end())
                .lineToLinearHeading(new Pose2d(5, 2, Math.toRadians(92)))
                .build();

        TrajectorySequence toStackTraject5 = drive.trajectorySequenceBuilder(toStackTrajectory5.end())
                .lineToLinearHeading(new Pose2d(5, 50, Math.toRadians(92)))
                .build();


        TrajectorySequence toStackTraj5 = drive.trajectorySequenceBuilder(toStackTraject5.end())
                .lineToLinearHeading(new Pose2d(25, 105, Math.toRadians(92)))
                .build();


        waitForStart();

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

    private void updateState() {
        while (!isStopRequested()) {
            liftPID.update();
        }
    }


}

