package org.firstinspires.ftc.teamcode.util;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvPipeline;
import org.openftc.easyopencv.OpenCvWebcam;

@Disabled
@Deprecated
public class Web_red {
    OpenCvWebcam webcam;
    Pipeline pipeLine;

    static final Scalar RED = new Scalar(154, 121, 128);
    static final Scalar BLUE = new Scalar(60, 67, 88);//106,123,157);
    static final Scalar BLACK = new Scalar(0, 0, 0);

    HardwareMap hardwareMap;
    // public Web_red.Pipeline pipeLine;


    public void init(HardwareMap hm) {
        hardwareMap = hm;

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "WebCam1"), cameraMonitorViewId);

        pipeLine = new Pipeline(); // Uncomment if you want to use it

        webcam.setPipeline(pipeLine);

        webcam.setMillisecondsPermissionTimeout(2500);
        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened()
            {
                webcam.startStreaming(1920,1080, OpenCvCameraRotation.UPSIDE_DOWN);
            }

            // streamRotation == OpenCvCameraRotation.SIDEWAYS_RIGHT

            @Override
            public void onError(int errorCode)
            {
                /*
                 * This will be called if the camera could not be opened
                 */
            }
        });

    }

    public class Pipeline extends OpenCvPipeline {
        private final Scalar TARGET_COLOR = new Scalar(0, 0, 255);

        private static final int COLOR_THRESHOLD = 50;

        private static final int ROI_LEFT = 0;
        private static final int ROI_CENTER = 1;
        private static final int ROI_RIGHT = 2;

        private String maxRedROILabel = "Unknown";
        private int redPixelCount = 0;

        @Override
        public Mat processFrame(Mat input) {
            Mat mask = new Mat();
            Core.inRange(input, new Scalar(TARGET_COLOR.val[0] - COLOR_THRESHOLD, TARGET_COLOR.val[1] - COLOR_THRESHOLD, TARGET_COLOR.val[2] - COLOR_THRESHOLD),
                    new Scalar(TARGET_COLOR.val[0] + COLOR_THRESHOLD, TARGET_COLOR.val[1] + COLOR_THRESHOLD, TARGET_COLOR.val[2] + COLOR_THRESHOLD), mask);

            //  white pixels in ROI
            int[] redCount = new int[3];

            for (int i = 0; i < 3; i++) {
                Rect roi = getRegionOfInterest(i, input.cols(), input.rows());
                Mat roiMask = new Mat(mask, roi);
                redCount[i] = Core.countNonZero(roiMask);
            }


            // ROI with the max red
            int maxRedROI = getMaxRedROI(redCount);

            maxRedROILabel = getROILabel(maxRedROI);
            redPixelCount = redCount[maxRedROI];

            telemetry.addData("Max Red ROI", maxRedROILabel);
            telemetry.addData("Red Pixel Count", redPixelCount);

            return input; // return original frame
        }


        public String getMaxRedROILabel() {
            return maxRedROILabel;
        }

        public int getRedPixelCount() {
            return redPixelCount;
        }

        private Rect getRegionOfInterest(int roiType, int imageWidth, int imageHeight) {
            // regions of ROI left, center, right
            int roiWidth = imageWidth / 3;
            int roiHeight = imageHeight;

            switch (roiType) {
                case ROI_LEFT:
                    return new Rect(0, 0, roiWidth, roiHeight);
                case ROI_CENTER:
                    return new Rect(roiWidth, 0, roiWidth, roiHeight);
                case ROI_RIGHT:
                    return new Rect(2 * roiWidth, 0, roiWidth, roiHeight);
                default:
                    return new Rect(); // Default to the entire image
            }
        }

        private int getMaxRedROI(int[] redCount) {
            // max index redCount array
            int maxIndex = 0;
            for (int i = 1; i < redCount.length; i++) {
                if (redCount[i] > redCount[maxIndex]) {
                    maxIndex = i;
                }
            }
            return maxIndex;
        }

        private String getROILabel(int roiType) {
            switch (roiType) {
                case ROI_LEFT:
                    return "Left";
                case ROI_CENTER:
                    return "Center";
                case ROI_RIGHT:
                    return "Right";
                default:
                    return "Unknown";
            }
        }
//        public String getMaxRedROILabel() {
//            return maxRedROILabel;
//        }
//
//        public int getRedPixelCount() {
//            return redPixelCount;
//        }
    }

    public Pipeline getPipeline() {
        return pipeLine;
    }

}
