// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.frcteam3255.components.motors.SN_CANSparkMax;
import com.frcteam3255.preferences.SN_DoublePreference;
import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.constIntake;
import frc.robot.Constants.GamePiece;
import frc.robot.RobotMap.mapIntake;
import frc.robot.RobotPreferences.prefIntake;

public class Intake extends SubsystemBase {

  SN_CANSparkMax leftMotor;
  SN_CANSparkMax rightMotor;
  DigitalInput limitSwitch;
  ColorSensorV3 colorSensor;
  ColorMatch colorMatcher;
  Color coneColor;
  Color cubeColor;

  public Intake() {
    leftMotor = new SN_CANSparkMax(mapIntake.LEFT_MOTOR_CAN);
    rightMotor = new SN_CANSparkMax(mapIntake.RIGHT_MOTOR_CAN);

    colorSensor = new ColorSensorV3(mapIntake.COLOR_SENSOR_I2C);
    colorMatcher = new ColorMatch();

    limitSwitch = new DigitalInput(mapIntake.LIMIT_SWITCH_DIO);

    configure();
  }

  public void configure() {
    leftMotor.configFactoryDefault();
    rightMotor.configFactoryDefault();

    leftMotor.setInverted(constIntake.LEFT_MOTOR_INVERTED);
    rightMotor.setInverted(constIntake.RIGHT_MOTOR_INVERTED);

    coneColor = new Color(constIntake.coneColorR, constIntake.coneColorG, constIntake.coneColorB);
    cubeColor = new Color(constIntake.cubeColorR, constIntake.cubeColorG, constIntake.cubeColorB);

    colorMatcher.setConfidenceThreshold(prefIntake.colorMatcherConfidence.getValue());
    colorMatcher.addColorMatch(coneColor);
    colorMatcher.addColorMatch(cubeColor);
  }

  public GamePiece hasGamePiece() {
    ColorMatchResult currentColor = colorMatcher.matchColor(colorSensor.getColor());

    if (currentColor == null) {
      return GamePiece.NONE;
    } else if (currentColor.color == coneColor) {
      return GamePiece.CONE;
    } else if (currentColor.color == cubeColor) {
      return GamePiece.CUBE;
    }

    return GamePiece.NONE;
  }

  public boolean isGamePieceCollected() {
    return limitSwitch.get();
  }

  public double getPieceProximity() {
    return colorSensor.getProximity();
  }

  public void setMotorSpeed(double speed) {

    if (isGamePieceCollected() == true) {
      leftMotor.set(ControlMode.PercentOutput, 0);
      rightMotor.set(ControlMode.PercentOutput, 0);

    } else if (getPieceProximity() <= prefIntake.colorMatcherConfidence.getValue()) {
      leftMotor.set(ControlMode.PercentOutput, 0);
      rightMotor.set(ControlMode.PercentOutput, 0);

    } else {
      leftMotor.set(ControlMode.PercentOutput, speed);
      rightMotor.set(ControlMode.PercentOutput, speed);
    }
  }

  public void setMotorSpeed(SN_DoublePreference speed) {
    setMotorSpeed(speed.getValue());
  }

  @Override
  public void periodic() {

    SmartDashboard.putString("Current Game Piece", hasGamePiece().toString());

    if (Constants.OUTPUT_DEBUG_VALUES) {
      SmartDashboard.putString("Color Sensor Color", colorSensor.getColor().toHexString());
      SmartDashboard.putNumber("Color Sensor Red", colorSensor.getRed());
      SmartDashboard.putNumber("Color Sensor Green", colorSensor.getGreen());
      SmartDashboard.putNumber("Color Sensor Blue", colorSensor.getBlue());
      SmartDashboard.putNumber("Color Sensor Proximity", colorSensor.getProximity());
    }
  }
}
