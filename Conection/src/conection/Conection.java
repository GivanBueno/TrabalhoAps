package conection;

import net.viamais.serial.SerialCom;
import net.viamais.serial.SerialComLeitura;


public class Conection extends SerialCom
{


    public static void main(String[] args) 
    {
        
        SerialComLeitura leitura = new SerialComLeitura("COM3", 9600, 0);
        leitura.HabilitarLeitura();
        leitura.ObterIdDAPorta();
        leitura.AbrirPortas();
        leitura.LerDados();
  
        
        
        try
        {
            Thread.sleep(600000);
      
        }
        catch(InterruptedException ex)
        {
            System.out.println("Erro na Thread:" + ex);
        }
        
        leitura.FecharCom();
        
        
    }
    

}
