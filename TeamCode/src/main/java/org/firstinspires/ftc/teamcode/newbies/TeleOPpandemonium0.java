package org.firstinspires.ftc.teamcode.newbies;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by yearbook on 5/2/18.
 **/

@TeleOp (name = "TeleOp Pandemonium")
public class TeleOPpandemonium0 extends OpMode {

    private DcMotor leftDrive;
    private DcMotor rightDrive;

    private DcMotor armMove;


    private Servo rightMove;
    private Servo leftMove;

    @Override
    public void init() {
        leftDrive = hardwareMap.dcMotor.get("leftDrive");
        rightDrive = hardwareMap.dcMotor.get("rightDrive");

        armMove = hardwareMap.dcMotor.get("armMove");

        rightMove = hardwareMap.servo.get("rightMove");
        leftMove = hardwareMap.servo.get("leftMove");
    }

    @Override
    public void loop() {
        final double THRESHOLD = 0.05;

        if (gamepad1.left_stick_y > THRESHOLD) {  // 0.0 - 1.0
            leftDrive.setPower(1.0); // 0.0 - 1.0
            telemetry.addData("Left Motor", "On");
        } else {
            leftDrive.setPower(0.0);
            telemetry.addData("Left Motor", "Off");
        }

        if (gamepad1.right_stick_y > THRESHOLD) { // 0.0 - 1.0
            rightDrive.setPower(1.0);
            telemetry.addData("Right Motor", "On");
        } else {
            rightDrive.setPower(0.0);
            telemetry.addData("Right Motor", "Off");
        }
        armMove.setPower(gamepad2.right_trigger); //up?
        armMove.setPower(-gamepad2.left_trigger); //down?

        if (gamepad2.right_stick_x > THRESHOLD) {
            rightMove.setPosition(1.00);
        } else {
            if (gamepad2.right_stick_x < THRESHOLD) {
                rightMove.setPosition(0.00);
            } else {
                rightMove.setPosition(0.50);
            }
        }
        if (gamepad2.left_stick_x > THRESHOLD) {
            leftMove.setPosition(1.00);
        } else {
            if (gamepad2.left_stick_x < THRESHOLD) {
                leftMove.setPosition(0.00);
            } else {
                leftMove.setPosition(0.50);
            }
        }
    }
}
