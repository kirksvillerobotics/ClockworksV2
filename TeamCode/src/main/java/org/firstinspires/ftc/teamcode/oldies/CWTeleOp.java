package org.firstinspires.ftc.teamcode.oldies;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;


@TeleOp(name = "Main")
public class CWTeleOp extends OpMode {

    private DcMotor leftDrive, rightDrive, glyphLift;
    private Servo jewelPitch, jewelYaw, leftGrabber, rightGrabber, upperGrabber;

    private ColorSensor jewelCol;
    private boolean togGrab;
    private boolean a2Available = false;

    private int loop = 0;

    private final double THRESHOLD = 0.05;

    private long pastTimeMillis = System.currentTimeMillis();

    private double leftSpeed = 0.0;
    private double rightSpeed = 0.0;
    private double liftSpeed = 0.0;

    @Override
    public void init() {
        telemetry.addData("Init", "started");

        //Grab motor, servo, and sensors names from the phone config
        leftDrive = hardwareMap.get(DcMotor.class, "leftDrive");
        rightDrive = hardwareMap.get(DcMotor.class, "rightDrive");
        jewelPitch = hardwareMap.get(Servo.class, "jewelPitch");
        jewelYaw = hardwareMap.get(Servo.class, "jewelYaw");
        glyphLift = hardwareMap.get(DcMotor.class, "glyphLift");
        leftGrabber = hardwareMap.get(Servo.class, "leftGrabber");
        rightGrabber = hardwareMap.get(Servo.class, "rightGrabber");
        upperGrabber = hardwareMap.get(Servo.class, "upperGrabber");
        jewelCol = hardwareMap.get(ColorSensor.class, "jewelCol");

        telemetry.addData("Init", "devices found");

        //So directions will be the same for both motors
        rightDrive.setDirection(DcMotorSimple.Direction.REVERSE);

        jewelCol.enableLed(false);

        telemetry.addData("Init", "main stuff completed");

        //jewelYaw.setPosition(0.4);
        jewelPitch.setPosition(0.09);

        rightGrabber.setPosition(0.85);
        leftGrabber.setPosition(0.12);
        upperGrabber.setPosition(0.41);
        togGrab = true;

        telemetry.addData("Init", "totally complete");
    }

    //test
    @Override
    public void loop() {
        telemetry.addData("Init", "looping");

        loop++; //Just to keep track

        /* GAMEPAD1 - DRIVING */

        //Controls for LEFT MOTOR (gamepad 1 left stick, A, and B)
        if (gamepad1.left_stick_y > THRESHOLD || gamepad1.left_stick_y < -THRESHOLD) {
            telemetry.addData("StickPos:", gamepad1.left_stick_y);
            leftSpeed = -gamepad1.left_stick_y;
        } else if (gamepad1.a) {
            leftSpeed = 0.75;
            telemetry.addData("Left", "positive");
        } else if (gamepad1.b) {
            leftSpeed = -0.75;
            telemetry.addData("Left", "negative");
        } else {
            leftSpeed = 0.0;
            telemetry.addData("Left", "zero");
        }

        //Controls for RIGHT MOTOR (gamepad 1 right stick, A, and B)
        if (gamepad1.right_stick_y > THRESHOLD || gamepad1.right_stick_y < -THRESHOLD) {
            telemetry.addData("StickPos:", gamepad1.right_stick_y);
            rightSpeed = -gamepad1.right_stick_y;
        } else if (gamepad1.a) {
            rightSpeed = 0.75;
        } else if (gamepad1.b) {
            rightSpeed = -0.75;
        } else {
            rightSpeed = 0.0;
        }

        /* GAMEPAD2 - ACCESSORIES */

        //For grabber servos, assuming the moving end is on the outside:
        //On left, 91deg is grabbing, 0deg is in.
        //On right, 91deg is grabbing, 180deg is in.
        //It turns out that it's nice to have a little extra in both positions, for various reasons.

        //Controls for GLYPH LIFT MOTOR (gamepad 2 X & Y)
        telemetry.addData("X", gamepad2.x);
        if (gamepad2.x) {
            liftSpeed = 1.0;
        } else if (gamepad2.y) {
            liftSpeed = -1.0;
        } else liftSpeed = 0.0;

        //Controls for GLYPH GRABBERS, LEFT AND RIGHT (gamepad 2 A & B)
        if(gamepad2.a && a2Available) {
            if (togGrab) { //Grab
                leftGrabber.setPosition(0.40);
                rightGrabber.setPosition(0.52);
                upperGrabber.setPosition(0.14);
                togGrab = false;
            } else { //Release
                leftGrabber.setPosition(0.53);
                rightGrabber.setPosition(0.38);
                upperGrabber.setPosition(0.01);
                togGrab = true;
            }
            a2Available = false;
        } else if(! gamepad2.a) a2Available = true;

        leftDrive.setPower(leftSpeed);
        rightDrive.setPower(rightSpeed);
        glyphLift.setPower(liftSpeed);

        telemetry.addData("Red", jewelCol.red());
        telemetry.addData("Green", jewelCol.green());
        telemetry.addData("Blue", jewelCol.blue());

        //TODO remove
        telemetry.addData("encoderLeft:", leftDrive.getCurrentPosition());
        telemetry.addData("encoderRight:", rightDrive.getCurrentPosition());
    }
}