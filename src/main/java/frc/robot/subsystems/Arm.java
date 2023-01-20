// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;
import com.frcteam3255.components.motors.SN_CANSparkMax;
import com.frcteam3255.utils.SN_Math;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.constArm;
import frc.robot.RobotMap.mapArm;
import frc.robot.RobotPreferences.prefArm;

public class Arm extends SubsystemBase {

  SN_CANSparkMax shoulderJoint;
  SN_CANSparkMax elbowJoint;

  TalonFXConfiguration shoulderConfig;
  TalonFXConfiguration elbowConfig;

  DutyCycleEncoder shoulderEncoder;
  DutyCycleEncoder elbowEncoder;

  public Arm() {
    shoulderJoint = new SN_CANSparkMax(mapArm.SHOULDER_CAN);
    elbowJoint = new SN_CANSparkMax(mapArm.ELBOW_CAN);

    shoulderEncoder = new DutyCycleEncoder(mapArm.SHOULDER_ABSOLUTE_ENCODER_DIO);
    elbowEncoder = new DutyCycleEncoder(mapArm.ELBOW_ABSOLUTE_ENCODER_DIO);

    shoulderConfig = new TalonFXConfiguration();
    elbowConfig = new TalonFXConfiguration();

    configure();
  }

  public void configure() {

    // shoulder config
    shoulderJoint.configFactoryDefault();

    shoulderConfig.slot0.kD = prefArm.shoulderP.getValue();
    shoulderConfig.slot0.kI = prefArm.shoulderI.getValue();
    shoulderConfig.slot0.kD = prefArm.shoulderD.getValue();
    shoulderConfig.slot0.closedLoopPeakOutput = prefArm.shoulderMaxSpeed.getValue();

    shoulderJoint.configAllSettings(shoulderConfig);

    shoulderJoint.setInverted(constArm.SHOULDER_MOTOR_INVERT);

    // this will set the encoder counts per rotation to be the same as the falcon,
    // so we can use the falcon conversion methods in SN_Math
    shoulderJoint.encoder
        .setPositionConversionFactor(SN_Math.TALONFX_ENCODER_PULSES_PER_COUNT / Constants.NEO_ENCODER_CPR);

    // elbow config
    elbowJoint.configFactoryDefault();

    elbowConfig.slot0.kD = prefArm.elbowP.getValue();
    elbowConfig.slot0.kI = prefArm.elbowI.getValue();
    elbowConfig.slot0.kD = prefArm.elbowD.getValue();
    elbowConfig.slot0.closedLoopPeakOutput = prefArm.elbowMaxSpeed.getValue();

    elbowJoint.encoder
        .setPositionConversionFactor(SN_Math.TALONFX_ENCODER_PULSES_PER_COUNT / Constants.NEO_ENCODER_CPR);

    elbowJoint.configAllSettings(elbowConfig);

    elbowJoint.setInverted(constArm.ELBOW_MOTOR_INVERT);
  }

  public void setShoulderPosition(double degrees) {
    double encoderCounts = SN_Math.degreesToFalcon(
        degrees, constArm.SHOULDER_GEAR_RATIO);
    shoulderJoint.set(ControlMode.Position, encoderCounts);
  }

  public void setElbowPosition(double degrees) {
    double encoderCounts = SN_Math.degreesToFalcon(
        degrees, constArm.ELBOW_GEAR_RATIO);
    elbowJoint.set(ControlMode.Position, encoderCounts);
  }

  public Rotation2d getShoulderAbsoluteEncoder() {
    double degrees = shoulderEncoder.getAbsolutePosition();
    degrees -= constArm.SHOULDER_ABSOLUTE_ENCODER_OFFSET;
    return Rotation2d.fromDegrees(degrees);
  }

  public Rotation2d getElbowAbsoluteEncoder() {
    double degrees = elbowEncoder.getAbsolutePosition();
    degrees -= constArm.ELBOW_ABSOLUTE_ENCODER_OFFSET;
    return Rotation2d.fromDegrees(degrees);
  }

  public void resetJointsToAbsolute() {
    resetShoulderToAbsolute();
    resetElbowToAbsolute();
  }

  private void resetShoulderToAbsolute() {
    double absoluteEncoderCount = SN_Math.degreesToFalcon(
        getShoulderAbsoluteEncoder().getDegrees(),
        constArm.SHOULDER_GEAR_RATIO);
    shoulderJoint.setSelectedSensorPosition(absoluteEncoderCount);
  }

  private void resetElbowToAbsolute() {
    double absoluteEncoderCount = SN_Math.degreesToFalcon(
        getElbowAbsoluteEncoder().getDegrees(),
        constArm.ELBOW_GEAR_RATIO);
    shoulderJoint.setSelectedSensorPosition(absoluteEncoderCount);
  }

  @Override
  public void periodic() {

    if (Constants.OUTPUT_DEBUG_VALUES) {
      SmartDashboard.putNumber("Shoulder Absolute Encoder Raw", shoulderEncoder.getAbsolutePosition());
      SmartDashboard.putNumber("Shoulder Absolute Encoder", getShoulderAbsoluteEncoder().getDegrees());
      SmartDashboard.putNumber("Shoulder Motor Encoder", shoulderJoint.getSelectedSensorPosition());

      SmartDashboard.putNumber("Elbow Absolute Encoder Raw", elbowEncoder.getAbsolutePosition());
      SmartDashboard.putNumber("Elbow Absolute Encoder", getElbowAbsoluteEncoder().getDegrees());
      SmartDashboard.putNumber("Elbow Motor Encoder", elbowJoint.getSelectedSensorPosition());
    }
  }
}
