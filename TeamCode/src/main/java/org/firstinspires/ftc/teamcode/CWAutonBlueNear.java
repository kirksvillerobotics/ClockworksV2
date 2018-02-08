package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="Blue Near")
public class CWAutonBlueNear extends CWAuton {

    @Override
    public void runOpMode(){
        /* INITIALIZATION */
        super.runOpMode();

        waitForStart();

        // 30 SEC AUTONOMOUS PERIOD ----------------------------------------------------------------

        // 1 Do all the crap with the jewel
        motionlessJewelRoutine(BLUE);

        //encoderDrive(24.5, 24.5, 0.75);

        // 2 Turn towards the parking space
        encoderDrive(turnDis(0.31), -turnDis(0.31), 0.75);

        // 3 Drive into the parking space
        encoderDrive(-40, -40, 1.0);
    }
}
