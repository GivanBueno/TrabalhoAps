package net.viamais.serial;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class SerialComLeitura implements Runnable, SerialPortEventListener
{
    
    public String Dadoslidos;
    public int noteBytes;
    private int baudrate;
    private int timeout;
    private CommPortIdentifier cp;
    private SerialPort porta;
    private OutputStream saida;
    private InputStream entrada;
    private Thread threadLeitura;
    private boolean IDPortaOK;
    private boolean PortaOK;
    private boolean Leitura;
    private boolean Escrita;
    private String Porta;
    protected String peso;

    public String getPeso()
    {
        return this.peso;
    }

    public void setPeso(String peso)
    {
        this.peso = peso;
    }

    public SerialComLeitura( String p, int b, int t)
    {
        this.Porta = p;
        this.baudrate = b;
        this.timeout = t;
    }
    
    public void HabilitarEscrita()
    {
        this.Escrita = true;
        this.Leitura = false;
    }
    
    public void HabilitarLeitura()
    {
        this.Escrita = false;
        this.Leitura = true;
    }
    
    public void ObterIdDAPorta()
    {
        try
        {
            this.cp = CommPortIdentifier.getPortIdentifier(Porta);
            if (this.cp == null)
            {
                System.out.println("Erro na porta");
                this.IDPortaOK = false;
                System.exit(1);
            }
            this.IDPortaOK = true;
        }
        
        catch(Exception e)
        {
            System.out.println("Erro obtendo ID da porta:" + e);
            this.IDPortaOK = false;
            System.exit(1);
        }
    }
    
    public void AbrirPortas()
    {
        try
        {
            this.porta = (SerialPort)cp.open("SerialComLeitura",timeout);
            this.PortaOK = true;
            this.porta.setSerialPortParams(baudrate, this.porta.DATABITS_8, this.porta.STOPBITS_1, this.porta.PARITY_NONE );
            this.porta.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);
        }
        
        catch(Exception e)
        {
            this.PortaOK = false;
            System.out.println("Erro abrindo comunicação:" + e);
            System.exit(1);
        }
    }    
    
    public void LerDados()
    {
        if(this.Escrita==false)
        {
            try
            {
                this.entrada = this.porta.getInputStream();
            }
            catch(Exception e)
            {
                System.out.println("Erro de stream:" + e);
            }
            try
            {
                this.porta.addEventListener(this);
            }
            catch(Exception e)
            {
                System.out.println("Erro de listener:" + e);
                System.exit(1);
            }
            this.porta.notifyOnDataAvailable(true);
            try
            {
                this.threadLeitura = new Thread(this);
                this.threadLeitura.start();
                run();
            }
            catch(Exception e)
            {
                    System.out.println("Erro de Thred:" + e);
            }
            
            
        }
    }
    
    public void EnviarUmaString(String msg)
    {
        if (this.Escrita==true)
        {
            try
            {
                this.saida = this.porta.getOutputStream();
                System.out.println("FLUXO OK!");
            }
            catch(Exception e)
            {
                System.out.println("Erro.STATUS:" + e);
            }
            
            try
            {
                System.out.println("Enviando um byte para" + Porta);
                System.out.println("Enviando:" + msg);
                this.saida.write(msg.getBytes());
                Thread.sleep(100);
                this.saida.flush();
            }
            catch(Exception e)
            {
                System.out.println("Houve um erro durante o envio.");
                System.out.println("STATUS:" + e);
                System.exit(1);
            }
        }
        
        else
        {
            System.exit(1);
        }
    }

    
    @Override
    public void run()
    {
       try{
            Thread.sleep(5);
        }
        catch(Exception e)
        {
            System.out.println("Erro de Thread:" + e);
        }
    }

    @Override
    public void serialEvent(SerialPortEvent ev)
    {
        StringBuffer bufferLeitura = new StringBuffer();
        int novoDado = 0;
        
        switch (ev.getEventType())
        {
            case SerialPortEvent.BI:
            case SerialPortEvent.OE:
            case SerialPortEvent.FE:
            case SerialPortEvent.PE:
            case SerialPortEvent.CD:
            case SerialPortEvent.CTS:
            case SerialPortEvent.DSR:
            case SerialPortEvent.RI:
            case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
            break;
            case SerialPortEvent.DATA_AVAILABLE:
                while (novoDado != -1)
                {
                    try
                    {
                        novoDado = this.entrada.read();
                        if(novoDado == -1)
                        {
                            break;
                        }
                        
                        if('\r' == (char)novoDado)
                        {
                            bufferLeitura.append('\n');
                        }
                        else
                        {
                            bufferLeitura.append((char)novoDado);
                        }
                    }
                    
                    catch (IOException ioe)
                    {
                        System.out.println("Erro de leitura serial:" + ioe);
                    }
                    
                }
                
                setPeso(new String(bufferLeitura));
                System.out.println(getPeso());
                break;
        }
    }
    
    public void FecharCom()
    {
        try
        {
            this.porta.close();
        }
        catch(Exception e)
        {
            System.out.println("Erro fechando porta:" + e);
            System.exit(0);
        }
    }
    
    public String obterPorta()
    {
        return this.Porta;
    }
    
    public int obterBaudrate()
    {
        return this.baudrate;
    }
}
