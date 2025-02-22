package org.firstinspires.ftc.teamcode.Auto.HardwareClassesNActions;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Light;

import org.firstinspires.ftc.teamcode.teleop.Values;

public class SlideMotors {

    private DcMotor outtakeSlide1;
    private DcMotor outtakeSlide2;

    public SlideMotors(HardwareMap hardwareMap) {
        outtakeSlide1 = hardwareMap.get(DcMotor.class, "Motor5");
        outtakeSlide1.setDirection(DcMotorSimple.Direction.REVERSE);
        outtakeSlide2 = hardwareMap.get(DcMotor.class, "Motor6");
        outtakeSlide2.setDirection(DcMotorSimple.Direction.FORWARD);

        outtakeSlide1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        outtakeSlide2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public class LiftDown implements Action {
        private boolean initialized = false;

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            if (!initialized) {
                outtakeSlide1.setTargetPosition(0);
                outtakeSlide2.setTargetPosition(0);
                outtakeSlide1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                outtakeSlide2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                outtakeSlide1.setPower(1);
                outtakeSlide2.setPower(1);

                initialized = true;
            }

            double pos = outtakeSlide1.getCurrentPosition();
            packet.put("liftPos", pos);
            if (pos > 100.0) {
                return true;
            } else {
                outtakeSlide1.setPower(0);
                outtakeSlide2.setPower(0);
                return false;
            }
        }
    }
    public Action liftDown() {
        return new LiftDown();
    }

    public class LiftPutClips implements Action {
        private boolean initialized = false;

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            if (!initialized) {
                outtakeSlide1.setTargetPosition(1600);
                outtakeSlide2.setTargetPosition(1600);
                outtakeSlide1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                outtakeSlide2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                outtakeSlide1.setPower(1);
                outtakeSlide2.setPower(1);

                initialized = true;
            }

            double pos = outtakeSlide1.getCurrentPosition();
            packet.put("liftPos", pos);
            if (pos < 1600.0) {
                return true;
            } else {
                //todo: the slide go down after the action
                outtakeSlide1.setPower(0);
                outtakeSlide2.setPower(0);
                return false;
            }
        }
    }
    public Action liftPutClips() {
        return new LiftPutClips();
    }

    public class LiftPutClipsDown implements Action {
        private boolean initialized = false;

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            if (!initialized) {
                outtakeSlide1.setTargetPosition(700);
                outtakeSlide2.setTargetPosition(700);
                outtakeSlide1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                outtakeSlide2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                outtakeSlide1.setPower(1);
                outtakeSlide2.setPower(1);

                initialized = true;
            }

            double pos = outtakeSlide1.getCurrentPosition();
            packet.put("liftPos", pos);
            if (pos > 750.0 || pos < 600) {
                return true;
            } else {
                //todo: the slide go down after the action, fix this
                outtakeSlide1.setPower(0);
                outtakeSlide2.setPower(0);
                return false;
            }
        }
    }
    public Action liftPutClipsDown() {
        return new LiftPutClips();
    }

    public class LiftUp implements Action {
        private boolean initialized = false;

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            if (!initialized) {
                outtakeSlide1.setTargetPosition(Values.slideMax);
                outtakeSlide2.setTargetPosition(Values.slideMax);
                outtakeSlide1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                outtakeSlide2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                outtakeSlide1.setPower(1);
                outtakeSlide2.setPower(1);

                initialized = true;
            }

            double pos = outtakeSlide1.getCurrentPosition();
            packet.put("liftPos", pos);
            if (pos > 2800) {
                return true;
            } else {
                outtakeSlide1.setPower(0);
                outtakeSlide2.setPower(0);
                return false;
            }
        }
    }
    public Action liftUp() {
        return new LiftUp();
    }

    public class LiftMax implements Action {
        private boolean initialized = false;

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            if (!initialized) {
                outtakeSlide1.setTargetPosition(Values.slideMax);
                outtakeSlide2.setTargetPosition(Values.slideMax);
                outtakeSlide1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                outtakeSlide2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                outtakeSlide1.setPower(1);
                outtakeSlide2.setPower(1);

                initialized = true;
            }

            double pos = outtakeSlide1.getCurrentPosition();
            packet.put("liftPos", pos);
            if (pos < 2600.0) {
                return true;
            } else {
                outtakeSlide1.setPower(0);
                outtakeSlide2.setPower(0);
                return false;
            }
        }
    }
    public Action lifeMax() {
        return new LiftMax();
    }

    public int GetPos (){
            int pos = outtakeSlide1.getCurrentPosition();
            return pos;

    }

}
