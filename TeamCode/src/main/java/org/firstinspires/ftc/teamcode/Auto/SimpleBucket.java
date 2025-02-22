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
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Auto.HardwareClassesNActions.Servos;
import org.firstinspires.ftc.teamcode.MecanumDrive;

@Config
@Autonomous(name = "SIMPLE BUCKET", group = "Autonomous")
public class SimpleBucket extends LinearOpMode {


    @Override public void runOpMode(){
        //all of these are during init

        // instantiate your MecanumDrive at a particular pose.
        Pose2d initialPose = Positions.bucketInitialPos;
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);
        Servos servos = new Servos(hardwareMap);

        //test path
        TrajectoryActionBuilder initToPark = drive.actionBuilder(initialPose)
//                .setTangent(Math.toRadians(270))
//                .strafeTo(new Vector2d(37,53))
//                .splineToConstantHeading(new Vector2d(47,40),Math.toRadians(280))
//                .waitSeconds(1)
//                //pickup
//                .setTangent(Math.toRadians(90))
//                .splineToLinearHeading(new Pose2d(53,58,Math.toRadians(45)),Math.toRadians(70))
//                .waitSeconds(1)
//                //Slides up
//                //open claw
//                .setTangent(Math.toRadians(225))
//                .splineToConstantHeading(new Vector2d(50,53.5),Math.toRadians(225))
//                //slides down
//                .splineToLinearHeading(new Pose2d(28,12,Math.toRadians(0)),Math.toRadians(180));
                .setTangent(Math.toRadians(270))
                .splineToLinearHeading(new Pose2d(32,8,Math.toRadians(0)),Math.toRadians(180));

        waitForStart();

        if (isStopRequested()) return;
        Actions.runBlocking(
                new SequentialAction(
                        initToPark.build(),
                        servos.outtakeFlat()
                )
        );
    }
}
