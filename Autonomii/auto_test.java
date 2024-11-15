package org.firstinspires.ftc.teamcode.Autonomii;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

@Autonomous(name="auto_test")
public class auto_test extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        TrajectorySequence traiectorie1 = drive.trajectorySequenceBuilder(new Pose2d())
                .lineToLinearHeading(new Pose2d(31,0 , Math.toRadians(0)))
                .build();
        TrajectorySequence traiectorie2 = drive.trajectorySequenceBuilder(traiectorie1.end())
                .lineToLinearHeading(new Pose2d(31,0 , Math.toRadians(0)))
                .build();
        waitForStart();
        while (opModeIsActive()){
            idle();
            drive.followTrajectorySequence(traiectorie1);
            sleep(100);
            drive.followTrajectorySequence(traiectorie2);
        }
    }
}
