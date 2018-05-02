package org.firstinspires.ftc.teamcode.newbies;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by yearbook on 4/16/18.
 */
@Autonomous(name = "Autonomous Learning")
public class AutonLearning0 extends LinearOpMode {

    // use ALT+ENTER to import a class

    public DcMotor leftDrive;
    public DcMotor rightDrive;

    public Servo foobar;

    public void runOpMode(){

        // INIT - like init() in TeleOp
        // MOTORS
        leftDrive = hardwareMap.dcMotor.get("leftDrive");
        rightDrive = hardwareMap.dcMotor.get("rightDrive");

        // SERVOS
        foobar = hardwareMap.servo.get("foobar");

        telemetry.addData("Initialization", "Complete"); // prints message to phone

        waitForStart();

        rightDrive.setPower(1.0);
        leftDrive.setPower(1.0);

        sleep(5000); // 5sec or 5000milli

        rightDrive.setPower(0.0);
        leftDrive.setPower(0.0);
    }

}
