// java
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.mechanisms.ArcadeDrive;
import org.firstinspires.ftc.teamcode.mechanisms.IntakeClass;
import org.firstinspires.ftc.teamcode.mechanisms.LauncherClass;

@TeleOp (name = "FullTeleOPTest")
public class FullTeleopCode extends OpMode {
    private final LauncherClass shootFunc = new LauncherClass();
    private final ArcadeDrive drive = new ArcadeDrive();
    private final  IntakeClass intakeFunc = new IntakeClass(); 

    private boolean shooting = false;
    private boolean prevA = false;
    private boolean intaking = false;
    private boolean prevB = false;
            
    @Override
    public void init() {
        shootFunc.init(hardwareMap);
        drive.init(hardwareMap);
    }

    @Override
    public void loop() {
        // Toggle shooter on rising edge of A
        if (gamepad1.a && !prevA) {
            shooting = !shooting;
        }
        prevA = gamepad1.a;

        //Telemetry for monitoring and debugging shooting
        telemetry.addData("Shooting?", shooting);
        telemetry.update();

        // Apply shooter power from toggle state
        double shooterPower = shooting ? 1.0 : 0.0;
        shootFunc.shootFunc(shooterPower);

        // Drive controls
        double throttle = -gamepad1.left_stick_y;
        double spin = gamepad1.left_stick_x;
        drive.drive(throttle, spin);

        // Telemetry for debugging
        telemetry.addData("Shooter", shooting ? "ON" : "OFF");
        telemetry.update();

        // Toggle intake on rising edge of B
        if (gamepad1.b && !prevB) {
            intaking = !intaking;
        }
        prevB = gamepad1.b;

        //Telemetry for monitoring and debugging intake
        telemetry.addData("Intaking?", intaking);
        telemetry.update();

        //Apply intake power
        double intakingPower = intaking ? 1.0 : 0.0;
        intakeFunc.intakeFunc(intakingPower);
    }

    @Override
    public void stop() {
        // Ensure all outputs are zeroed when OpMode stops
        shootFunc.shootFunc(0.0);
        intakeFunc.intakeFunc(0.0);
        drive.drive(0.0, 0.0);
    }
}
