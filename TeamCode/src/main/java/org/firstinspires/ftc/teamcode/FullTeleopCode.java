// java
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.mechanisms.ArcadeDrive;
import org.firstinspires.ftc.teamcode.mechanisms.IntakeClass;
import org.firstinspires.ftc.teamcode.mechanisms.LauncherClass;

@TeleOp (name = "FullTeleOpCode")
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
        intakeFunc.init(hardwareMap);
        drive.init(hardwareMap);
    }

    @Override
    public void loop() {
        // Toggle shooter on rising edge of A
        if (gamepad1.a && !prevA) {
            shooting = !shooting;
        }
        prevA = gamepad1.a;


        // Apply shooter power from toggle state
        double shooterPower = shooting ? 1.0 : 0.0;
        shootFunc.shootFunc(shooterPower);

        // Drive controls
        double throttle = -gamepad1.left_stick_y;
        double spin = gamepad1.left_stick_x;
        drive.drive(throttle, spin);


        // Toggle intake on rising edge of B
        if (gamepad1.b && !prevB) {
            intaking = !intaking;
        }
        prevB = gamepad1.b;


        //Apply intake power
        double intakingPower = intaking ? 1.0 : 0.0;
        intakeFunc.intakeFunc(intakingPower);

        //All needed telemetry
        telemetry.addData("Intaking?", intaking);
        telemetry.addData("Shooter", shooting ? "ON" : "OFF");
        telemetry.update();
    }

    @Override
    public void stop() {
        // Ensure all outputs are zeroed when OpMode stops
        shootFunc.shootFunc(0.0);
        intakeFunc.intakeFunc(0.0);
        drive.drive(0.0, 0.0);
    }
}
