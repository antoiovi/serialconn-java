#!/bin/bash
cp Talk.sh target/Talk.sh
cp Talk.bat target/Talk.bat
cd target/

tar -cvf Talk-1.2.tar Talk-1.2.jar Talk.sh lib/jssc-2.8.0.jar 
tar -cvf Talk-1.2.zip Talk-1.2.jar Talk.bat lib/jssc-2.8.0.jar 
