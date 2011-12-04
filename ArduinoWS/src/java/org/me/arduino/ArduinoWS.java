/*
 * Copyright (c) 2007, Sun Microsystems, Inc. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * * Redistributions of source code must retain the above copyright notice,
 *   this list of conditions and the following disclaimer.
 * 
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * * Neither the name of Sun Microsystems, Inc. nor the names of its contributors
 *   may be used to endorse or promote products derived from this software without
 *   specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.me.arduino;

import gnu.io.SerialPort;
import java.io.InputStream;
import java.io.OutputStream;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 *
 * @author p90puma
 */
@WebService()
public class ArduinoWS {//implements SerialPortEventListener {

    SerialPort serial_port;
    InputStream input;
    OutputStream output;
    static ArduinoSerialPortListener aspl;

    @WebMethod(operationName = "SendSerialInput")
    public String sendSerialInput(@WebParam(name = "input") String inputToArduino) {
        try {
            if (aspl == null) {
                aspl = new ArduinoSerialPortListener();
            }
            aspl.writeToArduino(inputToArduino);
            return "Sent [" + inputToArduino + "] via Serial!";
        } catch (Exception ex) {
            return ex.toString();
        }
    }

    @WebMethod(operationName = "SetupArduinoSerial")
    public String setupArduinoSerial(@WebParam(name = "serialPort") String serialPort) {
        try {

            //close the serial connection if it already exists, to prevent port conflicts.
            if(aspl != null){
                aspl.close();
            }
            aspl = new ArduinoSerialPortListener(serialPort);
            return "Setup the Arduino on port: [" + serialPort + "]";
        } catch (Exception ex) {
            return ex.toString();
        }
    }


/*
 * refactor to not take an input.
 */
    @WebMethod(operationName = "closeArduinoSerial")
    public String closeArduinoSerial(@WebParam(name = "input") String inputToArduino) {
        try {
            aspl.close();
            aspl = null;
            return "Closed the Arduino serial connection";
        } catch (Exception ex) {
            return ex.toString();
        }
    }
}


