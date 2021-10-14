#!/bin/bash
cp Talk-1.3.sh target/Talk-1.3.sh
cp Talk-1.3.bat target/Talk-1.3.bat
cd target/

tar -cvf Talk-1.3.tar Talk-1.3.jar Talk-1.3.sh lib/jssc-2.8.0.jar 
tar -cvf Talk-1.3.zip Talk-1.3.jar Talk-1.3.bat lib/jssc-2.8.0.jar 
