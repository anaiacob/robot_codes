package org.firstinspires.ftc.teamcode.autonomy;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.common.Hardware;
import org.firstinspires.ftc.teamcode.common.PIDController;
import org.firstinspires.ftc.teamcode.common.drivetrain.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.common.drivetrain.trajectorysequence.TrajectorySequence;

@Disabled
@Deprecated
@Autonomous(name = "blueRight")
public class blueRight extends LinearOpMode {

    private Hardware hardware;

    private PIDController liftPID;

    private Thread updateStateThread = new Thread(() -> updateState());


    private SampleMecanumDrive drive;

    @Override
    public void runOpMode() throws InterruptedException {
        hardware = new Hardware(hardwareMap);
        drive = new SampleMecanumDrive(hardwareMap);

        TrajectorySequence firstPixelTraj = drive.trajectorySequenceBuilder(new Pose2d())
                .lineToLinearHeading(new Pose2d(30, -1, Math.toRadians(0)))
                .build();

        TrajectorySequence backdropPixelTraj = drive.trajectorySequenceBuilder(firstPixelTraj.end())
                .lineToLinearHeading(new Pose2d(46, 0, Math.toRadians(92)))
                .build();

        TrajectorySequence toStackTraj1 = drive.trajectorySequenceBuilder(backdropPixelTraj.end())
                .lineToLinearHeading(new Pose2d(46, 40, Math.toRadians(92)))
                .build();

        TrajectorySequence toStackTraj2 = drive.trajectorySequenceBuilder(toStackTraj1.end())
                .lineToLinearHeading(new Pose2d(25, 90, Math.toRadians(92)))
                .build();

        TrajectorySequence toStackTraj3 = drive.trajectorySequenceBuilder(toStackTraj2.end())
                .lineToLinearHeading(new Pose2d(25, -8, Math.toRadians(92)))
                .build();

        TrajectorySequence toStackTraj4 = drive.trajectorySequenceBuilder(toStackTraj3.end())
                .lineToLinearHeading(new Pose2d(25, -9, Math.toRadians(92)))
                .build();

        TrajectorySequence toStackTraj5 = drive.trajectorySequenceBuilder(toStackTraj4.end())
                .lineToLinearHeading(new Pose2d(25, 105, Math.toRadians(92)))
                .build();


        waitForStart();


        drive.followTrajectorySequence(firstPixelTraj);

        // sleep(800);
        hardware.dropNow();

        drive.followTrajectorySequence(backdropPixelTraj);

        drive.followTrajectorySequence(toStackTraj1);

        drive.followTrajectorySequence(toStackTraj2);

        hardware.dropOnAuton();
        sleep(300);

        drive.followTrajectorySequence(toStackTraj3);

        drive.followTrajectorySequence(toStackTraj4);

        hardware.setPower(hardware.vacuumMotor, 1);
        sleep(1000);

        drive.followTrajectorySequence(toStackTraj5);

        hardware.dropOnAuton();
        sleep(500);


    }

    private void updateState() {
        while (!isStopRequested()) {
            liftPID.update();
        }
    }


}

