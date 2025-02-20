
package com.example.meepmeeptesting.JoycePaths;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.example.meepmeeptesting.ActualBotBuilder;

import org.rowlandhall.meepmeep.MeepMeep;
import org.rowlandhall.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class Clips {
    public static void main(String[] args) {
        System.setProperty("sun.java2d.opengl", "true");
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new ActualBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .followTrajectorySequence(drive -> drive.trajectorySequenceBuilder(new Pose2d(-15.5, 60, Math.toRadians(270)))
                        .splineToConstantHeading(new Vector2d(-2,35),Math.toRadians(270))
                        .setTangent(90)
                        //Slides down open claw

                        .splineToConstantHeading(new Vector2d(-35,38),Math.toRadians(270))
                        .waitSeconds(1)
                        .strafeTo(new Vector2d(-35,13))

                        // 1st pixel
                        .strafeTo(new Vector2d(-46,13))
                        .strafeTo(new Vector2d(-46,59))

                        //2nd Pixel
                        .strafeTo(new Vector2d(-46,13))
                        .strafeTo(new Vector2d(-56,13))
                        .setTangent(Math.toRadians(270))
                        .strafeTo(new Vector2d(-56,59))


                        //3rd Pixel
                        .strafeTo(new Vector2d(-56,13))
                        .strafeTo(new Vector2d(-63,13))
                        .setTangent(Math.toRadians(270))
                        .strafeTo(new Vector2d(-63,59))


                        .build());


        meepMeep.setBackground(MeepMeep.Background.FIELD_INTOTHEDEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}