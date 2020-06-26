# Remote operated vehicle (hardware + software) project. Using Python and C (MATLAB for testing communication)
This project is a remote-operated vehicle that is operated via a gamepad. In addition to the software component, it should be noted that I personally designed and constructed the hardware of the vehicle, which utilizes a polycarbonate structure. 

The gamepad sends commands to the central hub, which is a Raspberry Pi 3. The commands are sent in two strings, with one per axis. This is followed by an 8-bit or 10-bit direction value. These values were mapped to "F", "C", and/or "B", which represent forward, center, and back on the Y-axis, and
"L", "C", and/or "R", for left, center, and right on the X-axis.

After the string conversion, the commands are sent to the Explorer 16 board over UART communication, using an original Python program.
These commands are then translated into speed and direction for our two motors.

The motors have opposite GPIO polarities in order to move forward and backwards, which ensures they move in the same direction. For example, if the motor turns left, the left motor GPIO will reverse polarity in order to turn with respect to the center of the vehicle while mantaining full speed on the right motor (and vice versa for a right turn.) To turn diagonally left, the left motor slows down to half it's PWM, while maintaining full speed on the right motor, and vice versa for diagonal right. The program constantly monitors and updates the state of the change notification pins, which are triggered when the motors rotate or when the magnetic counters are incremented or decremented according to the direction of rotation.

Our vehicle (named "Model-E") also includes a sharp distance sensor, which is implemented by using a timer interrupt. Using an ADC, the sensor constantly calculates its distance from any given object. When the vehicle is within 15cm of a object, it takes over the control, overriding any user input and immediately backing up to prevent collision. It then returns control to the user. The vehicle also includes LED lights that are controled by the sensor and are proportionately lit based on distance from obstacles. 

Lastly, we incorporated a 360 degree camera which can be monitored through a Raspberry Pi Monitor to further optimize its navigation abilities.

