package org.firstinspires.ftc.teamcode.oldies;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="Red Far")
public class CWAutonRedFar extends CWAuton {

    @Override
    public void runOpMode(){
        /* INITIALIZATION */
        super.runOpMode();

        waitForStart();

        // 30 SEC AUTONOMOUS PERIOD ----------------------------------------------------------------

        //1 Do all the crap with the jewel
        motionlessJewelRoutine(RED);

        // 3 Rotate towards glyph boxes
        encoderDrive(-turnDis(55.0), turnDis(55.0), 0.75, 2000);

        // 4 Drive into the glyph zone
        encoderDrive(-39, -39, 0.75, 4000);
    }
}