package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="Red Near")
public class CWAutonRedNear extends CWAuton {

    @Override
    public void runOpMode(){
        /* INITIALIZATION */
        super.runOpMode();

        waitForStart();

        // 30 SEC AUTONOMOUS PERIOD ----------------------------------------------------------------

        // 1 Do all the crap with the jewel
        motionlessJewelRoutine(RED);

        // 2 Turn towards the parking space
        encoderDrive(-turnDis(0.31), turnDis(0.31), 0.75);

        // 3 Drive into the parking space
        encoderDrive(-40, -40, 0.75);
    }

}
