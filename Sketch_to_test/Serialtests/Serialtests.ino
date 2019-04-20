
int BAUDRATE=9600;
char buf[80];

void setup() {
Serial.begin(BAUDRATE);
}

int readline(int readch, char *buffer, int len) {
    static int pos = 0;
    int rpos;
          //   Serial.print(buffer[pos]);
            // Serial.print("OLA...");

    if (readch > 0) {
             //  Serial.print(String(readch));

        switch (readch) {
            case '\r': // Ignore CR
                break;
            case '\n': // Return on new-line
              //  buffer[pos++] = readch;
               // buffer[pos] = 0;
                rpos = pos;
                pos = 0;  // Reset position index ready for next time
               // Serial.print('\n');
                return rpos;
                
            default:
                if (pos < len-1) {
                    buffer[pos++] = readch;
                    buffer[pos] = 0;
                }
        }
    }
    return 0;
}

void loop() {
if (readline(Serial.read(), buf, 80) > 0) {
         String str=String(buf);
      if(str=="A"){
      
             Serial.println("Ricevuto_A");

      }else if(str=="B"){
               Serial.println("Ricevuto_B");
      }else if(str=="C"){
              Serial.println("Ricevuto_C");
      }else if(str=="D"){
              Serial.println("Ricevuto_C");
      }else{
              Serial.println("RICEVUTO... :"+str);
     }
 
     
    }

/************************************************/
      delay(10);                      
}


