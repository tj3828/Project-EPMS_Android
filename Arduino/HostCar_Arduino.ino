#include <SoftwareSerial.h>

// SoftwareSerial(RX, TX)
SoftwareSerial BTSerial(2, 3);    // RX, TX를 각각 2번핀과 3번핀에 연결

byte rcvByte;

void setup(){
  Serial.begin(9600);
  BTSerial.begin(9600);
}
 
void loop(){
  rcvByte = 'a';
  Serial.write(rcvByte);           // 'a'를 문자를 보낸다.
  BTSerial.write(Serial.read());

   delay(1000);
 }

