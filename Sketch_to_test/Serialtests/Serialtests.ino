
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
int ON=0;
int OFF=1;
int Switch_1=1;
int Trimmer1=50;
int X=0;
int M=1;
void loop() {
 
if (readline(Serial.read(), buf, 80) > 0) {
         String str=String(buf);
      if(str=="Switch_1-ON"){
         Switch_1=ON;

      }else if(str=="Switch_1-OFF"){
                  Switch_1=OFF;
      }else if(str.startsWith("TRIM1:",0)){
                  String a=str.substring(6);
                  int H=a.toInt();
                  Trimmer1=((H<0||H>100)?Trimmer1:H);
      } else if(str=="A"){
                  M=1;
      }  else if(str=="B"){
                  M=2;
      } else if(str=="C"){
                  M=3;
      }    
    }

if(Switch_1==ON){
  Serial.println(X);
}
X=(X<1000?++X:0);
/************************************************/
      delay(Trimmer1*M+10);                      
}
