
package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="test servos of teleop", group="Linear OpMode")
//@Disabled
public class TeleopServoTesting extends LinearOpMode {

    /* Declare OpMode members. */
    public Servo intakeClaw = null;
    public Servo clawPivot = null;
    public Servo wrist = null;
    public Servo intakeElbow = null;

    public Servo outtakeClaw = null;
    public Servo outtakeElbow = null;

    public Servo intakeSlide1 = null;
    public Servo intakeSlide2 = null;
    private boolean elbowIsDown = false;
    private boolean outtakeIsDown = false;
    private boolean outtakeIsFlat = false;




    @Override
    public void runOpMode() {

        // Define and initialize ALL installed servos.
        intakeClaw = hardwareMap.get(Servo.class, "0");
        clawPivot = hardwareMap.get(Servo.class, "1");
        wrist = hardwareMap.get(Servo.class, "2");
        intakeElbow = hardwareMap.get(Servo.class, "3");
        intakeSlide2 = hardwareMap.get(Servo.class, "4");
        intakeSlide1 = hardwareMap.get(Servo.class, "5");

        outtakeElbow = hardwareMap.get(Servo.class, "7");
        outtakeClaw = hardwareMap.get(Servo.class, "6");

        outtakeClaw.setPosition(Values.outakeclawOpen);
        outtakeElbow.setPosition(Values.outtakeElbowDown);
        intakeClaw.setPosition(Values.intakeclawClose);
        intakeElbow.setPosition(Values.intakeElbowUp);
        telemetry.update();

        // Wait for the game to start (driver presses START)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            //horizontal slides in N out
            {
                if (gamepad1.dpad_up ) {
                    slidesOut();
                } else if (gamepad1.dpad_down) {
                    slidesIn();
                }
            }
            //intake slide out & in
            if (gamepad1.dpad_left ) {
                slideServo(true);
            } else if (gamepad1.dpad_right) {
                slideServo(false);
            }

            //intake open N close
            //don know y its kinda jank
            if (gamepad1.circle) {
                if (!elbowIsDown) {
                    intakeElbow.setPosition(Values.intakeElbowWait);
                    intakeClaw.setPosition(Values.intakeClawOpen);
                    wrist.setPosition(Values.wristDown);
                    outtakeClaw.setPosition(Values.outakeclawOpen);
                    outtakeElbow.setPosition(Values.outtakeElbowDown);
                    //where the slide stuff goes
                    elbowIsDown = true;
                    slidesIn();
                } else if (elbowIsDown == true) {
                    intakeElbow.setPosition(Values.intakeElbowDown);
                    sleep(500);
                    intakeClaw.setPosition(Values.intakeclawClose);
                    sleep(400);
                    wrist.setPosition(Values.wristUp);
                    clawPivot.setPosition(Values.MID_SERVO);
                    intakeElbow.setPosition(Values.intakeElbowUp);
                    slidesIn();
                    //slidesIn();
                    sleep(1200);
                    outtakeClaw.setPosition(Values.outtakeClawClose);
                    sleep(1000);
                    intakeClaw.setPosition(Values.intakeClawOpen);
                    sleep(200);
                    intakeElbow.setPosition(Values.intakeElbowWait);
                    outtakeElbow.setPosition(Values.outtakeElbowUp);
                    elbowIsDown = false;
                }
            }
            if (gamepad1.cross) {
                if (!elbowIsDown) {
                    intakeElbow.setPosition(Values.intakeElbowWait);
                    wrist.setPosition(Values.wristDown);
                    intakeClaw.setPosition(Values.intakeClawOpen);
                    elbowIsDown= true;
                } else if (elbowIsDown) {
                    //intakeClaw.setPosition(Values.intakeClawOpen);
                    intakeElbow.setPosition(Values.intakeElbowDown);
                    sleep(1000);
                    intakeClaw.setPosition(Values.intakeclawClose);
                    sleep(400);
                    wrist.setPosition(Values.wristUp);
                    clawPivot.setPosition(Values.MID_SERVO);
                    intakeElbow.setPosition(Values.intakeElbowUp);elbowIsDown = false;
                }

                sleep(250);
                intakeClaw.setPosition(Values.intakeclawClose);
                sleep(200);
                wrist.setPosition(Values.wristUp);
                intakeElbow.setPosition(Values.intakeElbowUp);
                slidesIn();
            }

            //outtake
            if (gamepad1.triangle) {
                if (!outtakeIsDown) {
                    outtakeClaw.setPosition(Values.outakeclawOpen);
                    sleep(200);
                    outtakeElbow.setPosition(Values.outtakeElbowDown);
                    outtakeIsDown = true;
                } else if (outtakeIsDown) {
                    outtakeClaw.setPosition(Values.outtakeClawClose);
                    sleep(200);
                    outtakeElbow.setPosition(Values.outtakeElbowUp);
                    outtakeIsDown = false;
                }
            }
            if (gamepad1.square) {
                if (!outtakeIsFlat) {
                    outtakeClaw.setPosition(Values.outakeclawOpen+0.03);
                    sleep(200);
                    outtakeElbow.setPosition(Values.outtakeElbowFlat);
                    outtakeIsFlat = true;
                } else if (outtakeIsFlat) {
                    outtakeClaw.setPosition(Values.outtakeClawClose);
                    sleep(200);
                    outtakeElbow.setPosition(Values.outtakeElbowUp);
                    outtakeIsFlat = false;
                }
            }


            //Send telemetry message to signify robot running;
            telemetry.addData("Intake Claw (circle)", "%.02f", intakeClaw.getPosition());
            telemetry.addData("Intake pitch angle (square)", "%.02f", wrist.getPosition());
            telemetry.addData("Intake big rotate (cross)", "%.02f", intakeElbow.getPosition());
            telemetry.addData("outake, triangle, elbow claw", "%.02f, %.02f", outtakeElbow.getPosition(), outtakeClaw.getPosition());
            telemetry.addData("slides servos (dpad up, or leftNright bumpy)", "%.02f, %.02f", intakeSlide1.getPosition(), intakeSlide2.getPosition());
            telemetry.update();
        }

    }

    private void slidesOut () {
        intakeSlide1.setPosition(Values.slide1out);
        intakeSlide2.setPosition(Values.slide2out);
    }
    private void slidesIn () {
        intakeSlide1.setPosition(Values.slide1in);
        intakeSlide2.setPosition(Values.slide2in);
    }
    private void slidesTransfer () {
        intakeSlide1.setPosition(Values.slide1wait);
        intakeSlide2.setPosition(Values.slide2wait);
    }
    private void slideServo (boolean goingOut){
        if (goingOut) {
            intakeSlide2.setPosition(intakeSlide2.getPosition() + 0.05);
            intakeSlide1.setPosition(intakeSlide1.getPosition() - 0.05);
        } else {
            intakeSlide2.setPosition(intakeSlide2.getPosition() - 0.05);
            intakeSlide1.setPosition(intakeSlide1.getPosition() + 0.05);
        }
    }
}

