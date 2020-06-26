# Remote operated vehicle (hardware + software) project. Using Python and C (MATLAB for testing communication)

The overall project was to drive a remotely operated vehicle using a gamepad.
The project consisted of a gamepad, we chose the d-pad mode, which sent commands
to the Raspberry Pi 3. The commands were sent in two strings, one per axis,
followed by 8-bit or 10-bit direction value. These were mapped to "F", "C", "B",
indicating forward, center, back, starting with Y for the Y-axis, and
"L", "C", "R", indicating left, center, right starting with X for the X-axis.
The remapped commands were then sent to the Explorer 16 board over UART
communication, using our python program.
These commands were then translated into speed and direction for our two motors.
The motors will have opposite GPIO polarities in order to move forward and backwards,
that way they are moving in the same direction.
If the motor was turning left, the left motor GPIO will reverse polarity
in order to turn respective to the center of the vehicle, while mantaining
full speed on the right motor. Vice versa for a right turn.
To turn diagonally left, the left motor slows down to half it's PWM, while
maintaining full speed on the right motor, and vice versa for diagonal right.
The program constantly monitors and updates the state of the change notification
pins, which are triggered when the motors rotate and counters are incremented or
decremented according to the direction of rotation.
At the end, using the d-pad from the gamepad our vehicle Model-E can move
forward, left, right, back and diagonal.
Our Model-E also includes a sharp distance sensor, the sensor is implemented by
using a timer interrupt. Using an ADC the sensor constantly calculates how far
it is from objects, so when the vehicle is within 15cm of a object it will
take over the control, overriding any user input and shortly backing up. After
which it will return control to the user. The sensor also controls the LEDs
so it will proportionally light LEDs based on distance.



