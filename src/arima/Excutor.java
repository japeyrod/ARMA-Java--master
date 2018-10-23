package arima;

import java.util.ArrayList;
import java.util.List;

public class Excutor {
    public int excu(List<Long> arraylist){
        System.out.println("input:" + arraylist);
        double[] dataArray=new double[arraylist.size()-1];
        for(int i=0;i<arraylist.size()-1;i++)
            dataArray[i] = Double.parseDouble(arraylist.get(i).toString());

        ARIMAModel arima = new ARIMAModel(dataArray);

        ArrayList<int []> list = new ArrayList<>();
        int period = 1;
        int modelCnt = 5, cnt = 0;			//通过多次预测的平均值作为预测值
        int [] tmpPredict = new int [modelCnt];
        for (int k = 0; k < modelCnt; ++k)			//控制通过多少组参数进行计算最终的结果
        {
            int [] bestModel = arima.getARIMAModel(period, list, (k == 0) ? false : true);
            if (bestModel.length == 0)
            {
                tmpPredict[k] = (int)dataArray[dataArray.length - period];
                cnt++;
                break;
            }
            else
            {
                int predictDiff = arima.predictValue(bestModel[0], bestModel[1], period);
                tmpPredict[k] = arima.aftDeal(predictDiff, period);
                cnt++;
            }
//                System.out.println("BestModel is " + bestModel[0] + " " + bestModel[1]);
            list.add(bestModel);
        }
        double sumPredict = 0.0;
        for (int k = 0; k < cnt; ++k)
        {
            sumPredict += (double)tmpPredict[k] / (double)cnt;
        }
        int predict = (int)Math.round(sumPredict);
        System.out.println("Predict value="+predict);

//            System.out.println("Best model is [p,q]="+"["+model[0]+" "+model[1]+"]");
//            System.out.println("Predict value="+arima.aftDeal(arima.predictValue(model[0],model[1])));
//            System.out.println("Predict error="+(arima.aftDeal(arima.predictValue(model[0],model[1]))-arraylist.get(arraylist.size()-1))/arraylist.get(arraylist.size()-1)*100+"%");

        //	String[] str = (String[])list1.toArray(new String[0]);
        return predict;
    }
}
