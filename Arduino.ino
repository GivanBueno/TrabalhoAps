//========================================================
// VARIÁVEIS
float temperatura = 0x00, umidade = 0x00;
int precipitacao = 0;
//========================================================
// CÓDIGO
void setup() 
{


}

//========================================================
// LOOPING DOS VALORES
void loop() 
{
  
Serial.print("Valor da temperatura:");
Serial.print(temperatura);                  //Uso da variável temperatura
Serial.print(" C");
Serial.print(" /t");                        //Quebra de linha
Serial.print("Percentual de umidade:");
Serial.print(umidade);                      //Uso da variável umidade
Serial.print("%");
Serial.print(" /t");  
Serial.print("Valor da precipitação:");
Serial.print(precipitacao);
Serial.print("%");
//falta colocar as instruções ppara imprimir a pricipitação atmosférica

//delay(60000);        //Tempo de atualização dos dados de 1 minuto
//delay(1500);         //Testar esse com 1,5 segundo antes de usar o acima


}
