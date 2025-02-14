
package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name="Motors, and servo slide", group="Linear OpMode")
@Disabled
public class SlideTestingStuff extends LinearOpMode {

    private DcMotor daMotor = null;
    private DcMotor slide1 = null;
    private DcMotor slide2 = null;
    public Servo servo1    = null;
    public Servo servo2   = null;
    private final int[] slidePosition = {0};
    private boolean slidesIsUp;


    @Override
    public void runOpMode() {

        daMotor = hardwareMap.get(DcMotorEx.class, "Motor7");
        slide1 = hardwareMap.get(DcMotor.class, "Motor5");
        slide2 = hardwareMap.get(DcMotor.class, "Motor6");
        servo2 = hardwareMap.get(Servo.class, "4");
        servo1 = hardwareMap.get(Servo.class, "5");

        slide1.setDirection(DcMotor.Direction.REVERSE);
        slide1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slide2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        telemetry.update();

        // Wait for the game to start (driver presses START)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
//
            daMotor.setPower(gamepad1.left_stick_x);
            if (gamepad1.left_trigger != 0) {

                if (gamepad1.right_trigger > 0) {
                    slidePosition[0] += (int) (20 * gamepad1.right_trigger);
                }
                // Move Slide Down
                if (gamepad1.left_trigger > 0) {
                    slidePosition[0] -= (int) (20 * gamepad1.left_trigger);
                }
                //straight up or down
                else if (gamepad1.right_bumper) {
                    slidePosition[0] = Values.slideMax;
                } else if (gamepad1.left_bumper) {
                    slidePosition[0] = 0;
                }

                // Ensure slides stay within bounds
                if (slidePosition[0] < 0) {
                    slidePosition[0] = 0;
                }

                if (slidePosition[0] > Values.slideMax) {
                    slidePosition[0] = Values.slideMax;
                }
                moveSlides(slidePosition[0], Values.velocity);
            }
            if (gamepad1.circle) {
                if (!slidesIsUp) {
                    slidePosition[0] = Values.slideMax;
                    slidesIsUp = true;
                } else if (slidesIsUp) {
                    slidePosition[0] = 0;
                    slidesIsUp = false;
                }
            }

            {
                if (gamepad1.dpad_up) {
                    slidesOut();
                } else if (gamepad1.dpad_down && gamepad1.dpad_down) {
                    slidesIn();
                }
                if (gamepad1.dpad_left && !gamepad1.dpad_left) {
                    slideServo(true);
                } else if (gamepad1.dpad_right && !gamepad1.dpad_right) {
                    slideServo(false);
                }
            }

            telemetry.update();
        }
    }
    private void slidesOut () {
        servo1.setPosition(Values.slide1out);
        servo2.setPosition(Values.slide2out);
    }
    private void slidesIn () {
        servo1.setPosition(Values.slide1in);
        servo2.setPosition(Values.slide2in);
    }
    private void slideServo (boolean goingOut) {
        if (goingOut) {
            servo2.setPosition(servo2.getPosition() + 0.05);
            servo1.setPosition(servo1.getPosition() - 0.05);
        } else {
            servo2.setPosition(servo2.getPosition() - 0.05);
            servo1.setPosition(servo1.getPosition() + 0.05);
        }
    }
    private void moveSlides(int distance, double velocity){
        slide1.setTargetPosition(distance);
        slide2.setTargetPosition(distance);

        slide1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        slide2.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        slide1.setPower(velocity);
        slide2.setPower(velocity);
    }
}

