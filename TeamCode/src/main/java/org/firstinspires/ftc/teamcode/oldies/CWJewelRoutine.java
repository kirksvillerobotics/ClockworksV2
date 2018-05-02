package org.firstinspires.ftc.teamcode.oldies;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "TESTING ONLY Jewel (Blue)")
public class CWJewelRoutine extends CWAuton {

    @Override
    public void runOpMode(){
        // INITIALIZATION --------------------------------------------------------------------------
        super.runOpMode();

        waitForStart();

        // 30 SEC AUTONOMOUS PERIOD ----------------------------------------------------------------

        // 1 Do the jewel thing
        motionlessJewelRoutine(BLUE);
    }
}
