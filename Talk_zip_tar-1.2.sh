#!/bin/bash
cp Talk-1.2.sh target/Talk-1.2.sh
cp Talk-1.2.bat target/Talk-1.2.bat
cd target/

tar -cvf Talk-1.2.tar Talk-1.2.jar Talk-1.2.sh lib/jssc-2.8.0.jar 
tar -cvf Talk-1.2.zip Talk-1.2.jar Talk-1.2.bat lib/jssc-2.8.0.jar 
