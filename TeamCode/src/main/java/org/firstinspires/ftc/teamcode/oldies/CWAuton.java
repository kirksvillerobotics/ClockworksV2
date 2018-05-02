package org.firstinspires.ftc.teamcode.oldies;

import android.app.Activity;
import android.view.View;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

@Autonomous(name = "Autonomous")
@Disabled
public abstract class CWAuton extends LinearOpMode {
    // THIS CLASS SHOULD NOT BE RUN
    // This is a class for the more specific auton programs to borrow methods from.

    private DcMotor leftDrive, rightDrive, glyphWinch;
    ColorSensor jewelCol;
    private static final double ROBOT_DIAM = 12.5;
    private static final double DEG_PER_REV = 1440; //technically quarter-degrees
    private static final double GEAR_RATIO = 1.0;
    private static final double WHEEL_DIAM = 4.0; //in inches
    private static final double DEG_PER_INCH = (DEG_PER_REV * GEAR_RATIO) / (WHEEL_DIAM * Math.PI);

    protected static final byte RED = 0;
    protected static final byte BLUE = 1;
    protected static final byte LEFT = 1;
    protected static final byte RIGHT = -1;

    public Servo jewelYaw;
    public Servo jewelPitch, glyphPusher, leftGlyphGrabber, rightGlyphGrabber;
    private VuforiaLocalizer vuforia;
    private VuforiaTrackable relicTemplate;

    public void runOpMode(){
        /* INITIALIZATION */
        //Grab the Vuforia parameters
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters vuParameters = new VuforiaLocalizer.Parameters (cameraMonitorViewId);
        //you will forever be accidentally horizontally scrolling because of this next line
        vuParameters.vuforiaLicenseKey = "AT0wrgH/////AAAAGZ82i6mOOEGnv6hV+FHOAgkR+sNJShoL2nSLxnxgM9dYPkKoFknXp26HuIP0k5wNOjgCsOD7lJwf5SrmxM7mQymx5uAsno1kj7mEwdGDsbwqzjrH6vH1ImHCva/1MS2uWs9H3ADGto3BpIrSr0iglmGQah+eBsPKsoK4qnH5vlNbsg+oU3JE6WehDaOqU7RLU54zT3kfwbRwSsfW1sLoTNIauQZU06V04ObJVjUrorhh2QVQ0blP69upGw0eYXp83P4Fi2IiXhSDlMNbHUTRmG1ZgXxQij/JfSl5tdZRujJcHHs2qnQJh/bZsz4rpmfheglMKPhzJC2/tV0KtO0tCV3Jm23PqG5dcQnWGMxKqWju";
        vuParameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;

        this.vuforia = ClassFactory.createVuforiaLocalizer(vuParameters);

        //Grab the Vuforia Relic Recovery trackables
        VuforiaTrackables relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate");

        //Grab motor, servo, and sensors names for the phone config
        leftDrive = hardwareMap.get(DcMotor.class, "leftDrive");
        rightDrive = hardwareMap.get(DcMotor.class, "rightDrive");
        jewelPitch = hardwareMap.get(Servo.class, "jewelPitch");
        jewelYaw = hardwareMap.get(Servo.class, "jewelYaw");
        glyphWinch = hardwareMap.get(DcMotor.class, "glyphLift");
        jewelCol = hardwareMap.get(ColorSensor.class, "jewelCol");

        //So directions will be the same for both motors
        rightDrive.setDirection(DcMotorSimple.Direction.REVERSE);

        // color sensor
        int relativeLayoutId = hardwareMap.appContext.getResources().getIdentifier("RelativeLayout", "id", hardwareMap.appContext.getPackageName());
        final View relativeLayout = ((Activity) hardwareMap.appContext).findViewById(relativeLayoutId);
        jewelCol = hardwareMap.get(ColorSensor.class, "jewelCol");
        jewelCol.enableLed(true);


        //Woot
        telemetry.addData("Initialized", "Yay");
        resetJewelStick();
    }

