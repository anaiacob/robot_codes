package org.firstinspires.ftc.teamcode.util;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.HardwareMap;

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

@Disabled
@Deprecated
public class webCode {
    OpenCvWebcam webcam;
    static final Scalar RED = new Scalar(154, 121, 128);
    static final Scalar BLUE = new Scalar(60,67,88);//106,123,157);
    static final Scalar BLACK = new Scalar(0, 0,0);

    HardwareMap hardwareMap;
    Pipeline pipeLine;

    public void init(HardwareMap hm)
    {
        hardwareMap = hm;

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "WebCam1"), cameraMonitorViewId);

        pipeLine=new Pipeline(2f);

        webcam.setPipeline(pipeLine);

        webcam.setMillisecondsPermissionTimeout(2500); // Timeout for obtaining permission is configurable. Set before opening.
        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
        {
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

//    public String getColor() {
//        if ((new Scalar(255, 0, 0)).equals(pipeLine.color))
//            return "red";
//        else if ((new Scalar(0, 255, 0)).equals(pipeLine.color))
//            return "green";
//        else if ((new Scalar(0, 0, 255)).equals(pipeLine.color))
//            return "blue";
//        return "unknown";
//    }

    public void updateDetectedColor() {
        String color = getColor();
        telemetry.addData("Detected Color", color);
        telemetry.update();
    }

    public String getColor() {
        return pipeLine.getSquare(); // Assuming getSquare() returns the name of the most dominant square
    }



    static class Pipeline extends OpenCvPipeline
    {

        int rStart=0,rEnd=1080,cStart=0,cEnd=1920;
        Mat mat1;
//        Mat YCrCb = new Mat();
//        Mat Cb = new Mat();
        String dominantSquareName="null";

//        public Scalar color;

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

            Rect LEFT_ROI = new Rect(new Point(100, 100), new Point(200, 200));
            Rect CENTER_ROI = new Rect(new Point(300, 100), new Point(400, 200));
            Rect RIGHT_ROI = new Rect(new Point(500, 100), new Point(600, 200));

            // Extract color information from each square
            Scalar colorSquareLEFT = getColorInformation(mat1, LEFT_ROI);
            Scalar colorSquareCENTER = getColorInformation(mat1, CENTER_ROI);
            Scalar colorSquareRIGHT = getColorInformation(mat1, RIGHT_ROI);

            // Draw rectangles around the additional squares
            Imgproc.rectangle(mat1, LEFT_ROI, colorSquareLEFT, 2);
            Imgproc.rectangle(mat1, CENTER_ROI, colorSquareCENTER, 2);
            Imgproc.rectangle(mat1, RIGHT_ROI, colorSquareRIGHT, 2);

            // Compare the squares and determine which one has the most blue or red
            determineMostColor(LEFT_ROI, colorSquareLEFT, CENTER_ROI, colorSquareCENTER, RIGHT_ROI, colorSquareRIGHT);

            // Determine the dominant color and find in which squares the dominant color is found the most
            determineDominantColor(LEFT_ROI, colorSquareLEFT, CENTER_ROI, colorSquareCENTER, RIGHT_ROI, colorSquareRIGHT);

            return mat1;
        }

        private Scalar getColorInformation(Mat input, Rect square) {
            int x1 = (int)square.tl().x;
            int y1 = (int)square.tl().y;
            int x2 = (int)square.br().x;
            int y2 = (int)square.br().y;

            double avgRed = Core.mean(input.submat(y1, y2, x1, x2)).val[0];
            double avgGreen = Core.mean(input.submat(y1, y2, x1, x2)).val[1];
            double avgBlue = Core.mean(input.submat(y1, y2, x1, x2)).val[2];

            return colorMethod2(avgRed, avgGreen, avgBlue);
        }

        private void determineMostColor(Rect LEFT_ROI, Scalar colorSquareLEFT, Rect CENTER_ROI, Scalar colorSquareCENTER, Rect RIGHT_ROI, Scalar colorSquareRIGHT) {
            double blueLEFT = colorSquareLEFT.val[2];
            double blueCENTER = colorSquareCENTER.val[2];
            double blueRIGHT = colorSquareRIGHT.val[2];

            double redLEFT = colorSquareLEFT.val[0];
            double redCENTER = colorSquareCENTER.val[0];
            double redRIGHT = colorSquareRIGHT.val[0];

            // Compare blue values
            if (blueLEFT > blueCENTER && blueLEFT > blueRIGHT) {
                // has mmost blue
                dominantSquareName = "Square 1";
            } else if (blueCENTER > blueLEFT && blueCENTER > blueRIGHT) {
                // has mmost blue
                dominantSquareName = "Square 2";
            } else {
                // has mmost blue
                dominantSquareName = "Square 3";
            }

            // Compare red values
            if (redLEFT > redCENTER && redLEFT > redRIGHT) {
                // has mmost red
                dominantSquareName = "Square 1";
            } else if (redCENTER > redLEFT && redCENTER > redRIGHT) {
                // has mmost red
                dominantSquareName = "Square 2";
            } else {
                // has mmost red
                dominantSquareName = "Square 3";
            }
        }

        private void determineDominantColor(Rect squareLEFT, Scalar colorSquareLEFT, Rect squareCENTER, Scalar colorSquareCENTER, Rect squareRIGHT, Scalar colorSquareRIGHT) {
            double blueLEFT = colorSquareLEFT.val[2];
            double blueCENTER = colorSquareCENTER.val[2];
            double blueRIGHT = colorSquareRIGHT.val[2];

            double redLEFT = colorSquareLEFT.val[0];
            double redCENTER = colorSquareCENTER.val[0];
            double redRIGHT = colorSquareRIGHT.val[0];

            // Compare blue values to determine the dominant color
            if (blueLEFT > blueCENTER && blueLEFT > blueRIGHT) {
                // Dominant color is blue
                findSquaresWithDominantColor(squareLEFT, squareCENTER, squareRIGHT, colorSquareLEFT, colorSquareCENTER, colorSquareRIGHT, "blue");
            } else if (blueCENTER > blueLEFT && blueCENTER > blueRIGHT) {
                // Dominant color is blue
                findSquaresWithDominantColor(squareLEFT, squareCENTER, squareRIGHT, colorSquareLEFT, colorSquareCENTER, colorSquareRIGHT, "blue");
            } else {
                // Dominant color is blue
                findSquaresWithDominantColor(squareLEFT, squareCENTER, squareRIGHT, colorSquareLEFT, colorSquareCENTER, colorSquareRIGHT, "blue");
            }

            // Compare red values to determine the dominant color
            if (redLEFT > redCENTER && redLEFT > redRIGHT) {
                // Dominant color is red
                findSquaresWithDominantColor(squareLEFT, squareCENTER, squareRIGHT, colorSquareLEFT, colorSquareCENTER, colorSquareRIGHT, "red");
            } else if (redCENTER > redLEFT && redCENTER > redRIGHT) {
                // Dominant color is red
                findSquaresWithDominantColor(squareLEFT, squareCENTER, squareRIGHT, colorSquareLEFT, colorSquareCENTER, colorSquareRIGHT, "red");
            } else {
                // Dominant color is red
                findSquaresWithDominantColor(squareLEFT, squareCENTER, squareRIGHT, colorSquareLEFT, colorSquareCENTER, colorSquareRIGHT, "red");
            }
        }


//        private void findSquaresWithDominantColor(Rect square1, Rect square2, Rect square3, Scalar colorSquare1, Scalar colorSquare2, Scalar colorSquare3, String dominantColor) {
//            int countDominantColor = 0;
//
//            // Check each square for the dominant color
//            if (getDominantColor(colorSquare1) == dominantColor) {
//                countDominantColor++;
//            }
//            if (getDominantColor(colorSquare2) == dominantColor) {
//                countDominantColor++;
//            }
//            if (getDominantColor(colorSquare3) == dominantColor) {
//                countDominantColor++;
//            }
//
//            // Print or use the count of squares with the dominant color
//            System.out.println("Number of squares with dominant color " + dominantColor + ": " + countDominantColor);
//        }

        private void findSquaresWithDominantColor(Rect squareLEFT, Rect squareCENTER, Rect squareRIGHT, Scalar colorSquareLEFT, Scalar colorSquareCENTER, Scalar colorSquareRIGHT, String dominantColor) {
            int countDominantColorLEFT = getDominantColorCount(colorSquareLEFT, dominantColor);
            int countDominantColorCENTER = getDominantColorCount(colorSquareCENTER, dominantColor);
            int countDominantColorRIGHT = getDominantColorCount(colorSquareRIGHT, dominantColor);

            // Find the square with the most of the dominant color
            String dominantSquare = findMostDominantSquare(countDominantColorLEFT, countDominantColorCENTER, countDominantColorRIGHT);

            // Print or use the count of squares with the dominant color and the name of the square with the most of the dominant color
            System.out.println("Number of squares with dominant color " + dominantColor + ": " + countDominantColorLEFT + ", " + countDominantColorCENTER + ", " + countDominantColorRIGHT);
            System.out.println("Most dominant square for " + dominantColor + ": " + dominantSquare);
            telemetry.addData("Dominant Color", dominantColor);
            telemetry.addData("Most Dominant Square", dominantSquare);
            telemetry.update();

            dominantSquareName = dominantSquare;
        }

        private int getDominantColorCount(Scalar color, String dominantColor) {
            if (getDominantColor(color).equals(dominantColor)) {
                return 1;
            } else {
                return 0;
            }
        }

        private String findMostDominantSquare(int countLEFT, int countCENTER, int countRIGHT) {
            if (countLEFT > countCENTER && countLEFT > countRIGHT) {
                return "Square LEFT";
            } else if (countCENTER > countLEFT && countCENTER > countRIGHT) {
                return "Square CENTER";
            } else {
                return "Square RIGHT";
            }
        }

        private String getDominantColor(Scalar color) {
            double red = color.val[0];
            double green = color.val[1];
            double blue = color.val[2];

            if (blue > red && blue > green) {
                return "blue";
            } else if (red > blue && red > green) {
                return "red";
            } else {
                return "null"; // or any other handling for the case where no single color is dominant
            }
        }

        private void zoom(float z){
            float width=1920f/z,height=1080/z;
            rStart=Math.round(1080/2-height/2);
            rEnd=Math.round(rStart+height);
            cStart=Math.round(1920f/2-width/2);
            cEnd=Math.round(cStart+width);
        }

//        private Scalar colorMethod1(int x1,int y1,int x2, int y2) {
//
//            int avgRed = (int) Core.mean(mat1.submat(y1, y2, x1, x2)).val[0],
//                    avgGreen = (int) Core.mean(mat1.submat(y1, y2, x1, x2)).val[1],
//                    avgBlue = (int) Core.mean(mat1.submat(y1, y2, x1, x2)).val[2];
//            int avgMax = Math.max(avgRed, Math.max(avgGreen, avgBlue));
//            /*double difRed=Math.abs(RED.val[0]-avgRed)+Math.abs(RED.val[1]-avgGreen)+Math.abs(RED.val[2]-avgBlue),
//                   difGreen=Math.abs(GREEN.val[0]-avgRed)+Math.abs(GREEN.val[1]-avgGreen)+Math.abs(GREEN.val[2]-avgBlue),
//                   difBlue=Math.abs(BLUE.val[0]-avgRed)+Math.abs(BLUE.val[1]-avgGreen)+Math.abs(BLUE.val[2]-avgBlue);
//            double difMin=Math.min(difRed,Math.min(difGreen,difBlue));*/
//
//            if (avgRed == avgMax) {
//                return RED.clone();
//            } else if (avgBlue == avgMax) {
//                return BLUE.clone();
//            }
//        }



        private Scalar colorMethod2(double avgRed,double avgGreen,double avgBlue) {
            double distRed = getDistance(avgRed, avgGreen, avgBlue, RED.val[0], RED.val[1], RED.val[2]);
            double distBlue = getDistance(avgRed, avgGreen, avgBlue, BLUE.val[0], BLUE.val[1], BLUE.val[2]);
            double distMin = Math.min(distRed, distBlue);

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



        private String getSquare(){



            return dominantSquareName;
        }


        /*private int getClosestColor(double[] p){
            double avg=(p[0]+p[1]+p[2])/3;
            Scalar color=new Scalar(0,0,0);

            if(avg<p[0]){
                return 0;
            }
            else if(avg<p[1]){
                return 1;
            }
            return 2;
            Mat canvas=mat1.submat(y1,y2,x1,x2);

            int kR=0,kB=0,kG=0;

            for(int i=0;i<canvas.height();i++){
                for(int j=0;j<canvas.width();j++){
                    int c=getClosestColor(Core.mean(canvas.submat(i,i,j,j)).val);
                    if(c==0)
                        kR++;
                    else if(c==1)
                        kG++;
                    else
                        kB++;

                }
            }

            int kMax=Math.max(kR,Math.max(kG,kB));

            if(kR==kMax){
                color=RED.clone();
            }
            else if(kG==kMax){
                color=GREEN.clone();
            }
            else{
                color=BLUE
            }
        }*/

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
//        public String getSquare() {
//            // Your existing logic to determine and return the most dominant square
//            return dominantSquareName;
//        }

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