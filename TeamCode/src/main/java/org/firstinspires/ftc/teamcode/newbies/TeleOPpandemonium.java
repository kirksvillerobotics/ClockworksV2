package org.firstinspires.ftc.teamcode.newbies;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by yearbook on 5/2/18.
 */

@TeleOp (name = "TeleOp pandemonium")
public class TeleOPpandemonium extends OpMode {

    public DcMotor leftDrive;
    public DcMotor rightDrive;

    public DcMotor armMove;

    public final double THRESHOLD = 0.05;

    @Override
    public void init() {
        leftDrive = hardwareMap.dcMotor.get("leftDrive");
        rightDrive = hardwareMap.dcMotor.get("rightDrive");

        armMove = hardwareMap.dcMotor.get("armMove");

    }

    @Override
    public void loop() {
        if (gamepad1.left_stick_y > THRESHOLD) {  // 0.0 - 1.0
            leftDrive.setPower(1.0); // 0.0 - 1.0
            telemetry.addData("Left Motor", "On");
        } else {
            leftDrive.setPower(0.0);
            telemetry.addData("Right Motor", "Off");
        }

        if (gamepad1.right_stick_y > THRESHOLD) { // 0.0 - 1.0
            rightDrive.setPower(1.0);
            telemetry.addData("Right Motor", "On");
        } else {
            rightDrive.setPower(0.0);
            telemetry.addData("Right Motor", "Off");
        }
        armMove.setPower(gamepad2.right_trigger);
        armMove.setPower(-gamepad2.left_trigger);
    }
}
