// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import com.frcteam3255.components.SN_Blinkin;
import com.frcteam3255.joystick.SN_F310Gamepad;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.RobotPreferences.prefArm;
import frc.robot.RobotPreferences.prefCollector;
import frc.robot.RobotPreferences.prefIntake;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Collector;
import frc.robot.subsystems.Intake;

public class intakeCube extends SequentialCommandGroup {
  Collector subCollector;
  Intake subIntake;
  Arm subArm;
  SN_F310Gamepad conOperator;

  public intakeCube(Arm subArm, Collector subCollector, Intake subIntake, SN_F310Gamepad conOperator) {
    this.conOperator = conOperator;

    addCommands(
        // - Deploy collector
        new InstantCommand(() -> subCollector.setPivotMotorAngle(prefCollector.pivotAngleCubeCollecting.getValue())),

        // - Move arm to collector
        new InstantCommand(
            () -> subArm.setJointPositions(
                prefArm.armPresetCollectorElbowAngle,
                prefArm.armPresetCollectorShoulderAngle)),

        // - Spin intake & rollers and rumble the controller until a game piece is
        // collected
        Commands.parallel(
            new IntakeGamePiece(subIntake),
            new InstantCommand(() -> subCollector.setRollerMotorSpeed(prefCollector.rollerSpeed.getValue()))
                .until(subIntake::isGamePieceCollected),
            new InstantCommand(() -> conOperator.setRumble(RumbleType.kBothRumble, 0.5))
                .until(subIntake::isGamePieceCollected)),

        // - "Stop" all motors
        Commands.parallel(
            new InstantCommand(() -> subCollector.setRollerMotorSpeed(0)),
            new InstantCommand(() -> subIntake.setMotorSpeed(prefIntake.intakeHoldSpeed)),

            // - Raise arm to mid shelf position
            new InstantCommand(
                () -> subArm.setJointPositions(
                    prefArm.armPresetMidElbowAngle,
                    prefArm.armPresetMidShoulderAngle)),

            // - Retract collector
            new InstantCommand(
                () -> subCollector.setPivotMotorAngle(prefCollector.pivotAngleStartingConfig.getValue()))));
  }
}