    // distances should be in inches
    // speed should usually be a constant
    public void encoderDrive(double leftDis, double rightDis, double speed, int duration){

        // the distance the encoders will run in 1/4 degrees
        int leftTar = leftDrive.getCurrentPosition() + (int)(leftDis * DEG_PER_INCH);
        int rightTar = rightDrive.getCurrentPosition() + (int)(rightDis * DEG_PER_INCH);

        // put targets into encoders
        leftDrive.setTargetPosition(leftTar);
        rightDrive.setTargetPosition(rightTar);

        // encoders will run till there target is met
        leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // go!
        leftDrive.setPower(speed);
        rightDrive.setPower(speed);

        long time = System.currentTimeMillis();

        // loop while robot is moving
        while( (leftDrive.isBusy() || rightDrive.isBusy()) && (System.currentTimeMillis() < time+duration)) {
            if(! opModeIsActive()) {
                leftDrive.setPower(0);
                rightDrive.setPower(0);
                break;
            }
        }

        // stop
        leftDrive.setPower(0.0);
        rightDrive.setPower(0.0);

        // switch to turn off RUN_TO_POSITION
        leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public static double turnDis(double degrees){
        return ROBOT_DIAM * Math.PI * 2 * (degrees/360.0);
    }
//
//    @Deprecated
//    public void jewelRoutine(int alliance) {
//        //Put the jewel stick in the up position
//        jewelYaw.setPosition(0.4);
//        jewelPitch.setPosition(0.09);
//
//        //Drive towards the jewels
//        encoderDrive(-2.5, -2.5, 0.50);
//
//        //Put the jewel stick in the down middle position
//        jewelYaw.setPosition(0.4);
//        jewelPitch.setPosition(0.5);
//
//        jewelCol.enableLed(true);
//
//        //Put the stick in the left position and measure the redness of the left jewel
//        jewelPitch.setPosition(0.4);
//        sleep(1000);
//        int leftRedness = jewelCol.red();
//        telemetry.addData("Left redness", leftRedness);
//
//        //Put the stick in the right position and measure the redness of the right jewel
//        jewelPitch.setPosition(0.6);
//        sleep(1000);
//        int rightRedness = jewelCol.red();
//        telemetry.addData("Right redness", rightRedness);
//
//        //Put the stick in the middle position again
//        jewelPitch.setPosition(0.5);
//        sleep(1000);
//
//        jewelCol.enableLed(false);
//
//        //Drive backwards
//        encoderDrive(2, 2, 0.4);
//        sleep(1000);
//
//        //Put the stick in the upside down position
//        jewelYaw.setPosition(0.0);
//        sleep(1000);
//
//        //Drive forward
//        encoderDrive(-2, -2, 1.0);
//
//        if(alliance == RED) {
//            //Knock over the correct jewel
//            if (leftRedness < rightRedness) jewelPitch.setPosition(0.8);
//            if (rightRedness < leftRedness) jewelPitch.setPosition(0.2);
//
//
//        } else if(alliance == BLUE) {
//            if (leftRedness > rightRedness) jewelPitch.setPosition(0.8);
//            if (rightRedness > leftRedness) jewelPitch.setPosition(0.2);
//        }
//
//        sleep(1000);
//        resetJewelStick();
//    }

    public void motionlessJewelRoutine(int alliance) {
        //jewelCol.enableLed(true);

        // down, measuring right jewel
        jewelPitch.setPosition(0.7);

        int rightRedness = 0;
        int rightBlueness = 0;

        sleep(1000);

        //while(rightBlueness == 0 && rightBlueness == 0 && jewelPitch.getPosition() > 0.55) {
            sleep(100);
            rightRedness = jewelCol.red();
            telemetry.addData("Red: ", rightRedness);
            rightBlueness = jewelCol.blue();
            telemetry.addData("Blue: ", rightBlueness);
            telemetry.update();

            //jewelPitch.setPosition(jewelPitch.getPosition() - 0.01);
        //}

        sleep(1000);

        if(alliance == RED) {
            //Knock over the correct jewel
            if (rightRedness > rightBlueness) {
                telemetry.addData("knock:", "RED (left)");
                telemetry.update();
                knockJewel(LEFT);
            } else if(rightBlueness > rightRedness) {
                telemetry.addData("knock:", "RED (right)");
                telemetry.update();
                knockJewel(RIGHT);
            } else {
                jewelPitch.setPosition(0.6);
                sleep(400);

            }
        } else if(alliance == BLUE) {
            if (rightRedness > rightBlueness) {
                telemetry.addData("knock:", "BLUE (right)");
                telemetry.update();
                knockJewel(RIGHT);
            } else if(rightBlueness > rightRedness){
                telemetry.addData("knock:", "BLUE (left)");
                telemetry.update();
                knockJewel(LEFT);
            } else {

            }
        }

        sleep(1000);

        encoderDrive(26, 26, 1.0, 3000);
        sleep(500);
        resetJewelStick();
    }

    public void knockJewel(int dir) {

        if(dir == RIGHT) {
            encoderDrive(4.0, -4.0, 0.75, 1000);
            sleep(500);
            resetJewelStick();
            sleep(500);
            encoderDrive(-4.0, 4.0, 0.75, 1000);
            sleep(500);
        }

        if(dir == LEFT) {
            encoderDrive(-4.0, 4.0, 0.75, 1000);
            sleep(500);
            resetJewelStick();
            sleep(500);
            encoderDrive(4.0, -4.0, 0.75, 1000);
            sleep(500);
        }
    }

    public void resetJewelStick() {
        jewelPitch.setPosition(0.08);
    }
}