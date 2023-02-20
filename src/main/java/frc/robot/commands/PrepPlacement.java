// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import com.frcteam3255.joystick.SN_F310Gamepad;
import com.frcteam3255.joystick.SN_XboxController;
import com.frcteam3255.preferences.SN_DoublePreference;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.Constants.constControllers.ScoringLevel;
import frc.robot.Constants.constVision.GamePiece;
import frc.robot.RobotPreferences.prefArm;
import frc.robot.RobotPreferences.prefControllers;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;

public class PrepPlacement extends InstantCommand {
  Arm subArm;
  Drivetrain subDrivetrain;
  Intake subIntake;
  GamePiece currentGamePiece;
  ScoringLevel scoringLevel;
  SN_XboxController conOperator;

  SN_DoublePreference shoulderDegrees;
  SN_DoublePreference elbowDegrees;

  public PrepPlacement(Arm subArm, Drivetrain subDrivetrain, Intake subIntake, SN_XboxController conOperator) {
    this.subArm = subArm;
    this.subDrivetrain = subDrivetrain;
    this.subIntake = subIntake;
    this.conOperator = conOperator;
  }

  @Override
  public void initialize() {
    scoringLevel = subArm.scoringLevel;

    switch (scoringLevel) {
      case HYBRID:
        shoulderDegrees = prefArm.armPresetLowShoulderAngle;
        elbowDegrees = prefArm.armPresetLowElbowAngle;
        break;
      case MID:
        shoulderDegrees = prefArm.armPresetMidShoulderAngle;
        elbowDegrees = prefArm.armPresetMidElbowAngle;
        break;
      case HIGH:
        shoulderDegrees = prefArm.armPresetHighShoulderAngle;
        elbowDegrees = prefArm.armPresetHighElbowAngle;
        break;
      case NONE:
        return;
    }
    subArm.setGoalAngles(shoulderDegrees, elbowDegrees);

    // Rumble the controller
    conOperator.setRumble(RumbleType.kBothRumble, prefControllers.rumbleOutput.getValue());
    Timer.delay(prefControllers.rumbleDelay.getValue());
    conOperator.setRumble(RumbleType.kBothRumble, 0);
  }
}
