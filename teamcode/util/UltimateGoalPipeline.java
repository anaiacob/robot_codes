package org.firstinspires.ftc.teamcode.util;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

@Disabled
@Deprecated
public class UltimateGoalPipeline extends OpenCvPipeline {

        private static final double CENTER_THRESHOLD = 0.1;

    private static final double LEFT_THRESHOLD = 0.5;

    private static final Rect CENTER_ROI = new Rect(200, 200, 200, 200); // Example ROI for the center
    private static final Rect LEFT_ROI = new Rect(50, 200, 150, 200);   // Example ROI for the left
    private static final Rect RIGHT_ROI = new Rect(400, 200, 150, 200);  // Example ROI for the right

    private volatile String objectPosition = "Unknown"; // Default position


    @Override
    public Mat processFrame(Mat input) {
        double objectCenterX = detectObject(input);

        // Determine the position of the object in the field of view
        updateObjectPosition(objectCenterX, input);

        // Check if the object is within a certain ROI
        checkROIs(objectCenterX, input);

        // Display the position information on telemetry
        telemetry.addData("Object Position", objectPosition);
        telemetry.update();

        // Draw rectangles and overlays as needed
        // ...

        return input;
    }

    private void checkROIs(double objectCenterX, Mat input) {
        // Check if the object is within the predefined ROIs and perform actions accordingly
        if (isInROI(objectCenterX, CENTER_ROI)) {
            // Object is in the center ROI, perform actions as needed
            telemetry.addData("Object Status", "In Center ROI");
            // Add actions for the center ROI
        } else if (isInROI(objectCenterX, LEFT_ROI)) {
            // Object is in the left ROI, perform actions as needed
            telemetry.addData("Object Status", "In Left ROI");
            // Add actions for the left ROI
        } else if (isInROI(objectCenterX, RIGHT_ROI)) {
            // Object is in the right ROI, perform actions as needed
            telemetry.addData("Object Status", "In Right ROI");
            // Add actions for the right ROI
        } else {
            telemetry.addData("Object Status", "Outside ROIs");
            // Add actions for when the object is outside all ROIs
        }
    }

    private boolean isInROI(double objectCenterX, Rect roi) {
        // Check if the object's X-coordinate is within the specified ROI
        return objectCenterX >= roi.x && objectCenterX <= roi.x + roi.width;
    }


        private double detectObject(Mat input) {
            // Implement your object detection logic here
            // Return the X-coordinate of the object's center
            // For demonstration purposes, let's assume you have a method called findObjectCenterX
            return findObjectCenterX(input);
        }

        private double findObjectCenterX(Mat input) {
            // Implement your logic to find the X-coordinate of the object's center
            // This could involve image processing techniques like contour analysis, template matching, etc.
            // For demonstration purposes, let's assume a simple approach:
            Rect boundingBox = new Rect(100, 100, 50, 50); // Example bounding box
            Point center = new Point(boundingBox.x + boundingBox.width / 2.0, boundingBox.y + boundingBox.height / 2.0);
            return center.x;
        }

    private void updateObjectPosition(double objectCenterX, Mat input) {
        if (input != null && input.cols() > 0) {
            double normalizedPosition = objectCenterX / input.cols();

            if (Math.abs(normalizedPosition) < CENTER_THRESHOLD) {
                objectPosition = "Center";
            } else if (normalizedPosition < 0) {
                objectPosition = "Left";
            } else {
                objectPosition = "Right";
            }
        } else {
            telemetry.addData("Error", "Input Mat is null or empty");
        }
    }

        public String getObjectPosition() {
            return objectPosition;
        }
    }

