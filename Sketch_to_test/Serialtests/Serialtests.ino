
int BAUDRATE=9600;
int count=0;

void setup() {
Serial.begin(BAUDRATE);
}

void loop() {



count++;

  if ( Serial.available() > 0 ) {
    // Read the incoming byte
    char theChar = Serial.read();
    String str=Serial.readString();
      // Parse character
    switch (theChar) {
      case 'A':     
       Serial.println("Ricevuto A");
        break;
      case 'B':      
       Serial.println("Ricevuto B");
       
        break;
      case 'C':   
       Serial.println("Ricevuto C");
        break;

      case 'D': 
             Serial.println("Ricevuto D");
          break;

    }
                 Serial.println(str);

  }
      delay(1000);                      
   
   Serial.println("Ciao. ");

 if(count==100) 
      count=0;
}
