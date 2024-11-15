package org.firstinspires.ftc.teamcode.autonomy;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.util.Web_red;

@Disabled
@Deprecated
@Autonomous(group = "webcam_red_auto")
public class webcam_red_auto extends LinearOpMode {
    Web_red web = new Web_red();

    @Override
    public void runOpMode() {

        web.init(hardwareMap);

        waitForStart();

        while (opModeIsActive()) {
            String maxBlueROILabel =web.getPipeline().getMaxRedROILabel();
            // int bluePixelCount = web.getPipeline().getBluePixelCount();

            switch (maxBlueROILabel) {
                case "Left":
                    //left ROI
                    telemetry.addData("Action", "Performing actions for the Left ROI");
                    break;
                case "Center":
                    //center ROI
                    telemetry.addData("Action", "Performing actions for the Center ROI");
                    break;
                case "Right":
                    //right ROI
                    telemetry.addData("Action", "Performing actions for the Right ROI");
                    break;
                default:
                    //unknown
                    telemetry.addData("Action", "No blue pixels detected or unknown region");
                    break;
            }
        }
    }
}