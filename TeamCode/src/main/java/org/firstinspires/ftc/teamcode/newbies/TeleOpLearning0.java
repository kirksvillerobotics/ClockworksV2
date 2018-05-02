package org.firstinspires.ftc.teamcode.newbies;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

/**
 * Created by yearbook on 4/16/18.
 */

@TeleOp(name = "TeleOp Learning")
public class TeleOpLearning0 extends OpMode {

    // use ALT+ENTER to import a class

    public DcMotor leftDrive;
    public DcMotor rightDrive;

    public Servo foobar;

    public final double THRESHOLD = 0.05;

    @Override
    public void init() { // DECLARE MOTORS AND CONFIGURE
        // MOTORS
        leftDrive = hardwareMap.dcMotor.get("leftDrive");
        rightDrive = hardwareMap.dcMotor.get("rightDrive");

        // SERVOS
        foobar = hardwareMap.servo.get("foobar");

        telemetry.addData("Initialization", "Complete"); // prints message to phone
    }

    @Override
    public void loop(){ // CONTROLS AND SENSORS

        // MOTORS
        if(gamepad1.left_stick_y > THRESHOLD){  // 0.0 - 1.0
            leftDrive.setPower(1.0); // 0.0 - 1.0
            telemetry.addData("Left Motor", "On");
        }else{
            leftDrive.setPower(0.0);
            telemetry.addData("Right Motor", "Off");
        }

        if(gamepad1.right_stick_y > THRESHOLD){ // 0.0 - 1.0
            rightDrive.setPower(1.0);
            telemetry.addData("Right Motor", "On");
        }else{
            rightDrive.setPower(0.0);
            telemetry.addData("Right Motor", "Off");
        }

        // SERVOS
        if(gamepad1.a){
            foobar.setPosition(0.5); // 0.0 - 1.0
        }else{
            foobar.setPosition(0.0);
        }
    }
}
