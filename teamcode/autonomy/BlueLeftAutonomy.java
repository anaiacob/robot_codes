package org.firstinspires.ftc.teamcode.autonomy;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.profile.VelocityConstraint;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.acmerobotics.roadrunner.trajectory.constraints.TrajectoryVelocityConstraint;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.common.Hardware;
import org.firstinspires.ftc.teamcode.common.PIDController;
import org.firstinspires.ftc.teamcode.common.drivetrain.DriveConstants;
import org.firstinspires.ftc.teamcode.common.drivetrain.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.common.drivetrain.trajectorysequence.TrajectorySequence;

@Disabled
@Deprecated
@Autonomous(name = "BlueLeftAutonomy")
public class BlueLeftAutonomy extends LinearOpMode {

    private Hardware hardware;

    private PIDController liftPID;

    private Thread updateStateThread = new Thread(() -> updateState());


    private SampleMecanumDrive drive;

    @Override
    public void runOpMode() throws InterruptedException {
        drive = new SampleMecanumDrive(hardwareMap);

        TrajectorySequence firstPixelTraj = drive.trajectorySequenceBuilder(new Pose2d())
                .lineToLinearHeading(new Pose2d(27, -22, Math.toRadians(270)))
                .build();

        TrajectorySequence backdropPixelTraj = drive.trajectorySequenceBuilder(firstPixelTraj.end())
                .lineToLinearHeading(new Pose2d(26, -40, Math.toRadians(93)))
                .build();

        TrajectorySequence toStackTraj1 = drive.trajectorySequenceBuilder(backdropPixelTraj.end())
                .lineToLinearHeading(new Pose2d(24, 10, Math.toRadians(93)))
                .build();

        TrajectorySequence toStackTraj2 = drive.trajectorySequenceBuilder(toStackTraj1.end())
                .lineToLinearHeading(new Pose2d(24, 20, Math.toRadians(93)))
                .build();

        TrajectorySequence toStackTraj3 = drive.trajectorySequenceBuilder(toStackTraj2.end())
                .lineToLinearHeading(new Pose2d(24, 60, Math.toRadians(93)))
                .build();


        waitForStart();


        drive.followTrajectorySequence(firstPixelTraj);

        drive.followTrajectorySequence(backdropPixelTraj);

        drive.followTrajectorySequence(toStackTraj1);

        drive.followTrajectorySequence(toStackTraj2);

        drive.followTrajectorySequence(toStackTraj3);

    }

    private void updateState() {
        while (!isStopRequested()) {
            liftPID.update();
        }
    }

}
