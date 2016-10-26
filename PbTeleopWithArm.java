package org.firstinspires.ftc.robotcontroller.external.samples;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.util.RobotLog;

/**
 * This OpMode uses the common Pushbot hardware class to define the devices on the robot.
 * All device access is managed through the HardwarePushbot class.
 * The code is structured as a LinearOpMode
 *
 * This particular OpMode executes a POV Game style Teleop for a PushBot
 * In this mode the left stick moves the robot FWD and back, the Right stick turns left and right.
 * It raises and lowers the claw using the Gampad Y and A buttons respectively.
 * It also opens and closes the claws slowly using the left and right Bumper buttons.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@TeleOp(name="Pushbot: Teleop POV", group="Pushbot")
public class PbTeleopWithArm extends LinearOpMode {

    /* Declare OpMode members. */
    RealHardwarePushbot robot           = new RealHardwarePushbot();   // Use a Pushbot's hardware
    // could also use HardwarePushbotMatrix class.
    double          clawOffset      = 0;                       // Servo mid position
    final double    CLAW_SPEED      = 0.02 ;                   // sets rate to move servo

    @Override
    public void runOpMode() {
        double left;
        double right;
        double max;

        /* Initialize the hardware variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Say", "Hello Driver");    //
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            // Run wheels in POV mode (note: The joystick goes negative when pushed forwards, so negate it)
            // In this mode the Left stick moves the robot fwd and back, the Right stick turns left and right.
            left  = -gamepad1.left_stick_y + gamepad1.right_stick_x;
            right = -gamepad1.left_stick_y - gamepad1.right_stick_x;

            // Normalize the values so neither exceed +/- 1.0
            max = Math.max(Math.abs(left), Math.abs(right));
            if (max > 1.0)
            {
                left /= max;
                right /= max;
            }

            // Use gamepad left & right Bumpers to open and close the claw
            if (gamepad1.right_bumper)
                clawOffset += CLAW_SPEED;
            else if (gamepad1.left_bumper)
                clawOffset -= CLAW_SPEED;

            // Move both servos to new position.  Assume servos are mirror image of each other.
            clawOffset = Range.clip(clawOffset, -0.5, 0.5);
            robot.unlimitedServo.setPosition(robot.MID_SERVO + clawOffset);
            robot.limitedServo.setPosition(robot.MID_SERVO - clawOffset);


            //button y up, a down
           /*if (gamepad1.y)
                robot.usliderMotor.setPower(robot.ARM_UP_POWER);
            else if (gamepad1.a)
                robot.usliderMotor.setPower(robot.ARM_DOWN_POWER);
            else
                robot.usliderMotor.setPower(0.0);

            //button x up, b down
            if (gamepad1.x)
                robot.bsliderMotor.setPower(robot.ARM_UP_POWER);
            else if (gamepad1.b)
                robot.bsliderMotor.setPower(robot.ARM_DOWN_POWER);
            else
                robot.bsliderMotor.setPower(0.0);*/

            if (gamepad2.y)
                robot.usliderMotor.setPower(robot.ARM_UP_POWER);
            else if (gamepad2.a)
                robot.usliderMotor.setPower(robot.ARM_DOWN_POWER);
            else
                robot.usliderMotor.setPower(0.0);

            //button x up, b down
            if (gamepad2.x)
                robot.bsliderMotor.setPower(robot.ARM_UP_POWER);
            else if (gamepad2.b)
                robot.bsliderMotor.setPower(robot.ARM_DOWN_POWER);
            else
                robot.bsliderMotor.setPower(0.0);


            robot.leftMotor.setPower(left);
            robot.rightMotor.setPower(right);

            // Send telemetry message to signify robot running;
            telemetry.addData("left",  "%.2f", left);
            telemetry.addData("right", "%.2f", right);
            telemetry.update();

            // Pause for metronome tick.  40 mS each cycle = update 25 times a second.
            robot.waitForTick(40);
        }
    }
}
