// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.frcteam3255.components.motors.SN_CANSparkMax;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.constChargerTreads;
import frc.robot.RobotMap.mapChargerTreads;

public class ChargerTreads extends SubsystemBase {

  SN_CANSparkMax leftMotor;
  SN_CANSparkMax rightMotor;

  public ChargerTreads() {
    leftMotor = new SN_CANSparkMax(mapChargerTreads.TREADS_LEFT_MOTOR_CAN);
    rightMotor = new SN_CANSparkMax(mapChargerTreads.TREADS_RIGHT_MOTOR_CAN);

    configure();
  }

  public void configure() {
    leftMotor.configFactoryDefault();
    rightMotor.configFactoryDefault();

    leftMotor.setInverted(constChargerTreads.LEFT_MOTOR_INVERTED);
    rightMotor.setInverted(constChargerTreads.RIGHT_MOTOR_INVERTED);
  }

  public void resetChargerTreadsEncodersCount() {
    leftMotor.setSelectedSensorPosition(constChargerTreads.RESET_ENCODERS);
    rightMotor.setSelectedSensorPosition(constChargerTreads.RESET_ENCODERS);
  }

  public double getLeftEncoderCount() {
    return leftMotor.getSelectedSensorPosition();
  }

  public double getRightEncoderCount() {
    return rightMotor.getSelectedSensorPosition();
  }

  public void setMotorSpeed(double speed) {
    leftMotor.set(ControlMode.PercentOutput, speed);
    rightMotor.set(ControlMode.PercentOutput, speed);
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Charger Treads Left Encoder", getLeftEncoderCount());
    SmartDashboard.putNumber("Charger Treads Right Encoder", getRightEncoderCount());

    SmartDashboard.putBoolean("is Left Charger Tread Inverted", constChargerTreads.LEFT_MOTOR_INVERTED);
    SmartDashboard.putBoolean("is Right Charger Tread Inverted", constChargerTreads.RIGHT_MOTOR_INVERTED);
  }
}
