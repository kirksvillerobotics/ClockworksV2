package org.firstinspires.ftc.teamcode.oldies;

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

        // 2 Turn towards the parking space
        encoderDrive(turnDis(45.0), -turnDis(45.0), 0.75, 2000);

        // 3 Drive into the parking space
        encoderDrive(-42, -42, 1.0, 4000);
    }
}
