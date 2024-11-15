package org.firstinspires.ftc.teamcode.autonomy;

import android.view.View;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.util.Detect;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;
@Disabled
@Deprecated
@Autonomous(name = "OBJECT", group = "auto")

public class obj extends LinearOpMode {
    OpenCvCamera camera;

    HardwareMap hardweare;

    @Override
    public void runOpMode() throws InterruptedException {
        int cameraMonitorViewId = hardwareMap.appContext
                .getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        camera = OpenCvCameraFactory.getInstance()
                .createInternalCamera(OpenCvInternalCamera.CameraDirection.BACK, cameraMonitorViewId);
        Detect detector = new Detect(telemetry);
        camera.setPipeline(detector);
        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
                                         @Override
                                         public void onOpened() {
                                             camera.startStreaming(1280, 720, OpenCvCameraRotation.SIDEWAYS_LEFT);

                                         }

                                         @Override
                                         public void onError(int errorCode) {

                                         }
                                     }
        );

        // camera.startStreaming(1920, 1080, OpenCvCameraRotation.UPSIDE_DOWN);

        waitForStart();

    }

}
