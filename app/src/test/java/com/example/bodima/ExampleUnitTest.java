package com.example.bodima;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
//    @Test
//    public void addition_isCorrect() {
//        assertEquals(4, 2 + 2);
//    }
//
//

    private Expenses_Dashboard expenses_dashboard;

    @Before
    public void setup() {
                expenses_dashboard= new Expenses_Dashboard();

    }
    @Test
    public  void  getExpense_test(){
        float total=10;
        float ex_total=5;
        float expected= 5;


       float  actualResult =   expenses_dashboard.getDifference(total,ex_total);
       Assert.assertEquals(expected,actualResult,0.001);
;
    }

    @Test
    public  void  getotalRevenue_test(){

        float amount =5;
        float expected= 5; //Assume total as 0


        float  actualResult =   expenses_dashboard.getTotalRevanue(amount);
        Assert.assertEquals(expected,actualResult,0.001);

    }


}