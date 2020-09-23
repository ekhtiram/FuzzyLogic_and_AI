/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ysaverisetli;

import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author User
 */
public class YSAverisetli {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        Scanner in=new Scanner(System.in);
        int araKatmanNoron;
        double momentum,ok,error;
        int epoch,sec;
        classYSA ysa=null;
        do{
            System.out.println("1.Ağı Eğit");
            System.out.println("2.Ağı Test Et");
            System.out.println("3.Tek Veri İle Test Et");
            System.out.println("4. Çıkış");
            System.out.println("=>");
            sec=in.nextInt();
            switch(sec){
                case 1:
                    araKatmanNoron =5;
                    momentum=0.9;
                    ok=0.1;
                    error=0.000001;
                    epoch=10000;
                    ysa=new classYSA(araKatmanNoron, momentum, ok, error, epoch);
                    ysa.egit();
                    System.out.println("Egitimdeki Hata Degeri : "+ ysa.egitimHata());
                    System.in.read();
                    break;
                case 2:
                    System.out.println("Test Verisindeki Hata Degeri : "+ ysa.test());
                    break;
                case 3:
                    if(ysa==null)
                    {
                        System.out.println("Once Egitim : ");
                        System.in.read();
                        break;
                    }
                    else {
                        double[] inputs=new double[17];
                        System.out.print("Saat (0-23): ");
                        String saat=in.next();
                        switch(saat){
                            case  "1" :
                                inputs[0]=0;
                                inputs[1]=0;
                                inputs[2]=0;
                                inputs[3]=0;                            
                                inputs[4]=1;
                                break;
                            case  "2" :
                                inputs[0]=0;
                                inputs[1]=0;
                                inputs[2]=0;
                                inputs[3]=1;                            
                                inputs[4]=0;
                                break;
                            case  "3" :
                                inputs[0]=0;
                                inputs[1]=0;
                                inputs[2]=0;
                                inputs[3]=1;                            
                                inputs[4]=1;
                                break;
                            case  "4" :
                                inputs[0]=0;
                                inputs[1]=0;
                                inputs[2]=1;
                                inputs[3]=0;                            
                                inputs[4]=0;
                                break;
                            case  "5" :
                                inputs[0]=0;
                                inputs[1]=0;
                                inputs[2]=1;
                                inputs[3]=0;                            
                                inputs[4]=1;
                                break;
                            case  "6" :
                                inputs[0]=0;
                                inputs[1]=0;
                                inputs[2]=1;
                                inputs[3]=1;                            
                                inputs[4]=0;
                                break;
                            case  "7" :
                                inputs[0]=0;
                                inputs[1]=0;
                                inputs[2]=1;
                                inputs[3]=1;                            
                                inputs[4]=1;
                                break;
                            case  "8" :
                                inputs[0]=0;
                                inputs[1]=1;
                                inputs[2]=0;
                                inputs[3]=0;                            
                                inputs[4]=0;
                                break;
                            case  "9" :
                                inputs[0]=0;
                                inputs[1]=1;
                                inputs[2]=0;
                                inputs[3]=0;                            
                                inputs[4]=1;
                                break;
                            case  "0" :
                                inputs[0]=0;
                                inputs[1]=0;
                                inputs[2]=0;
                                inputs[3]=0;                            
                                inputs[4]=0;
                                break;
                        }
                        System.out.print("Dakika (79-400) : ");
                        inputs[1]=in.nextDouble();
                        System.out.print("Saniye (60-175) : ");
                        inputs[2]=in.nextDouble();
                        System.out.println("Yakit : "+ysa.tekTest(inputs));
                        System.in.read();
                    }
                    break;
                   
            }

        }while(sec!=3);
    }
    
}
