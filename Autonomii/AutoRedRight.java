package org.firstinspires.ftc.teamcode.Autonomii;


import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.WebCam;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;
//extremitatea de langa stack-uri
@Disabled
@Autonomous (name="AutoRedRight")
public class AutoRedRight extends LinearOpMode {
    private WebCam webcam;
    private String square = "";
    public Servo handLeftServo = null;
    public Servo handRightServo = null;
    public Servo wristServo = null;
    @Override
    public void runOpMode() throws InterruptedException {
        webcam = new WebCam();

        webcam.init(hardwareMap);
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        handLeftServo = hardwareMap.servo.get("handLeftServo");
        handRightServo = hardwareMap.servo.get("handRightServo");
        wristServo = hardwareMap.servo.get("wristServo");
        while (!isStarted()) {
            // Update and display the color discovered by the webcam during the waiting period
            square = webcam.findSquareWithRedAverageFunc();
            telemetry.addData("Detected square", square);
            telemetry.update();
            idle();
        }

        //robotul are 14,331 inch
        ///TRAIECTORII
        //left
        TrajectorySequence lasa_pixel_mov_left = drive.trajectorySequenceBuilder(new Pose2d())
                .lineToLinearHeading(new Pose2d(31, 5, Math.toRadians(0)))
                .build();
        TrajectorySequence indreptare_left = drive.trajectorySequenceBuilder(lasa_pixel_mov_left.end())
                .lineToLinearHeading(new Pose2d(31, 0, Math.toRadians(0)))
                .build();
        TrajectorySequence spate_left = drive.trajectorySequenceBuilder(indreptare_left.end())
                .lineToLinearHeading(new Pose2d(24,0 , Math.toRadians(0)))
                .build();
        TrajectorySequence intors_left =drive.trajectorySequenceBuilder(spate_left.end())
                .turn(85)
                .build();
        TrajectorySequence backdrop_left = drive.trajectorySequenceBuilder(intors_left.end())
                .lineToLinearHeading(new Pose2d(-15,0 , Math.toRadians(0)))
                .build();
        TrajectorySequence parcare_left = drive.trajectorySequenceBuilder(backdrop_left.end())
                .lineToLinearHeading(new Pose2d(-15,0 , Math.toRadians(0)))
                .build();
        //center
//        TrajectorySequence firstPixelTraj = drive.trajectorySequenceBuilder(new Pose2d())
//                .lineToLinearHeading(new Pose2d(33.67, 0, Math.toRadians(0)))
//                .build();
//        TrajectorySequence dat_spate_dupa_ce_pune_pixel =drive.trajectorySequenceBuilder(new Pose2d())
//                .lineToLinearHeading(new Pose2d(-11, 0, Math.toRadians(0)))
//                .build();
//        TrajectorySequence prima_rotatie = drive.trajectorySequenceBuilder(new Pose2d())
//                .lineToLinearHeading(new Pose2d(0, 0, Math.toRadians(-90)))
//                .build();
//        TrajectorySequence merge_catre_stack = drive.trajectorySequenceBuilder(new Pose2d())
//                .lineToLinearHeading(new Pose2d(-29, 0, Math.toRadians(0)))
//                .build();
//        TrajectorySequence da_cu_spatele_dupa_ce_ia_pixel_alb = drive.trajectorySequenceBuilder(new Pose2d())
//                .lineToLinearHeading(new Pose2d(10, 0, Math.toRadians(-0)))
//                .build();
//        TrajectorySequence rotatie_cu_pixel_alb = drive.trajectorySequenceBuilder(new Pose2d())
//                .lineToLinearHeading(new Pose2d(0, 0, Math.toRadians(-90)))
//                .build();
//        //nu stiu pe y daca e corect
//        TrajectorySequence mers_fata = drive.trajectorySequenceBuilder(new Pose2d())
//                .lineToLinearHeading(new Pose2d(-40, 10, Math.toRadians(90)))
//                .build();
//        TrajectorySequence mers_catre_backdrop = drive.trajectorySequenceBuilder(new Pose2d())
//                .lineToLinearHeading(new Pose2d(-100, 0, Math.toRadians(0)))
//                .build();
//        TrajectorySequence ajuns_backdrop = drive.trajectorySequenceBuilder(new Pose2d())
//                .lineToLinearHeading(new Pose2d(-25, 20, Math.toRadians(0)))
//                .build();
//31 5
        TrajectorySequence lasa_pixel_mov_center = drive.trajectorySequenceBuilder(new Pose2d())
                .lineToLinearHeading(new Pose2d(31,0 , Math.toRadians(0)))
                .build();
        TrajectorySequence spate = drive.trajectorySequenceBuilder(lasa_pixel_mov_center.end())
                .lineToLinearHeading(new Pose2d(24,0 , Math.toRadians(0)))
                        .build();
        TrajectorySequence intors =drive.trajectorySequenceBuilder(spate.end())
                .turn(85)
                .build();
        TrajectorySequence backdrop = drive.trajectorySequenceBuilder(intors.end())
                .lineToLinearHeading(new Pose2d(-15,0 , Math.toRadians(0)))
                .build();
        TrajectorySequence parcare_center = drive.trajectorySequenceBuilder(backdrop.end())
                .lineToLinearHeading(new Pose2d(-15,0 , Math.toRadians(0)))
                .build();
        //right
        TrajectorySequence lasa_pixel_mov_right = drive.trajectorySequenceBuilder(new Pose2d())
                .lineToLinearHeading(new Pose2d(31,3 , Math.toRadians(0)))
                .build();
        TrajectorySequence indreptare_right = drive.trajectorySequenceBuilder(lasa_pixel_mov_left.end())
                .lineToLinearHeading(new Pose2d(31, 0, Math.toRadians(0)))
                .build();
        TrajectorySequence spate_right = drive.trajectorySequenceBuilder(indreptare_right.end())
                .lineToLinearHeading(new Pose2d(24,0 , Math.toRadians(0)))
                .build();
        TrajectorySequence intors_right =drive.trajectorySequenceBuilder(spate_right.end())
                .turn(85)
                .build();
        TrajectorySequence backdrop_right = drive.trajectorySequenceBuilder(intors_right.end())
                .lineToLinearHeading(new Pose2d(-15,0 , Math.toRadians(0)))
                .build();
        TrajectorySequence parcare_right = drive.trajectorySequenceBuilder(backdrop_right.end())
                .lineToLinearHeading(new Pose2d(-15,0 , Math.toRadians(0)))
                .build();
        //handLeftServo.setPosition(0.65);
        handRightServo.setPosition(0.32);
        //sleep(100);
        //wristServo.setPosition(0.48);
        waitForStart();

        square = webcam.findSquareWithRedAverageFunc();
        telemetry.addData("Square", square);
        telemetry.update();

        while (opModeIsActive()) {

            idle();
            if (square == "left"){
                //drive.followTrajectorySequence(firstPixelTraj);

                //drive.followTrajectorySequence(lasa_pixel_mov_center);
                drive.followTrajectorySequence(lasa_pixel_mov_left);
                sleep(300);
                drive.followTrajectorySequence(indreptare_left);
                sleep(300);
                drive.followTrajectorySequence(spate_left);
                sleep(300);
                drive.followTrajectorySequence(intors_left);
//                sleep(300);
//                wristServo.setPosition(0.48);
//                drive.followTrajectorySequence(backdrop_left);
//                handRightServo.setPosition(0.48);
//

                break;
            }
            else if(square == "center")
            {
                //drive.followTrajectorySequence(firstPixelTraj);

                drive.followTrajectorySequence(lasa_pixel_mov_center);
                sleep(300);
                drive.followTrajectorySequence(spate);
                sleep(300);
                drive.followTrajectorySequence(intors);
//                sleep(300);
//                wristServo.setPosition(0.48);
//                drive.followTrajectorySequence(backdrop);
                break;
            }
            else if(square == "right")
            {
                //drive.followTrajectorySequence(firstPixelTraj);

                drive.followTrajectorySequence(lasa_pixel_mov_right);
                sleep(300);
                drive.followTrajectorySequence(indreptare_right);
                sleep(300);
                drive.followTrajectorySequence(spate_right);
                sleep(100);
                drive.followTrajectorySequence(intors_right);
//                sleep(300);
//                wristServo.setPosition(0.48);
//                drive.followTrajectorySequence(backdrop_right);
                break;
            }
            else if(square == "unknown")
            {
                //drive.followTrajectorySequence(firstPixelTraj);

                drive.followTrajectorySequence(lasa_pixel_mov_center);
                sleep(300);
                drive.followTrajectorySequence(spate);
                sleep(300);
                drive.followTrajectorySequence(intors);
//                drive.followTrajectorySequence(backdrop);
                break;
            }

        }

    }
}
