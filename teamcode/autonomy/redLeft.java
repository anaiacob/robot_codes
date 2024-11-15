package org.firstinspires.ftc.teamcode.autonomy;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.common.Hardware;
import org.firstinspires.ftc.teamcode.common.PIDController;
import org.firstinspires.ftc.teamcode.common.drivetrain.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.common.drivetrain.trajectorysequence.TrajectorySequence;


        import com.acmerobotics.roadrunner.geometry.Pose2d;
        import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
        import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

        import org.firstinspires.ftc.teamcode.common.Hardware;
        import org.firstinspires.ftc.teamcode.common.PIDController;
        import org.firstinspires.ftc.teamcode.common.drivetrain.SampleMecanumDrive;
        import org.firstinspires.ftc.teamcode.common.drivetrain.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.util.WebcamCodeplm;


@Autonomous(name = "redLeft")
public class redLeft extends LinearOpMode {

    private Hardware hardware;
    private PIDController liftPID;
    private WebcamCodeplm webcam;
    private String square = "";


    private boolean isRunning = true;


    private Thread updateStateThread = new Thread(() -> updateState());


    private SampleMecanumDrive drive;


    @Override
    public void runOpMode() throws InterruptedException {
        hardware = new Hardware(hardwareMap);
        drive = new SampleMecanumDrive(hardwareMap);
        webcam = new WebcamCodeplm();

        webcam.init(hardwareMap);


        while (!isStarted()) {
            // Update and display the color discovered by the webcam during the waiting period
            square = webcam.findSquareWithRedAverageFunc();
            telemetry.addData("Detected square", square);
            telemetry.update();
            idle();
        }

        //left

        TrajectorySequence firstPixelTraj = drive.trajectorySequenceBuilder(new Pose2d())
                .lineToLinearHeading(new Pose2d(25, 7, Math.toRadians(0)))
                .build();

        TrajectorySequence backdropPixelTraject = drive.trajectorySequenceBuilder(firstPixelTraj.end())
                .lineToLinearHeading(new Pose2d(25, -2, Math.toRadians(0)))
                .build();


        TrajectorySequence toStackTraj1 = drive.trajectorySequenceBuilder(backdropPixelTraject.end())
                .lineToLinearHeading(new Pose2d(45, -2, Math.toRadians(-0)))
                .build();

        TrajectorySequence toStackTrajec1 = drive.trajectorySequenceBuilder(toStackTraj1.end())
                .lineToLinearHeading(new Pose2d(50, -6, Math.toRadians(-91)))
                .build();


        TrajectorySequence toStackTraj2 = drive.trajectorySequenceBuilder(toStackTrajec1.end())
                .lineToLinearHeading(new Pose2d(50, -60, Math.toRadians(-91)))
                .build();

        TrajectorySequence toStackTraje2 = drive.trajectorySequenceBuilder(toStackTraj2.end())
                .lineToLinearHeading(new Pose2d(25, -90, Math.toRadians(-91)))
                .build();

        TrajectorySequence toStackTrajec2 = drive.trajectorySequenceBuilder(toStackTraje2.end())
                .lineToLinearHeading(new Pose2d(24.25, -93, Math.toRadians(-90)))
                .build();


//probleme la pus pe tabla

        //center
        TrajectorySequence firstPixelTrajC = drive.trajectorySequenceBuilder(new Pose2d())
                .lineToLinearHeading(new Pose2d(38, 4, Math.toRadians(-92)))
                .build();

//        TrajectorySequence backdropPixelTraj = drive.trajectorySequenceBuilder(firstPixelTraj.end())
//                .lineToLinearHeading(new Pose2d(25, 0, Math.toRadians(-92)))
//                .build();

        TrajectorySequence backdropPixelTrajectC = drive.trajectorySequenceBuilder(firstPixelTrajC.end())
                .lineToLinearHeading(new Pose2d(23, -2, Math.toRadians(-91)))
                .build();


        TrajectorySequence toStackTraj1C = drive.trajectorySequenceBuilder(backdropPixelTrajectC.end())
                .lineToLinearHeading(new Pose2d(23, -40, Math.toRadians(-91)))
                .build();

        TrajectorySequence toStackTraj2C = drive.trajectorySequenceBuilder(toStackTraj1C.end())
                .lineToLinearHeading(new Pose2d(24, -85, Math.toRadians(-91)))
                .build();

        TrajectorySequence toStackTrajec2C = drive.trajectorySequenceBuilder(toStackTraj2C.end())
                .lineToLinearHeading(new Pose2d(24.25, -92, Math.toRadians(-90)))
                .build();

//        TrajectorySequence toStackTraj3 = drive.trajectorySequenceBuilder(toStackTrajec2.end())
//                .lineToLinearHeading(new Pose2d(23, 8, Math.toRadians(-91)))
//                .build();
//
//        TrajectorySequence toStackTraj4 = drive.trajectorySequenceBuilder(toStackTraj3.end())
//                .lineToLinearHeading(new Pose2d(23, -9, Math.toRadians(-91)))
//                .build();
//
//        TrajectorySequence toStackTraj5 = drive.trajectorySequenceBuilder(toStackTraj4.end())
//                .lineToLinearHeading(new Pose2d(23, 105, Math.toRadians(-91)))
//                .build();


        //right
        TrajectorySequence firstPixelTrajL = drive.trajectorySequenceBuilder(new Pose2d())
                .lineToLinearHeading(new Pose2d(25, 7, Math.toRadians(0)))
                .build();

        TrajectorySequence backdropPixelTrajectL = drive.trajectorySequenceBuilder(firstPixelTrajL.end())
                .lineToLinearHeading(new Pose2d(25, 0, Math.toRadians(-178)))
                .build();
//
        TrajectorySequence firstPixelTrajecL = drive.trajectorySequenceBuilder( backdropPixelTrajectL.end())
                .lineToLinearHeading(new Pose2d(25, -5, Math.toRadians(-178)))
                .build();

        TrajectorySequence backdropPixelTrajectorL = drive.trajectorySequenceBuilder(firstPixelTrajecL.end())
                .lineToLinearHeading(new Pose2d(25, 7, Math.toRadians(-178)))
                .build();

        TrajectorySequence backdropPixelTrajectoryL = drive.trajectorySequenceBuilder(backdropPixelTrajectorL.end())
                .lineToLinearHeading(new Pose2d(25, 8, Math.toRadians(0)))
                .build();

        TrajectorySequence backdropPixelTrajectorysL = drive.trajectorySequenceBuilder(backdropPixelTrajectoryL.end())
                .lineToLinearHeading(new Pose2d(45, 7, Math.toRadians(-178)))
                .build();


        TrajectorySequence toStackTraj1L = drive.trajectorySequenceBuilder(backdropPixelTrajectorysL.end())
                .lineToLinearHeading(new Pose2d(45, -2, Math.toRadians(-0)))
                .build();

        TrajectorySequence toStackTrajec1L = drive.trajectorySequenceBuilder(toStackTraj1L.end())
                .lineToLinearHeading(new Pose2d(50, -6, Math.toRadians(-91)))
                .build();


        TrajectorySequence toStackTraj2L = drive.trajectorySequenceBuilder(toStackTrajec1L.end())
                .lineToLinearHeading(new Pose2d(50, -60, Math.toRadians(-91)))
                .build();

        TrajectorySequence toStackTraje2L = drive.trajectorySequenceBuilder(toStackTraj2L.end())
                .lineToLinearHeading(new Pose2d(25, -90, Math.toRadians(-91)))
                .build();

        TrajectorySequence toStackTrajec2L = drive.trajectorySequenceBuilder(toStackTraje2L.end())
                .lineToLinearHeading(new Pose2d(24.25, -93, Math.toRadians(-90)))
                .build();

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

                // sleep(800);
                hardware.dropNow();
                sleep(800);

                drive.followTrajectorySequence(backdropPixelTraject);

                drive.followTrajectorySequence(toStackTraj1);

                drive.followTrajectorySequence(toStackTrajec1);
//
                drive.followTrajectorySequence(toStackTraj2);

                drive.followTrajectorySequence(toStackTraje2);

                drive.followTrajectorySequence(toStackTrajec2);

                hardware.dropOnAuton();
                sleep(300);

                break;

            } else if (square == "center") {


                drive.followTrajectorySequence(firstPixelTrajC);

                // sleep(800);
                hardware.dropNow();

                drive.followTrajectorySequence(backdropPixelTrajectC);

                drive.followTrajectorySequence(toStackTraj1C);

                drive.followTrajectorySequence(toStackTraj2C);

                drive.followTrajectorySequence(toStackTrajec2C);

                hardware.dropOnAuton();
                sleep(300);
//
                drive.followTrajectorySequence(backdropPixelTrajectorL);
//
//                drive.followTrajectorySequence(toStackTraj4);
//
//                hardware.setPower(hardware.vacuumMotor, 1);
//                sleep(1000);
//
//                drive.followTrajectorySequence(toStackTraj5);
//
//                hardware.dropOnAuton();
//                sleep(500);


                break;
            } else if (square == "right") {

                drive.followTrajectorySequence(firstPixelTraj);

                // sleep(800);
                hardware.dropNow();
                sleep(800);

                drive.followTrajectorySequence(backdropPixelTraject);

                drive.followTrajectorySequence(toStackTraj1);

                drive.followTrajectorySequence(toStackTrajec1);
//
                drive.followTrajectorySequence(toStackTraj2);

                drive.followTrajectorySequence(toStackTraje2);

                drive.followTrajectorySequence(toStackTrajec2);

                hardware.dropOnAuton();
                sleep(300);

                break;



//
//                drive.followTrajectorySequence(firstPixelTrajL);
//
//                drive.followTrajectorySequence(backdropPixelTrajectL);
//
//                drive.followTrajectorySequence(firstPixelTrajecL);
//
//                sleep(800);
//                hardware.dropNow();
//                sleep(800);

//                drive.followTrajectorySequence(backdropPixelTraject);
//
//                drive.followTrajectorySequence(toStackTraj1);
//
//                drive.followTrajectorySequence(toStackTrajec1);
////
//                drive.followTrajectorySequence(toStackTraj2);
//
//                drive.followTrajectorySequence(toStackTraje2);
//
//                drive.followTrajectorySequence(toStackTrajec2);
//
//                hardware.dropOnAuton();
//                sleep(300);

            }
        }

    }

    private void updateState() {
        while (!isStopRequested()) {
            liftPID.update();
        }
    }

}

