/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ysaverisetli;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.learning.MomentumBackpropagation;
import org.neuroph.util.TransferFunctionType;

/**
 * 
 * 
 *
 * @author User
 */
public class classYSA {
    private static final File dataTXT=new File(YSAverisetli.class.getResource("data.txt").getPath());
    private static final File egitimDosya=new File(YSAverisetli.class.getResource("Egitim.txt").getPath());
    private static final File testDosya=new File(YSAverisetli.class.getResource("Test.txt").getPath());
    
    private double[] maksimumlar;
    private double[] minimumlar;
    private DataSet egitimVeriSeti;
    private DataSet testVeriSeti;
    private int araKatmanNoron;
    MomentumBackpropagation bp;
    
    public classYSA(int araKatmanNoron,double momentum,double ok,double error,int epoch) throws FileNotFoundException{
        maksimumlar=new double[17];
        minimumlar=new double[17];
        for (int i = 0; i < 17; i++) {
            maksimumlar[i]=Double.MIN_VALUE;
            minimumlar[i]=Double.MAX_VALUE;
        }
        EgitimSetiMaks();
        TestVeriSetiMaks();
        egitimVeriSeti=EgitimVeriSeti();
        testVeriSeti=TestVeriSeti();
        bp=new MomentumBackpropagation();
        bp.setMomentum(momentum);
        bp.setLearningRate(ok);
        bp.setMaxError(error);
        bp.setMaxIterations(epoch);//EPOCH ASLINDA
        this.araKatmanNoron=araKatmanNoron;
    }
    
    public void egit(){
        MultiLayerPerceptron sinirselAg=new MultiLayerPerceptron(TransferFunctionType.SIGMOID,17,araKatmanNoron,3);
        sinirselAg.setLearningRule(bp);
        sinirselAg.learn(egitimVeriSeti);
        sinirselAg.save("ag.nnet");
        System.out.println("Egitim Tamamlandi");
    }
    double mse(double[] beklenen,double[] cikti){
        double satirHAta=0;
        for (int i = 0; i < 3; i++) satirHAta+=Math.pow(beklenen[i]-cikti[i],2);
        return satirHAta/3;
    }
    public double test(){
        NeuralNetwork sinirselAg=NeuralNetwork.createFromFile("ag.nnet");
        double toplamHata=0;
        for (DataSetRow r: testVeriSeti) {
            sinirselAg.setInput(r.getInput());
            sinirselAg.calculate();
            toplamHata+=mse(r.getDesiredOutput(),sinirselAg.getOutput());
        }
        return toplamHata/testVeriSeti.size();
    }
    public String sonuc(double[]outputs){
        int indeks=0;
        double maks=outputs[0];
        if(outputs[1]>maks){
            maks=outputs[1];
            indeks=1;
        }
        if(outputs[2]>maks){
            maks=outputs[2];
            indeks=2;
        }
        if(indeks==0) return "Kotu";
        else if(indeks==1) return "Normal";
        else return "Iyi";
    }
    public String tekTest(double[] inputs) {
        for (int i = 0; i < 17; i++) {
            inputs[i]=min_max(maksimumlar[i], minimumlar[i], inputs[i]);
        }
        NeuralNetwork sinirselAg=NeuralNetwork.createFromFile("ag.nnet");
        sinirselAg.setInput(inputs);
        sinirselAg.calculate();
        return sonuc(sinirselAg.getOutput());
    }
    public double egitimHata(){
        return bp.getTotalNetworkError();
    }
    public DataSet getEgitimVeriSeti(){
        return egitimVeriSeti;
    }
    public DataSet getTestVeriSeti(){
        return testVeriSeti;
    }
    private void EgitimSetiMaks() throws FileNotFoundException {
        Scanner oku = new Scanner(egitimDosya);
        while (oku.hasNextDouble()) {
            for (int i = 0; i < 17; i++) {
                double d=oku.nextDouble();
                if(d > maksimumlar[i]) maksimumlar[i]=d;
                if(d < minimumlar[i]) minimumlar[i]=d;
            }
            oku.nextDouble();oku.nextDouble();oku.nextDouble();
        }
        oku.close();
    }

    private void TestVeriSetiMaks() throws FileNotFoundException {
         Scanner oku = new Scanner(testDosya);
        while (oku.hasNextDouble()) {
            for (int i = 0; i < 17; i++) {
                double d=oku.nextDouble();
                if(d > maksimumlar[i]) maksimumlar[i]=d;
                if(d < minimumlar[i]) minimumlar[i]=d;
            }
            oku.nextDouble();oku.nextDouble();oku.nextDouble();
        }
        oku.close();
    }
    private double min_max(double max,double min,double x){
        return (x-min)/(max-min);
    }
    private DataSet EgitimVeriSeti() throws FileNotFoundException {
        Scanner oku=new Scanner(egitimDosya);
        DataSet egitim=new DataSet(17, 3);
        while(oku.hasNextDouble()){
            double[] inputs =new double[17];
            for (int i = 0; i < 17; i++) {
                double d=oku.nextDouble();
                inputs[i]=min_max(maksimumlar[i], minimumlar[i], d);
            }
            DataSetRow satir =new DataSetRow(inputs,new double[]{oku.nextDouble(),oku.nextDouble(),oku.nextDouble()});
            egitim.add(satir);
        }
        oku.close();
        return egitim;
    }

    private DataSet TestVeriSeti() throws FileNotFoundException {
        Scanner oku=new Scanner(testDosya);
        DataSet test=new DataSet(17, 3);
        while(oku.hasNextDouble()){
            double[] inputs =new double[17];
            for (int i = 0; i < 17; i++) {
                double d=oku.nextDouble();
                inputs[i]=min_max(maksimumlar[i], minimumlar[i], d);
            }
            DataSetRow satir =new DataSetRow(inputs,new double[]{oku.nextDouble(),oku.nextDouble(),oku.nextDouble()});
            test.add(satir);
        }
        oku.close();
        return test;
    }
}
