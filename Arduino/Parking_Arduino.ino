#include <Servo.h>
#include <SoftwareSerial.h>

Servo myservo;
SoftwareSerial BTSerial(2, 3); 

byte rcvByte;
String myString=""; //받는 문자열
 
int min_angle = 0; //최소 0도
int max_angle = 90;//최대 90도
int pos = 0;       //초기 각도 0도
int pinNum = A0;   // A0핀에 연결
int distance = 0; 

void setup(){
  BTSerial.begin(9600);
  Serial.begin(9600);
  pinMode(pinNum, INPUT);
  myservo.attach(9);  //서보모터는 아두이노의 9핀에 연결
}

void loop(){
  int data = analogRead(pinNum);
  int volt = map(data, 0, 1023, 0, 5000);
  int distance = 0;
  distance = (21.61/(volt-0.1696))*1000;
  Serial.println(distance);
  myservo.write(pos);

 if(BTSerial.available()){                    // 블루투스가 켜져있을 때                  
   rcvByte = BTSerial.read();                 // 블루투스에서 신호 read
    if(rcvByte =='a' && distance<15){         // 'a'문자와 센서의 거리가 15미만
        pos = 0;    }                        
    else if(rcvByte =='a' && distance>=15){   // 'a'문자와 센서의 거리가 15이상 
        pos = 90;   }                         
      
    else if(rcvByte =='o'){                   // 'o' 문자가 입력되었을 때
        pos = 90;   }                        
    else if(rcvByte =='x'){                   // 'x' 문자가 입력되었을 때
        pos = 0;    }                         
    else if(rcvByte !='x' && distance<15) {   // 'x' 문자가 아니고 거리가 15 미만일 때  
        pos = 0;    }                         
    else if(rcvByte !='o' && distance>=15) {  // 'o'문자가 아니고 거리가 15 이상일 때 
        pos = 90;   }                        
 }else ;
  delay(500);                                 // 0.5초 주기로 검사하여 실행                           
}
