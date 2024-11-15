package org.firstinspires.ftc.teamcode.util;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

@Disabled
@Deprecated
public class Detect  extends OpenCvPipeline {
    Telemetry telemetry;

    Mat mat = new Mat();

    public enum Location {
        LEFT,
        CENTER,
        RIGHT
    }
    private Location location;

    static final Rect LEFT_ROI = new Rect(
            new Point(200, 450),
            new Point(400, 600)
    );
    static final Rect CENTER_ROI = new Rect(
            new Point(550, 350),
            new Point(800, 500)
    );
    static final Rect RIGHT_ROI = new Rect(
            new Point(900, 450),
            new Point(1100, 600)
    );

    static double PRECENT_COLOR_THRESHOLD = 0.4;

    public Detect(Telemetry t) {telemetry = t;}

    @Override
    public Mat processFrame(Mat input) {
        // Imgproc.cvtColor(input, mat, Imgproc.COLOR_RGB2HSV);
//        Scalar lowHSV = new Scalar(23, 50, 70);
//        Scalar highHSV = new Scalar(32, 255, 255);
//
//        Core.inRange(mat, lowHSV, highHSV, mat);

        Mat left = mat.submat(LEFT_ROI);
        Mat right = mat.submat(RIGHT_ROI);
        Mat center = mat.submat(CENTER_ROI);

        double leftValue = Core.sumElems(left).val[0] / LEFT_ROI.area() / 255;
        double rightValue = Core.sumElems(right).val[0] / RIGHT_ROI.area() / 255;
        double centerValue = Core.sumElems(center).val[0] / CENTER_ROI.area() / 255;

        left.release();
        right.release();
        center.release();

        boolean posLeft = leftValue > PRECENT_COLOR_THRESHOLD;
        boolean posRght = rightValue > PRECENT_COLOR_THRESHOLD;
        boolean posCenter = centerValue > PRECENT_COLOR_THRESHOLD;

        if(posLeft) {
            location = Location.LEFT;
            telemetry.addData("location:", "left");
        }
        if(posCenter) {
            location = Location.CENTER;
            telemetry.addData("location:", "center");
        }
        if(posRght) {
            location = Location.RIGHT;
            telemetry.addData("location:", "right");
        }
        else {
            telemetry.addData("location:", "not found, move robot");
        }
        telemetry.update();

        // Imgproc.cvtColor(mat, mat, Imgproc.COLOR_GRAY2BGR);

        Scalar color = new Scalar(255, 0, 0);
        Scalar colorObj = new Scalar(0, 255, 0);

        Imgproc.rectangle(mat, LEFT_ROI, location == Location.LEFT? colorObj:color);
        Imgproc.rectangle(mat, RIGHT_ROI, location == Location.RIGHT? colorObj:color);
        Imgproc.rectangle(mat, CENTER_ROI, location == Location.CENTER? colorObj:color);

        return input;
    }

    public Location getLocation() {
        return location;
    }
}