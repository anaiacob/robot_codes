package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.JavaUtil;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvPipeline;
import org.openftc.easyopencv.OpenCvWebcam;

public class WebCam {
    OpenCvWebcam webcam;
    static final Scalar RED = new Scalar(245, 121, 128);
    static final Scalar GREEN = new Scalar(160,160,200);  //76,147,121
    static final Scalar BLUE = new Scalar(60,67,88);//106,123,157);
    static final Scalar BLACK = new Scalar(0, 0,0);

    HardwareMap hardwareMap;
    Pipeline pipeLine;

    public void init(HardwareMap hm)
    {
        hardwareMap = hm;
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "WebCam1"), cameraMonitorViewId);

        pipeLine=new Pipeline(1f);
        // pipeLine.init();

        webcam.setPipeline(pipeLine);

        webcam.setMillisecondsPermissionTimeout(2500); // Timeout for obtaining permission is configurable. Set before opening.
        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
        {
            @Override
            public void onOpened()
            {
                webcam.startStreaming(1920,1080, OpenCvCameraRotation.UPRIGHT);
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

    public String findSquareWithRedAverageFunc() {
        int val = pipeLine.getSquareWithRedAverage();
        if (val == 1) {
            return "left";
        } else if (val == 2) {
            return "center";
        } else if (val == 3) {
            return "right";
        } else
            return "unknown";
    }

    public String findSquareWithBlueAverageFunc() {
        int val = pipeLine.getSquareWithBlueAverage();
        if (val == 1) {
            return "left";
        } else if (val == 3) {
            return "right";
        } else if (val == 2) {
            return "center";
        } else
            return "unknown";
    }


//    public String getColor() {
//        if ((new Scalar(255, 0, 0)).equals(pipeLine.color))
//            return "red";
//        else if ((new Scalar(0, 255, 0)).equals(pipeLine.color))
//            return "green";
//        else if ((new Scalar(0, 0, 255)).equals(pipeLine.color))
//            return "blue";
//        return "unknown";
//    }

//    public String getSquareWithRedAverage() {
//        Scalar colorSquareLeft = calculateAndDisplayAverageColor(mat1, LEFT_ROI, "LEFT");
//        Scalar colorSquareCenter = calculateAndDisplayAverageColor(mat1, CENTER_ROI, "CENTER");
//        Scalar colorSquareRight = calculateAndDisplayAverageColor(mat1, RIGHT_ROI, "RIGHT");
//
//        // Check if the average color of any square is red
//        if ((new Scalar(255, 0, 0)).equals(colorSquareLeft)) {
//            return "LEFT";
//        } else if ((new Scalar(255, 0, 0)).equals(colorSquareCenter)) {
//            return "CENTER";
//        } else if ((new Scalar(255, 0, 0)).equals(colorSquareRight)) {
//            return "RIGHT";
//        }
//
//        // If no square has the average of red, return "NONE"
//        return "NONE";
//    }


    class Pipeline extends OpenCvPipeline
    {

        int rStart=0,rEnd=1080,cStart=0,cEnd=1920;
        Rect LEFT_ROI = new Rect(new Point(400, 650), new Point(600, 750)); //150 670 300 750

        Rect CENTER_ROI = new Rect(new Point(900, 550), new Point(1200, 690)); //900 660 1150 720
        Rect RIGHT_ROI = new Rect(new Point(1500, 660), new Point(1750, 750)); //1650 600 1800 750

        Mat mat1;

        Mat YCrCb = new Mat();
        Mat Cb = new Mat();
        String colorName="null";

        public Scalar color;

        Pipeline(float z){zoom(z);};

        @Override
        public void init(Mat firstFrame)
        {
            mat1 = firstFrame.submat(rStart,rEnd,cStart,cEnd);

        }

        @Override
        public Mat processFrame(Mat input)
        {
            mat1=input.submat(rStart,rEnd,cStart,cEnd);

            drawSquare(mat1, LEFT_ROI, new Scalar(0, 255, 0));

            drawSquare(mat1, LEFT_ROI, new Scalar(0, 255, 0));

            drawSquare(mat1, RIGHT_ROI, new Scalar(0, 255, 0));

            int widthRect = (int)(mat1.width()/6f*1.5f*3f/4f*1.19*0.7);
            int heightRect = (int)((mat1.height()/3+50)*3/4.6 * 0.7);
            int x1 = (int)(mat1.width()*0.45),
                    y1=(int)(mat1.height()*0.5);
            int x2 = x1+widthRect,y2=y1+heightRect;//(mat1.width()/2+mat1.width()/6)+5,y2=mat1.height()-60;

            double avgRed= Core.mean(mat1.submat(y1,y2,x1,x2)).val[0],
                    avgGreen= Core.mean(mat1.submat(y1,y2,x1,x2)).val[1],
                    avgBlue= Core.mean(mat1.submat(y1,y2,x1,x2)).val[2];
            color=colorMethod2(avgRed, avgGreen, avgBlue);//colorMethod1(x1,y1,x2,y2);
            Imgproc.rectangle(
                    mat1, // Buffer to draw on
                    new Point(x1,y1), // First point which defines the rectangle
                    new Point(x2,y2), // Second point which defines the rectangle
                    color, // The color the rectangle is drawn in
                    2);

            Scalar colorSquareLeft = calculateAndDisplayAverageColor(mat1, LEFT_ROI, "LEFT");

            // Calculate and display average color in SQUARE2
            Scalar colorSquareRight = calculateAndDisplayAverageColor(mat1, RIGHT_ROI, "RIGHT");

            Scalar colorSquareCenter = calculateAndDisplayAverageColor(mat1, CENTER_ROI, "CENTER");


            return mat1;
        }

        public int getSquareWithRedAverage() {
            if(mat1==null)
                return 0;

            Scalar colorSquareLeft = calculateAndDisplayAverageColor(mat1, LEFT_ROI, "LEFT");
            Scalar colorSquareCenter = calculateAndDisplayAverageColor(mat1, CENTER_ROI, "CENTER");
            Scalar colorSquareRight = calculateAndDisplayAverageColor(mat1, RIGHT_ROI, "RIGHT");

            double maxx= JavaUtil.maxOfList(JavaUtil.createListWith(3,colorSquareLeft.val[0],
                    colorSquareCenter.val[0],
                    colorSquareRight.val[0]));


            // Check if the average color of any square is red
            if (maxx==colorSquareLeft.val[0]) {
                return 1;
            } else if (maxx==colorSquareCenter.val[0]) {
                return 2;
            } else if (maxx==colorSquareRight.val[0]) {
                return 3;
            }

            // If no square has the average of red, return "NONE"
            return 0;
        }

        public int getSquareWithBlueAverage() {


            if(mat1==null)
                return 0;

            Scalar colorSquareLeft = calculateAndDisplayAverageColor(mat1, LEFT_ROI, "LEFT");
            Scalar colorSquareCenter = calculateAndDisplayAverageColor(mat1, CENTER_ROI, "CENTER");
            Scalar colorSquareRight = calculateAndDisplayAverageColor(mat1, RIGHT_ROI, "RIGHT");

            double maxx= JavaUtil.maxOfList(JavaUtil.createListWith(3,colorSquareLeft.val[2],
                    colorSquareCenter.val[2],
                    colorSquareRight.val[2]));




            // Check if the average color of any square is blue
            if (maxx==colorSquareLeft.val[2]) {
                return 1;
            } else if (maxx==colorSquareRight.val[2]) {
                return 3;
            } else if (maxx==colorSquareCenter.val[2]) {
                return 2;
            }

            // If no square has the average of blue, return "NONE"
            return 0;
        }



        private Scalar calculateAndDisplayAverageColor(Mat input, Rect roi, String squareName) {
            double avgRed = Core.mean(input.submat(roi)).val[0];
            double avgGreen = Core.mean(input.submat(roi)).val[1];
            double avgBlue = Core.mean(input.submat(roi)).val[2];

            Scalar color = colorMethod2(avgRed, avgGreen, avgBlue);

            Imgproc.rectangle(
                    input,
                    new Point(roi.tl().x, roi.tl().y),
                    new Point(roi.br().x, roi.br().y),
                    color,
                    2);

            Imgproc.putText(
                    input,
                    squareName + ": " + (int) avgRed + "," + (int) avgGreen + "," + (int) avgBlue,
                    new Point(roi.tl().x, roi.tl().y - 10),
                    0, 0.5,
                    new Scalar(255, 255, 255),
                    2);

            return color;
        }


        private void drawSquare(Mat input, Rect roi, Scalar color) {
            Imgproc.rectangle(
                    input,
                    new Point(roi.tl().x, roi.tl().y),
                    new Point(roi.br().x, roi.br().y),
                    color,
                    2);
        }

        private void zoom(float z){
            float width=1920f/z,height=1080/z;
            rStart=Math.round(1080/2-height/2);
            rEnd=Math.round(rStart+height);
            cStart=Math.round(1920f/2-width/2);
            cEnd=Math.round(cStart+width);
        }

        private Scalar colorMethod1(int x1,int y1,int x2, int y2){

            int avgRed=(int)Core.mean(mat1.submat(y1,y2,x1,x2)).val[0],
                    avgGreen = (int)Core.mean(mat1.submat(y1,y2,x1,x2)).val[1],
                    avgBlue = (int)Core.mean(mat1.submat(y1,y2,x1,x2)).val[2];
            int avgMax=Math.max(avgRed,Math.max(avgGreen,avgBlue));
            /*double difRed=Math.abs(RED.val[0]-avgRed)+Math.abs(RED.val[1]-avgGreen)+Math.abs(RED.val[2]-avgBlue),
                   difGreen=Math.abs(GREEN.val[0]-avgRed)+Math.abs(GREEN.val[1]-avgGreen)+Math.abs(GREEN.val[2]-avgBlue),
                   difBlue=Math.abs(BLUE.val[0]-avgRed)+Math.abs(BLUE.val[1]-avgGreen)+Math.abs(BLUE.val[2]-avgBlue);
            double difMin=Math.min(difRed,Math.min(difGreen,difBlue));*/

            if(avgRed==avgMax){
                return RED.clone();
            }
            else if(avgGreen==avgMax){
                return GREEN.clone();
            }
            return BLUE.clone();
        }

        private Scalar colorMethod2(double avgRed,double avgGreen,double avgBlue) {
            double distRed = getDistance(avgRed, avgGreen, avgBlue, RED.val[0], RED.val[1], RED.val[2]);
            double distGreen = getDistance(avgRed, avgGreen, avgBlue, GREEN.val[0], GREEN.val[1], GREEN.val[2]);
            double distBlue = getDistance(avgRed, avgGreen, avgBlue, BLUE.val[0], BLUE.val[1], BLUE.val[2]);
            double distMin = Math.min(distRed, Math.min(distGreen, distBlue));

            Imgproc.putText(mat1, (int) avgRed + " , " + (int) avgGreen + " , " + (int) avgBlue, new Point(mat1.width() / 3, mat1.height() / 3), 0, 1, new Scalar(0, 0, 0), 2, 1, false);

            if(avgGreen < avgBlue && avgRed < avgBlue)
                return new Scalar(0, 0, 255);
            if(avgGreen < avgRed && avgBlue < avgRed)
                return new Scalar(255, 0, 0);
            return new Scalar(0, 255, 0);

//            if (distMin == distRed) {
//                return new Scalar(255, 0, 0);
//            } else if (distMin == distGreen) {
//                return new Scalar(0, 255, 0);
//            }
//            return new Scalar(0, 0, 255);
        }

        private double getDistance(double x1,double y1,double z1,double x2,double y2,double z2){
            double tangent=Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2));
            return Math.sqrt(tangent*tangent+(z1-z2)*(z1-z2));
        }


    }



    class SamplePipeline extends OpenCvPipeline
    {
        boolean viewportPaused;

        /*
         * NOTE: if you wish to use additional Mat objects in your processing pipeline, it is
         * highly recommended to declare them here as instance variables and re-use them for
         * each invocation of processFrame(), rather than declaring them as new local variables
         * each time through processFrame(). This removes the danger of causing a memory leak
         * by forgetting to call mat.release(), and it also reduces memory pressure by not
         * constantly allocating and freeing large chunks of memory.
         */

        @Override
        public Mat processFrame(Mat input)
        {
            /*
             * IMPORTANT NOTE: the input Mat that is passed in as a parameter to this method
             * will only dereference to the same image for the duration of this particular
             * invocation of this method. That is, if for some reason you'd like to save a copy
             * of this particular frame for later use, you will need to either clone it or copy
             * it to another Mat.
             */

            /*
             * Draw a simple box around the middle 1/2 of the entire frame
             */
            Imgproc.rectangle(
                    input,
                    new Point(
                            input.cols()/4,
                            input.rows()/4),
                    new Point(
                            input.cols()*(3f/4f),
                            input.rows()*(3f/4f)),
                    new Scalar(0, 255, 0), 4);

            /**
             * NOTE: to see how to get data from your pipeline to your OpMode as well as how
             * to change which stage of the pipeline is rendered to the viewport when it is
             * tapped, please see {@link PipelineStageSwitchingExample}
             */

            return input;
        }

        @Override
        public void onViewportTapped()
        {
            /*
             * The viewport (if one was specified in the constructor) can also be dynamically "paused"
             * and "resumed". The primary use case of this is to reduce CPU, memory, and power load
             * when you need your vision pipeline running, but do not require a live preview on the
             * robot controller screen. For instance, this could be useful if you wish to see the live
             * camera preview as you are initializing your robot, but you no longer require the live
             * preview after you have finished your initialization process; pausing the viewport does
             * not stop running your pipeline.
             *
             * Here we demonstrate dynamically pausing/resuming the viewport when the user taps it
             */

            viewportPaused = !viewportPaused;

            if(viewportPaused)
            {
                webcam.pauseViewport();
            }
            else
            {
                webcam.resumeViewport();
            }
        }
    }
}