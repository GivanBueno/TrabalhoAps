#include "DHT.h"

#define DHTPIN A1 // pino que estamos conectado
#define DHTTYPE DHT11 // DHT 11

int pino_d = 2; //Pino ligado ao D0 do sensor
int pino_a = A5; //Pino ligado ao A0 do sensor
int val_d = 0; //Armazena o valor lido do pino digital
int val_a = 0; //Armazena o valor lido do pino analogico


DHT dht(DHTPIN, DHTTYPE);


void setup() {

  pinMode(pino_d, INPUT);
  pinMode(pino_a, INPUT);
  Serial.begin(9600);
  dht.begin();
}

void loop() {

  float h = dht.readHumidity();
  float t = dht.readTemperature();
  val_d = digitalRead(pino_d);
  val_a = analogRead(pino_a);
  
  if (isnan(t) || isnan(h)) 
  {
    Serial.println("Falha de leitura dos Sensores");
  } 
  else 
  {

    Serial.print("Temperatura: ");
    Serial.print(t);
    Serial.print(" *C");
    Serial.print(" \t");
    Serial.print("  Umidade: ");
    Serial.print(h);
    Serial.print(" %");
    Serial.print(" \t");
    Serial.print("Precipitação: ");
    Serial.print(val_a);
    Serial.print(" \t");
    Serial.print("Intensidade: ");
    
    if (val_a >900 && val_a <1024)
  {
    Serial.println("Baixa");
  }
  if (val_a >400 && val_a <900)
  {
    Serial.println("Moderada");
  }
  if (val_a > 0 && val_a <=400)
  {
    Serial.println("Alta");
  }
 
    delay(5000);
  }

  

}
