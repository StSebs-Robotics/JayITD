/* Copyright (c) 2023 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode.Auto;

// RR-specific imports
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;

// Non-RR imports
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Auto.HardwareClassesNActions.Servos;
import org.firstinspires.ftc.teamcode.Auto.HardwareClassesNActions.SlideMotors;
import org.firstinspires.ftc.teamcode.MecanumDrive;

@Config
@Autonomous(name = "1 clip", group = "Autonomous")
public class Clips extends LinearOpMode {

    private Servos servos;
    private SlideMotors slideMotor;

    @Override public void runOpMode(){
        //all of these are during init

        // instantiate your MecanumDrive at a particular pose.
        Pose2d initialPose = Positions.clipsInitialPos;
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);
        // make instance
        servos = new Servos(hardwareMap);
        slideMotor = new SlideMotors(hardwareMap);

        //test path
        TrajectoryActionBuilder initToCLips = drive.actionBuilder(initialPose)
                //Slides Up

                .lineToY(drive.pose.position.y-1.0)
                .splineToConstantHeading(Positions.toClips,Math.toRadians(270))
                .waitSeconds(5);

        TrajectoryActionBuilder backOff = drive.actionBuilder(initialPose)
                //slides down
                //Open claw
                .lineToY(drive.pose.position.y+3);

        TrajectoryActionBuilder cLipsToPushFirstBlock = drive.actionBuilder(initialPose)
                //slides all the way down
                .splineToConstantHeading(new Vector2d(-27,38),Math.toRadians(180))
                .splineToLinearHeading(Positions.readyToPushFirst,Math.toRadians(180))
                .lineToY(drive.pose.position.y+45);
//                        .back(45)
//                        .strafeLeft(10)
//                        .forward(45)
//                        .back(45)
//                        .strafeLeft(7)
//                        .forward(45)
//                        .back(5)
//                        .strafeRight(15);
        TrajectoryActionBuilder backOffNWait = drive.actionBuilder(initialPose)
                //slides up
                //open claw
            .lineToY(drive.pose.position.y-10);


        TrajectoryActionBuilder goInToGrab = drive.actionBuilder(initialPose)
                .waitSeconds(3)
                .lineToY(drive.pose.position.y+10);

        //close claw
        TrajectoryActionBuilder GrabToClip = drive.actionBuilder(initialPose)
                .waitSeconds(4)
                .setTangent(Math.toRadians(270))
                .splineToLinearHeading(new Pose2d(-7,32,Math.toRadians(270)),Math.toRadians(270));
        //slides down
        //Open claw

        TrajectoryActionBuilder wait1sec = drive.actionBuilder(initialPose).waitSeconds(1);

        TrajectoryActionBuilder waitHalfSec = drive.actionBuilder(initialPose).waitSeconds(0.5);
        // vision here that outputs position
        int visionOutputPosition = 1;

        // actions that need to happen on init; for instance, a claw tightening.
        //Actions.runBlocking(claw.closeClaw());
        while (!isStopRequested() && !opModeIsActive()) {
            int position = visionOutputPosition;
            telemetry.addData("Position during Init", position);
            telemetry.update();
        }
        int startPosition = visionOutputPosition;
        telemetry.addData("Starting Position", startPosition);
        telemetry.update();
        waitForStart();

        if (isStopRequested()) return;
        Actions.runBlocking(
                new SequentialAction(
                        servos.OuttakeClose(),
                        servos.outtakeFlat(),
                        slideMotor.liftUp(),
                        //initToCLips.build(),
                        slideMotor.liftPutClips(),
                        servos.outtakeOpen(),
                        //backOff.build(),
                        slideMotor.liftDown(),
                        //cLipsToPushFirstBlock.build(),
                        //backOffNWait.build(),
                        servos.outtakeOpen(),
                        servos.outtakeFlat(),
                        //goInToGrab.build(),
                        servos.OuttakeClose(),
                        servos.outtakeUp(),
                        slideMotor.liftUp(),
                        //GrabToClip.build(),
                        slideMotor.liftPutClips(),
                        wait1sec.build(),
                        wait1sec.build(),
                        wait1sec.build(),
                        servos.outtakeOpen()
                )
        );
    }
}