package org.firstinspires.ftc.teamcode.autonomy;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.acmerobotics.roadrunner.trajectory.TrajectoryBuilder;
import com.acmerobotics.roadrunner.trajectory.constraints.TrajectoryVelocityConstraint;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.common.drivetrain.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.common.drivetrain.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.common.drivetrain.trajectorysequence.TrajectorySequenceBuilder;

@Deprecated
@Disabled
@Config
@Autonomous(name = "TestAuto")
public class TestAuto extends LinearOpMode {

    SampleMecanumDrive drive;


    static public double endX=0;
    static public double endY=0;
    static public double endAngle=0;

    @Override
    public void runOpMode() throws InterruptedException {
        drive=new SampleMecanumDrive(hardwareMap);

        TrajectorySequence trajectory=drive.trajectorySequenceBuilder(new Pose2d()).lineToLinearHeading(new Pose2d(endX,endY, Math.toRadians(endAngle))).build();

        waitForStart();

        drive.followTrajectorySequence(trajectory);


    }
}
